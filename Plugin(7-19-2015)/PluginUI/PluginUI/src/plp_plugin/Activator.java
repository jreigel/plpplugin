package plp_plugin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import plp_plugin.launcher.DisassemblyEventManager;
import plp_plugin.launcher.PLPLaunchSockets;
import plp_plugin.launcher.RegistersEventManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	
	//These Event Managers handle calling the update 
	//events that trigger all views to update their
	//displays to the latest data:
	public static DisassemblyEventManager em;
	public static RegistersEventManager rem;
	

	// The plug-in ID
	public static final String PLUGIN_ID = "PluginUI"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		em = new DisassemblyEventManager();
		rem = new RegistersEventManager();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		PLPLaunchSockets.closeSockets();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
