package fr.hoa.pie_menu;

import fr.lri.swingstates.canvas.Canvas;
import javax.swing.JFrame;

/**
 *
 * @author Amirouche HALFAOUI
 */
public class Application extends JFrame {

	private Canvas canvas;

	public Application(){
		this.setSize(800, 600);
		canvas = new Canvas();
		this.add(canvas);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args){

		Application appli = new Application();
		appli.setVisible(true);

	}
}
