package fr.hoa.pie_menu;

import fr.lri.swingstates.canvas.CPolyLine;
/**
 *
 * @author Amirouche HALFAOUI
 */
public class Item extends CPolyLine {

	final public static int RADIUS = 100;
	final public static int RADIUS_MIN = 10;

	private int index;
	private double x;
	private double y;

	public Item(int id, double x, double y){
		super(x, y);
		float angle = (float) (Math.PI / 4.0);

		this.index = id;
		this.setAntialiased(true);
		this.moveTo(RADIUS, 0);
		this.arcTo(0.0, angle, RADIUS, RADIUS);
		this.lineTo(RADIUS_MIN * Math.cos(angle), -RADIUS_MIN * Math.sin(angle));
		this.arcTo(angle, -Math.PI / 4.0, RADIUS_MIN, RADIUS_MIN);
		this.moveTo(0,0);
		this.setReferencePoint(0,1);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
