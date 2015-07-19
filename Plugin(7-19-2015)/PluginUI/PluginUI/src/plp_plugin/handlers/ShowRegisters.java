package plp_plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * Handler to show the Registers view extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * 
 * @author Reigel, Justin
 */
public class ShowRegisters extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ShowRegisters() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	//TODO: Create CPU view and link this event to the view (see ShowIOPanel or ShowUART for implementation examples.)
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView("PluginUI1.registersview");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}
}
