package plp_plugin.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/** 
 * This class displays a UART style view based on PLP tool.
 * The user is able to send strings to the UART by typing in the text box and pressing enter.
 * Currently, back end functionality is not supported. 
 * 
* @author Reigel, Justin
*/
public class UARTView extends ViewPart{ // implements plp_plugin.launcher.RegistersListener {
	
	/** TODO: link plp back end to IOPanel input
	 Currently, text is displayed on the UART by typing and pressing enter.
	 Back end will process the information, provide output, and manipulate
	 registers
	*/
	
	public Table table;
	
	/**
	 * Here is where the initial setup of the table, its columns,
	 * and their header labels is performed
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		//Uart
		table = new Table(parent, SWT.TOP | SWT.MULTI  | SWT.V_SCROLL| SWT.FULL_SELECTION);
	    final GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
	    data.heightHint = 200;
	    table.setLayoutData(data);
		table.setBackground(new Color(null, 0,0,0));
		
		//TODO: Remove these messages after back end gets implemented.
		String[] descriptionLabels = {
		    	"This is a test of the UART front end.",
		    	"Type in the text box at the bottom and press enter to display your text in the UART.",
		    	"Note: The backend is still not synced with this UART. This is just a demonstration of the front end"};

		for(int i = 0; i < descriptionLabels.length; i++){
	    	TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, descriptionLabels[i]);
			item.setForeground(new Color(null, 255, 0,0 ));
    	}
		scrollToBottom();
		
		//Text box
	    final GridData data2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
	    data2.heightHint = 50;
		Text t = new Text(parent, SWT.BORDER);
		t.setLayoutData(data2);
		t.addListener(SWT.KeyDown, new Listener() {
		      public void handleEvent(Event e) {
	    	  if(e.keyCode == SWT.CR || e.keyCode == SWT.LF)
	    	  {
	    		System.out.println("KEY");
	    	  	TableItem item = new TableItem(table, SWT.NONE);
				item.setText(t.getText());
				item.setForeground(new Color(null, 255, 0,0 ));
				t.setText("");
				scrollToBottom();
	    	  }
		    }
		});
	}

	@Override
	public void setFocus() {

	}

	/**
	 * Populate the table with the latest information from the SimCore process,
	 * discarding the old information.
	 */
	public void performAction() {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	//Get the data to be added:
		    	//Populate the table with the new data:
		    	
		    	//TODO: Remove these messages after back end gets implemented.
		    	TableItem item = new TableItem(table, SWT.NONE);
				item.setText("Testing performAcction()");
		    }
		});
		
	}
	
	/**
	 * Scrolls to the bottom of the UART table. Automatically deSelects anything before and after it scrolls, as the scrolling
	 * depends on the selection. 
	 */
	public void scrollToBottom(){
		table.deselectAll();
		table.select(table.getItemCount() - 1);
	    table.showSelection();
	    table.deselectAll();
	}

}

