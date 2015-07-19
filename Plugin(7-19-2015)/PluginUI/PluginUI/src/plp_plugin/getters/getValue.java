package plp_plugin.getters;

import plp_plugin.launcher.RegistersDataHolder;

/**
 * This class is a stub intended for testing use only.
 * It provides dummy register and bus values.
 * 
 * @author Cameron Bartee
 */
public class getValue {
	
	public static long getRegisterValue(String register){
		long result = 0;
		switch(register) {
			case "$t0":
				result = RegistersDataHolder.registers[8];
				break;
			case "$t1":
				result = RegistersDataHolder.registers[9];
				break;
			case "$s0":
				result = RegistersDataHolder.registers[18];
				break;
		}
		return result;
	}
	
	public static long getBusValue(long addr){
		long result = 0;
		
		if(addr == 0x10000000)
			result = 99;
		else if(addr == 0x10000004)
			result = 102;
		else if(addr == 0x10000008)
			result = 1773;
		else if(addr == 0x1000000c)
			result = 555;
		else if(addr == 0x10000010)
			result = 1;
		
		return result;
	}

}
