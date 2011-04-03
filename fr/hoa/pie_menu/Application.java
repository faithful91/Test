package fr.hoa.pie_menu;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Amirouche HALFAOUI
 */
public class Application extends JFrame {

	private Canvas canvas;
	private Menu menu;

	public Application() throws Exception{
			final Color[] colors = {Color.WHITE, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
			final String[] labels = {"Blanc", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange", "Rouge"};

			this.setSize(800, 600);
			canvas = new Canvas(800, 600);
			menu = new Menu(canvas, labels, colors);

			menu.setActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					canvas.setBackground(colors[e.getID()]);
				}
			});

			this.add(canvas);
			canvas.attachSM(menu, false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws Exception{

		Application appli = new Application();
		appli.setVisible(true);

	}
}
