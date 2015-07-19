package plp_plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.program.Program;

/**
 * Handler to lead users to the appropriate url for bug reporting 
 * extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * 
 * @author Reigel, Justin
 */
public class ReportBug extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ReportBug() {
	}

	/**
	 * Opens the url to report bugs after the command is executed.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Program.launch("http://code.google.com/p/progressive-learning-platform/issues/entry");
		return null;
	}
}
