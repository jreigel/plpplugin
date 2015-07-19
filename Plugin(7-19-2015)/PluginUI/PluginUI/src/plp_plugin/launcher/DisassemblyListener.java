package plp_plugin.launcher;

/**
 * Classes interested in being notified when the disassembly data
 * is updated should implement this interface.
 * 
 * @author Cameron Bartee
 */
public interface DisassemblyListener {
	public void performAction();
}
