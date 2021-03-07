package model;

/**Classe qui implémente un thread pour faire avancer le score et donc permettre le défilement vertical de la route*/
public class Avancer extends Thread {
	
	/**Attributs*/
		public Etat etat;
	
	/**Constructeur*/
		public Avancer(Etat etat) {
			this.etat=etat;
		}
	
	/**Méthode run*/
	@Override
	  public void run() {
		/**On vérifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
		   
		   	/**On augmente le score de la partie*/ 
	    		etat.route.setScore();
	    	
	    	/**On vérifie si le joueur a franchi un point de contrôle*/
	    		etat.PDCFranchit();
	    		
	    	try { Thread.sleep((int)etat.avance);}
	    	catch (Exception e) { e.printStackTrace(); }
	    }
	  }

}