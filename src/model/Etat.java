package model;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import view.Affichage;

/**Classe charg�e de repr�senter le mod�le*/
public class Etat {
	
	/**Attributs*/
	
		/**Bornes de la vitesse*/
		public static final int VITESSE_MAX=700;
		public static final int AVANCE_MAX=70;
		public static final int AVANCE_MIN=10;
	
		public Affichage affichage;
	
		/**Abscisse de la voiture*/
		public int x = Affichage.x;
		
		public Route route;
		
		/**Attribut utilis�e pour la fin de la partie*/
		public boolean continuer;
	
		/**Acceleration*/
		public int acceleration;
		
		/**Vitesse*/
		public int vitesse;
		
		/**Attributs utilis�es pour relier le d�filement de l'affichage et la vitesse de la voiture*/
		public int avance;
		
		/**Attributs utilis�es pour repr�senter le temps*/
		public double temps;
		public double add;
	
	/**Constructeur*/	
	public Etat() throws IOException {
		this.affichage = new Affichage(this);
		this.route = new Route(this);
		this.continuer = true;
		this.acceleration=1;
		this.vitesse=100;
		this.avance=50;
		this.temps=20;
		this.add=5;
	}
	
	/**M�thode appel�e lorsque la voiture s� d�place vers la gauche*/
	public void moveLeft(int a) {
		/**On v�rifie que la voiture ne sort pas de l'interface graphique*/
		if(this.x > -100) {
			this.x-=a;
			this.affichage.decalage+=a;
		}
		this.affichage.repaint();
	}

	/**M�thode appel�e lorsque la voiture se d�place vers la droite*/
	public void moveRight(int a) {
		/**On v�rifie que la voiture ne sort pas de l'interface graphique*/
		if(this.x + Affichage.LARGEUR + 1 < Affichage.LARGEUR_FENETRE+100) {
			this.x+=a;
			this.affichage.decalage-=a;
		}
		this.affichage.repaint();
	}
	
	/**M�thode utilis�e pour calculer la vitesse*/
	public void calcul_Vit() {
		/**On calcule la vitesse gr�ce � l'acc�l�ration*/
		int vit = this.vitesse+calcul_Acc()*10;
		/**Si la vitesse a d�pass�e la limite max*/
		if(vit>Etat.VITESSE_MAX) {
			this.vitesse=Etat.VITESSE_MAX;
		/**Si la vitesse est nulle*/ 
		} else if(vit<=0){
			this.vitesse=0;
		/**Si la vitesse diminue*/
		} else if(vit<vitesse){
			int av = this.avance+1;
			if(av>Etat.AVANCE_MAX) {
				this.avance=Etat.AVANCE_MAX;
			} else {
				this.avance=av;
			}
			this.vitesse=vit;
		/**Si la vitesse augmente*/
		} else if(vit>vitesse) {
			int av = this.avance-1;
			if(av<Etat.AVANCE_MIN) {
				this.avance=Etat.AVANCE_MIN;
			} else {
				this.avance=av;
			}
			this.vitesse=vit;
		} else {
			this.vitesse=vit;
		}
		//System.out.println("VITESSE :"+this.vitesse);
	}
	
	/**M�thode qui renvoie vrai si la voiture est sur la route*/
	public boolean carOnRoad() {
		/**On r�cup�re l'abscisse des deux extremit�s de la route situ�e � la m�me hauteur de l'ovale*/
		ArrayList<Point> res = route.getRouteGauche();
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.LONGUEUR+2))+Affichage.LONGUEUR/2;
		int xPoint = res.get(0).x;
		int yPoint = res.get(0).y;
		int nextX=0;
		int nextY=0;
		boolean boucle = true;
		int i=1;
		/**Boucle utilis�e pour r�cuper�e les points de la route qui encadrent la voiture*/
		while(boucle) {
			if(res.get(i).y>y) {
				xPoint=res.get(i).x;
				yPoint=res.get(i).y;
				i+=1;
			} else { 
				nextX=res.get(i).x;
				nextY=res.get(i).y;
				boucle=false;
			}
		}
		/**On calcule l'abscisse gr�ce � au calcul de la pente*/
		float pente = ((nextY) - (yPoint)) / ((float)nextX - (float)xPoint);
		float xP = xPoint + ((y-yPoint)/pente);
		/**Abscisse de l'extremit� gauche*/
		int xG = (int)xP;
		/**Abscisse de l'extremit� droite*/
		int xD = (int)xP+route.Ecart;
		return this.x>=xG && this.x<=xD;
	}
	
	/**M�tthode utilis�e pour calculer l'acc�l�ration*/
	public int calcul_Acc() {
		/**On r�cup�re l'abscisse des deux extremit�s de la route situ�e � la m�me hauteur de l'ovale*/
		ArrayList<Point> res = route.getRouteGauche();
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.LONGUEUR+2))+Affichage.LONGUEUR/2;
		int xPoint = res.get(0).x;
		int yPoint = res.get(0).y;
		int nextX=0;
		int nextY=0;
		boolean boucle = true;
		int i=1;
		/**Boucle utilis�e pour r�cuper�e les points de la route qui encadrent la voiture*/
		while(boucle) {
			if(res.get(i).y>y) {
				xPoint=res.get(i).x;
				yPoint=res.get(i).y;
				i+=1;
			} else { 
				nextX=res.get(i).x;
				nextY=res.get(i).y;
				boucle=false;
			}
			
		}
		/**On calcule l'abscisse gr�ce � au calcul de la pente*/
		float pente = ((nextY) - (yPoint)) / ((float)nextX - (float)xPoint);
		float xP = xPoint + ((y-yPoint)/pente);
		/**Abscisse de l'extremit� gauche*/
		int xG = (int)xP;
		/**Abscisse de l'extremit� droite*/
		int xD = (int)xP+route.Ecart;
		/**On v�rifie que la voiture soit sur la route*/
		if(this.x>=xG && this.x<=xD) {
			/**On regarde si l'acc�l�ration est la m�me que pr�cedemment*/
			if(this.acceleration==1) {
				return 1;
			} else {
				this.acceleration=1;
				return 0;
			}	
		} else {
			if(this.acceleration==-1) {
				return -1;
			} else {
				this.acceleration=-1;
				return 0;
			}
		}	
	}
	
	/**M�thode utilis�e pour mettre � jour le temps*/
	public void majTemps() {
		if(this.tempsZero()) {
			this.temps=this.temps-0.005;
		}
	}
	
	/**M�thode utilis�e pour v�rifier si le temps est nul*/
	public boolean tempsZero() {
		return this.temps>0;
	}
	
	/**M�thodes utilis�es pour l'incr�mentation du temps*/
		public boolean add() {
			return this.add>0;
		}
	
		public void addTemps() {
			if(this.tempsZero() && this.add()) {
				this.temps=this.temps+this.add;
				this.add=this.add-0.005;
			}
		}
	
	
}