package fr.hoa.pie_menu;

import fr.hoa.pie_menu.demos.PanelBase;
import fr.hoa.pie_menu.demos.PanelVariable;
import fr.hoa.pie_menu.demos.PanelRecursif;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/**
 *
 * @author Amirouche HALFAOUI
 */
public class Demonstration extends JFrame {

	public Demonstration() {
		super("Démonstration - Projet IG - Oliver Hamza Amirouche");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		initComponents();
		initDemos();
	}

	private void initComponents(){
		JPanel pnlTop = new JPanel();
		JPanel pnlBottom = new JPanel();

		pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel lblTitle = new JLabel("Pie-Menu - Démo");
		lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		pnlTop.add(lblTitle);

		pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlBottom.add(new JLabel("Oliver DE CRAMER - Amirouche HALFAOUI - Hamza ABDOULHOUSSEN"));

		this.add(pnlTop, BorderLayout.NORTH);
		this.add(pnlBottom, BorderLayout.SOUTH);
	}

	private void initDemos(){
		JTabbedPane tpnDemos = new JTabbedPane();
		try{
			PanelBase pnlBase = new PanelBase();
			PanelVariable pnlVariable = new PanelVariable();
			PanelRecursif pnlRecursif = new PanelRecursif();

			tpnDemos.addTab("Base avec 8 items", pnlBase);
			tpnDemos.addTab("Nombre variable d'items + Animation", pnlVariable);
			tpnDemos.addTab("Menu récursif", pnlRecursif);
		} catch(Exception e){
			System.err.println(e.getMessage());
		}
		this.add(tpnDemos);
	}

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		Demonstration d = new Demonstration();
		d.setVisible(true);
	}

}
