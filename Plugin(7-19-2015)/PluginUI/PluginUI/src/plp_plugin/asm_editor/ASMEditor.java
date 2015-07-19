package plp_plugin.asm_editor;

import org.eclipse.ui.editors.text.TextEditor;

/**
 * This is the class that creates the PLP asm editor and
 * begins the process which sets it up with its unique
 * syntax highlighting.
 * 
 * I actually do not have a deep understanding of what exactly
 * this class does, and I do not know exactly what a 
 * "SourceViewerConfiguration" is; I mostly just followed tutorials
 * and that is why I know this class must be here and be set up in this
 * way.
 * 
 * @author Cameron Bartee
 */
public class ASMEditor extends TextEditor {

	public ASMEditor(){
		super();
		setSourceViewerConfiguration(new ASMConfiguration());//This line leads us to the file ASMConfiguration.java...
	}
}
