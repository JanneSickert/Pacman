package pacman;

/**
 * Startklasse mit Main-Methode
 * 
 * @author Janne
 *
 */
public class Pacman {

	public static void main(String[] args) {
		Var.initVars();
		new gui.MyFrame();
	}
}
