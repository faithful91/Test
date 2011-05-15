package fr.hoa.pie_menu.menu.variable;

import fr.lri.swingstates.animations.Animation;
import fr.lri.swingstates.animations.AnimationScaleTo;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Machine à état d'un pie-menu avec le nombre d'items variable.
 */
public class Menu extends CStateMachine{

	final public static int RADIUS = 70;
	final public static int RADIUS_MIN = 5;
	private Canvas canvas;
	private String[] labels;
	private Color[] colors;
	private int lastSelectedItem;
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

		// Tous les tableaux doivent avoir les mêmes dimensions
		if(labels.length != colors.length){
			throw new Exception("Menu :: Arrays with differents sizes");
		}

		// Calcul de l'angle des items en fonction du nombre d'items
		double angle = Math.PI * 2 / labels.length;

		// Affectation des attributs avec les paramètres
		this.canvas = canvas;
		this.labels = labels;
		this.colors = colors;

		// Aucun item sélectionné au départ
		this.lastSelectedItem = -1;
		
		// Boucle d'initialisation des items
		for(int i = 0; i < this.colors.length; i++){

			// Création de l'item
			Item item = new Item(i, 0, 0, angle);

			// On applique un angle pour l'affichage (par rapport au pie menu)
			item.rotateTo(i * angle);

			// Mise en place de la couleur de l'item
			Paint fp = new GradientPaint(0, 0, colors[i], RADIUS, 0, colors[i].darker());
			item.setFillPaint(fp);

			// On enlève les bordures, ordre esthétique
			item.setOutlined(false);

			// Invisible au départ
			item.setDrawable(false).setPickable(false);

			// Ajout au canvas
			item.addTo(canvas);

			// Mise en place des tags pour intéraction simplifiée ensuite
			item.addTag("menu").addTag("item");

			// Mise en place du label de texte
			CText text = canvas.newText(0, 0, labels[i], new Font(Font.SANS_SERIF, Font.BOLD, 9));
			text.rotateBy(-i * angle);
			text.translateTo(RADIUS/1.4, -1);

			// On attache le texte à l'item
			text.setParent(item);

			// Zone de clipping pour éviter les débordements
			text.setClip(item);

			// Couleur du texte en fonction de la couleur de l'item
			text.setFillPaint(colors[i].darker().darker().darker());

			// Activation de l'anti-aaliasing
			text.setAntialiased(true);

			// Ajout des tags pour intéraction simplifiée dans le futur
			text.addTag("menu").addTag("label");

			// Textes invisibles au départ
			text.setDrawable(false).setPickable(false);

			// Ajout au canvas
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

				// On place le menu à la position de la souris
				canvas.getTag("item").translateTo(canvas.getMousePosition().x, canvas.getMousePosition().y);

				// Animation d'ouverture
				canvas.getTag("menu").scaleTo(0);
				Animation anim = new AnimationScaleTo(1, 1);
				anim.setLapDuration(200);
				anim.setFunction(Animation.FUNCTION_SIGMOID);
				anim.setDelay(1);

				// On affiche le menu avec l'animation
				canvas.getTag("menu").setDrawable(true).setPickable(true);
				canvas.getTag("menu").animate(anim);
			}
		};
	};

	/**
	 * Etat menu : actif lorsque le menu est affiché
	 */
	private State menu = new State("Menu"){

		/** Retour à l'état par défaut lorsque la souris est relachée */
		Transition releaseRight = new Release(MouseEvent.BUTTON3, "Default"){
			@Override
			public void action(){

				// Si un item a été sélectionné, on lance l'écouteur d'événements
				if(lastSelectedItem != -1){
					itemSelected();
				}

				// Retour visuel à l'état initial
				canvas.getTag("item").setTransparencyFill(1);
				canvas.getTag("menu").setDrawable(false).setPickable(false);
			}
		};

		/** Lorque la souris quitte un élément du menu (Texte ou Item) */
		Transition leaveShape = new LeaveOnTag("menu"){

			@Override
			public void action() {

				// Aucun item sélectionné à ce moment
				lastSelectedItem = -1;

				// Les items deviennent opaques
				canvas.getTag("item").setTransparencyFill(1);
			}
		};

		/**
		 * Transition lorsque la souris franchit le menu
		 * On cherche à savoir l'item sélectionné
		 */
		Transition enterShape = new EnterOnTag("menu"){

			@Override
			public void action() {

				// Tous les items deviennent semi-transparents pour mettre en valeur celui sélectionné
				canvas.getTag("item").setTransparencyFill((float)0.5);

				// Si c'est un texte qui est quitté
				if(getShape() instanceof CText){

					// On s'intéresse à son parent
					lastSelectedItem = ((Item)(getShape().getParent())).getIndex();
					getShape().getParent().setTransparencyFill(1);

				// Si c'est un item, on récupère ses informations directement
				} else if(getShape() instanceof Item){
					lastSelectedItem = ((Item)getShape()).getIndex();
					getShape().setTransparencyFill(1);
				}
			}
		};
	};

	/**
	 * Méthode appelée lorsque un élément du menu a été choisi
	 */
	private void itemSelected(){

		// S'il existe un écouteur d'événements
		if(this.actionListener != null){

			// On le déclenche avec les bons paramètres
			ActionEvent e = new ActionEvent(this, lastSelectedItem, "Click");
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
