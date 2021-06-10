package gui;

import javax.swing.JFrame;
import pacman.Var;

/**
 * Hier wird das JFrame definiert und
 * das Game Objekt übergeben.
 * 
 * @author Janne
 *
 */
public class MyFrame extends JFrame{
	
	private static final long serialVersionUID = 4070509246110827584L;
	public Gui gui = new Gui();
	
	public MyFrame() {
        add(gui);
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Var.BLOCK_SIZE * Var.ELEMENTS_X + 200, Var.BLOCK_SIZE * Var.ELEMENTS_Y);
        setLocation(200, 50);
        setVisible(true);
	}
}
