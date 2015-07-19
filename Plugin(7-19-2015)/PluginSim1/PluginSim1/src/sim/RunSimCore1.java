package sim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import plptool.PLPAsmSource;
import plptool.mips.Asm;
import plptool.mips.SimCore;
import plptool.mods.MemModule;
import plptool.dmf.CallbackRegistry;

public class RunSimCore1 {
	
	public static Socket toSim;
	public static Socket fromSim;

	public static void main(String[] args) {
		
		
		
//------First, setup the sockets that will be used to communicate with Eclipse:-----------------------------------------------------------//
		
		toSim = null;
		fromSim = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		System.out.println("portTo: " + args[0]);
		System.out.println("portFrom: " + args[1]);
		
		try {
			toSim = new Socket("localhost", Integer.parseInt(args[0]));
			fromSim = new Socket("localhost", Integer.parseInt(args[1]));
			out = new PrintWriter(fromSim.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(toSim.getInputStream()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Setup a shutdown hook that closes the sockets when the process ends:
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	        	try {
	    			toSim.close();
	    			fromSim.close();
	    		} catch (IOException e) {
	    			System.out.println("Error closing sockets!");
	    			e.printStackTrace();
	    		}
	        }
	    }, "Shutdown-thread"));
		
		
		
//------Now, we have to setup the SimCore:--------------------------------------------------------------------------------------------------//
		
		/* I don't know exactly what this is, but a lot
		 * of the methods that are called after this use
		 * CallBackRegistry in some way, and need it to
		 * be initialized somehow.  This code does this:
		 */
		if(!CallbackRegistry.INITIALIZED)
			CallbackRegistry.setup(null);
		
		ArrayList<PLPAsmSource> asms = getAsms();//------------------//Convert the .asm files on disk into an ArrayList of PLPAsmSource objects
		Asm a = new Asm(asms);//-------------------------------------//Feed the ArrayList holding our .asm source code into an assembler object
		a.preprocess(0);//-------------------------------------------//the assembler object does it's magic converting our code into a form...
		a.assemble();//----------------------------------------------//...that the SimCore can understand.
		SimCore s = new SimCore(a, a.getAddrTable()[0]);//-----------//Create the SimCore, passing it the assembler object and the address the assembler has determined to be the starting address of RAM memory.
		s.bus.add(new MemModule(a.getAddrTable()[0], 2048, true));//-//Here we add the RAM memory to the SimCore.  This is where the instructions assembled by the assembler are actually stored while the PLP assembly code is running.
		s.bus.enableAllModules();//----------------------------------//Gotta enable the modules in order for them to work!
		s.reset();//-------------------------------------------------//Perform an initial reset. It doesn't seem to work without this, so make sure you do it!
		
		
		
//------SimCore is now setup, now to begin a loop where we listen for commands sent from Eclipse---------------------------------------------//		
		
		//Here is the int into which we will be taking commands sent from Eclipse:
		String commandsIn = null;
		
		//Command input loop:
		while(true){
			try {
				commandsIn = in.readLine();
				if(commandsIn.equals("stepOnce")) {//"stepOnce" is a command to cause the simulation to step once and then send updates
					
					//Step once through the simulation:
					s.stepW();
					//Update the disassembly:
					updateDisassembly(out, s, a);
					//Update the Registers:
					updateRegisters(out, s);
					
				} else if(commandsIn.equals("init")){
					updateDisassembly(out, s, a);
					updateRegisters(out, s);
				} else
					System.out.println("Not a valid command.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * This method gets the .asm files we want to run,
	 * extracts their contents into strings, which are then converted into PLPAsmSource objects which are added
	 * to the ArrayList of PLPAsmSource objects which is then returned by this method.
	 * 
	 * Basically, it gets the text data out of .asm files on your hard drive and puts them into a form the program
	 * can understand.
	 * 
	 * @return ArrayList<<PLPAsmSource>> This is an ArrayList of the .asm files converted into PLPAsmSource objects.
	 */
	public static ArrayList<PLPAsmSource> getAsms(){
		
		ArrayList<PLPAsmSource> asms = new ArrayList<PLPAsmSource>();
		PLPAsmSource asm = null;
		
		//TODO
		//This is a temporary structure which gets the directory where the
		//asm files are stored.  It will likely have to be changed for the
		//final version of the plugin.
		File getDir = new File("");//Necessary for getting the "bin" directory
		File binDir = null;//The "bin" directory
		File mainDir = null;//The root directory for all the Eclipse workspaces
		try {
			binDir = new File(getDir.getCanonicalPath());//This seems to be necessary to get the absolute path for the "bin" directory
			mainDir = binDir.getParentFile().getParentFile().getParentFile();//This gets the root directory for all the Eclipse workspaces
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		File asmDir = new File(mainDir + "\\runtime-EclipseApplication\\PLP_Example_Project\\asms\\");//TODO HARD CODED PATH Here is the directory where we are keeping our .asm files
		File[] asmFiles = asmDir.listFiles();//Here we get an array of all the files contained in the directory where we are keeping our .asm files.
		BufferedReader in = null;
		
		for(int i = 0; i < asmFiles.length; i++){
			try {
//--------------VVV  This is the bit where we read in the files  VVV
				in = new BufferedReader(new FileReader(asmFiles[i]));
				int c;
				StringBuilder s = new StringBuilder();
				while((c = in.read()) != -1)
					s.append((char)c);
				asm = new PLPAsmSource(s.toString(), asmFiles[i].getName(), i);
				asms.add(asm);
//--------------^^^  This is the bit where we read in the files ^^^
			//Bunches of error handling below here:
			} catch (FileNotFoundException e) {
				System.out.println("Whoops! File not found.");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Whoops! IOException.");
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					System.out.println("Whoops! IOException.");
					e.printStackTrace();
				}
			}
		}

		//Finally, we can return our result!
		return asms;
	}
	
	/**
	 * Call this method to send a disassembly update through the Socket.
	 */
	public static void updateDisassembly(PrintWriter out, SimCore s, Asm a){
		
		out.println("disassemblyUpdate");//Signal Eclipse that a disassembly update is incoming.
		for(int i = 0; i < a.getAddrTable().length; i++){//Now, send the data:
			out.println(a.getAddrTable()[i]);
			out.println(s.bus.read(a.getAddrTable()[i]));
		}
		out.println("endofaddrs");//Signal Eclipse that we have sent the entire disassembly.
		out.println(s.pc.eval());//Send the value of the program counter
		
	}
	
	public static void updateRegisters(PrintWriter out, SimCore s){
		
		out.println("registerUpdate");//Signal Eclipse that a registers update is incoming.
		for(int i = 0; i < 32; i++){//Now, send the data:
			out.println(s.regfile.read(i));
		}
	}

}
