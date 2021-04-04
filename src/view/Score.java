package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Etat;

/**Classe qui gère l'affichage de l'écran de fin de partie*/
public class Score extends JPanel {
	
	/**Constante*/
	
		public static final int LARGEUR_FENETRE = Affichage.LARGEUR_FENETRE;
		public static final int HAUTEUR_FENETRE = Affichage.HAUTEUR_FENETRE;
	
	/**Attributs*/
		public Etat etat;
		
	/**Constructeur*/
		public Score(Etat e) {
			setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
			setFocusable(true);
			this.etat=e;
		}
	
	/**Méthode utilisée pour la création de la fenêtre de l'écran de fin*/
	public static void createFenetre(JFrame fenetre, JPanel a) {
		fenetre.add(a);
		fenetre.pack();
			
		/**Commande utilisée pour placer l'interface graphique au milieu de l'écran*/
		fenetre.setLocationRelativeTo(null);
			
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void paint(Graphics g) {
		this.setBackground(Color.GREEN);
		super.paint(g);
		g.setColor(Color.RED);
		Font myFont = new Font ("Courier New", 1, 17);
		g.setFont (myFont);
		/**D'abord on affiche l'ecriture suivante en haut de l'ecran*/
		g.drawString("FIN DE PARTIE!", (LARGEUR_FENETRE/2)-80, 12);
		g.setColor(Color.YELLOW);
		/**Puis on affiche notre score*/
		g.drawString("Votre score: "+etat.myScore, 50, 30);
		int hauteur = 60;
		g.setColor(Color.BLACK);
		int [] sc = etat.scores;
		int j=0;
		/**Puis on affiche les 10 meilleurs scores de tous les temps*/
		for(int i=9;i>-1;i--) {
			if(i==etat.indice) {
				/**Notre score on l'affiche en jaune*/
				g.setColor(Color.YELLOW);
				g.drawString(""+(j+1)+". "+sc[i], 50, hauteur);
				g.setColor(Color.BLACK);
			} else {
				g.drawString(""+(j+1)+". "+sc[i], 50, hauteur);
			}
			hauteur+=40;
			j++;
		}
	}

}
