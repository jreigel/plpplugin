package plp_plugin.launcher;

/**
 * This is a class for defining as constants the strings that
 * the simulation process will send through a socket in order
 * to communicate what sort of data is about to be sent.
 * 
 * For example, if the simulation process has just stepped,
 * and thus wishes to send updated register values, it will first
 * send the string "registerUpdate", which will let us, the Eclispe
 * workbench, know that register data is incoming and to prepare to
 * re-direct it to the correct place (in this case RegistersDataHolder)
 * 
 * @author Cameron Bartee
 */
public class MessageConstants {
	
	public static final String REGISTER_UPDATE = "registerUpdate";
	public static final String MEMORY_UPDATE = "memoryUpdate";
	public static final String DISASSEMBLY_UPDATE = "disassemblyUpdate";

}
