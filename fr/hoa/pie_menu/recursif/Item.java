package fr.hoa.pie_menu.recursif;

import fr.lri.swingstates.animations.Animation;
import fr.lri.swingstates.animations.AnimationScaleTo;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;

/**
 *
 * @author De Cramer Oliver
 */
public class Item extends CPolyLine {
	
	protected String label;
	protected Color color;
	protected Canvas canvas;

	protected int level;

	protected int id;
	protected int radiusMax;
	protected int radiusMin;

	protected Item[] subMenu = null;
	protected Item parent = null;
	protected Boolean isSubMenu = false;
	protected Boolean isSubDrawed = false;

	protected CText text;

	protected Boolean mouseIsIn = false;

	public Item(int id, String label, Color color, Canvas canvas, int level, Item parent) {
		super(0,0);
		this.label = label;
		this.color = color;
		this.level = level;
		this.id = id;
		this.canvas = canvas;
		this.parent = parent;
	}

	public Item(int id, String label, Color color, Canvas canvas, int level) {
		super(0,0);
		this.label = label;
		this.color = color;
		this.level = level;
		this.id = id;
		this.canvas = canvas;
	}

	public Item(int id, String label, Color color, Canvas canvas) {
		super(0,0);
		this.label = label;
		this.color = color;
		this.level = 1;
		this.id = id;
		this.canvas = canvas;
	}

	public void drawIt(int i, double angle, int radius, int radiusMin){

		radiusMax = radius;
		this.radiusMin = radiusMin;

		this.rotateTo(i * angle);

		System.out.println(radius);

		this.setAntialiased(true);
		this.moveTo(radius * Math.cos(angle/2.0), radius * Math.sin(angle/2.0));
		this.arcTo(-angle/2.0, angle, radius, radius);
		this.lineTo(radiusMin * Math.cos(angle/2.0), -radiusMin * Math.sin(angle/2.0));
		this.arcTo(angle/2, -angle, radiusMin, radiusMin);
		this.moveTo(0,0);
		this.setReferencePoint(0,0.5);

		// Sets the item's color
		Paint fp = new GradientPaint(0, 0, color, radiusMax, 0, color.darker());
		this.setFillPaint(fp);
		this.setOutlined(false);
		this.setDrawable(false).setPickable(false);
		this.addTo(canvas);

		this.addTag("menu").addTag("item");
		this.addTag("menu-"+level).addTag("item-"+level);

		// Adds the item's label
		text = canvas.newText(0, 0, label, new Font(Font.SANS_SERIF, Font.BOLD, 9));
		text.rotateBy(-i * angle);
		text.translateTo(radius/1.4, -1);
		text.setParent(this);
		text.setClip(this);
		text.setFillPaint(color.darker().darker().darker());
		text.setAntialiased(true);
		text.addTag("menu").addTag("label");
		text.addTag("menu-"+level).addTag("label-"+level);
		text.setDrawable(false).setPickable(false);
		text.addTo(canvas);
	}

	public void addChilds(Item[] sub){
		subMenu = sub;
		isSubMenu = true;
		isSubDrawed = false;
	}

	private void drawSubMenu(){

		double angle = Math.PI * 2 / subMenu.length;

		for(int i = 0; i < subMenu.length; i++){
			subMenu[i].drawIt(i, angle, (radiusMax-radiusMin)+radiusMin, radiusMax);
		}

		isSubDrawed = true;
	}

	private void showSubMenu(){
		// Draws the menu at the mouse position
		canvas.getTag("item-"+(level+1)).translateTo(0,0);
		canvas.getTag("menu-"+(level+1)).scaleTo(0);
		Animation anim = new AnimationScaleTo(1, 1);
		anim.setLapDuration(200);
		anim.setFunction(Animation.FUNCTION_SIGMOID);
		anim.setDelay(1);
		canvas.getTag("menu-"+(level+1)).setDrawable(true).setPickable(true);
		canvas.getTag("menu-"+(level+1)).animate(anim);
	}

	public void onMouseEnter(){

		if(this.isSubMenu){
			if(!isSubDrawed){
				drawSubMenu();
			}
			showSubMenu();
		}

		this.setTransparencyFill((float)0.5);
		mouseIsIn = true;

	}

	public void onMouseLeave(){
		if(this.isSubMenu){
			//@todo
		}else{
			this.setTransparencyFill((float)1);
		}
		mouseIsIn = false;
	}


}
