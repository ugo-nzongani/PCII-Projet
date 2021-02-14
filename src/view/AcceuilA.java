package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class AcceuilA extends JPanel {
	
	public static final int LARGEUR_FENETRE = 800;
	public static final int HAUTEUR_FENETRE = 250;
	
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
