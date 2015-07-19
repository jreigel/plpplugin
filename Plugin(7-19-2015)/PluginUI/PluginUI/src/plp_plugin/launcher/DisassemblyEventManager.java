package plp_plugin.launcher;

import java.util.ArrayList;

/**
 * This is an event manager that can be used to trigger
 * an event on all its registered listeners.  Here, it
 * is being used to notifiy listeners interested in the
 * disassembly data that the disassembly data has been 
 * updated and that they should read it in for display.
 * 
 * For this class in particular, listeners must implement
 * the DisassemblyListener interface and its one method,
 * perform action.
 * 
 * @author Cameron Bartee
 */
public class DisassemblyEventManager {
	
	public ArrayList<DisassemblyListener> listeners;
	
	public DisassemblyEventManager(){
		listeners = new ArrayList<DisassemblyListener>();
	}
	
	public void addListener(DisassemblyListener listener){
		listeners.add(listener);
	}
	
	public void fireEvent(){
		for(DisassemblyListener listener : listeners)
			listener.performAction();
	}

}
