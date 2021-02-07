package model;

public class Deplacements extends Thread {
	
	/**Attributs*/
	public Etat etat;
	
	/**Constructeur*/
	public Deplacements(Etat etat) {
		this.etat=etat;
	}
	
	/**Méthode run*/
	@Override
	  public void run() {
		/**On vérifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
	    	etat.affichage.control.update();
	    	try { Thread.sleep(10);}
	    	catch (Exception e) { e.printStackTrace(); }
	    	//etat.affichage.repaint();
	    }
	  }

}
