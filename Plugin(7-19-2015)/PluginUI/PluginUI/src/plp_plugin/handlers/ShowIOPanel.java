package plp_plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * Handler to show the IOPanel view  extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * 
 * @author Reigel, Justin
 */
public class ShowIOPanel extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ShowIOPanel() {
	}

	/**
	 * Opens the IO panel
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("PluginUI1.iopanel");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}
}
