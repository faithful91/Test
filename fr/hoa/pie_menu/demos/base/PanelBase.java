package fr.hoa.pie_menu.demos.base;

import fr.hoa.pie_menu.menu.base.Menu;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 * Démonstration du pie-menu de base avec ses 8 items
 */
public class PanelBase extends JPanel {

	/** Canvas dans lequel se font les dessin et auquel la machine à états est attachée */
	private Canvas canvas;

	/** Machine à état correspondant au menu de base */
	private Menu menu;

	/**
	 * Constructeur de la démonstration
	 * Met en place le menu et instancie les différentes variables nécessaires à la démo
	 * @throws Exception Exception levée par la création du menu si les tableaux n'ont pas exactement 8 éléments
	 */
	public PanelBase() throws Exception{

		// Couleurs utilisées par le menu
		final Color[] colors = {Color.WHITE, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};

		// Labels du menu
		final String[] labels = {"Blanc", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange", "Rouge"};

		// On instancie le canvas
		canvas = new Canvas();

		// On instancie la machine à états
		menu = new Menu(canvas, labels, colors);

		// On ajoute un écouteur d'événéments au menu
		menu.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Changement de la couleur de fond du canvas...
				canvas.setBackground(colors[e.getID()]);
			}
		});

		// Layout de la démo pour que le canvas prenne toute la place
		this.setLayout(new BorderLayout());

		// Ajout du canvas à la démo
		this.add(canvas);

		// On attache la SM au canvas
		canvas.attachSM(menu, false);
	}

}
