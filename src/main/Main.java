package main;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import control.Control;
import control.Deplacements;
import model.Avancer;
import model.Etat;
import model.Temps;
import model.Vitesse;
import view.AcceuilB;
import view.AcceuilA;


/**Classe principale pour lancer le programme*/
public class Main {
	
		/**Méthode utilisée pour la création de la fenêtre de l'écran d'acceuil*/
		public static void createFenetreAcceuil(JFrame fenetre, JPanel a,JPanel b) {
			/**On divise l'écran d'acceuil en deux*/
			fenetre.setLayout(new BorderLayout());
			/**L'écran du haut sert à afficher les informations*/
			fenetre.add(a,BorderLayout.NORTH);
			/**L'écran du bas est dédié au bouton qui sert à choisir la difficuté de la partie*/
			fenetre.add(b,BorderLayout.SOUTH);
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
		}
}