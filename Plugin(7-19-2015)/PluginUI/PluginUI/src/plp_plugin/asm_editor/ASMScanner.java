package plp_plugin.asm_editor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import plp_plugin.Activator;
import plp_plugin.preferences.PreferenceConstants;

/**
 * Okay, here we have the "meat-and-potatoes" of our custom PLP asm Eclipse
 * editor: the ASMScanner.  This is where we define the rules that the editor
 * uses to color our syntax.
 * 
 * @author Cameron Bartee
 */
public class ASMScanner extends RuleBasedScanner {
	
	//Here we define all the colors that our syntax highlighting will use:
	
	/**
	 * This is used with multiRule down below (added as rule #3)
	 * to help it detect instructions and nop.  Basically it
	 * just defines the fact that it is the end of the word if
	 * it encounters whitespace or a comment tag (#).
	 * 
	 * @author Cameron Bartee
	 */
	private class BasicWordDetector implements IWordDetector {
		
		@Override
		public boolean isWordStart(char c) {
			return isWordPart(c);
		}

		@Override
		public boolean isWordPart(char c) {
			boolean isWhitespace = Character.isWhitespace(c);
			boolean isCommentTag = (c == '#');
			return (!isWhitespace) && (!isCommentTag);
		}
		
	}
	
	/**
	 * <p>This custom rule was created because of a difficult 
	 * situation in differentiating "number" tokens from 
	 * "register w/ an offset" tokens.  In PLP assembly code, 
	 * a number will look something like "123", "0x00f0", or 
	 * "0b11011". We want numbers like this to be colored orange   
	 * in the editor, and as you can see, we can identify them by 
	 * the fact that they all begin w/ a digit.</p>
	 * 
	 * <p>However, registers w/ an offset (which look something 
	 * like this: "0($t5)" or "4($s3)") should be colored red, 
	 * however.  They also begin with a digit, so you see the 
	 * difficulty in determining how to color the token from 
	 * just looking at the first character alone.</p>
	 * 
	 * <p>This rule addresses this problem, and also handles 
	 * the coloring of registers w/out an offset (e.g. "$t0" 
	 * or "$s4").</p>
	 * 
	 * @author Cameron Bartee
	 */
	private class RegisterAndNumberRule implements IRule {
		
		private IToken registerToken;
		private IToken numberToken;
		
		/**
		 * @param registerToken The token used to color registers
		 * @param numberToken The token used to color numbers
		 */
		public RegisterAndNumberRule(IToken registerToken, IToken numberToken){
			this.registerToken = registerToken;
			this.numberToken = numberToken;
		}
		
		@Override
		public IToken evaluate(ICharacterScanner scanner) {
			
			int c = scanner.read();//Read in the initial character
			boolean isNumber = true;//Set to true if this token is a number, i.e.
								    //either an integer, hex value, or binary value
									//VS. a register w/ an offset
			
			//Tokens beginning with a digit or a '-' could be numbers or
			//registers with an offset.
			if(c == '-' || Character.isDigit(c)){
				while(c != ICharacterScanner.EOF && !Character.isWhitespace(c) && c != '#'){
					c = scanner.read();
					if(c == '$')//If a '$' is found, it is a register w/ an offset
						isNumber = false;
				}
				scanner.unread();
				if(isNumber)
					return numberToken;
				else
					return registerToken;
			}
			//Tokens beginning with a '$' are registers
			else if(c == '$'){
				while(c != ICharacterScanner.EOF && !Character.isWhitespace(c) && c != '#'){
					c = scanner.read();
				}
				scanner.unread();
				return registerToken;
			}
			scanner.unread();
			return Token.UNDEFINED;
		}
	}
	
	/**
	 * Here, in the constructor, is where we add all our rules
	 * to the array of rules which will then be applied in our 
	 * editor.
	 * 
	 * @author Cameron Bartee
	 */
	public ASMScanner(){
		
		//Setup all the Token types (color/text style)
		
		//Changed: colors and tokens to get from preference store. (Reigel, Justin)
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		Color comment_Color = new Color(Display.getCurrent(), PreferenceConverter.getColor(store, PreferenceConstants.P_COMMENT_COLOR));
		IToken commentToken = new Token(new TextAttribute(comment_Color));
		
		Color directive_Color = new Color(Display.getCurrent(), PreferenceConverter.getColor(store, PreferenceConstants.P_DIRECTIVE_COLOR));
		IToken directiveToken = new Token(new TextAttribute(directive_Color));
		
		Color instruction_Color = new Color(Display.getCurrent(), PreferenceConverter.getColor(store, PreferenceConstants.P_INSTRUCTION_COLOR));
		IToken instructionToken = new Token(new TextAttribute(instruction_Color));
		
		Color nop_Color = new Color(Display.getCurrent(), PreferenceConverter.getColor(store, PreferenceConstants.P_NOP_COLOR));
		IToken nopToken = new Token(new TextAttribute(nop_Color));
		
		Color label_Color = new Color(Display.getCurrent(), PreferenceConverter.getColor(store, PreferenceConstants.P_LABEL_COLOR));
		IToken labelToken = new Token(new TextAttribute(label_Color, null, SWT.BOLD));
		
		Color register_Color = new Color(Display.getCurrent(), PreferenceConverter.getColor(store, PreferenceConstants.P_REGISTER_COLOR));
		IToken registerToken = new Token(new TextAttribute(register_Color));
		
		Color number_Color = new Color(Display.getCurrent(), PreferenceConverter.getColor(store, PreferenceConstants.P_NUMBER_COLOR));
		IToken numberToken = new Token(new TextAttribute(number_Color));
		
		//Now begin adding the rules:
		IRule[] rules = new IRule[4];
//-------------------------------------------------------------
		//Rule for highlighting comment tags green.
		rules[0] = (new EndOfLineRule("#", commentToken));
//-------------------------------------------------------------
		//This rule handles the highlighting purple of any assembler
		//directives (they always begin with a '.')
		rules[1] = (new WordRule(new IWordDetector() {
			@Override
			public boolean isWordStart(char c) {
				boolean result = false;
				if(c == '.')
					result = true;
				return result;
			}
			@Override
			public boolean isWordPart(char c) {
				boolean result = true;
				if(c == '#')
					result = false;
				else if(c == 10)
					result = false;
				else if(c == 13)
					result = false;
				return result;
			}
		}, directiveToken));
//-------------------------------------------------------------	
		//RegisterAndNumberRule handles the highlighting of
		//numbers orange (e.g. "125", "0", "0xff00", and "0b0101")
		//and registers red (e.g. "$t0", "$s0", and "0($t0)")
		rules[2] = (new RegisterAndNumberRule(registerToken, numberToken));
//-------------------------------------------------------------	
		String[] instructions = new String[] { "addu", "subu", "and", "or",
				"nor", "mullo", "mulhi", "slt", "sltu", "sll", "srl", "sllv",
				"srlv", "jr", "jalr",
				"addiu", "andi", "ori", "slti", "sltiu", "lui", "lw", "sw",
				"beq", "bne",
				"j", "jal",
				"b", "move", "li", "push", "pop", "call", "return", "save",
				"restore", "lwm", "swm"
		};
		
		//multiRule handles the highlighting of instructions, nop, and labels.
		WordRule multiRule = new WordRule(new BasicWordDetector(), labelToken);
		
		for(String instruction : instructions)
			multiRule.addWord(instruction, instructionToken);
		
		multiRule.addWord("nop", nopToken);
		
		rules[3] = (multiRule);
//-------------------------------------------------------------
		setRules(rules);
	}

}
