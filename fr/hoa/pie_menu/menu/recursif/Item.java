package fr.hoa.pie_menu.menu.recursif;

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
 * Classe permettant la gestion d'un item dans le cas du menu récursif
 */
public class Item extends CPolyLine {

	/**
	 * Style par défaut avec lequel s'ouvriront les sous-menus
	 * Ce style ouvrira un nouveau pie menu à côté de l'item parent avec la même forme
	 */
	final public static int STYLE_DEFAULT = 0;

	/**
	 * Ce style ouvrira un nouveau cercle plus grand autour de son parent
	 */
	final public static int STYLE_CERCLES = 1;

	/**
	 * Ce style ouvrira un éventail à côté de l'item parent
	 */
	final public static int STYLE_TRANCHE = 2;

	/**
	 * label de l'item
	 */
	protected String label;

	/**
	 * La couleur de l'item. Utilisé pour mettre un fond d'une certaine couleur en arrière-plan
	 */
	protected Color color;

	/**
	 * Canvas dans lequel on va dessiner l'item
	 */
	protected Canvas Itemcanvas;

	/**
	 * Pour pouvoir afficher le label, on va le placer dans un CText
	 * Il va être initialisé par la fonction drawIt
	 */
	protected CText text;

	private double angleSize;

	/**
	 * Si une translation est effectuée, la valeur qu'elle a est stockée
	 * Celle-ci peut être utilisée pour afficher les sous-menus dans un certain SubMenuStyle
	 */
	protected double transPosX;
	protected double transPosY;
	protected int rotated;

	/**
	 * Le niveau de l'item par rapport au sous menu. Il peut être aussi calculé mais on le garde dans une variable
	 */
	private int level;

	/**
	 * Le nombre total d'items qui ont été créés
	 * Permet le calcul d'un ID unique par Item
	 */
	protected static int nbItem = 0;

	/**
	 * Le numéro d'identification de l'item
	 * Celui-ci est très important, surtout pour les sous-menus qui utiliseront celui de leur parent comme tag
	 * Il est calcule automatiquement
	 */
	private int id;

	/**
	 * La taille du cercle extérieur et intérieur
	 * Dans certains types d'affichage, nous allons utiliser des tailles variables
	 */
	protected int radiusMax;
	protected int radiusMin;

	/**
	 * Les items du sous-menu de cet item
	 * Chaque item peut avoir son propre sous-menu, sans limite de recursivité
	 */
	private Item[] subMenu = null;

	/**
	 * Si oui ou non cet item a un sous menu
	 */
	private Boolean isSubMenu = false;

	/*
	 * Le style avec lequel doit s'ouvrir le sous-menu de cette item s'il existe
	 */
	private int SubMenuStyle;
	/**
	 * L'item itemParent de cette item si celui-ci appartient a un sous-menu
	 */
	private Item itemParent = null;

	/**
	 * Nous permet de savoir si le sous-menu de cette item a deja ete dessine ou non
	 * Nous considerons le sous-menu dessine quand la fonction drawSubMenu a ete appele
	 */
	protected Boolean isSubDrawed = false;

	/**
	 * Nous permet de savoir si le sous-menu est actuellement affiche ou pas
	 */
	protected Boolean isSubShowed = false;

	/**
	 * Un timer pour que le sous-menu ne se ferme pas immediatement
	 */
	private Timer timer;

	/**
	 * Permet de verifier si la souris est actuellement sur l'item ou non
	 */
	private Boolean mouseIsIn = false;

	/**
	 * Un action listener pour definir l'action de cette item
	 */
	private ActionListener actionListener = null;

	/**
	 * @brief Le constructeur a utilise si l'Item appartient a un sous-menu et a, lui meme, un sous-menu
	 * Car il permet a l'item d'avoir un parent, chose obligatoire pour un sous-menu
	 * Il lui permet aussi de determiner un style pour ses sous-menus
	 *
	 * @param id			Le numero d'identification de l'item
	 * @param label			Le label de l'item
	 * @param color			La couleur de l'item
	 * @param Itemcanvas	L'itemcanvas a utilise pour dessiner l'item
	 * @param level			Le niveau de l'item par rapport au sous-menu
	 * @param Parent		L'item itemParent de cette item
	 * @param style			Le SubMenuStyle avec lequel doit s'afficher le menu
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
	 * @brief Le constructeur a utilise si l'item appartient a un sous-menu
	 *Il peut aussi etre utilise s'il a un sous menu de style default
	 *
	 * @param id			Le numero d'identification de l'item
	 * @param label			Le label de l'item
	 * @param color			La couleur de l'item
	 * @param Itemcanvas	L' Itemcanvas a utilise pour dessiner l'item
	 * @param level			Le niveau de l'item par rapport au sous-menu
	 * @param itemParent	L'item itemParent de cette item
	 */
	public Item(String label, Color color, Canvas canvas, int level, Item parent) {
		this(label, color, canvas, level, parent, STYLE_DEFAULT);
	}

	/**
	 * @brief Le constructeur a utilise si l'item est dans la racine et a un sous-menu
	 *
	 * @param id			Le numero d'identification de l'item
	 * @param label			Le label de l'item
	 * @param color			La couleur de l'item
	 * @param Itemcanvas	L'Itemcanvas a utilise pour dessiner l'item
	 * @param SubMenuStyle	Le SubMenuStyle avec lequel doit s'afficher le menu
	 */
	public Item(String label, Color color, Canvas canvas, int style) {
		this(label, color, canvas, 0, null, style);
	}

	/**
	 * @param id		Le numero d'identification de l'item
	 * @param label		Le label de l'item
	 * @param color		La couleur de l'item
	 * @param Itemcanvas	L'Itemcanvas a utilise pour dessiner l'item
	 */
	public Item(String label, Color color, Canvas canvas) {
		this(label, color, canvas, 0, null, STYLE_DEFAULT);
	}

	/**
	 *
	 * @param i			Le i eme element de ce menu (sous-menu)
	 * @param angle		C'est l'angle de l'item. la taille si on veut
	 * @param radius	La taille du cercle exterieur
	 * @param radiusMin	 La taille du cercle interieur
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

		double posX;

		if(itemParent == null || itemParent.getStyle() == STYLE_DEFAULT)
			posX = radius/1.4 + level;
		else
			posX = radius/1.4 + level*4;


		text.translateTo(posX, -1);

		text.translateTo(posX, -1);
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
	 * @brief L'action à effectuer quand cet item est utilisé
	 *
	 * @param actionListener
	 */
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	/**
	 * @brief Permet d'ajouter un sous-menu a cette item
	 *
	 * Quand cette fonction est utilisée, la variable isSubMenu est mise a true automatiquement
	 * Elle va aussi permettre un nouvel appel de la fonction drawSubMenu
	 *
	 * @param sub Les Items du sous-menu de cette item
	 */
	public void addChilds(Item[] sub) {
		subMenu = sub;
		isSubMenu = true;
		isSubDrawed = false;
	}

	/**
	 * @brief Cree et place les elements du sous-menu dans le canvas
	 */
	private void drawSubMenu() {

		double angle = Math.PI * 2 / subMenu.length;

		int rmax, rmin;
		if(SubMenuStyle == STYLE_DEFAULT){
			rmax = Menu.RADIUS;
			rmin = Menu.RADIUS_MIN;
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

	/**
	 *  @brief Affiche le sous-menu de l'item avec une animation
	 */
	private void showSubMenu() {
		if(isSubMenu){
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
	}

	/**
	 * @brief Ferme le sous-menu si celui-ci est ouvert
	 */
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
	 * Cette fonction est appelee par le menu quand la souris entre dans l'item
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
	 *  Cette fonction est appelee par le menu quand la souris quitte l'item
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
	 *  Cette fonction est appelee par le menu quand cet item a ete selectionne
	 */
	public void actionDo(){
		if(actionListener != null){
			ActionEvent e = new ActionEvent(this, id, "click");
			actionListener.actionPerformed(e);
		}
	}

	/**
	 *
	 * @return Le numero d'identifiant de l'item
	 */
	public int getId() {
		return id;
	}

	/**
	 *
	 * @return la couleur de l'item
	 */
	public Color getColor() {
		return color;
	}

	/**
	 *
	 * @return si oui ou non l'item a un sous-menu
	 */
	public Boolean getIsSubMenu() {
		return isSubMenu;
	}

	/**
	 *
	 * @return Le label de l'item
	 */
	public String getLabel() {
		return label;
	}

	/**
	 *
	 * @return Le niveau de l'item par rapport au sous-menu. Il peut etre aussi calcule mais on le garde dans une variable
	 */
	public int getLevel() {
		return level;
	}

	/**
	 *
	 * @return L'item parent de cette item si celui-ci est dans un sous-menu
	 */
	public Item getItemParent() {
		return itemParent;
	}

	/**
	 *
	 * @return le rayon exterieur de cercle
	 */
	public int getRadiusMax() {
		return radiusMax;
	}

	/**
	 *
	 * @return le rayon interieur de cercle
	 */
	public int getRadiusMin() {
		return radiusMin;
	}

	/**
	 *
	 * @return le style avec lequel s'ouvre le sous-menu
	 */
	public int getStyle() {
		return SubMenuStyle;
	}

	/**
	 * @brief le style avec lequel on veut que le sous-menu s'ouvre
	 *
	 * Si cette fonction est appelee apres que l'item ait ete dessine (la fonction DrawIt appelee),
	 * A ce moment la il n'y aura aucun effet
	 *
	 * @param style le style avec lequel on veut que le sous-menu s'ouvre
	 */
	public void setStyle(int style) {
		if(!isSubDrawed)
			this.SubMenuStyle = style;
	}

	/**
	 * @brief Controle d'une facon recursive si la souris est dans un des sous item
	 *
	 * @return Si la souris est dans l'item ou un de ses sous-menu
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
	 * @param item l'item que on veut controler
	 * @return Si oui ou non l'item passe en parametre est l'itemParent ou arriere grand itemParent de cette item
	 */
	public Boolean isFamilly(Item item){
		if(itemParent != null && (item.id == this.id || item.id == itemParent.id) )
			return true;
		else if(isSubMenu){
			if(isParent(item))
				return true;
		}else if(itemParent!= null){
			return isParent2(item, itemParent);
		}
		return false;
	}

	public Boolean isParent(Item item){
		if(!isSubMenu){
			return false;
		}
		for (int i = 0; i < subMenu.length; i++) {
			if (subMenu[i].isParent(item)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isParent2(Item item, Item check){
		if(check.itemParent != null && check.itemParent.id == item.id)
			return true;
		else if(check.itemParent != null)
			return isParent2(item, check.itemParent);

		return false;
	}

	@Override
	public CElement translateTo(double x, double y) {
		transPosX = x;
		transPosY = y;
		return super.translateTo(x, y);
	}


}
