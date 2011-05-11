package fr.hoa.pie_menu.recursif;

import fr.lri.swingstates.animations.Animation;
import fr.lri.swingstates.animations.AnimationScaleTo;
import fr.lri.swingstates.canvas.CElement;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author De Cramer Oliver
 */
public class Item extends CPolyLine {

	final public static int STYLE_DEFAULT = 0;
	final public static int STYLE_CERCLES = 1;
	final public static int STYLE_TRANCHE = 2;

	/**
	 * Le label de l’item.
	 */
	protected String label;

	/**
	 * La couleur de l'item. Utilise pour mettre un fond d'une certaine couleur en arrière plan.
	 */
	protected Color color;

	/**
	 * Le Itemcanvas dans le qu'elle en va dessiner.
	 */
	protected Canvas Itemcanvas;

	/**
	 * Pour pouvoir afficher le label on va le placer dans un CText. C'est celui-ci
	 * Il va être initié par la fonction drawIt
	 */
	protected CText text;

	private double angleSize;

	/**
	 * Si une translation est effectuée celle-ci est de combien.
	 * Celle-ci peut être utilise pour afficher les sous menu dans un certain SubMenuStyle.
	 */
	protected double transPosX;
	protected double transPosY;
	protected int rotated;

	/**
	 * Le niveau de l'Item par rapport ou sous menu. Il peut etre aussi calculer mais on le garde dans une variable
	 */
	private int level;

	/**
	 * Le numéro d’identification de l’Item
	 */
	private int id;
	private static int nbItem = 0;

	/**
	 * La taille du Cercle Exterieur et Interieur.
	 * Dans certain type d’affichage nous allons utiliser des tailles variables. 
	 */
	protected int radiusMax;
	protected int radiusMin;

	/**
	 * Les Item du sous menu de cette Item.
	 * Chaque Item peut avoir son propre sous Menu, sans limite de récursivité.
	 */
	private Item[] subMenu = null;

	/**
	 * Si oui ou non cette Item a un sous menu.
	 */
	private Boolean isSubMenu = false;
	private int SubMenuStyle;
	/**
	 * L’Item itemParent de cette Item si celui-ci appartiens a un sous menu.
	 */
	private Item itemParent = null;

	/**
	 * Nous permet de savoir si le Sous Menu de cette Item a déjà été dessiner ou pas.
	 * Nous considérons le Sous menu dessiner quand la fonction drawSubMenu a été appelé
	 */
	protected Boolean isSubDrawed = false;

	/**
	 * Nous permet de savoir si le sous menu est actuellement affiche ou pas.
	 */
	protected Boolean isSubShowed = false;

	/**
	 * Un timer pour que le sous Menu ne se ferme pas Imediatement.
	 */
	private Timer timer;

	/**
	 * Permet de verifier si la souris est actuellement sur l'Item ou pas.
	 */
	private Boolean mouseIsIn = false;

	/**
	 * Un action listener pour definir l'action de cette Item.
	 */
	private ActionListener actionListener = null;

	/**
	 * @brief Le constructor a utilise si l'Item appartient a un sous menu.
	 *
	 * @param id		Le numéro d’identification de l’Item.
	 * @param label		Le label de l’item.
	 * @param color		Le couleur de l’item
	 * @param Itemcanvas	Le Itemcanvas a utilise pour dessiner l’Item
	 * @param level		Le niveau de l'Item par rapport ou sous menu
	 * @param Parent	L’Item itemParent de cette Item
	 * @param style		Le SubMenuStyle avec le qu'elle doit s'afficher le Menu
	 */
	public Item(String label, Color color, Canvas canvas, int level, Item parent, int style) {
		super(0, 0);
		this.label = label;
		this.color = color;
		this.level = level;
		this.id = nbItem;
		nbItem++;
		this.Itemcanvas = canvas;
		this.itemParent = parent;
		SubMenuStyle = style;
	}

	/**
	 * @brief Le constructor a utilise si l'Item appartient a un sous menu.
	 *
	 * @param id		Le numéro d’identification de l’Item.
	 * @param label		Le label de l’item.
	 * @param color		Le couleur de l’item
	 * @param Itemcanvas	Le Itemcanvas a utilise pour dessiner l’Item
	 * @param level		Le niveau de l'Item par rapport ou sous menu
	 * @param itemParent	L’Item itemParent de cette Item
	 */
	public Item(String label, Color color, Canvas canvas, int level, Item parent) {
		this(label, color, canvas, level, parent, STYLE_DEFAULT);
	}

	/**
	 * @param id		Le numéro d’identification de l’Item.
	 * @param label		Le label de l’item.
	 * @param color		Le couleur de l’item
	 * @param Itemcanvas	Le Itemcanvas a utilise pour dessiner l’Item
	 * @param SubMenuStyle		Le SubMenuStyle avec le qu'elle doit s'afficher le Menu
	 */
	public Item(String label, Color color, Canvas canvas, int style) {
		this(label, color, canvas, 0, null, style);
	}
	
	/**
	 * @param id		Le numéro d’identification de l’Item.
	 * @param label		Le label de l’item.
	 * @param color		Le couleur de l’item
	 * @param Itemcanvas	Le Itemcanvas a utilise pour dessiner l’Item
	 */
	public Item(String label, Color color, Canvas canvas) {
		this(label, color, canvas, 0, null, STYLE_DEFAULT);
	}

	/**
	 * 
	 * @param i			Le i eme élément de ce menu (sous menu)
	 * @param angle		C'est l'angle de l'Item. la taille si on veut.
	 * @param radius	La taille du Cercle Exterieur
	 * @param radiusMin	 La taille du Cercle Interieur
	 */
	public void drawIt(int i, double angle, int radius, int radiusMin) {

		radiusMax = radius;
		this.radiusMin = radiusMin;
		this.angleSize = angle;

		int parentId;

		if (itemParent == null) {
			parentId = 0;
		} else {
			parentId = itemParent.getId();
		}

		this.rotateTo(i * angle);

		this.setAntialiased(true);
		this.moveTo(radius * Math.cos(angle / 2.0), radius * Math.sin(angle / 2.0));
		this.arcTo(-angle / 2.0, angle, radius, radius);
		this.lineTo(radiusMin * Math.cos(angle / 2.0), -radiusMin * Math.sin(angle / 2.0));
		this.arcTo(angle / 2, -angle, radiusMin, radiusMin);
		this.moveTo(0, 0);
		this.setReferencePoint(0, 0.5);

		// Sets the item's color
		Paint fp = new GradientPaint(0, 0, color, radiusMax, 0, color.darker());
		this.setFillPaint(fp);
		this.setOutlined(false);
		this.setDrawable(false).setPickable(false);
		this.addTo(Itemcanvas);

		this.addTag("menu").addTag("item");
		this.addTag("menu-" + parentId).addTag("item-" + parentId);

		// Adds the item's label
		text = Itemcanvas.newText(0, 0, label, new Font(Font.SANS_SERIF, Font.BOLD, 9));

		if(itemParent == null || itemParent.getStyle() != STYLE_TRANCHE)
			text.rotateBy(-i * angle);

		text.translateTo(radius / 1.4, -1);
		text.setParent(this);
		text.setClip(this);
		text.setFillPaint(color.darker().darker().darker());
		text.setAntialiased(true);
		text.addTag("menu").addTag("label");
		text.addTag("menu-" + parentId).addTag("label-" + parentId);
		text.setDrawable(false).setPickable(false);
		text.addTo(Itemcanvas);
	}

	/**
	 * @brief L'action a effectuer quand cette Item est utilisé
	 *
	 * @param actionListener
	 */
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	/**
	 * @brief Permet d'ajouter un sous menu a cette Item.
	 *
	 * Quand cette fonction est utilise la variable isSubMenu est mis a true automatiquement
	 * Elle va aussi permettre un nouveau appele de la fonction drawSubMenu
	 *
	 * @param sub Les Item du sous menu de cette Item.
	 */
	public void addChilds(Item[] sub) {
		subMenu = sub;
		isSubMenu = true;
		isSubDrawed = false;
	}
	
	private void drawSubMenu() {

		double angle = Math.PI * 2 / subMenu.length;

		int rmax, rmin;
		if(SubMenuStyle == STYLE_DEFAULT){
			rmax = radiusMax;
			rmin = radiusMin;
		}else{
			rmax = radiusMax + (radiusMax - radiusMin);
			rmin = radiusMax;
			if(SubMenuStyle == STYLE_TRANCHE)
				angle = angle/6;
		}

		for (int i = 0; i < subMenu.length; i++) {
			subMenu[i].drawIt(i, angle, rmax, rmin);
			if(SubMenuStyle == STYLE_TRANCHE){
				subMenu[i].rotateBy(this.getRotation() - (angleSize/2));
				if(this.getRotation() > Math.PI -  Math.PI/2 && this.getRotation() < (Math.PI + Math.PI/2)){
					subMenu[i].text.rotateBy( Math.PI);
				}
			}
		}

		isSubDrawed = true;
	}

	private void showSubMenu() {
		// Draws the menu at the mouse position
		if (!isSubDrawed) {
			drawSubMenu();
		}
		double px, py;

		if(SubMenuStyle == STYLE_DEFAULT){
			px = this.getCenterX() + (this.getCenterX() - transPosX)*2;
			py = this.getCenterY() + (this.getCenterY() - transPosY)*2;
		}else{
			px = transPosX;
			py = transPosY;
		}

		Itemcanvas.getTag("item-" + id).translateTo(px, py);

		Itemcanvas.getTag("menu-" + id).scaleTo(0.8);
		Animation anim = new AnimationScaleTo(1, 1);
		anim.setLapDuration(200);
		anim.setFunction(Animation.FUNCTION_SIGMOID);
		anim.setDelay(1);
		Itemcanvas.getTag("menu-" + id).setDrawable(true).setPickable(true);
		Itemcanvas.getTag("menu-" + id).animate(anim);
	}

	public void closeSubMenus() {
		if (isSubMenu & isSubDrawed) {
			timer = null;

			Itemcanvas.getTag("menu-" + id).setDrawable(false).setPickable(false);

			Itemcanvas.getTag("menu-" + id).scaleTo(1);
			Animation anim = new AnimationScaleTo(0.8, 0.8);
			anim.setLapDuration(200);
			anim.setFunction(Animation.FUNCTION_SIGMOID);
			anim.setDelay(1);
			Itemcanvas.getTag("menu-" + id).setDrawable(false).setPickable(false);
			Itemcanvas.getTag("menu-" + id).animate(anim);

			for (int i = 0; i < subMenu.length; i++) {
				subMenu[i].closeSubMenus();
				subMenu[i].onMouseLeave();
			}
			isSubShowed = false;
		}
	}

	/**
	 * Cette fonction est appelée par le Menu quand la souris entre dans l'Item.
	 */
	public void onMouseEnter() {

		if (!mouseIsIn) {
			if (this.isSubMenu && !isSubShowed) {

				if(timer == null)
					showSubMenu();
				else{
					timer.cancel();
					timer = null;
				}

				isSubShowed = true;
			}

			this.setTransparencyFill((float) 0.5);
			mouseIsIn = true;
		}
	}

	/**
	 *  Cette fonction est appelée par le Menu quand la souris quitte dans l'Item.
	 */
	public void onMouseLeave() {
		if (mouseIsIn) {

			mouseIsIn = false;
			if (this.isSubMenu) {

				if (!getMouseIsIn()) {
					
					timer = new Timer();
					TimerTask task = new TimerTask() {

						@Override
						public void run() {
							if(!getMouseIsIn()){
								closeSubMenus();
								setTransparencyFill((float) 1);
							}
						}
					};
					timer.schedule(task, (long)100);
				}

			} else {
				this.setTransparencyFill((float) 1);
			}
		}
	}
	
	/**
	 *  Cette fonction est appelée par le Menu quand cette Item a ete selectionne.
	 */
	public void actionDo(){
		System.out.println("Action");
		if(actionListener != null){
			ActionEvent e = new ActionEvent(this, id, "click");
			actionListener.actionPerformed(e);
		}
	}

	/**
	 *
	 * @return Le numero d'identifiant de l'Item
	 */
	public int getId() {
		return id;
	}

	public Color getColor() {
		return color;
	}

	public Boolean getIsSubMenu() {
		return isSubMenu;
	}

	public String getLabel() {
		return label;
	}

	public int getLevel() {
		return level;
	}

	public Item getItemParent() {
		return itemParent;
	}

	public int getRadiusMax() {
		return radiusMax;
	}

	public int getRadiusMin() {
		return radiusMin;
	}

	public int getStyle() {
		return SubMenuStyle;
	}

	public void setStyle(int style) {
		this.SubMenuStyle = style;
	}

	/**
	 *
	 * @return Si la souris est dans l'Item ou un de ses sous Menu
	 */
	public Boolean getMouseIsIn() {
		if (mouseIsIn) {
			return true;
		} else if (isSubMenu) {
			for (int i = 0; i < subMenu.length; i++) {
				if (subMenu[i].getMouseIsIn()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 *
	 * @param item l'Item que on veut controler.
	 * @return Si oui ou non l'item passe en parametre est le itemParent ou arriere grand itemParent de cette Item.
	 */
	public Boolean isParent(Item item){
		if(item == itemParent)
			return true;
		else if(isSubMenu){
			for (int i = 0; i < subMenu.length; i++) {
				if (subMenu[i].isParent(item)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public CElement translateTo(double x, double y) {
		transPosX = x;
		transPosY = y;
		return super.translateTo(x, y);
	}


}
