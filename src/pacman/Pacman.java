package pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import gui.MyFrame;

public class Pacman implements Konstanten{

	static MyFrame frame;
	
	public static void main(String[] args) {
		Var.initVars();
		new Pacman();
	}
	
	/**
	 * 
	 * @param index
	 * @return the x or y coordinate
	 */
	static int gc(int index) {
		return (index * Var.BLOCK_SIZE);
	}
	
	public static class Index{
		public static int nrOfAllPoints = 0;
		public static int nrOfGhosts = 0;
		public static int nrOfPills = 0;
		public static int nrOfWalls = 0;
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
					// Do nothing.
				} else {
					System.out.println("ERROR: Unexpected char at Var.co[" + x + "][" + y + "]");
				}
			}
		}
		new GameClock();
	}
	
	public static class GameClock implements ActionListener{

		Timer timer;
		int anniNr = 0;
		final int ANNI_LENGTH = 4;
		final int[] STEP = {12, 13, 12, 13};
		
		@Override
		public void actionPerformed(ActionEvent e) {
			movePacman();
			checkFood();
		}
		
		private void checkFood() {
			int i = 0;
			while (i < frame.gui.data.foodPoint.length) {
				if (frame.gui.data.foodPoint[i] != null) {
					if (frame.gui.data.pacman.x == frame.gui.data.foodPoint[i].x && frame.gui.data.pacman.y == frame.gui.data.foodPoint[i].y) {
						frame.gui.data.foodPoint[i] = null;
						frame.gui.data.punkte++;
					}
				}
				i++;
			}
		}
		
		private void movePacman() {
			int x = frame.gui.data.pacman.x;
			int y = frame.gui.data.pacman.y;
			switch(Var.dir) {
			case UP:
				frame.gui.drawPacman(x, y - STEP[anniNr], Dir.UP, anniNr);
				break;
			case LEFT:
				frame.gui.drawPacman(x - STEP[anniNr], y, Dir.LEFT, anniNr);
				break;
			case RIGHT:
				frame.gui.drawPacman(x + STEP[anniNr], y, Dir.RIGHT, anniNr);
				break;
			case DOWN:
				frame.gui.drawPacman(x, y + STEP[anniNr], Dir.DOWN, anniNr);
				break;
			case WAIT:
				frame.gui.drawPacman(x, y, Dir.WAIT, 0);
			}
			if (anniNr < ANNI_LENGTH - 1) {
				anniNr++;
			} else {
				anniNr = 0;
			}
		}
		
		GameClock() {
			timer = new Timer(40, this);
			timer.start();
		}
	}
}
