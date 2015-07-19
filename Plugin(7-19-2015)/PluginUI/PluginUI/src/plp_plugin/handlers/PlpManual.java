package plp_plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.program.Program;

/**
 * Handler to lead users to the online PLP manual
 *  extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * 
 * @author Reigel, Justin
 */
public class PlpManual extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public PlpManual() {
	}

	/**
	 * Opens the url for the online plp manual after the command is executed.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Program.launch("https://code.google.com/p/progressive-learning-platform/wiki/UserManual");
		return null;
	}
}
