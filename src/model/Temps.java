package model;

import java.io.FileNotFoundException;

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
		   
		   /**On met � jour le temps*/
	    		try {
					etat.majTemps();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	   
	    	try { Thread.sleep(1);}
	    	catch (Exception e) { e.printStackTrace(); }
	    	etat.affichage.repaint();
	    }
	  }
}
