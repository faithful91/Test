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
 *
 * @author Amirouche HALFAOUI
 */
public class PanelVariable extends JPanel {

	private Canvas canvas;
	private Menu menu;

	/** Pael de choix du nombre d'items */
	private JPanel pnlItemsNb;

	public PanelVariable() throws Exception{

		canvas = new Canvas();

		pnlItemsNb = new JPanel();
		pnlItemsNb.setLayout(new BoxLayout(pnlItemsNb, BoxLayout.Y_AXIS));
		pnlItemsNb.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlItemsNb.add(new JLabel("Choix du nombre d'items : "));
		pnlItemsNb.add(Box.createVerticalStrut(10));
		SpinnerNumberModel spnModel = new SpinnerNumberModel(10, 2, 20, 1);
		JSpinner spnNumber = new JSpinner(spnModel);
		spnNumber.setMaximumSize(new Dimension(200, 30));
		spnNumber.addChangeListener(new ChangeNumberListener(this));
		pnlItemsNb.add(spnNumber);
		pnlItemsNb.add(Box.createVerticalGlue());

		createMenu(10);

		this.setLayout(new BorderLayout());
		this.add(canvas);
		this.add(pnlItemsNb, BorderLayout.WEST);
		
	}

	public final void createMenu(int size) throws Exception{
		final Color[] colorsSrc = {Color.WHITE, Color.MAGENTA, Color.BLUE , Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
		final String[] labelsSrc = {"Blanc", "Violet", "Bleu" , "Cyan", "Vert", "Jaune", "Orange", "Rouge"};

		final Color[] colors = new Color[size];
		final String[] labels = new String[size];

		// Remplissage des items
		for(int i = 0; i < size; i++){
			colors[i] = colorsSrc[i % colorsSrc.length];
			labels[i] = labelsSrc[i % labelsSrc.length];
		}
		
		menu = new Menu(canvas, labels, colors);

		menu.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setBackground(colors[e.getID()]);
			}
		});

		canvas.attachSM(menu, false);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public Menu getMenu() {
		return menu;
	}

}
