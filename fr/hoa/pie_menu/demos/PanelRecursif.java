package fr.hoa.pie_menu.demos;

import fr.hoa.pie_menu.recursif.Item;
import fr.hoa.pie_menu.recursif.Menu;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Amirouche HALFAOUI
 */
public class PanelRecursif extends JPanel {

	private static class ColorChanger {

		public ColorChanger(int i, double d, double d0, Color color, Canvas canvas) {
		}
	}

	private Canvas canvas;
	private Menu menu;

	public PanelRecursif() throws Exception{
			final Color[] colors = {Color.WHITE, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};
			final String[] labels = {"Blanc", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};

			final Item[] items = new Item[labels.length];

			canvas = new Canvas();

			this.setLayout(new BorderLayout());

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

			items[colors.length-3] = subMenu2(colors[colors.length-3], colorChange);
			items[colors.length-3].setStyle(Item.STYLE_CERCLES);

			items[colors.length-2] = subMenu2(colors[colors.length-2], colorChange);
			items[colors.length-2].setStyle(Item.STYLE_TRANCHE);

			items[colors.length-1] = subMenu2(colors[colors.length-1], colorChange);

			menu = new Menu(canvas, items);
			this.add(canvas);

			canvas.attachSM(menu, false);
	}

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

	private Item subMenu2(Color c, ActionListener colorChange){
		final Color[] colors = { Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};
		final String[] labels = {"Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};
		final Item[] items = new Item[labels.length];

		Item smenu = new Item("Sub Menu", c, canvas);

		for(int cpt = 0; cpt < colors.length-3; cpt++){
			items[cpt] = new Item(labels[cpt], colors[cpt], canvas, 2, smenu);
			items[cpt].setActionListener(colorChange);
		}

		items[colors.length-3] = subMenu(colors[colors.length-3], colorChange, 3, smenu);
		items[colors.length-3].setStyle(Item.STYLE_CERCLES);

		items[colors.length-2] = subMenu(colors[colors.length-2], colorChange, 3, smenu);
		items[colors.length-2].setStyle(Item.STYLE_TRANCHE);

		items[colors.length-1] = subMenu(colors[colors.length-1], colorChange,3, smenu);

		smenu.addChilds(items);

		return smenu;
	}

}
