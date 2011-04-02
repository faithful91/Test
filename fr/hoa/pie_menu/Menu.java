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
		super();
		if(labels.length != colors.length){
			throw new Exception("Menu :: Arrays with differents sizes");
		}
		this.canvas = canvas;
		this.labels = labels;
		this.colors = colors;

		this.items = new Item[labels.length];
		for(int i = 0; i < this.items.length; i++){
			this.items[i] = new Item( i, 0, 0);
		}

	}

	private State none = new State("None") {
		Transition releaseRight = new Release(MouseEvent.BUTTON3){
			public void action(){
				canvas.removeAllShapes();
				currentState = menu;
			}
		};
	};

	private State menu = new State("Menu"){
		Transition pressRight = new Press(MouseEvent.BUTTON3){
			public void action(){
				draw();
				currentState = none;
			}
		};
	};

	private void draw(){
		for(Item i : this.items){
			i.translateTo(canvas.getMousePosition().x, canvas.getMousePosition().y);
			i.rotateTo(i.getIndex() * Math.PI / 4.0);
			i.setFillPaint(colors[i.getIndex()]);
			i.addTo(canvas);
		}

	}
}
