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

	public Menu(Canvas canvas, String[] labels, Color[] colors) throws Exception{
		if(labels.length != colors.length){
			throw new Exception("Menu :: Pas la meme taille");
		}
		this.canvas = canvas;
		this.labels = labels;
		this.colors = colors;
	}

	State neutre = new State("Neutre") {
		Transition releaseRight = new Release(MouseEvent.BUTTON3);
	};

	State menu = new State("Menu"){
		Transition pressRight = new Press(MouseEvent.BUTTON3);
	};
}
