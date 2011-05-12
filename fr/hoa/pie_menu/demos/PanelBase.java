package fr.hoa.pie_menu.demos;

import fr.hoa.pie_menu.base.Menu;
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
public class PanelBase extends JPanel {

	private Canvas canvas;
	private Menu menu;

	public PanelBase() throws Exception{

			final Color[] colors = {Color.WHITE, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
			final String[] labels = {"Blanc", "Violet", "Bleu", "Cyan", "Vert", "Jaune", "Orange", "Rouge"};
			canvas = new Canvas();
			menu = new Menu(canvas, labels, colors);

			menu.setActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					canvas.setBackground(colors[e.getID()]);
				}
			});
			this.setLayout(new BorderLayout());
			this.add(canvas);
			canvas.attachSM(menu, false);
	}

}
