package fr.hoa.pie_menu;

import fr.hoa.pie_menu.demos.base.PanelBase;
import fr.hoa.pie_menu.demos.variable.PanelVariable;
import fr.hoa.pie_menu.demos.recursif.PanelRecursif;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/**
 * Démonstration des différents pie-menu sous forme de programme Swing
 */
public class Demonstration extends JFrame {

	/**
	 * Constructeur de la démonstration
	 * Met en place la fenêtre
	 */
	public Demonstration() {

		// Mise en place du titre de la fenêtre avec appel constructeur parent
		super("Démonstration - Projet IG - Oliver Hamza Amirouche");

		// Dimensions et position
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Appel des initialisations
		initComponents();
		initDemos();
	}

	/**
	 * Mise en place des composants de la fenêtre
	 */
	private void initComponents(){

		// Panel contenant le titre la page
		JPanel pnlTop = new JPanel();

		// Panel contenant les noms des auteurs
		JPanel pnlBottom = new JPanel();

		// Ajout d'une marge
		pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Mise en place du titre
		JLabel lblTitle = new JLabel("Pie-Menu - Démo");
		lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		pnlTop.add(lblTitle);

		// Ajout d'une marge au pied de page
		pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlBottom.add(new JLabel("Oliver DE CRAMER - Amirouche HALFAOUI - Hamza ABDOULHOUSSEN"));

		// Ajout du haut et du bas de la fenêtre
		this.add(pnlTop, BorderLayout.NORTH);
		this.add(pnlBottom, BorderLayout.SOUTH);
	}

	/**
	 * Initialisation des différentes démos dans les panels
	 */
	private void initDemos(){

		// Zone avec des onglets pour passer de l'un à l'autre
		JTabbedPane tpnDemos = new JTabbedPane();

		try{
			// Démo du pie-menu de base
			PanelBase pnlBase = new PanelBase();

			// Démo du pie-menu avec nombre d'items variable
			PanelVariable pnlVariable = new PanelVariable();

			// Démo du pie-menu hierarchique
			PanelRecursif pnlRecursif = new PanelRecursif();

			// Ajout des onglets
			tpnDemos.addTab("Base avec 8 items", pnlBase);
			tpnDemos.addTab("Nombre variable d'items + Animation", pnlVariable);
			tpnDemos.addTab("Menu récursif", pnlRecursif);
			
		} catch(Exception e){

			// Si exception générée par les menus à cause des problèmes de dimensions des tableaux
			JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR);
		}

		this.add(tpnDemos);
	}

	/**
	 * Point d'entrée du programme.
	 * Lancement de l'application
	 * @param args Arguments de la ligne de commande
	 */
	public static void main(String[] args){
		// Pour avoir le thème de l'OS
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}

		// Initialisation de la démonstration
		Demonstration d = new Demonstration();
		d.setVisible(true);
	}
}