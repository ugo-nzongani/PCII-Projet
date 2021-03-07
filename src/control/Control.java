package control;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import model.Etat;
import view.Affichage;

/**Le controleur du mod�le impl�mentant le KeyListener*/
public class Control implements KeyListener{
	
	/**Constantes*/
		/**Constantes repr�sentant la valeur du d�placement effectu� par la voiture*/
		public static final int SAUT = 3;
	
    /**Attributs*/
    	public Affichage affichage;
    	public Etat etat;
    	/**Hashamp utilis�e utilis�e pour faire en sorte que la voiture soit plus r�active aux actions du joueur*/
    		public HashMap<Integer,Boolean> keys;
    
    /**Constructeur*/
    public Control(Affichage a, Etat e) {
        this.affichage = a;
        this.etat = e;
        /**On ajoute au hasmap les touches que le joueur pourra utiliser pour contr�ler la voiture*/
        	this.keys = new HashMap<Integer,Boolean>();
        	this.keys.put(KeyEvent.VK_LEFT, false);
        	this.keys.put(KeyEvent.VK_RIGHT, false);
        	this.keys.put(KeyEvent.VK_SPACE, false);
    }
    
    
	@Override
	/**Lorsqu'on appuie sur un bouton, sa case correspodante dans l'attribut keys est mise � true*/ 
	public void keyPressed(KeyEvent e) {
		this.keys.replace(e.getKeyCode(), true);
		/**if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.etat.moveLeft();
		} 
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.etat.moveRight();
		}*/
	}

	
	
	@Override
	/**Lorsqu'on appuie sur un bouton, sa case correspodante dans l'attribut keys est mise � true*/ 
	public void keyReleased(KeyEvent e) {
		this.keys.replace(e.getKeyCode(), false);
	}
	
	
	
	/**M�thode qui va �tre appel�e par le thread Deplacements*/
	public void update() {
		/**Si la voiture se d�place vers la gauche*/
		if(this.keys.get(KeyEvent.VK_LEFT)) {
			//if(this.keys.get(KeyEvent.VK_SPACE)) {
				//etat.moveLeft(5);
			//} else {
			/**On appelle la m�thode de classe Etat charg�e de changer la coordonn�e horizontale de la voiture*/
				etat.moveLeft(Control.SAUT);
			//}
		}
		/**Si la voiture se deplace vers la droite*/
		if(this.keys.get(KeyEvent.VK_RIGHT)) {
			//if(this.keys.get(KeyEvent.VK_SPACE)) {
				//etat.moveRight(5);
			//} else {
			/**On appelle la m�thode de classe Etat charg�e de changer la coordonn�e horizontale de la voiture*/
				etat.moveRight(Control.SAUT);
			//}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}