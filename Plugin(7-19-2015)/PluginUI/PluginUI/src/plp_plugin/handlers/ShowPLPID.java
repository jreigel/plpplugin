package plp_plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Handler to show the PLPID view  extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * 
 * @author Reigel, Justin
 */
public class ShowPLPID extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ShowPLPID() {
	}

	/**
	 * Opens the PLPID view
	 */
	//TODO: Create PLPID view and link this event to the view (see ShowIOPanel or ShowUART for implementation examples.)
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"TestProject5",
				"Placeholder text for the PLPID");
		return null;
	}
}
