package fr.hoa.pie_menu.base;
import fr.lri.swingstates.canvas.CPolyLine;
/**
 *
 * @author Amirouche HALFAOUI
 */
public class Item extends CPolyLine {

	private int index;

	public Item(int id, double x, double y){
		super(x, y);
		float angle = (float) (Math.PI / 4.0);

		this.index = id;
		this.setAntialiased(true);
		this.moveTo(Menu.RADIUS * Math.cos(angle/2.0), Menu.RADIUS * Math.sin(angle/2.0));
		this.arcTo(-angle/2.0, angle, Menu.RADIUS, Menu.RADIUS);
		this.lineTo(Menu.RADIUS_MIN * Math.cos(angle/2.0), -Menu.RADIUS_MIN * Math.sin(angle/2.0));
		this.arcTo(angle/2, -angle, Menu.RADIUS_MIN, Menu.RADIUS_MIN);
		this.moveTo(0,0);
		this.setReferencePoint(0,0.5);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
