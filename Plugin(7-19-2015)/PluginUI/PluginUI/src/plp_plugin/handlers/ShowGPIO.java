package plp_plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Handler to show the GPIO view  extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * 
 * @author Reigel, Justin
 */
public class ShowGPIO extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ShowGPIO() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	//TODO: Create GPIO view and link this event to the view (see ShowIOPanel or ShowUART for implementation examples.)
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"TestProject5",
				"Placeholder text for the GPIO");
		return null;
	}
}
