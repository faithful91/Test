package fr.hoa.pie_menu.demos.variable;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Amirouche HALFAOUI
 */
public class ChangeNumberListener implements ChangeListener {

	private PanelVariable parent;

	public ChangeNumberListener(PanelVariable parent) {
		this.parent = parent;
	}

	public void stateChanged(ChangeEvent e) {
		if(e.getSource() instanceof JSpinner){
			JSpinner spn = (JSpinner) e.getSource();
			parent.getCanvas().detachSM(parent.getMenu());
			parent.getCanvas().removeAllShapes();
			if(spn.getValue() instanceof Integer){
				try {
					parent.createMenu(((Integer) spn.getValue()).intValue());
				} catch (Exception ex) {}
			}
		}
	}

}
