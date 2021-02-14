package model;

/**Classe qui impl�mente un thread pour faire avancer le score*/
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
	    	etat.route.setScore();
	    	try { Thread.sleep((int)etat.avance);}
	    	catch (Exception e) { e.printStackTrace(); }
	    	//etat.affichage.repaint();
	    }
	  }

}