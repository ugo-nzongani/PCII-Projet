package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

import view.Affichage;
import view.Decor;

public class Route {
	
	/**Type utilisée pour la direction de la route*/
	public enum Direction{ DROIT,GAUCHE,DROITE};
	
	/**Attributs*/
		
		public static final Random rand = new Random();
	
		/**Liste des points de l'extremité gauche de la route*/
		public ArrayList<Point> pointsGauche;
		
		/**Liste des points de contrôle*/
		public ArrayList<Point> pointsDeControle;
	
		/**Attributs représentant l'écart entre les points de contrôle*/
		public int compteur;
		public int sous_compt;
		public int add;
		
		/**Liste des obstacles et attributs utilisés pour leur gestion*/
		public ArrayList<Point> obstacles;
		public boolean ob;
		public int xPrecOb;
		public int yPrecOb;
		public int largeurOb;
		public int hauteurOb;
		public boolean droit100;
		public boolean droit200;
		public boolean droit400;
		
		/**Largeur de la route*/
		public int Ecart = 200;
		
		/**Attributs utilisées pour gérer le franchissement d'un point de contrôle par la voiture*/
		public ArrayList<Point> pointsRemove;				
		public boolean premierPoint;
	
		/**Constante utilisée pour incrémenter le score*/
		public static final int INCR = 8;
	
		/**Attribut utilisé pour connaitre la direction de la route*/
		public Direction direction;
	
		/**Attribut utilisé pour représenter l'ordonnée à atteindre*/
		public int objy;
	
		/**Valeurs min et max des coordonnées x et y*/
		public int ymin;
		public int ymax;
		public int xmin;
		public int xmax;
	
		/**Coordonnées du dernier Point ajouté à la liste*/
		public int xPrecedent;
		public int yPrecedent;
		public int x1;
		public int x2;
		public int y;
	
		/**Attribut utilisée pour savoir si la limite est atteinte*/
		public boolean limite;
	
		/**Attribut représentant le score du joueur*/
		public int score;
	
		public Etat etat;
	
	/**Constructeur*/
	public Route(Etat etat) {
		/**On déclare les listes*/
		this.pointsGauche = new ArrayList<Point>();
		this.pointsDeControle = new ArrayList<Point>();
		this.pointsRemove = new ArrayList<Point>();
		this.obstacles = new ArrayList<Point>();
		
		/**Initialisation de la liste de points gauche*/
		this.direction = Direction.DROIT;
		this.objy = 0;
		this.ymin = 250;
		this.ymax = 400;
		this.xmin = 0;
		this.xmax = Affichage.LARGEUR_FENETRE;
		this.x1=Affichage.LARGEUR_FENETRE/2-(this.Ecart/2);
		this.x2=this.x1+this.Ecart;
		this.y=Affichage.HAUTEUR_FENETRE;
		this.limite=false;
		this.score=0;
		this.ob=false;
		this.largeurOb=20;
		this.hauteurOb=20;
		this.droit100=false;
		this.droit200=false;
		this.droit400=false;
		this.init();
		
		/**Initialisation de la liste de points de contrôle*/
		this.xPrecedent = this.pointsGauche.get(0).x;
		this.yPrecedent = this.pointsGauche.get(0).y;
		//this.pointsDeControle.add(new Point(this.xPrecedent,this.yPrecedent));
		this.compteur=100;
		this.sous_compt=1;
		this.add=300;
		this.init_PDC();
		
		for(int i=0;i<pointsGauche.size();i++) {
			System.out.println(" POINT GAUCHE X: "+this.pointsGauche.get(i).x+" Y: "+pointsGauche.get(i).y+"\n");
		}  
		System.out.println("PDC");
		for(Point p:this.pointsDeControle) {
			System.out.println(p);
		}
		System.out.println("OBSTACLES");
		for(Point p:this.obstacles) {
			System.out.println(p);
		}
		this.etat=etat;
	}
	
	/**Méthode utilisée pour ajouter un point à la route et préparer l'ajout du point suivant*/
	public void sous_init() {
		this.limite=false;
		/**On regarde qu'elle est la direction de la route*/
		if(this.direction==Direction.DROIT) {
			this.y=this.objy;
			this.pointsGauche.add(new Point(this.x1,this.y));
		} else if(this.direction==Direction.DROITE) {
			/**On change la valeur du x et du y*/
			this.x1=this.x1+(this.y-this.objy);
			//this.x2=this.x2+(this.y-this.objy);
			this.y=this.objy;
			/**On vérifie que le point ne dépasse pas les bornes qui lui ont été donné*/
			if(this.x1+this.Ecart>=this.xmax) {
				/**Si les bornes sont dépassés on choisi nous mêmel a direction de la prochaine portion de route qui sera celle opposée à la direction actuelle*/
				
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
		/**On ajoute l'obstacle*/
		if(this.ob) {
			this.ajouteObstacle();
		}
		/**Mantenant que le point a été ajouté on prépare l'ajot du point suivant*/
		/**Nouvel objectif*/
		this.objy=this.y - (rand.nextInt(ymax-ymin)+ymin);
		/**Nouvelle direction si les bornes n'ont pas été atteintes*/
		if(!this.limite) {
			int r = rand.nextInt(5);
			if(r<3) {			
				this.direction=Direction.DROIT;
				/**On n'ajoute des obstacles que quand la route est droite pour des problèmes de jouabilité*/
				int t = rand.nextInt(3);
				if(t==1) {
					this.objy=this.y-300;
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
		/**if(this.direction == Direction.DROIT) {
			this.objy=this.y - (rand.nextInt(ymax-ymin)+ymin);
		} else if (this.direction==Direction.DROITE) {
			float pente = ((this.y-(Route.INCR))-(this.y))/(((float)this.x1+3)-((float)this.x1));
			this.objy = (int)(this.y+pente*((rand.nextInt((this.x1+100)-(this.x1+30))+(this.x1+30))-this.x1));
		} else {
			float pente = ((this.y-(Route.INCR))-(this.y))/(((float)this.x1-3)-((float)this.x1));
			this.objy = (int)(this.y+pente*((rand.nextInt((this.x1-30)-(this.x1-100))+(this.x1-100))-this.x1));
		}*/
	}
	
	
	
	
	
	/**Méthode qui intialise la liste de points*/
	public void init() {
		this.pointsGauche.add(new Point(this.x1,this.y));
		/**Objcetif du deuxième point(toujours le même)*/
		this.objy = this.y - 100;
		/**On ajoute toujours des points au délà de l'horizion(de la zone visible) pour prévoir l'accélération du défilement de la route*/
		while(this.objy>Decor.HORIZON-(this.ymax*2)) {
			System.out.println("INIT");
			this.sous_init();
		}
	}
	
	
	/**Méthode utilisée pour ajouter un obstacle*/
	public void ajouteObstacle() {
		//System.out.println("OBSTACLE");
		//System.out.println("XPREC :"+xPrecOb);
		//System.out.println("YPREC :"+yPrecOb);
		int xminOb = this.xPrecOb+(this.largeurOb+3);
		int xmaxOb = (this.xPrecOb+this.Ecart)-(this.largeurOb+3);
		int yminOb = this.yPrecOb - (this.hauteurOb+3);
		if(this.droit100) {
			for(int i=20;i<100;i+=60) {
				int xOb = rand.nextInt(xmaxOb-xminOb)+xminOb;
				int yOb= yminOb-i;
				Point p = new Point(xOb,yOb);
				//System.out.println(p);
				this.obstacles.add(p);
			}
		} else if(this.droit200) {
			for(int i=20;i<200;i+=60) {
				int xOb = rand.nextInt(xmaxOb-xminOb)+xminOb;
				int yOb= yminOb-i;
				Point p = new Point(xOb,yOb);
				//System.out.println(p);
				this.obstacles.add(p);
			}
		} else if(this.droit400) {
			for(int i=20;i<400;i+=60) {
				int xOb = rand.nextInt(xmaxOb-xminOb)+xminOb;
				int yOb= yminOb-i;
				Point p = new Point(xOb,yOb);
				//System.out.println(p);
				this.obstacles.add(p);
			}
		}
		/**int xminOb = this.xPrecOb+(this.largeurOb+3);
		int xmaxOb = (this.x1+this.Ecart)-(this.largeurOb+3);
		int yminOb = this.yPrecedent - (this.hauteurOb+3);	
		int ymaxOb = this.y + (this.hauteurOb+3);
		int yCalcul = java.lang.Math.abs(ymaxOb)-java.lang.Math.abs(yminOb);
		for(int i=0;i<3;i++) {
			int xOb = rand.nextInt(xmaxOb-xminOb)+xminOb;
			int yOb = yminOb - rand.nextInt(yCalcul);
			this.obstacles.add(new Point(xOb,yOb));
		}*/
		this.ob=false;
		this.droit100=false;
		this.droit200=false;
		this.droit400=false;
	}
	
	/**Méthode qui initialise la liste de points de contrôle*/
	public void init_PDC() {
		//System.out.println("PDC_INIT");
		//System.out.println("XPREC : "+this.xPrecedent+" YPREC : "+this.yPrecedent+" X : "+this.x1+" Y : "+this.y);
		/**Boucle qui permet de récupérer les points de la route qui encadrent le point de controle qu'on veut placer*/
		for(int i=1; i<this.pointsGauche.size();i++) {
			Point p = this.pointsGauche.get(i);
			//System.out.println("POINT BOUCLE"+p);
			//System.out.println(p);
			if(p.y<=Affichage.HAUTEUR_FENETRE-this.compteur) {
				//System.out.println("XPREC : "+this.xPrecedent+" YPREC : "+this.yPrecedent+" X : "+p.x+" Y : "+p.y);
				/**Lorsqu'on a récupéré les points on calcule la pente afin de trouver l'abscisse du point de contrôle à placer*/
				float pente = ((p.y) - (this.yPrecedent)) / ((float)p.x - (float)this.xPrecedent);
				float  xP = this.xPrecedent + (((Affichage.HAUTEUR_FENETRE-this.compteur)-this.yPrecedent)/pente);
				this.pointsDeControle.add(new Point((int)xP,Affichage.HAUTEUR_FENETRE-this.compteur));
				/**On ajoute également les points à la liste que l'on va utiliser pour la cas où la voiture franchit un point de contrôle*/
				//System.out.println("OK");
				//System.out.println("POINT AJOUTE"+new Point((int)xP,Affichage.HAUTEUR_FENETRE-this.compteur));
				this.pointsRemove.add(new Point((int)xP,Affichage.HAUTEUR_FENETRE-this.compteur));
				/**On met à jour le compteur, chargé de l'écart de plus en plus grand entre les points de contrôle*/
				this.sous_compt=this.sous_compt+1;
				/**On a décidé de placer la limite de la distance maximale entre les points de contrôle à 500*/
				if(this.sous_compt==5) {
					this.sous_compt=0;
					if(this.add!=1000) {
						this.add=this.add+100;
					}
				}
				this.compteur=this.compteur+this.add;
			}
			this.xPrecedent = p.x;
			this.yPrecedent = p.y;
		}
	}
	
	
	/**Méthode qui renvoie les points visibles et met à jour la liste de points de al route*/
	public ArrayList<Point> getRouteGauche(){
		ArrayList<Point> res = new ArrayList<Point>();
		for(int i = 0;i<this.pointsGauche.size();i+=2) {
			/**On vérifie que les points ne sont pas sortis de l'interface graphique*/
			if(i<this.pointsGauche.size()-1) {
				Point p1 = this.pointsGauche.get(i);
                Point p2 = this.pointsGauche.get(i+1);
                if(p2.getY()<=Affichage.HAUTEUR_FENETRE-this.score) {
                	res.add(new Point(p1.x,p1.y+this.score));
                } else {
                	/**Si ils sont sortis on les enleve et on en ajoute un nouveau*/
                	this.pointsGauche.remove(i);
                	this.sous_init();
                	i-=1;
                }
                res.add(new Point(p2.x,p2.y+this.score));
			} else {
				Point p1 = this.pointsGauche.get(i);
				res.add(new Point(p1.x,p1.y+this.score));
			}
		}
		return res;
	}
	
	
	/**Méthode qui renvoie les points de contrôle visibles et vérifie si un point de contrôle a été franchi par la voiture*/
	public ArrayList<Point> getPointDeControle(){
		ArrayList<Point> res = new ArrayList<Point>();
		for(int i=0;i<this.pointsDeControle.size();i++) {
			Point p = this.pointsDeControle.get(i);
			if(p.y<=Affichage.HAUTEUR_FENETRE-this.score) {
				res.add(new Point(p.x,p.y+this.score));
			} else {
				/**Si le point est sorti de l'interface graphique on vérifie s'il a été franchi par la voiture 
				 * et on vérifie aussi si que pour un même point la méthode n'est pas apellée plusieurs fois
				 */
				/**Cas à part pour le premier point de contrôle*/
				if(this.premierPoint) {
					/**On sort le point de la liste*/
					res.remove(p);
					/**On en ajoute un nouveau*/
					this.init_PDC();
					/**Si le temps n'est pas null on le modifie*/
					if(this.etat.tempsZero() && this.etat.carOnRoad()) {
						this.etat.addTemps();
					}
					this.pointsRemove.remove(0);
					this.premierPoint=false;
				} else {
					res.remove(p);
					this.init_PDC();
					/**On vérifie que la modidifaction du temps pour ce point n'a pas déjà été effectuée*/
					if(p.equals(this.pointsRemove.get(0))) {
						if(this.etat.tempsZero() && this.etat.carOnRoad()) {
							this.etat.addTemps();
						}
						this.pointsRemove.remove(0);
					}
				}
			}
		}
		return res;
	}
	
	/**Méthode qui renvoie les obstacle visibles*/
	public ArrayList<Point> getObstacles(){
		ArrayList<Point> res = new ArrayList<Point>();
		for(int i=0;i<this.obstacles.size();i++) {
			Point p = this.obstacles.get(i);
			/**On vérifie que les points ne sont pas sortis de l'interface graphique*/
			if(p.y<=Affichage.HAUTEUR_FENETRE-this.score) {
				res.add(new Point(p.x,p.y+this.score));
			} else {
				this.obstacles.remove(p);
				i-=1;
			}
		}
		return res;
	}
	
	/**Méthode qui renvoie le score du joueur*/
	public int getScore() {
		return this.score/Route.INCR;
	}
	
	
	/**Méthode qui met à jour le score*/
	public void setScore() {
		 this.score+=Route.INCR;
	}
	

}
