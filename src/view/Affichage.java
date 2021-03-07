package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;

import control.Control;
import model.Etat;
import model.Route.Direction;


public class Affichage extends JPanel{
	
	/**Constante*/
	
		/**Largeur de l'interface graphique*/
		public static final int LARGEUR_FENETRE = 800;
		
		/**Hauteur de l'interface graphique*/
		public static final int HAUTEUR_FENETRE = 500;
	
		/**Extrémité gauche de l'interface graphique*/
		public static final int EX_GAUCHE = -100;

		/**Extrémité droite de l'interface graphique*/
		public static final int EX_DROITE = LARGEUR_FENETRE + 100;
		
		/**Largeur de la voiture*/
		public static final int LARGEUR = 33;
		
		/**Hauteur de la voiture*/
		public static final int HAUTEUR = 60;
		
		/**Abscisse de la voiture*/
		public static final int x = LARGEUR_FENETRE/2;
		
	/**Attributs*/
		
		public Etat etat;
		public Control control;
		public Decor decor;
		
		/**Attribut utilisée pour le défilement du décor et de la route*/
		public int decalage = 0;
				
		/**Attributs utilisés ppur l'affichage de l'image de la voiture*/
		
			Toolkit t;
			Toolkit t2;
			Toolkit t3;
			/**Image de la voiture lorsqu'elle est sur une ligne droite*/
			Image i;
			/**Image de la voiture lorsqu'elle tourne à gauche*/
			Image i2;
			/**Image de la voiture lorsqu'elle tourne à droite*/
			Image i3;
		
	/**Constructeur*/
		public Affichage(Etat e) {
			this.etat = e;
			setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
			setFocusable(true);
			/**On ajoute le controleur*/
			this.control = new Control(this, e);
			this.addKeyListener(this.control);	
			this.decor = new Decor(this, e);
			/**Initialisation de l'image*/
			this.t = Toolkit.getDefaultToolkit();
			this.t2 = Toolkit.getDefaultToolkit();
			this.t3 = Toolkit.getDefaultToolkit();
			this.i = t.getImage("C:\\Users\\gamin\\eclipse-workspace\\voiture\\src\\view\\car423.png");
			this.i2 = t2.getImage("C:\\Users\\gamin\\eclipse-workspace\\voiture\\src\\view\\car423g.png");
			this.i3 = t3.getImage("C:\\Users\\gamin\\eclipse-workspace\\voiture\\src\\view\\car423d.png");
		}
		
	
	/**Affichage de la voiture*/
	public void paintVoiture(Graphics g) {
		//g.drawRect(Affichage.x, HAUTEUR_FENETRE-(HAUTEUR+2), LARGEUR, HAUTEUR);
		/**Si la voiture n'est pas sur la route alors on ne modifie pas son affichage*/
		/*et on garde l'affichage pour lequel la voiture est sur une ligne droite*/
		if(this.etat.carOnRoad()) {
			if(this.etat.route.directions.get(0)==Direction.DROIT) {
				g.drawImage(this.i,Affichage.x-3,HAUTEUR_FENETRE-(HAUTEUR+2),this);
			} else if(this.etat.route.directions.get(0)==Direction.GAUCHE) {
				g.drawImage(this.i2,Affichage.x-3,HAUTEUR_FENETRE-(HAUTEUR+2),this);
			} else {
				g.drawImage(this.i3,Affichage.x-3,HAUTEUR_FENETRE-(HAUTEUR+2),this);
			}
		} else {
			g.drawImage(this.i,Affichage.x,HAUTEUR_FENETRE-(HAUTEUR+2),this);
		}
	}
	
	/**Affichage de la route*/
	public void paintRoute(Graphics g) {
		for(int i = 0; i < etat.route.getRouteGauche().size()-1; i++) {
			Point p1 = etat.route.getRouteGauche().get(i);
			Point p2 = etat.route.getRouteGauche().get(i+1);
			/**Extremité gauche*/
			g.drawLine(p1.x+decalage, p1.y, p2.x+decalage, p2.y);
			/**Extremité droite*/
			g.drawLine(p1.x+etat.route.Ecart+decalage, p1.y, p2.x+etat.route.Ecart+decalage, p2.y);
		}
	}
	
	
	/**Affichage des points de controle*/
	public void paintPointDeControle(Graphics g) {
		for(Point p:this.etat.route.getPointDeControle()) {
			g.drawLine(p.x+decalage,p.y,p.x+this.etat.route.Ecart+decalage,p.y);
		}
	}
	
	/**Affichage du décor*/
	public void paintDecor(Graphics g) {
		/**Horizon*/
		g.clearRect(0,0,LARGEUR_FENETRE,Decor.HORIZON);
        g.drawLine(EX_GAUCHE+decalage, Decor.HORIZON, EX_DROITE+decalage, Decor.HORIZON);
    	/**Montagnes*/
        for(int i = 0; i < this.decor.pointList.size()-1; i++) {
        	Point p1 = this.decor.pointList.get(i);
        	Point p2 = this.decor.pointList.get(i+1);
        	g.drawLine(p1.x+decalage, p1.y, p2.x+decalage, p2.y);
        }
        /**Extremités de l'écran*/
        g.drawLine(EX_GAUCHE+decalage, LARGEUR_FENETRE, EX_GAUCHE+decalage, 0);
        g.drawLine(EX_DROITE+decalage, LARGEUR_FENETRE, EX_DROITE+decalage, 0);
	}
	
	
	/**Affichage des informations*/
	public void paintInformations(Graphics g) {
		/**Vitesse*/
		g.setColor(Color.BLACK);
		g.fillRect(0, HAUTEUR_FENETRE-40, 100, 40);
		g.setColor(Color.WHITE);
		String v1 = "Vitesse :";
        g.drawString(v1,1,HAUTEUR_FENETRE-28);
        String v2 = ""+etat.vitesse+"km/h";
        g.drawString(v2, 1, HAUTEUR_FENETRE-10);
        /**Temps*/
        g.setColor(Color.BLACK);
        g.fillRect(LARGEUR_FENETRE-100,HAUTEUR_FENETRE-40,100,40);
        g.setColor(Color.WHITE);
        String v3 = "Temps :";
        g.drawString(v3, LARGEUR_FENETRE-100+1, HAUTEUR_FENETRE-28);
        String v4 = ""+(int)etat.temps+"s";
        g.drawString(v4, LARGEUR_FENETRE-100+1, HAUTEUR_FENETRE-10);
        /**Score*/
        g.setColor(Color.BLACK);
        g.fillRect((LARGEUR_FENETRE/2)-50, 0, 100, 40);
        g.setColor(Color.WHITE);
        String v5 = "Score : ";
        g.drawString(v5, (LARGEUR_FENETRE/2)-50+1, 12);
        String v6 = ""+etat.route.getScore();
        g.drawString(v6,(LARGEUR_FENETRE/2)-50+1, 30);
	}
		
	/**Affichage des obstacles*/
	public void paintObstacles(Graphics g) {
		for(int i=0;i<this.etat.route.getObstacles().size();i++) {
			Point p = this.etat.route.getObstacles().get(i);
			g.drawOval(p.x+decalage,p.y,this.etat.route.largeurOb,this.etat.route.hauteurOb);
		}
	}
		
	public void paint(Graphics g){
		this.setBackground(Color.WHITE);
		super.paint(g);
		this.paintRoute(g);
		this.paintPointDeControle(g);
		this.paintObstacles(g);
		this.paintDecor(g);
        this.paintInformations(g);
        this.paintVoiture(g);
	}
}
