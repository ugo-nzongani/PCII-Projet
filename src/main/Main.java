package main;
import java.io.IOException;

import javax.swing.JFrame;
import control.Control;
import model.Avancer;
import model.Deplacements;
import model.Etat;
import model.Temps;
import model.Vitesse;


/**Classe principale pour lancer le programme*/
public class Main {

		/**Méthode utilisée pour la création d'une fenêtre*/
		public static void createFenetre(JFrame fenetre, Control c) {
			fenetre.add(c.affichage);
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
			Etat e = new Etat();
			/**Threads*/
				Avancer a = new Avancer(e);
				Deplacements d = new Deplacements(e);
				Vitesse v = new Vitesse(e);
				Temps t = new Temps(e);
				d.start();
				a.start();
				v.start();
				t.start();
			createFenetre(j, e.affichage.control);
		}
}