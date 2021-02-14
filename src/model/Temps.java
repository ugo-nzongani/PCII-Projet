package model;

/**Thread utilis� pour d�crementer le temps*/
public class Temps extends Thread {

	/**Attributs*/
	public Etat etat;
	
	/**Constructeur*/
	public Temps(Etat etat) {
		this.etat=etat;
	}
	
	/**M�thode run*/
	@Override
	  public void run() {
		/**On v�rifie que le joueur n'a pas perdu*/
	   while(etat.continuer) {
	    	etat.majTemps();
	    	try { Thread.sleep(1);}
	    	catch (Exception e) { e.printStackTrace(); }
	    	//etat.affichage.repaint();
	    }
	  }
}
