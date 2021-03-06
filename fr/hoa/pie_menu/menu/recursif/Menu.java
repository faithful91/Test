package fr.hoa.pie_menu.menu.recursif;

import java.util.Timer;
import java.util.TimerTask;

import fr.lri.swingstates.animations.Animation;
import fr.lri.swingstates.animations.AnimationScaleTo;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import java.awt.event.MouseEvent;

/**
 *	Permet de creer un PieMenu recursif
 *
 */
public class Menu extends CStateMachine{

	/**
	 * Le rayon minimum du cercle du premier menu
	 */
	final public static int RADIUS = 70;

	/**
	 * Le rayon maximum du cercle du premier niveau
	 */
	final public static int RADIUS_MIN = 5;

	/**
	 * L'Itemcanvas dans lequel on va dessiner le menu
	 */
	private Canvas canvas;

	/**
	 * Un timer pour que le sous-menu ne se ferme pas immediatement
	 */
	private Timer timer;

	/**
	 * La liste des items du menu root
	 */
	private Item[] root;



	/**
	 * Le dernier item qui a ete selectionne par l'utilisateur
	 */
	private Item lastSelectedItem;

	/**
	 *
	 * @param canvas Le canvas a utiliser pour dessiner l'item
	 * @param items la liste des items
	 */
	public Menu(Canvas canvas, Item[] items){
		root = items;
		// items' angle is relative to the items' number
		double angle = Math.PI * 2 / items.length;

		this.canvas = canvas;

		for(int i = 0; i < root.length; i++){
			root[i].drawIt(i, angle, RADIUS, RADIUS_MIN);
		}
	}

	/**
	 * Ferme tous les menu sauf le menu root
	 */
	public void closeSubMenu() {
		for (int i = 0; i < root.length; i++) {
			if (root[i] != lastSelectedItem) {
				if (lastSelectedItem!=null && lastSelectedItem.isFamilly(root[i])) {
					return;
				} else {
					root[i].closeSubMenus();
				}
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
				if(lastSelectedItem != null && lastSelectedItem.getMouseIsIn()){
					lastSelectedItem.actionDo();
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
				if (getShape() instanceof CText) {
					i = (Item) getShape().getParent();
				} else if (getShape() instanceof Item) {
					i = (Item) getShape();
				}
				i.onMouseLeave();
				lastSelectedItem = null;

				timer = new Timer();
				TimerTask task = new TimerTask() {

					@Override
					public void run() {
						closeSubMenu();
					}
				};
				timer.schedule(task, (long) 400);
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
				lastSelectedItem = i;
			}
		};
	};
}
