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

	/** Constante définissant le rayon externe du pie-menu */
	final public static int RADIUS = 70;

	/** Constante définissant le rayon interne du pie menu */
	final public static int RADIUS_MIN = 5;

	/** Référence vers le canvas auquel est attaché le menu */
	private Canvas canvas;

	/** Permet de stocker le dernier item sélectionné */
	private int lastSelectedItem;

	/** Ecouteur des choix d'items */
	private ActionListener actionListener;

	/**
	 * Constructeur du menu.
	 * Initialise l'ensemble des attributs et prépare le dessin en vue de l'affichage
	 * @param canvas Référence vers le canvas où est dessiné le menu
	 * @param labels Textes contenus dans les différents items
	 * @param colors Couleurs des différents items
	 * @throws Exception Lève une exception si les tableaux n'ont pas tous deux exactement 8 éléments
	 */
	public Menu(Canvas canvas, String[] labels, Color[] colors) throws Exception{
		
		// Le menu supporte uniquement 8 items
		if(labels.length != 8){
			throw new Exception("Menu :: Wrong number of elements given. Designed to display exactly 8 items");
		}

		// Tous les tableaux en paramètre doivent avoir les mêmes dimensions
		if(labels.length != colors.length){
			throw new Exception("Menu :: Arrays with differents sizes");
		}

		// Stockage des attributs
		this.canvas = canvas;
		this.lastSelectedItem = -1;
		
		// Initialisation des items
		for(int i = 0; i < colors.length; i++){
			Item item = new Item(i, 0, 0);
			
			// On prépare l'angle de dessin
			item.rotateTo(i * Math.PI / 4.0);
			
			// Couleur de l'item
			Paint fp = new GradientPaint(0, 0, colors[i], RADIUS, 0, colors[i].darker());
			item.setFillPaint(fp);

			// Couleur des contours
			item.setOutlinePaint(colors[i].darker());

			// Au départ le menu est masqué
			item.setDrawable(false).setPickable(false);

			// On ajoute les items au canvas
			item.addTo(canvas);

			// On ajoute les bons tags pour un accès rapide plus tard
			item.addTag("menu").addTag("item");

			// Ajoute le label associé à l'item
			CText text = canvas.newText(0, 0, labels[i], new Font(Font.SANS_SERIF, Font.BOLD, 9));

			// Application de la rotation pour qu'il soit lisible
			text.rotateBy(-i * Math.PI/4.0);
			text.translateTo(RADIUS/1.4, -1);

			// On attache le texte à l'item
			text.setParent(item);

			// Zone de clipping restreinte à l'item pour éviter les débordements de textes
			text.setClip(item);
			text.setFillPaint(colors[i].darker().darker().darker());
			text.setAntialiased(true);

			// On ajoute les tags particuliers
			text.addTag("menu").addTag("label");

			// Non visible au départ
			text.setDrawable(false).setPickable(false);
			text.addTo(canvas);
		}
	}

	/**
	 * Etat par défaut. Le menu n'y est pas affiché.
	 * On intercepte le clic droit pour passer à l'état où le menu est affiché.
	 */
	private State none = new State("Default") {

		/**
		 * Transition au clic droit
		 */
		Transition pressRight = new Press(MouseEvent.BUTTON3, "Menu"){
			@Override
			public void action(){

				// Affiche le menu sous le curseur avant de changer d'état
				canvas.getTag("item").translateTo(canvas.getMousePosition().x, canvas.getMousePosition().y);
				canvas.getTag("menu").setDrawable(true).setPickable(true);
			}
		};
	};

	/**
	 * Etat menu : actif lorsque le menu est affiché
	 */
	private State menu = new State("Menu"){

		// Retour à l'état par défaut lorsque la souris est relachée
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
	 * Méthde appelée lorsque un élément du menu a été choisi
	 */
	private void itemSelected(){

		// S'il existe un écouteur d'événements, on le déclenche
		if(this.actionListener != null){

			/* L'élément déclencheur est le menu, l'id est celui de l'item sélectionné.
			 * Et l'action a été provoquée par un clic
			 */
			ActionEvent e = new ActionEvent(this, lastSelectedItem, "Click");
			
			// lancement de la methode voulue par l'utilisateur
			actionListener.actionPerformed(e);
		}
	};

	/**
	 * Permet de définir l'écouteur d'événements du menu
	 * Cet écouteur déclenchera l'action définie par l'utilisateur
	 * en lui passant en paramètres :
	 *  - ID : celui de l'item choisi
	 *  - Déclencheur : Une référence vers cet objet menu
	 *  - Action "Click"
	 * @param actionListener Ecouteur défini par l'utilisateur
	 */
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
}
