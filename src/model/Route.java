package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

import view.Affichage;
import view.Decor;

public class Route {
	
	/**Type utilis�e pour la direction de la route*/
	public enum Direction{ DROIT,GAUCHE,DROITE};
	
	public static Random rand = new Random();
	
	/**Constantes*/
		
		/**Largeur de la route*/
		public static final int Ecart = 200;
		
		/**Incr�ment du score*/
		public static final int INCR = 8;
	
	/**Attributs*/
	
		/**Liste des points de l'extremit� gauche de la route*/
		public ArrayList<Point> pointsGauche;
		
		/**Liste des points de contr�le*/
		public ArrayList<Point> pointsDeControle;
		
		/**Liste des directions de la route*/
		public ArrayList<Direction> directions;
	
		/**Distance entre 2 points de controle*/
		public int compteur;
		
		/**Attribut utilis� pour l'incr�mentation de la distance entre 2 point de contr�le*/
		public int sous_compt;
		
		/**Attribut utilis� pour l'incr�mentation de la distance entre 2 points de contr�le*/
		public int add;
		
		/**Liste des obstacles*/
		public ArrayList<Point> obstacles;
		/**Attribut utilis� pour l'ajout d'un nouvel obstacle*/
		public boolean ob;
		/**Abscisse de l'obstacle ajout�*/
		public int xPrecOb;
		/**Ordonn�e de l'obstacle ajout�*/
		public int yPrecOb;
		/**Largeur d'un obstacle*/
		public int largeurOb;
		/**Hauteur d'un obstacle*/
		public int hauteurOb;
		/**Attribut utilis� pour m'ajout d'un nouvel obstacle*/
		public boolean droit100;
		/**Attribut utilis� pour l'ajout d'un nouvel obstacle*/
		public boolean droit200;
		/**Attribut utilis� pour l'ajout d'un nouvel obstacle*/
		public boolean droit400;
		/**Attribut utilis� pour le d�placement horizontal des obstacles*/
		public boolean directionOrb;
		/**Directions des obstacles*/
		public ArrayList<Boolean> dirOb;
		/**Abscisse min de chaque obstacle*/
		public ArrayList<Integer> xOb;
	
		/**Attribut utilis� pour connaitre la direction de la route*/
		public Direction direction;
	
		/**Attribut utilis� pour repr�senter l'ordonn�e � atteindre*/
		public int objy;
	
		/**Valeur min de l'ordonn�e d'un point*/
		public int ymin;
		/**Valeur max de l'odonn�e d'un point*/
		public int ymax;
		/**Valeur min de l'abscisse d'un point*/
		public int xmin;
		/**Valeur max de l'abscisse d'un point*/
		public int xmax;
	
		/**Abscisse du dernier Point ajout� � la liste*/
		public int xPrecedent;
		/**Ordonn�e du dernier point ajout� � la liste*/
		public int yPrecedent;
		/**Abscisse du point � ajouter*/
		public int x1;
		/**Ordonn�e du point � ajouter*/
		public int y;
	
		/**Attribut utilis�e pour savoir si la limite est atteinte*/
		public boolean limite;
	
		/**Attribut repr�sentant le score du joueur*/
		public int score;
	
		public Etat etat;
	
	/**Constructeur*/
	public Route(Etat etat) {
		
		this.etat=etat;
		
		/**On d�clare les listes*/
		this.pointsGauche = new ArrayList<Point>();
		this.pointsDeControle = new ArrayList<Point>();
		this.obstacles = new ArrayList<Point>();
		this.directions = new ArrayList<Direction>();
		this.dirOb = new ArrayList<Boolean>();
		this.xOb = new ArrayList<Integer>();
		
		/**Initialisation de la liste de points gauche*/
		this.direction = Direction.DROIT;
		this.objy = 0;
		this.ymin = 250;
		this.ymax = 400;
		this.xmin = 0;
		this.xmax = Affichage.LARGEUR_FENETRE;
		this.x1=Affichage.LARGEUR_FENETRE/2-(this.Ecart/2);
		this.y=Affichage.HAUTEUR_FENETRE;
		this.limite=false;
		this.score=0;
		this.ob=false;
		this.largeurOb=20;
		this.hauteurOb=20;
		this.droit100=false;
		this.droit200=false;
		this.droit400=false;
		this.directionOrb=true;
		this.init();
		
		/**Initialisation de la liste de points de contr�le*/
		this.xPrecedent = this.pointsGauche.get(0).x;
		this.yPrecedent = this.pointsGauche.get(0).y;
		this.compteur=100;
		this.sous_compt=1;
		this.add=1000;
		this.init_PDC();
		for(int i=0;i<pointsGauche.size();i++) {
			//System.out.println(" POINT GAUCHE X: "+this.pointsGauche.get(i).x+" Y: "+pointsGauche.get(i).y+"\n");
		}  
		//System.out.println("PDC");
		for(Point p:this.pointsDeControle) {
			//System.out.println(p);
		}
		//System.out.println("OBSTACLES");
		for(Point p:this.obstacles) {
			//System.out.println(p);
		}
	}
	
	
	/**M�thode utilis�e pour ajouter un point � la route et pr�parer l'ajout du point suivant*/
	public void sous_init() {
		/**On ajoute la direction actuelle de la route � la liste de directions*/
		this.directions.add(this.direction);
		/**On met l'attribut limite � faux*/
		this.limite=false;
		/**On regarde qu'elle est la direction de la route*/
		if(this.direction==Direction.DROIT) {
			/**On met � jour l'attribut y*/
			this.y=this.objy;
			this.pointsGauche.add(new Point(this.x1,this.y));
		} else if(this.direction==Direction.DROITE) {
			/**On calcule l'abscisse du prochain point � ajouter*/
			this.x1=this.x1+(this.y-this.objy);
			this.y=this.objy;
			/**On v�rifie que le point ne d�passe pas les bornes qui lui ont �t� donn�*/
			if(this.x1+this.Ecart>=this.xmax) {
				/**Si les bornes sont d�pass�s on choisi nous m�me la direction de la prochaine portion de route qui sera celle oppos�e � la direction actuelle*/
				this.x1 = this.xmax-this.Ecart;
				this.limite=true;
				this.direction = Direction.GAUCHE;
			}
			this.pointsGauche.add(new Point(this.x1,this.y));
		} else if(this.direction==Direction.GAUCHE) {
			this.x1=this.x1-(this.y-this.objy);
			this.y=this.objy;
			if(this.x1<=this.xmin) {
				this.x1=this.xmin; 
				this.limite=true;
				this.direction=Direction.DROITE;
			}
			this.pointsGauche.add(new Point(this.x1,this.y));
		}
		/**On ajoute les obstacles*/
		if(this.ob) {
			this.ajouteObstacle();
		}
		/**Maintenant que le point a �t� ajout� on pr�pare l'ajot du point suivant*/
		/**Nouvel objectif*/
		this.objy=this.y - (rand.nextInt(this.ymax-this.ymin)+this.ymin);
		/**Nouvelle direction si les bornes n'ont pas �t� atteintes*/
		if(!this.limite) {
			int r = rand.nextInt(5);
			if(r<3) {
				this.direction=Direction.DROIT;
				/**On n'ajoute des obstacles que quand la route est droite pour des probl�mes de jouabilit�*/
				int t = rand.nextInt(3);
				if(t==0) {
					this.objy=this.y-100;
					this.droit100=true;
				} else if(t==1) {
					this.objy=this.y-200;
					this.droit200=true;
				} else {
					this.objy=this.y-400;
					this.droit400=true;
				}
				this.ob=true;
				this.xPrecOb=this.x1;
				this.yPrecOb=this.y;
			} else if(r==3) {
				this.direction=Direction.DROITE;
			} else {
				this.direction=Direction.GAUCHE;
			}
		}
	}
	
	
	/**M�thode qui intialise la liste de points*/
	public void init() {
		this.pointsGauche.add(new Point(this.x1,this.y));
		/**Objcetif du deuxi�me point(toujours le m�me)*/
		this.objy = this.y - 100;
		/**On ajoute toujours des points au d�l� de l'horizion(de la zone visible) pour pr�voir l'acc�l�ration du d�filement de la route*/
		while(this.objy>Decor.HORIZON-(this.ymax*10)) {
			this.sous_init();
		}
	}
	
	
	
	/**M�thode utilis�e pour ajouter un obstacle*/
	public void ajouteObstacle() {
		/**Abscisse min des obstacles que l'on va ajouter*/
		int xminOb = this.xPrecOb+(this.largeurOb+3);
		/**Abscisse max des obstacles que l'on va ajouter*/
		int xmaxOb = (this.xPrecOb+this.Ecart)-(this.largeurOb+3);
		/**Ordonn�e min des obstacles que l'on va ajouter*/
		int yminOb = this.yPrecOb - (this.hauteurOb+3);
		/**Si la longueur est de 100*/
		if(this.droit100) {
			for(int i=50;i<100;i+=50) {
				this.rand = new Random();
				/**Abscisse du nouvel obstacle que l'on va ajouter*/
				int xOb = rand.nextInt(xmaxOb-xminOb)+xminOb;
				/**Ordonn�e du nouvel obstacle que l'on va ajouter*/
				int yOb= yminOb-i;
				Point p = new Point(xOb,yOb);
				this.obstacles.add(p);
				/**On ajotue la direction de l'obstacle � l'attribut dirOb*/
				this.dirOb.add(this.directionOrb);
				/**On ajoute l'ordonn�e min de l'obstacle � l'attribut xOb*/
				this.xOb.add(this.x1);
				/**La direction d'un obstacle est l'inverse du celui pr�c�dent*/
				this.directionOrb=!this.directionOrb;
			}
		/**Si la longueur est de 200*/
		} else if(this.droit200) {
			for(int i=100;i<200;i+=100) {
				this.rand = new Random();
				int xOb = rand.nextInt(xmaxOb-xminOb)+xminOb;
				int yOb= yminOb-i;
				Point p = new Point(xOb,yOb);
				this.obstacles.add(p);
				this.dirOb.add(this.directionOrb);
				this.xOb.add(this.x1);
				this.directionOrb=!this.directionOrb;
			}
		/**Si la longueur est de 400*/
		} else if(this.droit400) {
			for(int i=100;i<400;i+=100) {
				this.rand = new Random();
				int xOb = rand.nextInt(xmaxOb-xminOb)+xminOb;
				int yOb= yminOb-i;
				Point p = new Point(xOb,yOb);
				this.obstacles.add(p);
				this.dirOb.add(this.directionOrb);
				this.xOb.add(this.x1);
				this.directionOrb=!this.directionOrb;
			}
		}
		/**On met l'attribut ob � false*/
		this.ob=false;
		this.droit100=false;
		this.droit200=false;
		this.droit400=false;
	}
	
	
	/**M�thode qui initialise la liste de points de contr�le*/
	public void init_PDC() {
		/**Boucle qui permet de r�cup�rer les points de la route qui encadrent le point de controle qu'on veut placer*/
		for(int i=1; i<this.pointsGauche.size();i++) {
			Point p = this.pointsGauche.get(i);
			if(p.y<=Affichage.HAUTEUR_FENETRE-this.compteur) {
				/**Lorsqu'on a r�cup�r� les points on calcule la pente afin de trouver l'abscisse du point de contr�le � placer*/
				float pente = ((p.y) - (this.yPrecedent)) / ((float)p.x - (float)this.xPrecedent);
				/**Abscisse du point de contr�le*/
				float  xP = this.xPrecedent + (((Affichage.HAUTEUR_FENETRE-this.compteur)-this.yPrecedent)/pente);
				this.pointsDeControle.add(new Point((int)xP,Affichage.HAUTEUR_FENETRE-this.compteur));
				/**On ajoute �galement les points � la liste que l'on va utiliser pour la cas o� la voiture franchit un point de contr�le*/
				/**On met � jour le compteur, charg� de l'�cart de plus en plus grand entre les points de contr�le*/
				/**On a d�cid� de placer la limite de la distance maximale entre les points de contr�le � 500*/
				if(this.add!=5000) {
						this.add=this.add+250;
				}
				/**On met � jour la distance entre les points de contr�le*/
				this.compteur=this.compteur+this.add;
			}
			this.xPrecedent = p.x;
			this.yPrecedent = p.y;
		}
	}
	
	
	
	/**M�thode qui renvoie les points visibles et met � jour la liste de points de al route*/
	public ArrayList<Point> getRouteGauche(){
		ArrayList<Point> res = new ArrayList<Point>();
		for(int i = 0;i<this.pointsGauche.size();i+=2) {
			/**On v�rifie que les points ne sont pas sortis de l'interface graphique*/
			if(i<this.pointsGauche.size()-1) {
				Point p1 = this.pointsGauche.get(i);
                Point p2 = this.pointsGauche.get(i+1);
                if(p2.getY()<=Affichage.HAUTEUR_FENETRE-this.score) {
                	res.add(new Point(p1.x,p1.y+this.score));
                } else {
                	/**Si ils sont sortis on les enleve et on en ajoute un nouveau*/
                	this.pointsGauche.remove(i);
                	this.directions.remove(i);
                	this.sous_init();
                	i-=1;
                	this.init_PDC();
                }
                res.add(new Point(p2.x,p2.y+this.score));
			} else {
				Point p1 = this.pointsGauche.get(i);
				res.add(new Point(p1.x,p1.y+this.score));
			}
		}
		return res;
	}
	
	
	
	
	/**M�thode qui renvoie les points de contr�le visibles*/
	public ArrayList<Point> getPointDeControle(){
		ArrayList<Point> res = new ArrayList<Point>();
		for(int i=0;i<this.pointsDeControle.size();i++) {
			Point p = this.pointsDeControle.get(i);
			/**On v�rifie que les points ne sont pas sortis de l'interface graphique*/
			if(p.y<=Affichage.HAUTEUR_FENETRE-this.score) {
				res.add(new Point(p.x,p.y+this.score));
			} else {
				this.pointsDeControle.remove(i);
				i-=1;
			}
		}
		return res;
	}
	
	
	
	/**M�thode qui renvoie les obstacle visibles*/
	public ArrayList<Point> getObstacles(){
		ArrayList<Point> res = new ArrayList<Point>();
		for(int i=0;i<this.obstacles.size();i++) {
			Point p = this.obstacles.get(i);
			/**On v�rifie que les points ne sont pas sortis de l'interface graphique*/
			if(p.y<=Affichage.HAUTEUR_FENETRE-this.score) {
				res.add(new Point(p.x,p.y+this.score));
			} else {
				this.obstacles.remove(p);
				this.dirOb.remove(i);
				this.xOb.remove(i);
				i-=1;
			}
		}
		return res;
	}
	
	
	
	/**M�thode qui renvoie le score du joueur*/
	public int getScore() {
		return this.score/Route.INCR;
	}
	
	
	/**M�thode qui met � jour le score*/
	public void setScore() {
		 this.score+=Route.INCR;
	}
	
	/**M�thode qui met � jour la position des obstacles*/
	public void bougeObstacles() {
		for(int i=0;i<this.obstacles.size();i++) {
			Point p = this.obstacles.get(i);
			if(this.dirOb.get(i)) {
				/**Si l'obstacle se d�place vers la gauche*/
				int xO = p.x - 1;
				/**on v�rifie que l'extremit� de la route n'a pas �t� atteinte*/
				if(xO<=this.xOb.get(i)) {
					/**Si l'extremit� est atteinte on change la direction de l'obstacle*/
					this.dirOb.set(i, !this.dirOb.get(i));
					xO=this.xOb.get(i);
				}
				/**On met � jour la liste d'obstacles*/
				this.obstacles.set(i, new Point(xO,p.y));
			} else {
				/**Si l'obstacle se d�place vers la droite*/
				int xO = p.x + 1;
				if(xO+this.largeurOb>=this.xOb.get(i)+this.Ecart) {
					this.dirOb.set(i, !this.dirOb.get(i));
					xO=this.xOb.get(i)+this.Ecart-this.largeurOb;
				}
				/**on met � jour la liste d'obstacles*/
				this.obstacles.set(i, new Point(xO,p.y));
			}
		}
	}
	

}
