package control;

import model.Etat;

/**Classe qu'on utilise pour faire en sorte que le d�placement horizontal de la voiture soit fluide*/
public class Deplacements extends Thread {
	
	/**Attributs*/
	public Etat etat;
	
	/**Constructeur*/
	public Deplacements(Etat etat) {
		this.etat=etat;
	}
	
	/**M�thode run*/
	@Override
	  public void run() {
		/**On v�rifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
	    	etat.affichage.control.update();
	    	try { Thread.sleep(etat.deplacements);}
	    	catch (Exception e) { e.printStackTrace(); }
	    }
	  }

}
