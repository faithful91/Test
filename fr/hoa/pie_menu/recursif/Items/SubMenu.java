package fr.hoa.pie_menu.recursif.Items;

import fr.hoa.pie_menu.recursif.Item;
import fr.hoa.pie_menu.recursif.Menu;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author De Cramer Oliver
 */
public class SubMenu extends Item {

	private Item[] items = null;
	private String[] labels = null;
	private Menu menu = null;

	public SubMenu(int id, double x, double y, Color c, Canvas canvas) {
		super(id, x, y, c, canvas);
	}

	public void setChildrens(String[] labels, Item[] items) {
		this.labels = labels;
		this.items = items;
	}

	@Override
	public void doAction() {
		mcanvas.getTag("item").setTransparencyFill(1);
		mcanvas.getTag("menu").setDrawable(false).setPickable(false);
	}

	@Override
	public void mouseOver() {
		if (labels != null && items != null && menu == null) {
			try {
				menu = new Menu(mcanvas, labels, items);
				mcanvas.attachSM(menu, false);
			} catch (Exception ex) {
				Logger.getLogger(SubMenu.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
