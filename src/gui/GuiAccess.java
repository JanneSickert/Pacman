package gui;

import pacman.Dir;

/**
 * All x and y parameters are pixel and not indexes.
 * @author janne
 *
 */
public interface GuiAccess {

	public void drawPacman(int x, int y, Dir dir, int open);
	
	public void drawGhost(int x, int y, int index);
	
	public void drawPill(int x, int y, int index);
	
	public void drawWall(int x, int y, int index);
	
	public void drawData(int punkte);
	
	public void drawGameOver(boolean gewonnen);
	
	public void drawFoodPoint(int x, int y, int index);
	
	public void drawWormhole(int x, int y, int anniNr, int index);
}