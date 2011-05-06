
package fr.hoa.pie_menu.recursif.Items;

import fr.hoa.pie_menu.recursif.Item;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;

/**
 *
 * @author De Cramer Oliver
 */
public class ColorChanger extends Item{

	
	public ColorChanger(int id, double x, double y, Color color, Canvas mcanvas) {
		super(id, x, y, color, mcanvas);
	}

	@Override
	public void doAction() {
		mcanvas.getTag("item").setTransparencyFill(1);
		mcanvas.getTag("menu").setDrawable(false).setPickable(false);
		mcanvas.setBackground(color);
	}

}
