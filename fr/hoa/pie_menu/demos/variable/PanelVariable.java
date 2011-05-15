package fr.hoa.pie_menu.demos.variable;

import fr.hoa.pie_menu.menu.variable.Menu;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Démonstration du pie-menu avec un nombre d'items variable.
 * Cette démonstration permet de changer le nombre d'items et de voir le résultat en conséquence
 */
public class PanelVariable extends JPanel {

	/** Référence vers le canvas où se font les dessins et auquel est attachée la machine à états */
	private Canvas canvas;

	/** Machine à états du menu à nombre d'items variable */
	private Menu menu;

	/** Panel de choix du nombre d'items */
	private JPanel pnlItemsNb;

	/**
	 * Constructeur de la démonstration.
	 * Initialise les composants pour permettre le changement de nombre d'items.
	 * Met en place le menu une première fois
	 * @throws Exception Exception levée par le menu si les nombres de labels et de couleurs ne correspondent pas.
	 */
	public PanelVariable() throws Exception{

		// Initiailisation du canvas
		canvas = new Canvas();

		// Initialisation du panneau de changement de nombre d'items
		pnlItemsNb = new JPanel();

		// Changement de layout pour pouvoir afficher correctement les boutons
		pnlItemsNb.setLayout(new BoxLayout(pnlItemsNb, BoxLayout.Y_AXIS));

		// Mise en place d'une marge
		pnlItemsNb.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Titre du panneau de gauche pour le choix du nombre d'items
		pnlItemsNb.add(new JLabel("Choix du nombre d'items : "));
		pnlItemsNb.add(Box.createVerticalStrut(10));

		// Widget de choix du nombre d'items
		SpinnerNumberModel spnModel = new SpinnerNumberModel(10, 2, 20, 1);
		JSpinner spnNumber = new JSpinner(spnModel);
		spnNumber.setMaximumSize(new Dimension(200, 30));
		spnNumber.addChangeListener(new ChangeNumberListener(this));
		pnlItemsNb.add(spnNumber);
		pnlItemsNb.add(Box.createVerticalGlue());

		// Création du menu avec 10 items au départ
		createMenu(10);

		// Ajout des panneaux et du canvas à la démo
		this.setLayout(new BorderLayout());
		this.add(canvas);
		this.add(pnlItemsNb, BorderLayout.WEST);
		
	}

	/**
	 * Méthode de création du menu de la démonstrations
	 * @param size Nombre d'items pour le menu
	 * @throws Exception Exception levée par le menu si les nombres de couleurs et de labels ne correspondent pas
	 */
	public final void createMenu(int size) throws Exception{

		/* Tableau des couleurs sources
		 * On s'en sert pour créer un tableau de couleurs en fonction du nombre d'items
		 */
		final Color[] colorsSrc = {Color.WHITE, Color.MAGENTA, Color.BLUE , Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
		
		/* Tableau des labels sources
		 * On s'en sert pour créer un tableau de labels en fonction du nombre d'items
		 */
		final String[] labelsSrc = {"Blanc", "Violet", "Bleu" , "Cyan", "Vert", "Jaune", "Orange", "Rouge"};

		// Couleurs du menu à créer
		final Color[] colors = new Color[size];

		// Labels du menu à créer
		final String[] labels = new String[size];

		// Remplissage des items
		for(int i = 0; i < size; i++){
			colors[i] = colorsSrc[i % colorsSrc.length];
			labels[i] = labelsSrc[i % labelsSrc.length];
		}

		// Lancement de la création du menu avec les bons paramètres
		menu = new Menu(canvas, labels, colors);

		// Ajout d'un écouteur d'événements au menu
		menu.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setBackground(colors[e.getID()]);
			}
		});

		// On attache le menu au canvas
		canvas.attachSM(menu, false);
	}

	/**
	 * Accesseur de la référence vers le canvas
	 * @return Référence vers le canvas de la démo
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * Accesseur de la référence vers la machine à état de la démonstration
	 * @return Réference de la machine à états
	 */
	public Menu getMenu() {
		return menu;
	}

}
