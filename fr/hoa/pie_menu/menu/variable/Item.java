package fr.hoa.pie_menu.menu.variable;

import fr.lri.swingstates.canvas.CPolyLine;

/**
 * Classe permettant le dessin et la gestion d'une portion de menu.
 * @author Amirouche HALFAOUI
 */
public class Item extends CPolyLine {

	/** Index de cette instance d'item */
	private int index;

	/**
	 * Constructeur d'un item. Il réalise le dessin de l'item en fonction des
	 * valeurs passées en paramètre
	 * @param id Identifiant de l'item
	 * @param x Position en x du début de dessin de l'item
	 * @param y Position en y du début de dessin de l'item
	 * @param angle Angle d'ouverture de l'item
	 */
	public Item(int id, double x, double y, double angle){

		// Appel du constructeur parent pour initialiser la position
		super(x, y);

		// Récupération de l'index passé en paramètre
		this.index = id;

		// Activation de l'anti-aliasing
		this.setAntialiased(true);

		// Dessin d'une première ligne
		this.moveTo(Menu.RADIUS * Math.cos(angle/2.0), Menu.RADIUS * Math.sin(angle/2.0));

		// Dessin de l'arc de cercle extérieur
		this.arcTo(-angle/2.0, angle, Menu.RADIUS, Menu.RADIUS);

		// Dessin de l'autre ligne
		this.lineTo(Menu.RADIUS_MIN * Math.cos(angle/2.0), -Menu.RADIUS_MIN * Math.sin(angle/2.0));

		// Arc de cercle intérieur
		this.arcTo(angle/2, -angle, Menu.RADIUS_MIN, Menu.RADIUS_MIN);

		// Fermeture de la forme
		this.close();

		// Retour à l'origine du repère
		this.moveTo(0,0);

		// Point de référence correspondant à la pointe de la portion de menu
		this.setReferencePoint(0,0.5);
	}

	/**
	 * Accesseur de l'attribut index
	 * @return Index de l'item
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Modifieur de l'attribut index
	 * @param index Index à appliquer à l'item courant
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
