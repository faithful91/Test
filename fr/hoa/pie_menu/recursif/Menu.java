package fr.hoa.pie_menu.recursif;


import fr.lri.swingstates.animations.Animation;
import fr.lri.swingstates.animations.AnimationScaleTo;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 *
 * @author De Cramer Oliver
 */
public class Menu extends CStateMachine{

	final public static int RADIUS = 70;
	final public static int RADIUS_MIN = 5;

	private Canvas canvas;
	private Item[] root;

	private Item lastSelectedItem;

	public Menu(Canvas canvas, Item[] items) throws Exception{

		root = items;
		// items' angle is relative to the items' number
		double angle = Math.PI * 2 / items.length;

		this.canvas = canvas;

		for(int i = 0; i < root.length; i++){
			root[i].drawIt(i, angle, RADIUS, RADIUS_MIN);
		}
		
	}

	public void closeSubMenu(){
		for(int i = 0; i < root.length; i++){
			if(root[i] != lastSelectedItem){
				if(lastSelectedItem.isParent(root[i]))
					return;
				else
					root[i].closeSubMenus();
			}
				
		}
	}

	/**
	 * Default state
	 */
	private State none = new State("Default") {
		Transition pressRight = new Press(MouseEvent.BUTTON3, "Menu"){
			@Override
			public void action(){
				// Draws the menu at the mouse position
				canvas.getTag("item-0").translateTo(canvas.getMousePosition().x, canvas.getMousePosition().y);
				canvas.getTag("menu-0").scaleTo(0);
				Animation anim = new AnimationScaleTo(1, 1);
				anim.setLapDuration(200);
				anim.setFunction(Animation.FUNCTION_SIGMOID);
				anim.setDelay(1);
				canvas.getTag("menu-0").setDrawable(true).setPickable(true);
				canvas.getTag("menu-0").animate(anim);
			}
		};
	};

	/**
	 * Menu state : enabled when the pie menu is displayed
	 */
	private State menu = new State("Menu"){

		// When the mouse bouton is released, enable default state
		Transition releaseRight = new Release(MouseEvent.BUTTON3, "Default"){
			@Override
			public void action(){
				if(lastSelectedItem != null){

				}
				canvas.getTag("item-0").setTransparencyFill(1);
				canvas.getTag("menu-0").setDrawable(false).setPickable(false);
				for(int i = 0; i < root.length; i++){
					root[i].closeSubMenus();
				}
			}
		};

		// When the mouse leaves a shape
		Transition leaveShape = new LeaveOnTag("menu"){
			@Override
			public void action() {
				Item i = null;
				if(getShape() instanceof CText){
					i = (Item)getShape().getParent();
				} else if(getShape() instanceof Item){
					i = (Item)getShape();
				}
				i.onMouseLeave();
				lastSelectedItem = i;
				closeSubMenu();
			}
		};

		// When the mouse enters on a shape
		Transition enterShape = new EnterOnTag("menu"){
			@Override
			public void action() {
				Item i = null;
				if(getShape() instanceof CText){
					i = (Item)getShape().getParent();
				} else if(getShape() instanceof Item){
					i = (Item)getShape();
				}
				i.onMouseEnter();
			}
		};
	};
}
