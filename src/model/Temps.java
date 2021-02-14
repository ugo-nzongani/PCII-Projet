package model;

/**Thread utilisé pour décrementer le temps*/
public class Temps extends Thread {

	/**Attributs*/
	public Etat etat;
	
	/**Constructeur*/
	public Temps(Etat etat) {
		this.etat=etat;
	}
	
	/**Méthode run*/
	@Override
	  public void run() {
		/**On vérifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
	    	etat.majTemps();
	    	try { Thread.sleep(1);}
	    	catch (Exception e) { e.printStackTrace(); }
	    	//etat.affichage.repaint();
	    }
	  }
}
