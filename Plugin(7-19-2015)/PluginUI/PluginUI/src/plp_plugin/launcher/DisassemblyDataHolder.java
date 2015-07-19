package plp_plugin.launcher;

import java.util.ArrayList;

/**
 * This class holds the disassembly data acquired from the simulation process
 * for printing here, on the Eclipse side.
 * 
 * @author Cameron Bartee
 */
public class DisassemblyDataHolder {

	/* This holds the disassembly data, specifically the memory addresses that
	 * all the instructions in the PLP asm code are stored at, and the numerical
	 * value of those instructions.  Currently, it is stored such that the first
	 * item in the ArrayList is the memory address of the first instruction, the
	 * second item in the ArrayList is the first instruction, the third item is
	 * the memory address of the second instruction, the fourth item is the second
	 * instruction, and so on.
	 */
	public static ArrayList<String> data = new ArrayList<String>();
	
	/* This is a means of letting the disassembly view know whether the data
	 * stored in this class has been updated since the view had last read it
	 * in.  There may be flaws with using this method, particularly if multiple
	 * views wind up being interested in the disassembly data.
	 */
	public static boolean updated = false;
	
	//This holds the current value of the program counter in the PLP simulation.
	public static long pc;
	
}
