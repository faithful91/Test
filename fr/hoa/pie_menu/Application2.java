package fr.hoa.pie_menu;
import fr.hoa.pie_menu.recursif.Item;
import fr.hoa.pie_menu.recursif.Menu;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Amirouche HALFAOUI
 */
public class Application2 extends JFrame {

	private static class ColorChanger {

		public ColorChanger(int i, double d, double d0, Color color, Canvas canvas) {
		}
	}

	private Canvas canvas;
	private Menu menu;

	public Application2() throws Exception{
			final Color[] colors = {Color.WHITE, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};
			final String[] labels = {"Blanc", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};

			final Item[] items = new Item[labels.length];

			this.setSize(800, 600);
			canvas = new Canvas(800, 600);

			ActionListener colorChange = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Item i = (Item) e.getSource();
					canvas.setBackground(i.getColor());
				}
			};

			for(int i = 0; i < colors.length-3; i++){
				items[i] = new Item(i+1, labels[i], colors[i], canvas);
				items[i].setActionListener(colorChange);
			}

			items[colors.length-3] = subMenu(colors.length, colors[colors.length-3], colorChange);
			//items[colors.length-3].setStyle(Item.STYLE_CERCLES);

			items[colors.length-2] = subMenu(colors.length+10, colors[colors.length-2], colorChange);
			items[colors.length-1] = subMenu(colors.length+20, colors[colors.length-1], colorChange);
			
			menu = new Menu(canvas, items);
			this.add(canvas);

			canvas.attachSM(menu, false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private Item subMenu(int i, Color c, ActionListener colorChange){
		final Color[] colors = { Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};
		final String[] labels = {"Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};
		final Item[] items = new Item[labels.length];

		Item smenu = new Item(i, "Sub Menu", c, canvas, Item.STYLE_CERCLES);

		for(int cpt = 0; cpt < colors.length; cpt++){
			items[cpt] = new Item(cpt+i+1, labels[cpt], colors[cpt], canvas, 2, smenu);
			items[cpt].setActionListener(colorChange);
		}

		smenu.addChilds(items);

		return smenu;
	}


	public static void main(String[] args) throws Exception{

		Application2 appli = new Application2();
		appli.setVisible(true);
	}
}
