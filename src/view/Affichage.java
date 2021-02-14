package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import control.Control;
import model.Etat;


public class Affichage extends JPanel{
	
	/**Attributs*/
	
		/**Dimensions de l'interface graphique*/
		public static final int LARGEUR_FENETRE = 800;
		public static final int HAUTEUR_FENETRE = 500;
		
		/**Dimesions de la voiture*/
		public static final int LARGEUR = 20;
		public static final int LONGUEUR = 20;
		public static int x = LARGEUR_FENETRE/2;
		
		public Etat etat;
		public Control control;
		public Decor decor;
		
		/**Attribut utilisée pour le défilement du décor et de la route*/
		public int decalage = 0;
		
		public BufferedImage image;
		
		public int j1;
		
		public int j2;
		
		
		/**Constructeur*/
		public Affichage(Etat e)  {
			this.etat = e;
			setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
			setFocusable(true);
			/**On ajoute le controleur*/
			this.control = new Control(this, e);
			this.addKeyListener(this.control);	
			this.decor = new Decor(this, e);
			//image = ImageIO.read(new File("images\\screen_07.png"));
			this.j1=0;
			this.j2=20;
		}
	
	/**Affichage de la voiture*/
	public void paintVoiture(Graphics g) {
		g.drawRect(Affichage.x, HAUTEUR_FENETRE-(LONGUEUR+2), LARGEUR, LONGUEUR);
		//g.drawLine(Affichage.x+(LONGUEUR/2), (HAUTEUR_FENETRE-(LONGUEUR+2))+LONGUEUR/2, Affichage.x+(LONGUEUR/2), (HAUTEUR_FENETRE-(LONGUEUR+2))+LONGUEUR/2);
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
        g.drawLine(-100+decalage, Decor.HORIZON, LARGEUR_FENETRE+100+decalage, Decor.HORIZON);
    	/**Montagnes*/
        for(int i = 0; i < this.decor.pointList.size()-1; i++) {
        	Point p1 = this.decor.pointList.get(i);
        	Point p2 = this.decor.pointList.get(i+1);
        	g.drawLine(p1.x+decalage, p1.y, p2.x+decalage, p2.y);
        }
        /**Extremités de l'écran*/
        g.drawLine(-100+decalage, LARGEUR_FENETRE, -100+decalage, 0);
        g.drawLine(LARGEUR_FENETRE+100+decalage, LARGEUR_FENETRE, LARGEUR_FENETRE+100+decalage, 0);
	}
	
	
	/**Affichage des informations*/
	public void paintInformations(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, HAUTEUR_FENETRE-40, 100, 40);
		g.setColor(Color.WHITE);
		String v1 = "Vitesse :";
        g.drawString(v1,1,HAUTEUR_FENETRE-28);
        String v2 = ""+etat.vitesse+"km/h";
        g.drawString(v2, 1, HAUTEUR_FENETRE-10);
        g.setColor(Color.BLACK);
        g.fillRect(LARGEUR_FENETRE-100,HAUTEUR_FENETRE-40,100,40);
        g.setColor(Color.WHITE);
        String v3 = "Temps :";
        g.drawString(v3, LARGEUR_FENETRE-100+1, HAUTEUR_FENETRE-28);
        String v4 = ""+etat.temps+"s";
        g.drawString(v4, LARGEUR_FENETRE-100+1, HAUTEUR_FENETRE-10);
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
		this.paintVoiture(g);
		this.paintRoute(g);
		this.paintPointDeControle(g);
		this.paintObstacles(g);
		this.paintDecor(g);
        this.paintInformations(g);
	}
}
