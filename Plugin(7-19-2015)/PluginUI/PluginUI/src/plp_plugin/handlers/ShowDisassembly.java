package plp_plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * Handler to show the CPU view extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * 
 * @author Reigel, Justin
 */
public class ShowDisassembly extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ShowDisassembly() {
	}

	/**
	 * Opens the disassembly view
	 */
	//TODO: Create CPU view and link this event to the view (see ShowIOPanel or ShowUART for implementation examples.)
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView("PluginUI1.disassemblyview");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}
}
