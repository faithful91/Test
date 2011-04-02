package fr.hoa.pie_menu;

import fr.lri.swingstates.canvas.CPolyLine;

/**
 *
 * @author Amirouche HALFAOUI
 */
public class Item extends CPolyLine {

	private int index;
	private double x;
	private double y;

	public Item(int id, double x, double y){
		super(x, y);
		this.index = id;
		this.x = x;
		this.y = y;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
