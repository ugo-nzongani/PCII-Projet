package model;

/**Classe qui impl�mente un thread pour faire avancer le score et donc permettre le d�filement vertical de la route*/
public class Avancer extends Thread {
	
	/**Attributs*/
		public Etat etat;
	
	/**Constructeur*/
		public Avancer(Etat etat) {
			this.etat=etat;
		}
	
	/**M�thode run*/
	@Override
	  public void run() {
		/**On v�rifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
		   
		   	/**On augmente le score de la partie*/ 
	    		etat.route.setScore();
	    	
	    	/**On v�rifie si le joueur a franchi un point de contr�le*/
	    		etat.PDCFranchit();
	    		
	    	try { Thread.sleep((int)etat.avance);}
	    	catch (Exception e) { e.printStackTrace(); }
	    }
	  }

}