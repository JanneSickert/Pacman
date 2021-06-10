package pacman;

import gui.MyFrame;

/**
 * Startklasse mit Main-Methode
 * 
 * @author Janne
 *
 */
public class Pacman implements Konstanten{

	private MyFrame frame;
	
	public static void main(String[] args) {
		Var.initVars();
		new Pacman();
	}
	
	/**
	 * 
	 * @param index
	 * @return the x or y coordinate
	 */
	private int gc(int index) {
		return (index * Var.BLOCK_SIZE);
	}
	
	public static class Index{
		public static int nrOfAllPoints = 0;
		public static int nrOfGhosts = 0;
		public static int nrOfPills = 0;
		public static int nrOfWalls = 0;
		public static int nrOfVoids = 0;
	}
	
	Pacman() {
		frame = new MyFrame();
		for (int y = 0; y < Var.co.length; y++) {
			for (int x = 0; x < Var.co[0].length; x++) {
				if (Var.co[y][x] == PACMAN) {
					frame.gui.drawPacman(gc(x), gc(y), Dir.WAIT, 0);
				} else if (Var.co[y][x] == GEIST) {
					frame.gui.drawGhost(gc(x), gc(y), false, Index.nrOfGhosts);
					Index.nrOfGhosts++;
				} else if (Var.co[y][x] == PILLE) {
					frame.gui.drawPill(gc(x), gc(y), Index.nrOfPills);
					Index.nrOfPills++;
				} else if (Var.co[y][x] == MAUER) {
					frame.gui.drawWall(gc(x), gc(y), Index.nrOfWalls);
					Index.nrOfWalls++;
				} else if (Var.co[y][x] == FRESSPUNKT) {
					frame.gui.drawFoodPoint(gc(x), gc(y), Index.nrOfAllPoints);
					Index.nrOfAllPoints++;
				} else if (Var.co[y][x] == VOID) {
					frame.gui.drawVoid(gc(x), gc(y), Index.nrOfVoids);
					Index.nrOfVoids++;
				} else {
					System.out.println("ERROR: Unexpected char at Var.co[" + x + "][" + y + "]");
				}
			}
		}
	}
}
