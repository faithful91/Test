package fr.hoa.pie_menu;

import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Amirouche HALFAOUI
 */
public class Application extends JFrame {

	private Canvas canvas;
	private Menu menu;

	public Application(){

		Color[] colors = {Color.PINK, Color.MAGENTA, Color.ORANGE, Color.RED, Color.PINK, Color.MAGENTA, Color.ORANGE, Color.RED};
		String[] labels = {"Rose", "Violet", "Orange", "Rouge", "Rose", "Violet", "Orange", "Rouge"};

		this.setSize(800, 600);
		canvas = new Canvas(800, 600);
		try {
			menu = new Menu(canvas, labels, colors);
		} catch (Exception ex) {
			Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
		}

		canvas.attachSM(menu, true);
		this.add(canvas);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args){

		Application appli = new Application();
		appli.setVisible(true);

	}
}
