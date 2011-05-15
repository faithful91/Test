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
 * @author Amirouche HALFAOUI
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
		canvas = new Canvas();
		pnlStyles = new JPanel();
		pnlStyles.setLayout(new BoxLayout(pnlStyles, BoxLayout.Y_AXIS));
		pnlStyles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

		final Color[] colors = {Color.WHITE, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};
		final String[] labels = {"Blanc", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};
		final Item[] items = new Item[labels.length];

		ActionListener colorChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item i = (Item) e.getSource();
				canvas.setBackground(i.getColor());
			}
		};

		for(int i = 0; i < colors.length-3; i++){
			items[i] = new Item(labels[i], colors[i], canvas);
			items[i].setActionListener(colorChange);
		}

		items[colors.length-3] = subMenu2(colors[colors.length-3], colorChange, style);
		items[colors.length-3].setStyle(style);

		items[colors.length-2] = subMenu2(colors[colors.length-2], colorChange, style);
		items[colors.length-2].setStyle(style);

		items[colors.length-1] = subMenu2(colors[colors.length-1], colorChange, style);
		items[colors.length-1].setStyle(style);

		menu = new Menu(canvas, items);
		canvas.attachSM(menu, false);

	}

	/**
	 * Fonction de génération de sous-sous-menu
	 */
	private Item subMenu(Color c, ActionListener colorChange, int level, Item parent){
		final Color[] colors = {Color.GRAY, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};
		final String[] labels = {"Gris", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};
		final Item[] items = new Item[labels.length];

		Item smenu = new Item("Sub Menu", c, canvas, level-1, parent);

		for(int cpt = 0; cpt < colors.length; cpt++){
			items[cpt] = new Item(labels[cpt], colors[cpt], canvas, level, smenu);
			items[cpt].setActionListener(colorChange);
		}

		smenu.addChilds(items);

		return smenu;
	}

	/**
	 * Fonction de génération des sous-menus
	 */
	private Item subMenu2(Color c, ActionListener colorChange, int style){
		final Color[] colors = { Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};
		final String[] labels = {"Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};
		final Item[] items = new Item[labels.length];
		Item smenu = new Item("Sub Menu", c, canvas);
		for(int cpt = 0; cpt < colors.length-3; cpt++){
			items[cpt] = new Item(labels[cpt], colors[cpt], canvas, 2, smenu);
			items[cpt].setActionListener(colorChange);
		}
		items[colors.length-3] = subMenu(colors[colors.length-3], colorChange, 3, smenu);
		items[colors.length-3].setStyle(style);
		items[colors.length-2] = subMenu(colors[colors.length-2], colorChange, 3, smenu);
		items[colors.length-2].setStyle(style);
		items[colors.length-1] = subMenu(colors[colors.length-1], colorChange,3, smenu);
		items[colors.length-1].setStyle(style);
		smenu.addChilds(items);
		return smenu;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public Menu getMenu() {
		return menu;
	}

}
