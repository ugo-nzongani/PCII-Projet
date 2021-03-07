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
	
		/**M�thode utilis�e pour la cr�ation de la fen�tre de l'�cran d'acceuil*/
		public static void createFenetreAcceuil(JFrame fenetre, JPanel a,JPanel b) {
			/**On divise l'�cran d'acceuil en deux*/
			fenetre.setLayout(new BorderLayout());
			/**L'�cran du haut sert � afficher les informations*/
			fenetre.add(a,BorderLayout.NORTH);
			/**L'�cran du bas est d�di� au bouton qui sert � choisir la difficut� de la partie*/
			fenetre.add(b,BorderLayout.SOUTH);
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
		}
}