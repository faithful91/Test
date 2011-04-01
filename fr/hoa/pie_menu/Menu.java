package fr.hoa.pie_menu;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;

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

}
