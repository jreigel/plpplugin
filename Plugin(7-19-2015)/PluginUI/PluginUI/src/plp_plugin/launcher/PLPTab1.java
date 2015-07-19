package plp_plugin.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PLPTab1 extends AbstractLaunchConfigurationTab {
	
	private String theText;

	@Override
	public void createControl(Composite parent) {
		
		Composite topControl = new Composite(parent, SWT.NONE);
		topControl.setLayout(new GridLayout(1, false));
		
		Label label = new Label(topControl, SWT.BORDER);
		label.setText("This is a label.");
		label.setToolTipText("This is the tooltip of this label");
		
		label.pack();
		
		Text text = new Text(topControl, SWT.NONE);
		if(theText != null)
			text.setText(theText);
		else
			text.setText("IT WAS NULL");
		
		text.pack();
		Button button = new Button(topControl, SWT.PUSH);
		button.setText("BUTTON");
		
		button.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        theText = text.getText();
		    }
		}); 

		
		button.pack();
		
		setControl(topControl);

	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		try {
			theText = configuration.getAttribute("theText", "Default Value");
		} catch (CoreException e) {
			System.out.println("Whoops! CoreException occured.");
			e.printStackTrace();
		}
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			theText = configuration.getAttribute("theText", "Default Value");
		} catch (CoreException e) {
			System.out.println("Whoops! CoreException occured.");
			e.printStackTrace();
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("theText", theText);

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
