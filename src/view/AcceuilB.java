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

import model.Avancer;
import model.Deplacements;
import model.Etat;
import model.Temps;
import model.Vitesse;

public class AcceuilB extends JPanel implements ActionListener{
	
	/**Attributs*/
	
		/**Dimensions de l'interface graphique*/
		public static final int LARGEUR_FENETRE = 800;
		public static final int HAUTEUR_FENETRE = 250;
		
		public boolean commencer;
		
	/**Constructeur*/
	public AcceuilB() {
		this.commencer=false;
		setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
		setFocusable(true);
		JButton b = new JButton("Normale");
		b.addActionListener(this);
		//b.setBounds(300, 0, 20, 20);
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
			d.start();
			a1.start();
			v.start();
			t.start();
		createFenetre(j, e1.affichage);
		
	}
	
	public boolean getCommencer() {
		return this.commencer;
	}
	
	public void paint(Graphics g) {
		this.setBackground(Color.WHITE);
		super.paint(g);
	}

		
}
