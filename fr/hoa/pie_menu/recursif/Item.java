package fr.hoa.pie_menu.recursif;

import fr.lri.swingstates.animations.Animation;
import fr.lri.swingstates.animations.AnimationScaleTo;
import fr.lri.swingstates.canvas.CElement;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import sun.security.x509.AVA;

/**
 *
 * @author De Cramer Oliver
 */
public class Item extends CPolyLine {

	protected String label;
	protected Color color;
	protected Canvas canvas;

	protected CText text;
	protected double transPosX;
	protected double transPosY;

	protected int level;
	protected int id;
	protected int radiusMax;
	protected int radiusMin;

	protected Item[] subMenu = null;
	protected Item parent = null;
	protected Boolean isSubMenu = false;
	protected Boolean isSubDrawed = false;
	protected Boolean isSubShowed = false;
	protected Timer timer;

	protected Boolean mouseIsIn = false;

	protected ActionListener actionListener = null;

	public Item(int id, String label, Color color, Canvas canvas, int level, Item parent) {
		super(0, 0);
		this.label = label;
		this.color = color;
		this.level = level;
		this.id = id;
		this.canvas = canvas;
		this.parent = parent;
	}

	public Item(int id, String label, Color color, Canvas canvas, int level) {
		super(0, 0);
		this.label = label;
		this.color = color;
		this.level = level;
		this.id = id;
		this.canvas = canvas;
	}

	public Item(int id, String label, Color color, Canvas canvas) {
		super(0, 0);
		this.label = label;
		this.color = color;
		this.level = 1;
		this.id = id;
		this.canvas = canvas;
	}

	public void drawIt(int i, double angle, int radius, int radiusMin) {

		radiusMax = radius;
		this.radiusMin = radiusMin;
		int parentId;

		if (parent == null) {
			parentId = 0;
		} else {
			parentId = parent.getId();
		}

		this.rotateTo(i * angle);

		this.setAntialiased(true);
		this.moveTo(radius * Math.cos(angle / 2.0), radius * Math.sin(angle / 2.0));
		this.arcTo(-angle / 2.0, angle, radius, radius);
		this.lineTo(radiusMin * Math.cos(angle / 2.0), -radiusMin * Math.sin(angle / 2.0));
		this.arcTo(angle / 2, -angle, radiusMin, radiusMin);
		this.moveTo(0, 0);
		this.setReferencePoint(0, 0.5);

		// Sets the item's color
		Paint fp = new GradientPaint(0, 0, color, radiusMax, 0, color.darker());
		this.setFillPaint(fp);
		this.setOutlined(false);
		this.setDrawable(false).setPickable(false);
		this.addTo(canvas);

		this.addTag("menu").addTag("item");
		this.addTag("menu-" + parentId).addTag("item-" + parentId);

		// Adds the item's label
		text = canvas.newText(0, 0, label, new Font(Font.SANS_SERIF, Font.BOLD, 9));
		text.rotateBy(-i * angle);
		text.translateTo(radius / 1.4, -1);
		text.setParent(this);
		text.setClip(this);
		text.setFillPaint(color.darker().darker().darker());
		text.setAntialiased(true);
		text.addTag("menu").addTag("label");
		text.addTag("menu-" + parentId).addTag("label-" + parentId);
		text.setDrawable(false).setPickable(false);
		text.addTo(canvas);


		if (this.isSubMenu && !isSubDrawed) {
			drawSubMenu();
		}
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public void addChilds(Item[] sub) {
		subMenu = sub;
		isSubMenu = true;
		isSubDrawed = false;
	}

	private void drawSubMenu() {

		double angle = Math.PI * 2 / subMenu.length;

		for (int i = 0; i < subMenu.length; i++) {
			subMenu[i].drawIt(i, angle, (radiusMax - radiusMin) + radiusMax, radiusMax);
		}

		isSubDrawed = true;
	}

	private void showSubMenu() {
		// Draws the menu at the mouse position
		canvas.getTag("item-" + id).translateTo(transPosX, transPosY);

		canvas.getTag("menu-" + id).scaleTo(0.8);
		Animation anim = new AnimationScaleTo(1, 1);
		anim.setLapDuration(200);
		anim.setFunction(Animation.FUNCTION_SIGMOID);
		anim.setDelay(1);
		canvas.getTag("menu-" + id).setDrawable(true).setPickable(true);
		canvas.getTag("menu-" + id).animate(anim);
	}

	public void closeSubMenus() {
		if (isSubMenu) {
			timer = null;

			canvas.getTag("menu-" + id).setDrawable(false).setPickable(false);

			canvas.getTag("menu-" + id).scaleTo(1);
			Animation anim = new AnimationScaleTo(0.8, 0.8);
			anim.setLapDuration(200);
			anim.setFunction(Animation.FUNCTION_SIGMOID);
			anim.setDelay(1);
			canvas.getTag("menu-" + id).setDrawable(false).setPickable(false);
			canvas.getTag("menu-" + id).animate(anim);

			for (int i = 0; i < subMenu.length; i++) {
				subMenu[i].closeSubMenus();
				subMenu[i].onMouseLeave();
			}
			isSubShowed = false;
		}
	}

	public void onMouseEnter() {

		if (!mouseIsIn) {
			if (this.isSubMenu && !isSubShowed) {

				if(timer == null)
					showSubMenu();
				else{
					timer.cancel();
					timer = null;
				}

				isSubShowed = true;
			}

			this.setTransparencyFill((float) 0.5);
			mouseIsIn = true;
		}
	}

	public void onMouseLeave() {
		if (mouseIsIn) {

			mouseIsIn = false;
			if (this.isSubMenu) {

				if (!getMouseIsIn()) {
					
					timer = new Timer();
					TimerTask task = new TimerTask() {

						@Override
						public void run() {
							if(!getMouseIsIn()){
								closeSubMenus();
								setTransparencyFill((float) 1);
							}
						}
					};
					timer.schedule(task, (long)100);
				}

			} else {
				this.setTransparencyFill((float) 1);
			}
		}
	}

	public void actionDo(){
		System.out.println("Action");
		if(actionListener != null){
			ActionEvent e = new ActionEvent(this, id, "click");
			actionListener.actionPerformed(e);
		}
	}

	public int getId() {
		return id;
	}

	public Color getColor() {
		return color;
	}

	public Boolean getIsSubMenu() {
		return isSubMenu;
	}

	public String getLabel() {
		return label;
	}

	public int getLevel() {
		return level;
	}

	public Item getParent() {
		return parent;
	}

	public int getRadiusMax() {
		return radiusMax;
	}

	public int getRadiusMin() {
		return radiusMin;
	}

	

	public Boolean getMouseIsIn() {
		if (mouseIsIn) {
			return true;
		} else if (isSubMenu) {
			for (int i = 0; i < subMenu.length; i++) {
				if (subMenu[i].getMouseIsIn()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public Boolean isParent(Item item){
		if(item == parent)
			return true;
		else if(isSubMenu){
			for (int i = 0; i < subMenu.length; i++) {
				if (subMenu[i].isParent(item)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public CElement translateTo(double x, double y) {
		transPosX = x;
		transPosY = y;
		return super.translateTo(x, y);
	}


}
