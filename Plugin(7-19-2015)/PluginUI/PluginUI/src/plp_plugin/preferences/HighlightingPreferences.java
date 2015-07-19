package plp_plugin.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import plp_plugin.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class HighlightingPreferences extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public HighlightingPreferences() {
		super(GRID);
		Activator.getDefault().getPreferenceStore()

		.addPropertyChangeListener(new IPropertyChangeListener() {
			// TODO: Fix bug where color is not updated immediately.
			/*
			 * Currently, tabs will have to be closed and opened again for the
			 * color change to take effect.
			 * Note: refreshing also doesn't cause the changes to take effect.
			 */
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				System.out.println("*******PREFERENCE CHANGED*******");
			}

		});

		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("PLP Syntax highlighting preferences.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		ColorFieldEditor commentColor = new ColorFieldEditor(
				PreferenceConstants.P_COMMENT_COLOR, "Comment color",
				getFieldEditorParent());
		ColorFieldEditor directiveColor = new ColorFieldEditor(
				PreferenceConstants.P_DIRECTIVE_COLOR, "Directive color",
				getFieldEditorParent());
		ColorFieldEditor instructionColor = new ColorFieldEditor(
				PreferenceConstants.P_INSTRUCTION_COLOR, "Instruction color",
				getFieldEditorParent());
		ColorFieldEditor nopColor = new ColorFieldEditor(
				PreferenceConstants.P_NOP_COLOR, "NOP color",
				getFieldEditorParent());
		ColorFieldEditor labelColor = new ColorFieldEditor(
				PreferenceConstants.P_LABEL_COLOR, "Label color",
				getFieldEditorParent());
		ColorFieldEditor registerColor = new ColorFieldEditor(
				PreferenceConstants.P_REGISTER_COLOR, "Register color",
				getFieldEditorParent());
		ColorFieldEditor numberColor = new ColorFieldEditor(
				PreferenceConstants.P_NUMBER_COLOR, "Number color",
				getFieldEditorParent());
		addField(commentColor);
		addField(directiveColor);
		addField(instructionColor);
		addField(nopColor);
		addField(labelColor);
		addField(registerColor);
		addField(numberColor);

	}

	public void reloadColors() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}