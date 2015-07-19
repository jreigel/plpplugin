package plp_plugin.views;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import plp_plugin.Activator;
import plp_plugin.launcher.DisassemblyDataHolder;

/**
 * This class is responsible for the creation and updating of the
 * Disassembly View.
 * 
 * @author Cameron Bartee
 */
public class DisassemblyViewPart extends ViewPart implements plp_plugin.launcher.DisassemblyListener {
	
	//The table which displays the disassembly information.
	Table table;
	
	/**
	 * Here is where the initial setup of the table, its columns,
	 * and their header labels is performed
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		
		TableColumn column = null;
		
		//Headers:
		column = new TableColumn(table, SWT.NONE);
		column.setText("PC");
		column.pack();
		column.setWidth(40);
		column.setResizable(false);
		column = new TableColumn(table, SWT.NONE);
		column.setText("Address");
		column.pack();
		column.setWidth(100);
		column.setResizable(false);
		column = new TableColumn(table, SWT.NONE);
		column.setText("Instruction (Hex)");
		column.pack();
		column.setResizable(false);
		column = new TableColumn(table, SWT.NONE);
		column.setText("Instruction");
		column.pack();
		
		table.pack();
		
		//This view is interested in knowing when the disassembly information has been
		//updated, so add it as a listener:
		Activator.em.addListener(this);
		
		if(DisassemblyDataHolder.updated == true){//If the data in DataHolder has been updated, populate the table
			performAction();
			DisassemblyDataHolder.updated = false;
		}
	}

	@Override
	public void setFocus() {
		
	}

	/**
	 * Populate the table with the latest information from the SimCore process,
	 * discarding the old information.
	 */
	@Override
	public void performAction() {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	table.removeAll();//Clear the table of old data
		    	ArrayList<String> data = DisassemblyDataHolder.data;//Get the data to be added
		    	//Populate the table with the new data:
		    	for(int i = 0; i < data.size(); i+=2){
			    	TableItem item = new TableItem(table, SWT.NONE);
			    	if(DisassemblyDataHolder.pc == Long.parseLong(data.get(i), 16))
			    		item.setText(0, ">>>");
			    	else
			    		item.setText(0, "   ");
					item.setText(1, "0x" + data.get(i));
					item.setText(2, "0x" + data.get(i+1));
					item.setText(3, " ");//TODO instruction data should go here, not yet implemented
		    	}
		    }
		});
		
	}

}
