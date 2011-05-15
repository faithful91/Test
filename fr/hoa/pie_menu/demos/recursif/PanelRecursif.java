package fr.hoa.pie_menu.demos.recursif;

import fr.hoa.pie_menu.menu.recursif.Item;
import fr.hoa.pie_menu.menu.recursif.Menu;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe permettant de tester le menu récursif.
 * Démonstration avec possibilité de changer le style d'affichage des sous-menus
 */
public class PanelRecursif extends JPanel {

	/** Canvas auquel est attachée la SM */
	private Canvas canvas;

	/** Machine à état pour me menu récursif */
	private Menu menu;

	/** Panel de changement de styles */
	private JPanel pnlStyles;

	/**
	 * Constructeur de la démo.
	 * Met en place les éléments et initialise le menu une première fois
	 * @throws Exception Exception générée par le menu si mauvais nombre d'items
	 */
	public PanelRecursif() throws Exception{
		// On instancie le canvas
		canvas = new Canvas();

		// On instancie un Japenl pour mettre les 3 option dedans
		pnlStyles = new JPanel();
		pnlStyles.setLayout(new BoxLayout(pnlStyles, BoxLayout.Y_AXIS));
		pnlStyles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// On remplit le Panel avec un titre et les 3 choix de style
		pnlStyles.add(new JLabel("Choix du style d'affichage : "));
		pnlStyles.add(Box.createVerticalStrut(10));


		JButton b = new JButton("Style Classique");
		b.addActionListener(new ChangeStyleListener(this, Item.STYLE_DEFAULT));
		pnlStyles.add(b);

		b = new JButton("Style Zoom");
		b.addActionListener(new ChangeStyleListener(this, Item.STYLE_CERCLES));
		pnlStyles.add(b);
		
		b = new JButton("Style Eventail");
		b.addActionListener(new ChangeStyleListener(this, Item.STYLE_TRANCHE));
		pnlStyles.add(b);

		//On cee un menu avec le style par default
		createMenu(Item.STYLE_DEFAULT);

		this.setLayout(new BorderLayout());
		this.add(canvas);
		this.add(pnlStyles, BorderLayout.WEST);
	}

	/**
	 * Méthode  de génération de menu avec un style particulier
	 * @param style Style d'ouverture des sous-menus
	 */
	public final void createMenu(int style){

		// Couleurs utilisées par le menu du premier niveau
		final Color[] colors = {Color.WHITE, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};

		// Labels du menu du premier niveau
		final String[] labels = {"Blanc", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};

		//Le tableau des Items du premier niveau
		final Item[] items = new Item[labels.length];

		//Un actionListener qui va changer la couleur de fond
		ActionListener colorChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item i = (Item) e.getSource();
				canvas.setBackground(i.getColor());
			}
		};

		// On cree les item du premier niveau de menu
		for(int i = 0; i < colors.length-3; i++){
			items[i] = new Item(labels[i], colors[i], canvas);
			//On definit un ActionListener pour cette Item.
			items[i].setActionListener(colorChange);
		}

		//On cree des item eyant un sous Menu
		items[colors.length-3] = subMenu2(colors[colors.length-3], colorChange, style);
		items[colors.length-3].setStyle(style);

		items[colors.length-2] = subMenu2(colors[colors.length-2], colorChange, style);
		items[colors.length-2].setStyle(style);

		items[colors.length-1] = subMenu2(colors[colors.length-1], colorChange, style);
		items[colors.length-1].setStyle(style);

		// On instancie la machine à états. Et lui passe en parametre les Item root  du premier niveau de menu
		menu = new Menu(canvas, items);
		canvas.attachSM(menu, false);

	}

	/**
	 * Fonction de génération de sous-sous-menu
	 * @param c la couleur de l'Item
	 * @param colorChange l'actionListener voulus pour les Item cree
	 * @param level Le niveau de l'Item qui sera cree
	 * @param parent L'Item pere de l'Item qui va etre retourne
	 *
	 * @return un Item qui a un sous menu
	 */
	private Item subMenu(Color c, ActionListener colorChange, int level, Item parent){

		// Couleurs utilisées par le menu du level'ieme niveau
		final Color[] colors = {Color.GRAY, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};

		// Labels du menu du level'ieme niveau
		final String[] labels = {"Gris", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};

		//Le tableau des Items du level'ieme niveau
		final Item[] items = new Item[labels.length];

		//On cree l'item per qui contiendra les item du sous menu
		Item smenu = new Item("Sub Menu", c, canvas, level-1, parent);

		//On cree les item du ous menu
		for(int cpt = 0; cpt < colors.length; cpt++){
			items[cpt] = new Item(labels[cpt], colors[cpt], canvas, level, smenu);
			//On definit un ActionListener pour cette Item.
			items[cpt].setActionListener(colorChange);
		}

		//On attache les Item du sous menu a leur pere
		smenu.addChilds(items);

		//On retourne l'Item qui a un sous menu et qui appartient a un sous menu
		return smenu;
	}


	/**
	 * Fonction de génération des sous-menus
	 *
	 * @param c la couleur de l'item pere
	 * @param colorChange l'actionListener voulus pour les Item cree
	 * @param style Le style du menu
	 *
	 * @return Un item qui est un sous menu eyant d'autre sous menu
	 */
	private Item subMenu2(Color c, ActionListener colorChange, int style){

		// Couleurs utilisées par le menu du deuxieme niveau
		final Color[] colors = { Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};

		// Labels du menu du deuxieme niveau
		final String[] labels = {"Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};

		//Le tableau des Items du deuxieme niveau
		final Item[] items = new Item[labels.length];

		//On cree l'item per qui contiendra les item du sous menu
		Item smenu = new Item("Sub Menu", c, canvas);
		for(int cpt = 0; cpt < colors.length-3; cpt++){
			items[cpt] = new Item(labels[cpt], colors[cpt], canvas, 2, smenu);
			//On definit un ActionListener pour cette Item.
			items[cpt].setActionListener(colorChange);
		}

		//On cree des sous Menu a ces Item. on aura donc un menu a 3 niveau.
		items[colors.length-3] = subMenu(colors[colors.length-3], colorChange, 3, smenu);
		items[colors.length-3].setStyle(style);

		items[colors.length-2] = subMenu(colors[colors.length-2], colorChange, 3, smenu);
		items[colors.length-2].setStyle(style);

		items[colors.length-1] = subMenu(colors[colors.length-1], colorChange,3, smenu);
		items[colors.length-1].setStyle(style);

		//On attache les Item du sous menu a leur pere
		smenu.addChilds(items);

		//On retourne l'Item qui a un sous menu
		return smenu;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public Menu getMenu() {
		return menu;
	}

}
