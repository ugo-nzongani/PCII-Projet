package view;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Etat;

/**Classe utilisée pour l'affichage du décor*/
public class Decor {
	
	public static final Random rand = new Random();
	
	/**Constantes*/
		/**Ordonnée minimale d'une montagne du décor*/
		public static final int Y_MIN_MONTAGNE=Affichage.HAUTEUR_FENETRE/8;
		/**Ordonnée maximale d'une montagne du décor*/
		public static final int Y_MAX_MONTAGNE=Affichage.HAUTEUR_FENETRE/4;
		/**Ordonnée de la ligne d'horizon*/
		public final static int HORIZON = Affichage.HAUTEUR_FENETRE/3;
		/**Longueur maximale d'une ligne sur l'axe des abscisses*/
		public static final int X_MAX = 20;
		/**Longueur minimale d'une ligne sur l'axe des abscisses*/
		public static final int X_MIN = 10;
	
	/**Attributs*/
	
		public Affichage affichage;
		public Etat etat;
    
		/**ArrayList contenant les points constituants la ligne brisée*/
		public ArrayList<Point> pointList;
	
		/**Abscisse du point à ajouter*/
		public int x;
		/**Ordonée du point à ajouter*/
		public int y;
	
	/**Constructeur*/
		public Decor(Affichage a, Etat e) {
			this.affichage = a;
			this.etat = e;
			this.pointList = new ArrayList<Point>();
			this.x=Affichage.EX_GAUCHE;
			this.y=Decor.HORIZON-Decor.Y_MAX_MONTAGNE;
			this.pointList.add(new Point(this.x, this.y));
			this.createMontagne();
			for(Point p:pointList) {
				//System.out.println(p);
			}
		}
	
	/**Méthode qui crée chaque sommet de la montagne*/
	public void createLigne() {
		this.x = this.x+rand.nextInt(Decor.X_MAX-Decor.X_MIN)+Decor.X_MIN;
		this.y = Decor.HORIZON - (rand.nextInt(Decor.Y_MAX_MONTAGNE-Decor.Y_MIN_MONTAGNE)+Decor.Y_MIN_MONTAGNE);
		this.pointList.add(new Point(this.x,this.y));
	}
	
	/**Méthode utilisée pour créer la montagne du décor*/
	public void createMontagne() {
		while(this.x<Affichage.EX_DROITE) {
			createLigne();
		}
	}
}
