package model;

import java.io.FileNotFoundException;

/**Thread utilisé pour le calcul de la vitesse*/
public class Vitesse extends Thread{
	
	/**Attributs*/
		public Etat etat;
	
	/**Constructeur*/
		public Vitesse(Etat etat) {
			this.etat=etat;
		}
	
	/**Méthode run*/
	@Override
	  public void run() {
		/**On vérifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
		    /**On modifie la vitesse de la voiture*/
	    		try {
					etat.calcul_Vit();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		
	    	try { Thread.sleep((int)etat.avance);}
	    	catch (Exception e) { e.printStackTrace(); }
	    	//etat.affichage.repaint();
	    }
	  }

}
