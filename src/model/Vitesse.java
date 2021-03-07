package model;

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
	    		etat.calcul_Vit();
	    		
	    	try { Thread.sleep((int)etat.avance);}
	    	catch (Exception e) { e.printStackTrace(); }
	    	//etat.affichage.repaint();
	    }
	  }

}
