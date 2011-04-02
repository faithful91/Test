package fr.hoa.pie_menu;
import fr.lri.swingstates.canvas.*;
import fr.lri.swingstates.sm.*;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;
import java.awt.Color;
import java.awt.event.*;

/**
 * @author Amirouche HALFAOUI
 */
public class Menu extends CStateMachine{

	private Canvas canvas;
	private String[] labels;
	private Color[] colors;
	private Item[] items;

	public Menu(Canvas canvas, String[] labels, Color[] colors) throws Exception{
		
		// All the given arrays must have the same lengths
		if(labels.length != colors.length){
			throw new Exception("Menu :: Arrays with differents sizes");
		}
		this.canvas = canvas;
		this.labels = labels;
		this.colors = colors;

		this.items = new Item[labels.length];
		
		// Items initialization
		for(int i = 0; i < this.items.length; i++){
			this.items[i] = new Item(i, 0, 0);
			
			// Angles change
			this.items[i].rotateTo(i * Math.PI / 4.0);
			
			// Sets the item's color
			this.items[i].setFillPaint(colors[i]);
			this.items[i].setDrawable(false).setPickable(false);
			this.items[i].addTo(canvas);
			this.items[i].addTag("menu");
		}
	}

	/**
	 * Default state
	 */
	private State none = new State("Default") {
		Transition pressRight = new Press(MouseEvent.BUTTON3, "Menu"){
			public void action(){

				// Draws the menu and moves to the menu state
				canvas.getTag("menu").translateTo(canvas.getMousePosition().x, canvas.getMousePosition().y);
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
			public void action(){

				Item selectedItem = (Item) (canvas.getTag("selected").getTopLeastShape());
				if(selectedItem != null)
					canvas.setBackground((Color)selectedItem.getFillPaint());
				canvas.getTag("menu").setDrawable(false).setPickable(false);
			}
		};

		// When the mouse leaves a shape
		Transition leaveShape = new LeaveOnTag("selected"){

			@Override
			public void action() {
				getShape().removeTag("selected");
			}
			
		};

		// When the mouse enters on a shape
		Transition enterShape = new EnterOnTag("menu"){

			@Override
			public void action() {
				canvas.getTag("menu").removeTag("selected");
				getShape().addTag("selected");
			}
			
		};
	};
}
