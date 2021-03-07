package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import control.Deplacements;
import model.Avancer;
import model.Etat;
import model.Obstacles;
import model.Temps;
import model.Vitesse;

/**Classe utilisée pour l'affichage de la moitié inférieure de l'écran d'acceuil*/
public class AcceuilB extends JPanel implements ActionListener{
	
	/**Constantes*/
		public static final int LARGEUR_FENETRE = Affichage.LARGEUR_FENETRE;
		public static final int HAUTEUR_FENETRE = Affichage.HAUTEUR_FENETRE/2;
		
		
	/**Constructeur*/
		public AcceuilB() {
			setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
			setFocusable(true);
			
			/**Bouton utilisé pour lancer la partie*/
				JButton b = new JButton("Normale");
				b.addActionListener(this);
				this.add(b);
		}
	
		
	/**Méthode utilisée pour la création d'une fenêtre*/
	public static void createFenetre(JFrame fenetre, JPanel a) {
		fenetre.add(a);
		fenetre.pack();
		
		/**Commande utilisée pour placer l'interface graphique au milieu de l'écran*/
		fenetre.setLocationRelativeTo(null);
		
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame j = new JFrame("Test");
		Etat e1 = new Etat();
		/**Threads*/
			Avancer a1 = new Avancer(e1);
			Deplacements d = new Deplacements(e1);
			Vitesse v = new Vitesse(e1);
			Temps t = new Temps(e1);
			Obstacles o = new Obstacles(e1);
			d.start();
			a1.start();
			v.start();
			t.start();
			o.start();
		createFenetre(j, e1.affichage);
		
	}
	
	public void paint(Graphics g) {
		this.setBackground(Color.WHITE);
		super.paint(g);
	}

		
}
