package fr.hoa.pie_menu;
import fr.hoa.pie_menu.recursif.Item;
import fr.hoa.pie_menu.recursif.Menu;
import fr.hoa.pie_menu.recursif.Items.ColorChanger;
import fr.hoa.pie_menu.recursif.Items.SubMenu;
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

			for(int i = 0; i < colors.length-1; i++){
				items[i] = (Item) new fr.hoa.pie_menu.recursif.Items.ColorChanger(i, 0.0, 0.0, colors[i], canvas);
			}

			items[colors.length-1] = subMenu(colors.length-1, colors[colors.length-1]);
			
			menu = new Menu(canvas, labels, items);
			this.add(canvas);

			canvas.attachSM(menu, false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private SubMenu subMenu(int i, Color c){
		final Color[] colors = {Color.WHITE, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE};
		final String[] labels = {"Blanc", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange"};
		final Item[] items = new Item[labels.length];

		for(int cpt = 0; cpt < colors.length; cpt++){
			items[cpt] = (Item) new fr.hoa.pie_menu.recursif.Items.ColorChanger(cpt, 0.0, 0.0, colors[cpt], canvas);
		}

		SubMenu smenu = new SubMenu(i, 0.0, 0.0, c, canvas);
		smenu.setChildrens(labels, items);

		return smenu;
	}

	public static void main(String[] args) throws Exception{

		Application2 appli = new Application2();
		appli.setVisible(true);
	}
}
