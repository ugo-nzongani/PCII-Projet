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
	
		/**M�thode utilis�e pour la cr�ation de la fen�tre de l'�cran d'acceuil*/
		public static void createFenetreAcceuil(JFrame fenetre, JPanel a,JPanel b) {
			fenetre.setLayout(new BorderLayout());
			fenetre.add(a,BorderLayout.NORTH);
			fenetre.add(b,BorderLayout.WEST);
			//fenetre.add(a);
			fenetre.pack();
			/**Commande utilis�e pour placer l'interface graphique au milieu de l'�cran*/
			fenetre.setLocationRelativeTo(null);
			fenetre.setVisible(true);
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		/**M�thode utilis�e pour la cr�ation d'une fen�tre*/
		public static void createFenetre(JFrame fenetre, JPanel a) {
			fenetre.add(a);
			fenetre.pack();
			/**Commande utilis�e pour placer l'interface graphique au milieu de l'�cran*/
			fenetre.setLocationRelativeTo(null);
			fenetre.setVisible(true);
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		/**M�thode principale
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