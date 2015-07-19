package plp_plugin.launcher;

import java.util.ArrayList;

/**
 * This is an event manager that can be used to trigger
 * an event on all its registered listeners.  Here, it
 * is being used to notifiy listeners interested in the
 * register contents data that the register contents data 
 * has been updated and that they should read it in for display.
 * 
 * For this class in particular, listeners must implement
 * the RegistersListener interface and its one method,
 * perform action.
 * 
 * @author Cameron Bartee
 */
public class RegistersEventManager {
	
public ArrayList<RegistersListener> listeners;
	
	public RegistersEventManager(){
		listeners = new ArrayList<RegistersListener>();
	}
	
	public void addListener(RegistersListener listener){
		listeners.add(listener);
	}
	
	public void fireEvent(){
		for(RegistersListener listener : listeners)
			listener.performAction();
	}

}
