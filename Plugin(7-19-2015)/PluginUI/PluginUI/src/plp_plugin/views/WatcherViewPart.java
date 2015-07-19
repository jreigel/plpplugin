package plp_plugin.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import plp_plugin.getters.getValue;

/**
 * This class is responsible for the creation and updating of the
 * Watcher View.
 * 
 * TODO it currently does not draw information from the actual Simulation
 * process; it only gets its information from the stub class  plp_plugin.getters.getValue
 * 
 * 
 * @author Cameron Bartee
 */
public class WatcherViewPart extends ViewPart {
	
	
	/**
	 * Here is where the initial setup of the Watcher View is performed.
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		parent.setLayout(new GridLayout(1, false));
		
//------UPPER CONTROLS:
		Composite upper = new Composite(parent, SWT.NONE);
		upper.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Label label1 = new Label(upper, SWT.NONE);
		label1.setText("Add: ");
		label1.setToolTipText("Choose whether to add a bus or a register to the Watcher.");
		label1.pack();

		Combo dropDown1 = new Combo(upper, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		dropDown1.add("Bus");
		dropDown1.add("Register");
		dropDown1.pack();
		
		Label label2 = new Label(upper, SWT.NONE);
		label2.setText("Address: ");
		label2.setToolTipText("Enter the address of the Bus/Register.");
		label2.pack();
		 
		Text text = new Text(upper, SWT.BORDER);//Text field for entering address.
		text.setText("");
		text.pack();
		
		Button button1 = new Button(upper, SWT.PUSH);
		button1.setText("Add");
		button1.pack();
		
//------MIDDLE TABLE:
		Table table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(GridDataFactory.swtDefaults().hint(500, 200).create());
		
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		
		TableColumn column = null;
		
		//Headers:
		column = new TableColumn(table, SWT.NONE);
		column.setText("Type");
		column.pack();
		column = new TableColumn(table, SWT.NONE);
		column.setText("Address");
		column.pack();
		column = new TableColumn(table, SWT.NONE);
		column.setText("Hex Value");
		column.pack();
		column = new TableColumn(table, SWT.NONE);
		column.setText("Value");
		column.pack();
		
		table.pack();
		
//------LOWER CONTROLS:
		Composite lower = new Composite(parent, SWT.NONE);
		lower.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Label label3 = new Label(lower, SWT.NONE);
		label3.setText("Display Value As:");
		label3.setToolTipText("Choose the display format for the Value column.");
		label3.pack();
		
		Combo dropDown2 = new Combo(lower, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		dropDown2.add("Unsigned Decimal");
		dropDown2.add("Signed Decimal");
		dropDown2.add("Binary");
		dropDown2.add("Binary - Least Significant 8 Bits");
		dropDown2.add("Unicode");
		dropDown2.add("Packed 4 ASCII Characters");
		dropDown2.pack();
		
//------BOTTOM CONTROLS:
		Composite bottom = new Composite(parent, SWT.NONE);
		bottom.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button button2 = new Button(bottom, SWT.PUSH);
		button2.setText("Remove All Entries");
		button2.pack();
		
		Button button3 = new Button(bottom, SWT.PUSH);
		button3.setText("Remove Selected");
		button3.pack();
		
//------SETUP "ADD" BUTTON FUNCTIONALITY:		
		button1.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		    	boolean valid = false;
		    	String string = null;
		    	if(dropDown1.getSelectionIndex() == 0){//"Bus" selected
		    		BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
		    		try {
						if(reader.read() == '0' && Character.toLowerCase(reader.read()) == 'x')
							string = reader.readLine();
							if(Long.parseLong(string)%4 == 0){
								valid = true;
								System.out.println("It's valid!");
							}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    		if(valid == true){
		    			TableItem item = new TableItem(table, SWT.NONE);
						item.setText(0, "Bus");
						item.setText(1, "0x" + string);
						item.setText(2, "0x" + Long.toHexString(getValue.getBusValue(Long.parseLong(string, 16))));
						item.setText(3, Long.toString(getValue.getBusValue(Long.parseLong(string, 16))));
		    		}
		    	} else if (dropDown1.getSelectionIndex() == 1) {//"Registers" selected
		    		String[] registers = { "$t0", "$t1", "$s0" };
		    		Scanner scanner = new Scanner(text.getText());
		    		string = scanner.next();
		    		scanner.close();
		    		for(int i = 0; i < registers.length; i++){
		    			if(registers[i].equals(string))
		    				valid = true;
		    		}
		    		if(valid == true){
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(0, "Register");
						item.setText(1, string);
						item.setText(2, "0x" + Long.toHexString(getValue.getRegisterValue(string)));
						item.setText(3, Long.toString(getValue.getRegisterValue(string)));
		    		}
		    	}
		    }
		});
		
//------SETUP "REMOVE ALL ENTRIES" BUTTON FUNCTIONALITY:
		button2.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		    	table.removeAll();
		    }
		});
//------SETUP "REMOVE SELECTED" BUTTON FUNCTIONALITY:
		button3.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		    	TableItem[] tableItems = table.getSelection();
		    	for(int i = 0; i < tableItems.length; i++)
		    		tableItems[i].dispose();
		    }
		});
		
//------SETUP "DISPLAY VALUE AS" DROPDOWN FUNCTIONALITY:
		dropDown2.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		    	TableItem[] tableItems = table.getSelection();
		    	for(int i = 0; i < tableItems.length; i++){
		    		System.out.println("It's working!");
		    		String string = null;
		    		Long value = Long.parseLong(tableItems[i].getText());
		    		if(dropDown2.getSelectionIndex() == 0)//Unsigned Decimal
		    			string = Long.toUnsignedString(value);
		    		else if(dropDown2.getSelectionIndex() == 1)//Signed Decimal
		    			string = Long.toString(value);
		    		else if(dropDown2.getSelectionIndex() == 2)//Binary
		    			string = Long.toBinaryString(value);
		    		else if(dropDown2.getSelectionIndex() == 3)//Binary - Least Significant 8 bits
		    			string = "NOT IMPLEMENTED";
		    		else if(dropDown2.getSelectionIndex() == 4)//Unicode
		    			string = "NOT IMPLEMENTED";
		    		else if(dropDown2.getSelectionIndex() == 5)//Packed 4 ASCII characters
		    			string = "NOT IMPLEMENTED";
		    		tableItems[i].setText(4, string);
		    	}
		    }
		});
	}

	@Override
	public void setFocus() {

	}

}
