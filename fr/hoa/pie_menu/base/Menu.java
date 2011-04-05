package fr.hoa.pie_menu.base;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.event.*;

/**
 * @author Amirouche HALFAOUI
 */
public class Menu extends CStateMachine{

	final public static int RADIUS = 70;
	final public static int RADIUS_MIN = 5;
	private Canvas canvas;
	private int lastSelectedItem;
	private ActionListener actionListener;

	public Menu(Canvas canvas, String[] labels, Color[] colors) throws Exception{
		
		// This menu supports only 8 items
		if(labels.length != 8){
			throw new Exception("Menu :: Wrong number of elements given. Designed to display only 8 items");
		}

		// All the given arrays must have the same lengths
		if(labels.length != colors.length){
			throw new Exception("Menu :: Arrays with differents sizes");
		}
		this.canvas = canvas;
		this.lastSelectedItem = -1;
		
		// Items initialization
		for(int i = 0; i < colors.length; i++){
			Item item = new Item(i, 0, 0);
			
			// Angles change
			item.rotateTo(i * Math.PI / 4.0);
			
			// Sets the item's color
			Paint fp = new GradientPaint(0, 0, colors[i], RADIUS, 0, colors[i].darker());
			item.setFillPaint(fp);
			item.setOutlinePaint(colors[i].darker());
			item.setDrawable(false).setPickable(false);	
			item.addTo(canvas);
			item.addTag("menu").addTag("item");

			// Adds the item's label
			CText text = canvas.newText(0, 0, labels[i], new Font(Font.SANS_SERIF, Font.BOLD, 9));
			text.rotateBy(-i * Math.PI/4.0);
			text.translateTo(RADIUS/1.4, -1);
			text.setParent(item);
			text.setClip(item);
			text.setFillPaint(colors[i].darker().darker().darker());
			text.setAntialiased(true);
			text.addTag("menu").addTag("label");
			text.setDrawable(false).setPickable(false);
			text.addTo(canvas);
		}
	}

	/**
	 * Default state
	 */
	private State none = new State("Default") {
		Transition pressRight = new Press(MouseEvent.BUTTON3, "Menu"){
			@Override
			public void action(){

				// Draws the menu and moves to the menu state
				canvas.getTag("item").translateTo(canvas.getMousePosition().x, canvas.getMousePosition().y);
				canvas.getTag("menu").setDrawable(true).setPickable(true);
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
				if(lastSelectedItem != -1){
					itemSelected();
				}
				canvas.getTag("item").setTransparencyFill(1);
				canvas.getTag("menu").setDrawable(false).setPickable(false);
			}
		};

		// When the mouse leaves a shape
		Transition leaveShape = new LeaveOnTag("menu"){

			@Override
			public void action() {
				lastSelectedItem = -1;
				canvas.getTag("item").setTransparencyFill(1);
			}	
		};

		// When the mouse enters on a shape
		Transition enterShape = new EnterOnTag("menu"){

			@Override
			public void action() {
				canvas.getTag("item").setTransparencyFill((float)0.5);
				if(getShape() instanceof CText){
					lastSelectedItem = ((Item)(getShape().getParent())).getIndex();
					getShape().getParent().setTransparencyFill(1);
				} else if(getShape() instanceof Item){
					lastSelectedItem = ((Item)getShape()).getIndex();
					getShape().setTransparencyFill(1);
				}
			}
		};
	};

	/**
	 * Method called when an item is selected
	 */
	private void itemSelected(){
		if(this.actionListener != null){
			ActionEvent e = new ActionEvent(this, lastSelectedItem, "Click");
			actionListener.actionPerformed(e);
		}
	};

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
}
