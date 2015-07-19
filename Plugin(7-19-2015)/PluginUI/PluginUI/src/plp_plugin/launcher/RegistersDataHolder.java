package plp_plugin.launcher;


/**
 * This class holds the register contents data acquired from the simulation process
 * for printing here, on the Eclipse side.
 * 
 * @author Cameron Bartee
 */
public class RegistersDataHolder {

	/* Here the register contents data is stored.  We know
	 * that there are exactly 32 registers, and so we can
	 * use an array of Long with 32 slots to store them.
	 */
	public static Long[] registers = new Long[32];
	
	/* Here we initialize the labels for the registers. They never change,
	 * and so we can just instantiate them here and not worry about reading
	 * them in or updating them.
	 */
	public static String[] registerLabels = { "0: $zero", "1: $at", "2: $v0",
			"3: $v1", "4: $a0", "5: $a1", "6: $a2", "7: $a3", "8: $t0",
			"9: $t1", "10: $t2", "11: $t3", "12: $t4", "13: $t5", "14: $t6",
			"15: $t7", "16: $t8", "17: $t9", "18: $s0", "19: $s1", "20: $s2",
			"21: $s3", "22: $s4", "23: $s5", "24: $s6", "25: $s7", "26: $i0",
			"27: $i1", "28: $iv", "29: $sp", "30: $ir", "31: $ra" };
	
	/* This is a means of letting the registers view know whether the data
	 * stored in this class has been updated since the view had last read it
	 * in.  There may be flaws with using this method, particularly if multiple
	 * views wind up being interested in the registers contents data.
	 */
	public static boolean updated = false;

}
