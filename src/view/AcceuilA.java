package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**Classe utilisée pour l'affichage de la moitié supérieure de l'écran d'acceuil*/
public class AcceuilA extends JPanel {
	
	/**Constantes*/
		public static final int LARGEUR_FENETRE = Affichage.LARGEUR_FENETRE;
		public static final int HAUTEUR_FENETRE = Affichage.HAUTEUR_FENETRE/2;
	
	/**Constructeur*/
		public AcceuilA() {
			setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
			setFocusable(true);
		}
	
	public void paint(Graphics g) {
		this.setBackground(Color.WHITE);
		super.paint(g);
		String v1 = "JEU";
		g.drawString(v1, LARGEUR_FENETRE/2-25, 12);
		String v2 = "Choisir la difficulté :";
		g.drawString(v2, (LARGEUR_FENETRE/2)-50, HAUTEUR_FENETRE-12);
	}

}
