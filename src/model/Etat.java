package model;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.Affichage;

/**Classe chargée de représenter le modèle*/
public class Etat {
	
	/**Attributs*/
	public static int ol = 0;
		/**Bornes de la vitesse*/
		public static final int VITESSE_MAX=700;
		public static final int AVANCE_MAX=90;
		public static final int AVANCE_MIN=10;
	
		public Affichage affichage;
	
		/**Abscisse de la voiture*/
		public int x = Affichage.x;
		
		public Route route;
		
		/**Attribut utilisée pour la fin de la partie*/
		public boolean continuer;
	
		/**Acceleration*/
		public int acceleration;
		
		/**Vitesse*/
		public int vitesse;
		
		/**Attributs utilisées pour relier le défilement de l'affichage et la vitesse de la voiture*/
		public double avance;
		
		/**Attributs utilisées pour représenter le temps*/
		public double temps;
		public double add;
		
		/**Attributs utilisées pour représenter la vitesse de déplacement vertical de la voiture*/
		public int deplacements;
	
	/**Constructeur*/	
	public Etat() {
		this.affichage = new Affichage(this);
		this.route = new Route(this);
		this.continuer = true;
		this.acceleration=1;
		this.vitesse=100;
		this.avance=50.0;
		this.temps=2;
		this.add=5;
		this.deplacements=10;
	}
	
	/**Méthode appelée lorsque la voiture sé déplace vers la gauche*/
	public void moveLeft(int a) {
		/**On vérifie que la voiture ne sort pas de l'interface graphique*/
		if(this.x > -100) {
			this.x-=a;
			this.affichage.decalage+=a;
		}
		this.affichage.repaint();
	}

	/**Méthode appelée lorsque la voiture se déplace vers la droite*/
	public void moveRight(int a) {
		/**On vérifie que la voiture ne sort pas de l'interface graphique*/
		if(this.x + Affichage.LARGEUR + 1 < Affichage.LARGEUR_FENETRE+100) {
			this.x+=a;
			this.affichage.decalage-=a;
		}
		this.affichage.repaint();
	}
	
	/**Méthode utilisée pour calculer la vitesse*/
	public void calcul_Vit() {
		/**On calcule la vitesse grâce à l'accélération*/
		int vit = this.vitesse+calcul_Acc()*2;
		//System.out.println("VIT "+vit);
		/**Si la vitesse a dépassée la limite max*/
		/**if(vit>Etat.VITESSE_MAX) {
			this.vitesse=Etat.VITESSE_MAX;
		/**Si la vitesse est nulle*/ 
		/**} else if(vit<=0){
			this.vitesse=0;*/
		/**Si la vitesse diminue*/
		 if(vit<vitesse){
			double av = this.avance+0.8;
			if(av>Etat.AVANCE_MAX) {
				this.avance=Etat.AVANCE_MAX;
			} else {
				this.avance=av;
			}
			if(vit<=0) {
				this.vitesse=0;
			} else {
				this.vitesse=vit;
			}
		/**Si la vitesse augmente*/
		} else if(vit>this.vitesse) {
			//System.out.println("OK");
			double av = this.avance-0.25;
			if(av<Etat.AVANCE_MIN) {
				this.avance=Etat.AVANCE_MIN;
			} else {
				this.avance=av;
			}
			if(vit>Etat.VITESSE_MAX) {
				this.vitesse=Etat.VITESSE_MAX;
			} else {
				this.vitesse=vit;
			}
		} else {
			this.vitesse=vit;
		}
		/**Vérifie s'il y a eu collision avec les obstacles*/
		/**Vérifie s'il y a eu collision avec les obstacles*/
		
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.LONGUEUR+2))+Affichage.LONGUEUR/2;
		for(int i=0;i<this.route.getObstacles().size();i++) {
			Point p = this.route.getObstacles().get(i);
			//System.out.println("OBSTACLE : "+p);
			if(((this.x>=p.x && this.x<=p.x+this.route.largeurOb) || (this.x+this.affichage.LARGEUR>=p.x && this.x+this.affichage.LARGEUR<=p.x+this.route.largeurOb)) && (y>=p.y && y<=p.y+this.route.hauteurOb)) {
				System.out.println(ol);
				this.ol+=1;
				System.out.println("COLLISION");
				this.vitesse = 100;
				this.avance = 50;
				this.route.obstacles.remove(i);
				i-=1;
				break;
			}
		}
		System.out.println("VITESSE :"+this.vitesse);
		if(this.avance>=20.0 && this.avance<=70) {
			this.deplacements=10;
		} else if(this.avance<20.0) {
			this.deplacements=5;
		} else if(this.avance>70) {
			this.deplacements=20;
		}
		System.out.println("AVANCE"+this.avance);
		//System.out.println("DEPL"+this.deplacements);
		if(this.vitesse==0) {
			this.continuer=false;
			JOptionPane.showMessageDialog(null,"Score: "+this.route.getScore()+" !");
		}
	}
	
	/**Méthode qui renvoie vrai si la voiture est sur la route*/
	public boolean carOnRoad() {
		/**On récupère l'abscisse des deux extremités de la route située à la même hauteur de l'ovale*/
		ArrayList<Point> res = route.getRouteGauche();
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.LONGUEUR+2))+Affichage.LONGUEUR/2;
		int xPoint = res.get(0).x;
		int yPoint = res.get(0).y;
		int nextX=0;
		int nextY=0;
		boolean boucle = true;
		int i=1;
		/**Boucle utilisée pour récuperée les points de la route qui encadrent la voiture*/
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
		/**On calcule l'abscisse grâce à au calcul de la pente*/
		float pente = ((nextY) - (yPoint)) / ((float)nextX - (float)xPoint);
		float xP = xPoint + ((y-yPoint)/pente);
		/**Abscisse de l'extremité gauche*/
		int xG = (int)xP;
		/**Abscisse de l'extremité droite*/
		int xD = (int)xP+route.Ecart;
		return this.x>=xG && this.x<=xD;
	}
	
	/**Métthode utilisée pour calculer l'accélération*/
	public int calcul_Acc() {
		/**On récupère l'abscisse des deux extremités de la route située à la même hauteur de l'ovale*/
		ArrayList<Point> res = route.getRouteGauche();
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.LONGUEUR+2))+Affichage.LONGUEUR/2;
		int xPoint = res.get(0).x;
		int yPoint = res.get(0).y;
		int nextX=0;
		int nextY=0;
		boolean boucle = true;
		int i=1;
		/**Boucle utilisée pour récuperée les points de la route qui encadrent la voiture*/
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
		/**On calcule l'abscisse grâce à au calcul de la pente*/
		float pente = ((nextY) - (yPoint)) / ((float)nextX - (float)xPoint);
		float xP = xPoint + ((y-yPoint)/pente);
		/**Abscisse de l'extremité gauche*/
		int xG = (int)xP;
		/**Abscisse de l'extremité droite*/
		int xD = (int)xP+route.Ecart;
		/**On vérifie que la voiture soit sur la route*/
		if(this.x+this.affichage.LARGEUR>=xG && this.x<=xD) {
			/**On regarde si l'accélération est la même que précedemment*/
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
	
	
	
	/**Méthode utilisée pour mettre à jour le temps*/
	public void majTemps() {
		if(this.tempsZero()) {
			this.temps=this.temps-0.005;
		} else {
			this.continuer=false;
			JOptionPane.showMessageDialog(null,"Score: "+this.route.getScore()+" !");
		}
	}
	
	/**Méthode utilisée pour vérifier si le temps est nul*/
	public boolean tempsZero() {
		return this.temps>0;
	}
	
	/**Méthodes utilisées pour l'incrémentation du temps*/
		public boolean add() {
			return this.add>0.05;
		}
	
		public void addTemps() {
			if(this.tempsZero() && this.add()) {
				this.temps=this.temps+this.add;
				this.add=this.add-0.05;
			}
		}
	
	
}