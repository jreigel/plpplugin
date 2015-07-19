package plp_plugin.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import plp_plugin.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_BOOLEAN, true);
		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_STRING,
				"Default value");
		
		Color comment_Color = new Color(Display.getCurrent(), 0, 153, 0);
		PreferenceConverter.setDefault(store, PreferenceConstants.P_COMMENT_COLOR, comment_Color.getRGB());
		
		Color directive_Color = new Color(Display.getCurrent(), 204, 0, 102);
		PreferenceConverter.setDefault(store, PreferenceConstants.P_DIRECTIVE_COLOR, directive_Color.getRGB());
		
		Color instruction_Color = new Color(Display.getCurrent(), 0, 0, 255);
		PreferenceConverter.setDefault(store, PreferenceConstants.P_INSTRUCTION_COLOR, instruction_Color.getRGB());
		
		Color nop_Color = new Color(Display.getCurrent(), 127, 127, 127);
		PreferenceConverter.setDefault(store, PreferenceConstants.P_NOP_COLOR, nop_Color.getRGB());
		
		Color label_Color = new Color(Display.getCurrent(), 0, 0, 0);
		PreferenceConverter.setDefault(store, PreferenceConstants.P_LABEL_COLOR, label_Color.getRGB());
		
		Color register_Color = new Color(Display.getCurrent(), new RGB(255,0,0));
		PreferenceConverter.setDefault(store, PreferenceConstants.P_REGISTER_COLOR, register_Color.getRGB());
		
		Color number_Color = new Color(Display.getCurrent(), new RGB(255,153,0));
		PreferenceConverter.setDefault(store, PreferenceConstants.P_NUMBER_COLOR, number_Color.getRGB());
		}

}
