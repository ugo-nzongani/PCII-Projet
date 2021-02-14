package main;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import control.Control;
import model.Avancer;
import model.Deplacements;
import model.Etat;
import model.Temps;
import model.Vitesse;
import view.AcceuilB;
import view.AcceuilA;


/**Classe principale pour lancer le programme*/
public class Main {
	
		/**Méthode utilisée pour la création de la fenêtre de l'écran d'acceuil*/
		public static void createFenetreAcceuil(JFrame fenetre, JPanel a,JPanel b) {
			fenetre.setLayout(new BorderLayout());
			fenetre.add(a,BorderLayout.NORTH);
			fenetre.add(b,BorderLayout.WEST);
			//fenetre.add(a);
			fenetre.pack();
			/**Commande utilisée pour placer l'interface graphique au milieu de l'écran*/
			fenetre.setLocationRelativeTo(null);
			fenetre.setVisible(true);
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		/**Méthode principale
		 * @throws IOException */
		public static void main(String []args) throws IOException {
			JFrame j = new JFrame("Test");
			AcceuilA acc1 = new AcceuilA();
			AcceuilB acc2 = new AcceuilB();
			createFenetreAcceuil(j,acc1,acc2);
			//Etat e = new Etat();
			/**Threads*/
				/**Avancer a1 = new Avancer(e);
				Deplacements d = new Deplacements(e);
				Vitesse v = new Vitesse(e);
				Temps t = new Temps(e);
				d.start();
				a1.start();
				v.start();
				t.start();
			createFenetre(j, e.affichage);*/
		}
}