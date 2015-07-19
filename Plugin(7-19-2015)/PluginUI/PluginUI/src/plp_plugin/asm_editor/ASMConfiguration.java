package plp_plugin.asm_editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Coming in from ASMEditor.java, this class performs some more setup
 * activities for the PLP asm editor.
 * 
 * I mostly took the stuff in this class from a tutorial, so I can't
 * explain it in detail, but I'll do my best in the comments you'll
 * find below.
 * 
 * The most important is the method getTagScanner(), which sets up the
 * instance of ASMScanner, which is where all the syntax highlighting rules
 * get defined.
 * 
 * @author Cameron Bartee
 */
public class ASMConfiguration extends SourceViewerConfiguration {
	
	/* I believe that this is setting up the default font/text color that will be used
	 * if no rules are matched.  I think that the way I have it set up now this doesn't get
	 * used at all, but it's still necessary that it be here.
	 */
	private static final Color DEFAULT_TAG_COLOR = new Color(Display.getCurrent(), new RGB(0,0,0));
	//Here is the scanner that has all the syntax highlighting rules:
	private ASMScanner scanner;
	
	/* Here we return the ASMScanner if there is one, and instantiate and setup one
	 * if there isn't.  The ASMScanner is where all the syntax highlighting rules
	 * are defined.
	 */
	protected ASMScanner getTagScanner(){
		if (scanner == null){
			scanner = new ASMScanner();
			scanner.setDefaultReturnToken(new Token(new TextAttribute(DEFAULT_TAG_COLOR)));
		}
		return scanner;
	}
	
	/* I don't understand much of what this is doing, I just know that its important.
	 * If you want to read up a bit on damagers, repairers, and presentation reconcilers,
	 * here is a link:
	 * https://wiki.eclipse.org/FAQ_How_do_I_provide_syntax_coloring_in_an_editor%3F
	 * 
	 * Of particular interest is the line I've marked, which is where we setup the ASMScanner
	 * which has the syntax highlighting rules defined.
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer){
		PresentationReconciler reconciler = new PresentationReconciler();
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getTagScanner());//#####
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}
}
