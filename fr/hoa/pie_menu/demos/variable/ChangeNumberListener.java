package fr.hoa.pie_menu.demos.variable;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Controleur de changement du nombre d'item voulu.
 * Sert pour la démonstration avec un nombre d'items variable par menu
 */
public class ChangeNumberListener implements ChangeListener {

	/** Référence vers la démonstration parente */
	private PanelVariable parent;

	/**
	 * Constructeur du contrôleur.
	 * Récupère la référence vers la démonstration parente
	 * @param parent Référence vers la démonstration parente
	 */
	public ChangeNumberListener(PanelVariable parent) {
		this.parent = parent;
	}

	/**
	 * Méthode appelée lors de la capture de l'événement
	 * @param e Description de l'événément déclencheur de l'action
	 */
	public void stateChanged(ChangeEvent e) {

		// Si la source est bien le sélecteur
		if(e.getSource() instanceof JSpinner){

			// On récupère la référence vers le sélecteur
			JSpinner spn = (JSpinner) e.getSource();

			// On détache la SM
			parent.getCanvas().detachSM(parent.getMenu());

			// On supprime toutes les formes
			parent.getCanvas().removeAllShapes();

			// On analyse le contenu du sélecteur
			if(spn.getValue() instanceof Integer){
				try {
					
					// On crée un nouveau menu en conséquence
					parent.createMenu(((Integer) spn.getValue()).intValue());
				} catch (Exception ex) {}
			}
		}
	}

}
