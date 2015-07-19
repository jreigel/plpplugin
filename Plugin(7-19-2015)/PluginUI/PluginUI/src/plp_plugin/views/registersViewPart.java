package plp_plugin.views;

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
import plp_plugin.launcher.RegistersDataHolder;

/**
 * This class is responsible for the creation and updating of the
 * Registers View.
 * 
 * @author Cameron Bartee
 */
public class registersViewPart extends ViewPart implements plp_plugin.launcher.RegistersListener {
	
	//The table which displays the registers information.
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
		table.pack();
		
		TableColumn column = null;
		
		//Headers:
		int registerColumnSize = 60;
		column = new TableColumn(table, SWT.NONE);
		column.setText("Register");
		column.setResizable(false);
		column.pack();
		column.setWidth(registerColumnSize);
		column = new TableColumn(table, SWT.NONE);
		column.setText("Contents");
		column.pack();
		column.setWidth(100);
		
		//Populate the table initially with the register labels:
		String[] registersLabels = RegistersDataHolder.registerLabels;
		for(int i = 0; i < registersLabels.length; i++){
	    	TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, registersLabels[i]);
    	}
		
		
		
		//This view is interested in knowing when the registers information has been
		//updated, so add it as a listener:
		Activator.rem.addListener(this);
		
		if(RegistersDataHolder.updated == true){//If the data in DataHolder has been updated, populate the table
			performAction();
			RegistersDataHolder.updated = false;
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
		    	//Get the data to be added:
		    	Long[] registersData = RegistersDataHolder.registers;
		    	//Populate the table with the new data:
		    	for(int i = 0; i < registersData.length; i++){
			    	TableItem item = table.getItem(i);
					item.setText(1, Long.toString(registersData[i]));
		    	}
		    }
		});
		
	}

}
