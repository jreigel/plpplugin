package plp_plugin.launcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class exists to hold the ServerSockets and Sockets
 * which are connected to the simulation process so that
 * other classes and methods may access them.
 * 
 * It also contains a method which allows for the addition of 
 * a shutdown hook which closes all the Sockets.
 * 
 * @author Cameron Bartee
 */
public class PLPLaunchSockets {

	public static ServerSocket serverSocketTo;
	public static ServerSocket serverSocketFrom;
	public static Socket toSim;
	public static Socket fromSim;
	
	/**
	 * Whatever calls this method will add a shutdown hook that safely
	 * closes the all the ServerSockets and Sockets.
	 * 
	 * Currently, at the time of this writing, it is called in the 
	 * plp_plugin.Activator class, in the method stop().
	 */
	public static void closeSockets(){
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	        	try {
	        		if(serverSocketTo != null)
	        			serverSocketTo.close();
	        		if(serverSocketFrom != null)
	        			serverSocketFrom.close();
	        		if(toSim != null)
	        			toSim.close();
	        		if(fromSim != null)
	        			fromSim.close();
	    		} catch (IOException e) {
	    			System.out.println("Error closing sockets!");
	    			e.printStackTrace();
	    		}
	        }
	    }, "Shutdown-thread"));
	}
}
