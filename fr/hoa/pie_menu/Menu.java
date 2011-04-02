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

	State neutre = new State("Neutre") {
		Transition releaseRight = new Release(MouseEvent.BUTTON3){
			public void action(){
				System.out.println("Etat Neutre");
				canvas.setBackground(Color.white);
				currentState = menu;
			}
		};
	};

	State menu = new State("Menu"){
		Transition pressRight = new Press(MouseEvent.BUTTON3){
			public void action(){
				System.out.println("Etat Menu");
				canvas.setBackground(Color.red);
				currentState = neutre;
			}
		};
	};

	private Canvas canvas;
	private String[] labels;
	private Color[] colors;

	public Menu(Canvas canvas, String[] labels, Color[] colors) throws Exception{
		super();
		if(labels.length != colors.length){
			throw new Exception("Menu :: Pas la meme taille");
		}
		this.canvas = canvas;
		this.labels = labels;
		this.colors = colors;
	}

	
}
