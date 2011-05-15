package fr.hoa.pie_menu.menu.base;
import fr.lri.swingstates.canvas.CPolyLine;
/**
 * Portion de menu, cette classe sert uniquement à dessiner une portion de menu.
 */
public class Item extends CPolyLine {

	/**
	 * Index de l'item courant
	 */
	private int index;

	/**
	 * Constructeur de la classe. Initialise les attributs et réalise le dessin
	 * @param id Identifiant de l'item
	 * @param x Position en x initiale
	 * @param y Position en y initiale
	 */
	public Item(int id, double x, double y){

		// Appel du constructeur de la classe mère pour initialiser la position
		super(x, y);

		// Calcul de l'angle que doit faire chaque item en fonction du nombre d'items
		float angle = (float) (Math.PI / 4.0);

		// Stockage de l'attribut id
		this.index = id;

		// Activation de l'anti-aliasing
		this.setAntialiased(true);

		// Dessin de l'arc exterieur
		this.moveTo(Menu.RADIUS * Math.cos(angle/2.0), Menu.RADIUS * Math.sin(angle/2.0));
		this.arcTo(-angle/2.0, angle, Menu.RADIUS, Menu.RADIUS);

		// Dessin de la ligne reliant l'arc exterieur et l'arc intérieur
		this.lineTo(Menu.RADIUS_MIN * Math.cos(angle/2.0), -Menu.RADIUS_MIN * Math.sin(angle/2.0));

		// Dessin de l'arc intérieur
		this.arcTo(angle/2, -angle, Menu.RADIUS_MIN, Menu.RADIUS_MIN);

		// On utilise l'origine comme référence
		this.moveTo(0,0);
		this.setReferencePoint(0,0.5);
	}

	/**
	 * Accesseur de l'attribut index
	 * @return Index de l'item courant
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Modifieur de l'attribut index de l'item courant
	 * @param index Index à appliquer à l'item courant
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
