package plp_plugin.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;


/** 
 * This class displays an IO Panel that includes switches, leds, and the seven segment display
 * Currently, back end functionality and the seven segment display is not supported. 
 * 
* @author Reigel, Justin
*/
public class IOPanelView extends ViewPart{ // implements plp_plugin.launcher.RegistersListener {
	
	//TODO: link plp back end to IOPanel input using flip() method in LEDs and Switches to update front end
	// Currently, double clicking on the switches and leds demonstrates the desired visual output that the backend will manipulate. 
	//TODO: Put in working 7 segment display
	Table table;
	
	/**
	 * Here is where the initial setup of the table, its columns,
	 * and their header labels is performed
	 */
	@Override
	public void createPartControl(Composite parent) {
	    final Table shell = new Table(parent, SWT.TOP | SWT.FULL_SELECTION);
	    shell.setBackground(new Color(null, 0, 100, 0));
	    shell.setLayout(new GridLayout(1, false));
	    Switches[] switches = new Switches[7];
	    LEDs[] leds = new LEDs[7];

		for(int i = 0; i < 7; i++){
			switches[i] = new Switches(i+1);
			leds[i] = new LEDs(i+1);
		}

	    Composite first = new Composite(shell, SWT.NONE);
	    first.setBackground(new Color(null, 0, 100, 0));
	    GridLayout g = new GridLayout(7, true);
	    g.marginLeft = 40;
	    g.horizontalSpacing = 70;
	    first.setLayout(g);

	    Composite second = new Composite(shell, SWT.NONE);
	    second.setLayout(new GridLayout(7, true));
	    second.setBackground(new Color(null, 0, 100, 0));

	    for (int i = 1; i < 8; i++)
	    {
	        CLabel led = new CLabel(first, SWT.NONE);
	        led.setImage(leds[i-1].getImage());
	        led.setSize(100, 100);
	        led.setBackground(new Color(null, 0, 100, 0));
	        led.setText(Integer.toString(leds[i-1].getNumber() ) );
	        //flip method cannot be called unless the array value is stored in a local variable.
	        LEDs loopLed = leds[i-1];
	        //TODO: Remove mouse listener after back end is implemented.
	        //Mouse listener is just to show functionality with flip methods
	        led.addMouseListener(new MouseListener() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					loopLed.flip();
					led.setImage(loopLed.getImage());
				}

				@Override
				public void mouseDown(MouseEvent e) {
				}

				@Override
				public void mouseUp(MouseEvent e) {
				}
	        });
	        
	        Label ioSwitch = new Label(second, SWT.NONE);
	        ioSwitch.setImage(switches[i-1].getImage());
	        //flip method cannot be called unless the array value is stored in a local variable.
	        Switches s = switches[i-1];
	        ioSwitch.addMouseListener(new MouseListener() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					s.flip();
					ioSwitch.setImage(s.getImage());
				}

				@Override
				public void mouseDown(MouseEvent e) {
				}

				@Override
				public void mouseUp(MouseEvent e) {
				}
	        });


	    }
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
		    	for(int i = 0; i < 10; i++){
			    	TableItem item = table.getItem(i);
					item.setText(1, "test");
		    	}
		    }
		});
		
	}

}

