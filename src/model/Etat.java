package model;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.Affichage;

/**Classe charg�e de repr�senter le mod�le*/
public class Etat {
	
	/**Constantes*/
	
	/**Attributs*/
		
		/**Vitesse max*/
		public static final int VITESSE_MAX=700;
		
		/**Avance max*/
		public static final int AVANCE_MAX=90;
		
		/**Avance min*/
		public static final int AVANCE_MIN=10;
		
		public int ol = 0;
		
		public Affichage affichage;
	
		/**Abscisse de la voiture*/
		public int x;
		
		public Route route;
		
		/**Attribut utilis�e pour la fin de la partie*/
		public boolean continuer;
	
		/**Acceleration*/
		public int acceleration;
		
		/**Vitesse*/
		public int vitesse;
		
		/**Attributs utilis�es pour relier le d�filement de l'affichage et la vitesse de la voiture*/
		public double avance;
		
		/**Attributs utilis�es pour repr�senter le temps*/
		public double temps;
		
		/**Attribut utilis� pour incr�menter le temps*/
		public double add;
		
		/**Attributs utilis�es pour repr�senter la vitesse de d�placement vertical de la voiture*/
		public int deplacements;
	
	/**Constructeur*/	
	public Etat() {
		this.affichage = new Affichage(this);
		this.route = new Route(this);
		this.continuer = true;
		this.acceleration=1;
		this.vitesse=route.INCR*13;
		this.avance=this.vitesse/2;
		this.temps=30;
		this.add=5;
		this.deplacements=10;
		this.x=Affichage.x;
	}
	
	/**M�thode appel�e lorsque la voiture s� d�place vers la gauche*/
	public void moveLeft(int a) {
		/**On v�rifie que la voiture ne sort pas de l'interface graphique*/
		if(this.x > Affichage.EX_GAUCHE) {
			this.x-=a;
			this.affichage.decalage+=a;
		}
	}

	/**M�thode appel�e lorsque la voiture se d�place vers la droite*/
	public void moveRight(int a) {
		/**On v�rifie que la voiture ne sort pas de l'interface graphique*/
		if(this.x + Affichage.LARGEUR + 1 < Affichage.EX_DROITE) {
			this.x+=a;
			this.affichage.decalage-=a;
		}
	}
	
	/**M�thode utilis�e pour calculer la vitesse*/
	public void calcul_Vit() {
		/**On calcule la vitesse gr�ce � l'acc�l�ration*/
		int vit = this.vitesse+calcul_Acc()*2;
		/**Si la vitesse a diminu�*/
		 if(vit<vitesse){
			 /**On modifie aussi l'attribut utilis� pour le d�filement de l'�cran*/
			double av = this.avance+0.8;
			/**On v�rifie que les limites n'ont pas �t� atteintes et on modifie la valeur de l'attribut*/
			if(av>Etat.AVANCE_MAX) {
				this.avance=Etat.AVANCE_MAX;
			} else {
				this.avance=av;
			}
			/**On v�rifie que les limites n'ont pas �t� atteintes et on modifie la valeur de l'attribut*/
			if(vit<=0) {
				this.vitesse=0;
			} else {
				this.vitesse=vit;
			}
		/**Si la vitesse augmente*/
		} else if(vit>this.vitesse) {
			 /**On modifie aussi l'attribut utilis� pour le d�filement de l'�cran*/
			double av = this.avance-0.25;
			/**On v�rifie que les limites n'ont pas �t� atteintes et on modifie la valeur de l'attribut*/
			if(av<Etat.AVANCE_MIN) {
				this.avance=Etat.AVANCE_MIN;
			} else {
				this.avance=av;
			}
			/**On v�rifie que les limites n'ont pas �t� atteintes et on modifie la valeur de l'attribut*/
			if(vit>Etat.VITESSE_MAX) {
				this.vitesse=Etat.VITESSE_MAX;
			} else {
				this.vitesse=vit;
			}
		} else {
			/**Si la vitesse n'a pas chang�*/
			this.vitesse=vit;
		}
		/** On v�rifie s'il y a eu une collision avec les obstacles*/
		 	/**Ordonn�e de la voiture*/
		 	int y = (Affichage.HAUTEUR_FENETRE-(Affichage.HAUTEUR+2))+Affichage.HAUTEUR/2;
		 	/**On regarde pour chaque obstacle contenu dans la liste au sein de la classe Route*/
		 	for(int i=0;i<this.route.getObstacles().size();i++) {
		 		Point p = this.route.getObstacles().get(i);
		 		/**On v�rifie s'il y a eu collision*/
		 		if(((p.x>=this.x && p.x<=this.x+this.affichage.LARGEUR) || (p.x+this.route.largeurOb>=this.x && p.x+this.route.largeurOb<=this.x+this.affichage.LARGEUR)) && (p.y+this.route.hauteurOb>=y /**&& p.y+this.route.hauteurOb<=y+this.affichage.HAUTEUR*/)) {
		 			System.out.println("COLLISION");
		 			/**On modifie la vitesse*/
		 			int v = this.vitesse-40;
		 			/**On verifie que les limites n'ont pas �t� atteintes et on modifie la valeur de l'attribut*/
		 			if(v<=0) {
		 				this.vitesse = 0;
		 			} else {
		 				this.vitesse = v;
		 			}
		 			/**On modifie aussi la vitesse de d�filement de l'�cran*/
		 			double a = this.avance+16.0;
		 			/**On v�rifie que les limites n'ont pas �t� atteintes et on modifie la valeur de l'attribut*/
		 			if(a>=Etat.AVANCE_MAX) {
		 				this.avance=this.AVANCE_MAX;
		 			}else {
		 				this.avance = a;
		 			}
		 			/**On enleve l'obstacle*/
		 			this.route.obstacles.remove(i);
		 			this.route.dirOb.remove(i);
		 			this.route.xOb.remove(i);
		 			/**On sort de la boucle*/
		 			break;
		 		}
		 	}
		/**On modifie aussi la vitesse de d�filement horizontal de la voiture en fonction de sa vitesse*/
		if(this.avance>=20.0 && this.avance<=70) {
			this.deplacements=10;
		} else if(this.avance<20.0) {
			this.deplacements=5;
		} else if(this.avance>70) {
			this.deplacements=20;
		}
		/**Si la vitesse est nulle on arrete la partie et on un affiche un message*/
		if(this.vitesse==0) {
			this.continuer=false;
			JOptionPane.showMessageDialog(null,"Score: "+this.route.getScore()+" !");
		}
	}
	
	/**M�thode qui renvoie vrai si la voiture est sur la route*/
	public boolean carOnRoad() {
		/**On r�cup�re l'abscisse des deux extremit�s de la route situ�e � la m�me hauteur de la voiture*/
		ArrayList<Point> res = route.getRouteGauche();
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.HAUTEUR+2))+Affichage.HAUTEUR/2;
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
		/**On r�cup�re l'abscisse des deux extremit�s de la route situ�e � la m�me hauteur de la voiture*/
		ArrayList<Point> res = route.getRouteGauche();
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.HAUTEUR+2))+Affichage.HAUTEUR/2;
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
		if(this.x+this.affichage.LARGEUR>=xG && this.x<=xD) {
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
	
	/**M�thode utilis�e pour g�rer le franchissement d'un point de contr�le*/
	public void PDCFranchit() {
		/**Ordonn�e de la voiture*/
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.HAUTEUR+2))+Affichage.HAUTEUR/2;
		/**On regarde pour tous les points de contr�le*/
		for(int i=0;i<this.route.getPointDeControle().size();i++) {
			Point p = this.route.getPointDeControle().get(i);
			//System.out.println("POCOPOCO");
			/**Si le point de contr�le est franchi*/
			if(p.y>=y && p.y<=y+this.affichage.HAUTEUR && this.carOnRoad()) {
				/**On retire le point de contr�le*/
				this.route.pointsDeControle.remove(i);
				/**On incr�mente le temps*/
				this.addTemps();
				/**On cr�e un nouveau point de contr�le*/
				this.route.init_PDC();
				/**On sort de la boucle*/
				break;
			}
		}
	}
	
	
	/**M�thode utilis�e pour mettre � jour le temps*/
	public void majTemps() {
		/**On v�rifie que le temps n'est pas nul*/
		if(this.tempsZero()) {
			/**On d�cr�mente le temps*/
			this.temps=this.temps-0.0025;
		} else {
			/**Si le temps est nul on arrete la partie et on affiche un message*/
			this.continuer=false;
			JOptionPane.showMessageDialog(null,"Score: "+this.route.getScore()+" !");
		}
	}
	
	/**M�thode utilis�e pour v�rifier si le temps est nul*/
	public boolean tempsZero() {
		if(this.temps>0) {
			return true;
		} else {
			this.temps=0;
			return false;
		}
	}
	
	/**M�thode utilis�e pour l'incr�mentation du temps*/
		public void addTemps() {
			/**On incr�mente le temps que si il est pas �gal � 0*/
			if(this.tempsZero() ) {
				/**On incr�mente le temps*/
				this.temps=this.temps+this.add;
				/**On d�cremente add jusqu'� arriver � une valeur limite*/
				if(this.add-0.05>0.05) {
					this.add = this.add-0.05;
				} else {
					this.add=0.05;
				}
			}
		}
	
	
}