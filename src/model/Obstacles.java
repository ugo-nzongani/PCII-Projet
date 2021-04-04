package model;

/**Classe qui g�re le mouvement des obstacles sur la route*/
public class Obstacles extends Thread {

	/**Attributs*/
		public Etat etat;
	
	/**Constructeur*/
		public Obstacles(Etat etat) {
			this.etat=etat;
		}
	
	/**M�thode run*/
	@Override
	  public void run() {
		/**On v�rifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
		   /**On met � jour la position des obstacles*/
		    etat.route.bougeObstacles();;
		    try { Thread.sleep(5);}
	    	catch (Exception e) { e.printStackTrace(); }
		    
	    }
	 }
	
}
