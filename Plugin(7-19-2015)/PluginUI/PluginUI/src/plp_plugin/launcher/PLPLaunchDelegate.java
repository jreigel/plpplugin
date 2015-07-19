package plp_plugin.launcher;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.RuntimeProcess;

/**
 * IMPORTANT: IF LAUNCHING ISN'T WORKING FOR YOU, TAKE A LOOK AT THE
 * LINES MARKED "TODO HARD CODED BELOW" AND ADJUST THE PATH THERE TO
 * POINT TO THE CORRECT DESTINATION.
 * 
 * This is the class that actually launches the simulation process.
 * It sets up the sockets that will be used for communication between
 * the simulation process and Eclipse; finds the simulation process code,
 * runs it as a new process, and then adds this process to the launch;
 * and finally it starts a new thread running in Eclipse that listens for
 * incoming messages from the simulation process.
 * 
 * @author Cameron Bartee
 */
public class PLPLaunchDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		
		System.out.println("Begin Launching!");
		
		//Here, we establish all the variables we will
		//be using:
		
		//Process to hold the simulation process, and 
		//a ProcessBuilder to assist in creating it:
		Process p = null;
		ProcessBuilder pb = null;
		
		//The ports we will be using to send data
		//to and from the simulation process, and
		//all the Sockets and ServerSockets that
		//Go along with that:
		Integer portTo = findFreePort();
		Integer portFrom = findFreePort();
		ServerSocket serverSocketTo = null;
		ServerSocket serverSocketFrom = null;
		Socket toSim = null;
		Socket fromSim = null;
		
		//Here we instantiate the ServerSockets:
		try {
			serverSocketTo = new ServerSocket(portTo);
			serverSocketFrom = new ServerSocket(portFrom);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Here we build the simulation process and then start it:
		pb = new ProcessBuilder("java", "sim.RunSimCore1", portTo.toString(), portFrom.toString());//TODO HARD CODED
		pb.directory(new File("C:\\J\\Summer2015Work\\PluginSim1\\PluginSim1\\bin\\"));//TODO HARD CODED PATH
		try {
			p = pb.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Here we connect the Sockets on the Eclipse side
		//to the Sockets in our newly-launched simulation
		//process:
		try {
			toSim = serverSocketTo.accept();
			fromSim = serverSocketFrom.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Now, we store all the ServerSockets and Sockets which are
		//now connected to the simulation process as static variables
		//in class PLPLaunchSockets so that we can access them in other
		//classes:
		PLPLaunchSockets.serverSocketTo = serverSocketTo;
		PLPLaunchSockets.serverSocketFrom = serverSocketFrom;
		PLPLaunchSockets.toSim = toSim;
		PLPLaunchSockets.fromSim = fromSim;
		
		//Add the simulation process to the launch:
		new RuntimeProcess(launch, p, "plp_process", null);
		
		//Begin thread listening for update events from SimCore process:
		new Thread(new UpdateEventRunnable(), "updateEventThread").start();
		
		System.out.println("Done Launching!");
	}
	
	/**
	 * This method will find a free port and return it.
	 * 
	 * @return a free port, or -1 if something went wrong
	 */
	public static int findFreePort() {
		ServerSocket socket= null;
		try {
			socket= new ServerSocket(0);
			return socket.getLocalPort();
		} catch (IOException e) { 
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
		return -1;		
	}

}
