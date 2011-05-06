package fr.hoa.pie_menu.recursif;

import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;

/**
 *
 * @author Amirouche HALFAOUI
 */
public abstract class Item extends CPolyLine {

	protected int index;
	protected Color color;
	protected Menu menu;
	protected Canvas mcanvas;

	public Item(int id, double x, double y, Color color, Canvas c){
		super(x, y);
		this.index = id;
		this.color = color;
		this.mcanvas = c;
	}

	public void setAngle(double angle){
		this.setAntialiased(true);
		this.moveTo(Menu.RADIUS * Math.cos(angle/2.0), Menu.RADIUS * Math.sin(angle/2.0));
		this.arcTo(-angle/2.0, angle, Menu.RADIUS, Menu.RADIUS);
		this.lineTo(Menu.RADIUS_MIN * Math.cos(angle/2.0), -Menu.RADIUS_MIN * Math.sin(angle/2.0));
		this.arcTo(angle/2, -angle, Menu.RADIUS_MIN, Menu.RADIUS_MIN);
		this.moveTo(0,0);
		this.setReferencePoint(0,0.5);
		
		Paint fp = new GradientPaint(0, 0, color, Menu.RADIUS, 0, color.darker());
		setOutlined(false);
		this.setDrawable(false).setPickable(false);
		this.setFillPaint(fp);
		this.setOutlined(false);
		this.setDrawable(false).setPickable(false);
			
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Color getColor(){
		return color;
	}

	public void setMenu(Menu m){
		menu = m;
	}

	public void doAction(){
		mcanvas.getTag("item").setTransparencyFill(1);
		mcanvas.getTag("menu").setDrawable(false).setPickable(false);
	}

	public void mouseOver(){
		this.setTransparencyFill((float)0.5);
		
	}

	public void mouseLeave(){
		this.setTransparencyFill((float)1);
	}
}
