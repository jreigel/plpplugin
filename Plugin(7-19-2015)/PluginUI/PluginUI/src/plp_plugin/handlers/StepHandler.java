package plp_plugin.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 * 
 * 
 */
public class StepHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public StepHandler() {
	}

	/**
	 * This is called when the command is executed.  When the command is
	 * executed, a message is sent to the simulation process (a '1' in this case)
	 * which tells it to step the simulation once.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		//First, get the socket used to send messages to the simulation process,
		//then setup a PrintWriter with this socket and print a "stepOnce" to 
		//signal the simulation process to step the simulation once.
		Socket toSocket = plp_plugin.launcher.PLPLaunchSockets.toSim;
		PrintWriter out = null;
		try {
			out = new PrintWriter(toSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println("stepOnce");//The signal to step the simulation once.
		
		return null;
	}
}
