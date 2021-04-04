package model;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import view.Affichage;
import view.Score;

/**Classe chargée de représenter le modèle*/
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
		
		public Score score;
		
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
		
		/**Attribut utilisé pour incrémenter le temps*/
		public double add;
		
		/**Attributs utilisées pour représenter la vitesse de déplacement vertical de la voiture*/
		public int deplacements;
		
		/**Tableau des meilleurs scores*/
		public int [] scores = new int[10];	
		
		/**Score du joueur dans le tableau*/
		public int indice;
		
		/**Score à la fin de la partie*/
		public int myScore;
		
		
	/**Constructeur*/	
	public Etat() {
		this.affichage = new Affichage(this);
		this.route = new Route(this);
		this.score = new Score(this);
		this.continuer = true;
		this.acceleration=1;
		this.vitesse=100;
		this.avance=50;
		this.temps=10;
		this.add=10;
		this.deplacements=10;
		this.x=Affichage.x;
		this.indice=20;
		}
	
	/**Méthode appelée lorsque la voiture sé déplace vers la gauche*/
	public void moveLeft(int a) {
		/**On vérifie que la voiture ne sort pas de l'interface graphique*/
		if(this.x > Affichage.EX_GAUCHE) {
			this.x-=a;
			this.affichage.decalage+=a;
		}
	}

	/**Méthode appelée lorsque la voiture se déplace vers la droite*/
	public void moveRight(int a) {
		/**On vérifie que la voiture ne sort pas de l'interface graphique*/
		if(this.x + Affichage.LARGEUR + 1 < Affichage.EX_DROITE) {
			this.x+=a;
			this.affichage.decalage-=a;
		}
	}
	
	/**Méthode utilisée pour calculer la vitesse
	 * @throws FileNotFoundException */
	public void calcul_Vit() throws FileNotFoundException {
		/**On calcule la vitesse grâce à l'accélération*/
		int vit = this.vitesse+calcul_Acc()*2;
		/**Si la vitesse a diminué*/
		 if(vit<vitesse){
			 /**On modifie aussi l'attribut utilisé pour le défilement de l'écran*/
			double av = this.avance+0.225;
			/**On vérifie que les limites n'ont pas été atteintes et on modifie la valeur de l'attribut*/
			if(av>Etat.AVANCE_MAX) {
				this.avance=Etat.AVANCE_MAX;
			} else {
				this.avance=av;
			}
			/**On vérifie que les limites n'ont pas été atteintes et on modifie la valeur de l'attribut*/
			if(vit<=0) {
				this.vitesse=0;
			} else {
				this.vitesse=vit;
			}
		/**Si la vitesse augmente*/
		} else if(vit>this.vitesse) {
			 /**On modifie aussi l'attribut utilisé pour le défilement de l'écran*/
			double av = this.avance-0.2;
			/**On vérifie que les limites n'ont pas été atteintes et on modifie la valeur de l'attribut*/
			if(av<Etat.AVANCE_MIN) {
				this.avance=Etat.AVANCE_MIN;
			} else {
				this.avance=av;
			}
			/**On vérifie que les limites n'ont pas été atteintes et on modifie la valeur de l'attribut*/
			if(vit>Etat.VITESSE_MAX) {
				this.vitesse=Etat.VITESSE_MAX;
			} else {
				this.vitesse=vit;
			}
		} else {
			/**Si la vitesse n'a pas changé*/
			this.vitesse=vit;
		}
		/** On vérifie s'il y a eu une collision avec les obstacles*/
		 	/**Ordonnée de la voiture*/
		 	int y = (Affichage.HAUTEUR_FENETRE-(Affichage.HAUTEUR+2))+Affichage.HAUTEUR/2;
		 	/**On regarde pour chaque obstacle contenu dans la liste au sein de la classe Route*/
		 	for(int i=0;i<this.route.getObstacles().size();i++) {
		 		Point p = this.route.getObstacles().get(i);
		 		/**On vérifie s'il y a eu collision*/
		 		if(((p.x>=this.x && p.x<=this.x+this.affichage.LARGEUR) || (p.x+this.route.largeurOb>=this.x && p.x+this.route.largeurOb<=this.x+this.affichage.LARGEUR)) && (p.y+this.route.hauteurOb>=y /**&& p.y+this.route.hauteurOb<=y+this.affichage.HAUTEUR*/)) {
		 			/**On modifie la vitesse*/
		 			int v = this.vitesse-200;
		 			/**On verifie que les limites n'ont pas été atteintes et on modifie la valeur de l'attribut*/
		 			if(v<=0) {
		 				this.vitesse = 0;
		 			} else {
		 				this.vitesse = v;
		 			}
		 			/**On modifie aussi la vitesse de défilement de l'écran*/
		 			double a = this.avance+20.0;
		 			/**On vérifie que les limites n'ont pas été atteintes et on modifie la valeur de l'attribut*/
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
		/**On modifie aussi la vitesse de défilement horizontal de la voiture en fonction de sa vitesse*/
		if(this.avance>=20.0 && this.avance<=70) {
			this.deplacements=14;
		} else if(this.avance<20.0) {
			this.deplacements=6;
		} else if(this.avance>70) {
			this.deplacements=20;
		}
		if(this.vitesse==0) {
			/**On met à jour les scores*/
			this.majScores();
			/**Si la vitesse est nulle on arrete la partie et on un affiche un message*/
			this.continuer=false;
			/**Fenetre*/
			JFrame j = new JFrame("Test");
			/**On appele l'écran avec les meilleurs scores*/
			score.createFenetre(j, this.score);
		}
	}
	
	/**Méthode qui renvoie vrai si la voiture est sur la route*/
	public boolean carOnRoad() {
		/**On récupère l'abscisse des deux extremités de la route située à la même hauteur de la voiture*/
		ArrayList<Point> res = route.getRouteGauche();
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.HAUTEUR+2))+Affichage.HAUTEUR/2;
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
		return this.x>=xG && this.x+this.affichage.LARGEUR<=xD;
	}
	
	/**Métthode utilisée pour calculer l'accélération*/
	public int calcul_Acc() {
		if(this.carOnRoad()) {
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
	
	/**Méthode utilisée pour gérer le franchissement d'un point de contrôle*/
	public void PDCFranchit() {
		/**Ordonnée de la voiture*/
		int y = (Affichage.HAUTEUR_FENETRE-(Affichage.HAUTEUR+2))+Affichage.HAUTEUR/2;
		/**On regarde pour tous les points de contrôle*/
		for(int i=0;i<this.route.getPointDeControle().size();i++) {
			Point p = this.route.getPointDeControle().get(i);
			//System.out.println("POCOPOCO");
			/**Si le point de contrôle est franchi*/
			if(p.y>=y && p.y<=y+this.affichage.HAUTEUR && this.carOnRoad()) {
				/**On retire le point de contrôle*/
				this.route.pointsDeControle.remove(i);
				/**On incrémente le temps*/
				this.addTemps();
				/**On crée un nouveau point de contrôle*/
				this.route.init_PDC();
				/**On sort de la boucle*/
				break;
			}
		}
	}
	
	
	/**Méthode utilisée pour mettre à jour le temps
	 * @throws FileNotFoundException */
	public void majTemps() throws FileNotFoundException {
		/**On vérifie que le temps n'est pas nul*/
		if(this.tempsZero()) {
			/**On décrémente le temps*/
			this.temps=this.temps-0.0025;
		} else {
			/**On met à jour les scores*/
			this.majScores();
			/**Si le tempts est nul on arrete la partie et on affiche un message*/
			this.continuer=false;
			JFrame j = new JFrame("Test");
			score.createFenetre(j, this.score);
		}
	}
	
	/**Méthode utilisée pour vérifier si le temps est nul*/
	public boolean tempsZero() {
		if(this.temps>=1) {
			return true;
		} else {
			this.temps=0;
			return false;
		}
	}
	
	/**Méthode utilisée pour l'incrémentation du temps*/
		public void addTemps() {
			/**On incrémente le temps que si il est pas égal à 0*/
			if(this.tempsZero() ) {
				/**On incrémente le temps*/
				this.temps=this.temps+this.add;
				/**On décremente add jusqu'à arriver à une valeur limite*/
				if(this.add-0.05>2) {
					this.add = this.add-0.05;
				} else {
					this.add=2;
				}
			}
		}
		
	/**Méthode utilisée pour mettre à jour le tableau de scores
	 * @throws FileNotFoundException */
		public void majScores() throws FileNotFoundException {
			String s = null;
			int i=0;
			this.myScore = this.route.getScore();
			boolean remplace = false;
			/**On sauvegarde les nouveaux scores dans un fichier*/
			try {
				/**On recupere le fichier*/
				File myObj = new File("src\\model\\score.txt");
				/**Variable qu'on utilise pour lire depuis le fichier*/
			    Scanner myReader = new Scanner(myObj);
			    while (myReader.hasNextLine() && i<10) {
			    	/**On recupere les caracteres d'une ligne*/
			    	String data = myReader.nextLine();
			    	/**On tranforme la chaine de caracteres en entier*/
			        int sc = Integer.parseInt(data);
			        /**On verifie si le score de la partie actuelle est meilleur*/
			        if(this.myScore>sc) {
			        	this.indice=i;
			        	remplace=true;
			        }
			        /**On initialise le tableau de scores*/
			        this.scores[i]=sc;
			        i++;
				}
			    /**On ferme le lecteur*/
			    myReader.close();
			    /**Si le score est dans le top 10*/
			    if(remplace) {
			    	/**On remplit le tableau de scores jusqu'au score de la partie actuelle*/
			    	for(int j=0;j<this.indice;j++) {
			    		this.scores[j]=this.scores[j+1];
			    	}
			    	this.scores[this.indice]=this.myScore;
			    }
			    /**Varaible qu'on utilise pour ecrire dans le fichier*/
			    FileWriter myWriter = new FileWriter(myObj);
			    /**Chaque ligne du fichier correspond au score du en ordre croissant*/
			    for(int j=0;j<10;j++) {
			    	myWriter.write(""+this.scores[j]+"\n");
			    }
			    /**On ferme l'écrivain*/
			    myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Erreur");
			}
		}
	
	
}