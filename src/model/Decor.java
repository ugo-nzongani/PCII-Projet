package model;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import view.Affichage;

public class Decor {
	
	public static final Random rand = new Random();
	
	/**Ordonn�e minimale d'une montagne du d�cor*/
	public static final int Y_MIN_MONTAGNE=Affichage.HAUTEUR_FENETRE/8;
	/**Ordonn�e maximale d'une montagne du d�cor*/
	public static final int Y_MAX_MONTAGNE=Affichage.HAUTEUR_FENETRE/4;
	/**Ordonn�e de la ligne d'horizon*/
	public final static int HORIZON = Affichage.HAUTEUR_FENETRE/3;
	/**Longueur maximale d'une ligne sur l'axe des abscisses*/
	public static final int X_MAX = 20;
	/**Longueur minimale d'une ligne sur l'axe des abscisses*/
	public static final int X_MIN = 10;
	
	/**Attributs*/
	
	public Affichage affichage;
    public Etat etat;
	/**ArrayList contenant les points constituants la ligne bris�e*/
	public ArrayList<Point> pointList;
	/**Valeur de l'itin�raire en cours repr�sent�e par un entier, 0 pour descendre et 1 pour monter*/
	public int oldItineraire = 0;
	public int x;
	public int y;
	
	public Decor(Affichage a, Etat e) {
		this.affichage = a;
        this.etat = e;
		this.pointList = new ArrayList<Point>();
		this.x=-100;
		this.y=Decor.HORIZON-Decor.Y_MAX_MONTAGNE;
		this.pointList.add(new Point(this.x, this.y));
		this.createMontagne();
		for(Point p:pointList) {
			//System.out.println(p);
		}
	}
	
	public void createLigne() {
		this.x = this.x+rand.nextInt(Decor.X_MAX-Decor.X_MIN)+Decor.X_MIN;
		this.y = Decor.HORIZON - (rand.nextInt(Decor.Y_MAX_MONTAGNE-Decor.Y_MIN_MONTAGNE)+Decor.Y_MIN_MONTAGNE);
		this.pointList.add(new Point(this.x,this.y));
	}
	
	/**M�thode utilis�e pour cr�er la montagne du d�cor*/
	public void createMontagne() {
		while(this.x<Affichage.LARGEUR_FENETRE+100) {
			System.out.println("BABABABA"+Affichage.LARGEUR+Decor.X_MAX);
			createLigne();
		}
	}
}
