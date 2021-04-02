package pacman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Hier befindet sich die Steuerung des Spiels.
 * Das Spiel wird mit den
 * Tasten a, s, d, w gesteuert.
 * 
 * @author Janne
 *
 */
public class Tastatur extends KeyAdapter{

	private final int a = 65, s = 83, d = 68, w = 87;
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (Var.inGame) {
			if (a == key) {
				Var.dir = Dir.LEFT;
			} else if (s == key) {
				Var.dir = Dir.DOWN;
			} else if (d == key) {
				Var.dir = Dir.RIGHT;
			} else if (w == key) {
				Var.dir = Dir.UP;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (Var.inGame) {
			if (a == key) {
				Var.dir = Dir.WAIT;
			} else if (s == key) {
				Var.dir = Dir.WAIT;
			} else if (d == key) {
				Var.dir = Dir.WAIT;
			} else if (w == key) {
				Var.dir = Dir.WAIT;
			}
		}
	}
}
