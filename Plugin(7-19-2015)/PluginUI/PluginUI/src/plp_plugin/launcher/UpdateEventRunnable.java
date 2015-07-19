package plp_plugin.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import plp_plugin.Activator;

/**
 * This Runnable listens for the simulation process to
 * send update messages, figures out what type of update
 * is being sent, and then takes and stores the data from 
 * the update in the proper location and fires an event 
 * letting all interested classes know that the data has
 * been updated.
 * 
 * This Runnable is started as as separate thread in the
 * class plp_plugin.launcher.PLPLaunchDelegate.
 * 
 * @author Cameron Bartee
 */
public class UpdateEventRunnable implements Runnable {
	
	boolean doneLooping;

	@Override
	public void run() {
		
		BufferedReader in = null;
		PrintWriter out = null;
		String input = null;
		
		//Setup BufferedReader to take input from SimCore Process:
		try {
			in = new BufferedReader(new InputStreamReader(PLPLaunchSockets.fromSim.getInputStream()));
			out = new PrintWriter(PLPLaunchSockets.toSim.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Send a command to the simulation to send the data
		 * needed to initialize the views and then initialize them.
		 */
		out.println("init");//The signal to step the simulation once.
		disassemblyUpdate(in);
		registersUpdate(in);
		
		//Main reading loop:
		doneLooping = false;
		while(!doneLooping){
			try {
				input = in.readLine();//Waiting for input...
			} catch (IOException e) {
				doneLooping = true;
			}
			if(input == null)//If input is null, we're done looping.
				doneLooping = true;
			
			if(!doneLooping && input.equals(MessageConstants.DISASSEMBLY_UPDATE)){
				disassemblyUpdate(in);
			} else if(!doneLooping && input.equals(MessageConstants.REGISTER_UPDATE)) {
				registersUpdate(in);
			} else if(!doneLooping) {
				System.out.println("No valid input.");
			}
		}
		//Close the input stream:
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is called to input the data from a disassembly update.
	 * 
	 * @param in the BufferedReader used to read in the data
	 */
	public void disassemblyUpdate(BufferedReader in){
		
		//The old data is outdated, so clear it first:
		DisassemblyDataHolder.data.clear();
		
		String holder = null;
		String raw = null;
		
		try {
			
			//If the first line is the disassembly update flag, discard it;
			//we don't need it at this point.
			raw = in.readLine();
			if(raw.equals(MessageConstants.DISASSEMBLY_UPDATE))
				raw = in.readLine();
			
			//Keep getting addresses and instructions until "endofaddrs" is sent,
			//signifying that there is no more data to be sent.
			while( !(raw.equals("endofaddrs")) ){
				holder = Long.toHexString(Long.parseLong(raw));
				DisassemblyDataHolder.data.add(holder);
				raw = in.readLine();
			}
			DisassemblyDataHolder.pc = Long.parseLong(in.readLine());//After all the addresses are sent, the value of the program counter is sent.
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			doneLooping = true;
		}
		
		DisassemblyDataHolder.updated = true;//The information has just been updated, so set "updated" accordingly
		
		Activator.em.fireEvent();//Signal listeners that the disassembly data has just been updated.
	}
	
	/**
	 * This method is called to input the data from a registers update.
	 * 
	 * @param in the BufferedReader used to read in the data
	 */
	public void registersUpdate(BufferedReader in){
		
		
		String input = null;
		try {
			input = in.readLine();
			//If the first line is the registers update flag, discard it;
			//we don't need it at this point.
			if(input.equals(MessageConstants.REGISTER_UPDATE))
				input = in.readLine();
		} catch (IOException e1) {
			doneLooping = true;
		}
		
			
		
		//We know that there are exactly 32 registers, so we
		//know exactly how long we'll loop.
		for(int i = 0; i < 32; i++){
			try {
				RegistersDataHolder.registers[i] = Long.parseLong(input);//Get the register contents.
				if(i != 31)
					input = in.readLine();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				doneLooping = true;
			}
		}
		
		RegistersDataHolder.updated = true;//The information has just been updated, so set "updated" accordingly
		Activator.rem.fireEvent();//Signal listeners that the register data has just been updated.
	}

}
