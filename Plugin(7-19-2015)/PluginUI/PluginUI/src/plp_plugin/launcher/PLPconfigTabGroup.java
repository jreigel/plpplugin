package plp_plugin.launcher;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;

/**
 * Here we setup a tab group for the "PLP Launch" launch configuration type.
 * You can see this by launching the plugin and then selecting 
 * Run > Run Configurations  and then double clicking on "PLP Launch"
 * to create a new run configuration of type "PLP Launch"
 * 
 * This is currently kind of in shambles - I think that all the tabs currently
 * on it are useless.  Nevertheless, I think that it has to be here in order
 * for the user to be able to create launch configurations of type "PLP Launch".
 * 
 * Perhaps later we will find it necessary to allow the users to configure certain
 * attributes of PLP Launches, and then we can put some effort into this section.
 * 
 * @author Cameron Bartee
 */
public class PLPconfigTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { new PLPTab1(), new JavaJRETab(), new CommonTab() };
		setTabs(tabs);
	}

}
