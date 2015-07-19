package plp_plugin.launcher;

/**
 * Classes interested in being notified when the registers 
 * contents data is updated should implement this interface.
 * 
 * @author Cameron Bartee
 */
public interface RegistersListener {
	public void performAction();
}
