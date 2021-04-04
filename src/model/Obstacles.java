package model;

/**Classe qui gère le mouvement des obstacles sur la route*/
public class Obstacles extends Thread {

	/**Attributs*/
		public Etat etat;
	
	/**Constructeur*/
		public Obstacles(Etat etat) {
			this.etat=etat;
		}
	
	/**Méthode run*/
	@Override
	  public void run() {
		/**On vérifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
		   /**On met à jour la position des obstacles*/
		    etat.route.bougeObstacles();;
		    try { Thread.sleep(5);}
	    	catch (Exception e) { e.printStackTrace(); }
		    
	    }
	 }
	
}
