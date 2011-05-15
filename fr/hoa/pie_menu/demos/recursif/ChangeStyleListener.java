package fr.hoa.pie_menu.demos.recursif;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Contrôleur permettant de chnager le style d'affichage
 * @author Amirouche HALFAOUI
 */
public class ChangeStyleListener implements ActionListener{

	/** Panel parent */
	private PanelRecursif parent;

	/** Style de menu qu'applique ce contrôleur */
	private int menuStyle;

	/**
	 * Constructeur du contrôleur
	 * @param parent Référence vers le pael parent
	 * @param menuStyle Style qu'applique ce contrôleur
	 */
	public ChangeStyleListener(PanelRecursif parent, int menuStyle) {
		this.parent = parent;
		this.menuStyle = menuStyle;
	}


	/**
	 * Déclenchement de l'action
	 * @param e Description de l'événement enclencheur
	 */
	public void actionPerformed(ActionEvent e) {
		parent.getCanvas().detachSM(parent.getMenu());
		parent.getCanvas().removeAllShapes();
		parent.createMenu(menuStyle);
	}

}
