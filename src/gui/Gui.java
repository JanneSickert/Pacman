package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JPanel;
import pacman.Sound;
import pacman.Tastatur;
import pacman.Var;
import pacman.Dir;
import pacman.Konstanten;

public class Gui extends JPanel implements Konstanten, GuiAccess{
	
	private static final long serialVersionUID = -7886147914342084854L;
	
	private final int
	SCREEN_SIZE_X = Var.ELEMENTS_X * Var.BLOCK_SIZE,
	SCREEN_SIZE_Y = Var.ELEMENTS_Y * Var.BLOCK_SIZE;
	
	public Data data;
	
	Gui() {
		addKeyListener(new Tastatur());
		setFocusable(true);
		setBackground(Color.BLACK);
		data = new Data();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (data.gameOver) {
			drawGameOver(g2d);
		} else {
			for (int i = 0; i < Var.nrOfGhosts; i++) {
				if (data.ghost[i] != null) {
					if (data.pacmanCanEatGhosts) {
						g.drawImage(Var.goodGoast, data.ghost[i].x, data.ghost[i].y, this);
					} else {
						g.drawImage(Var.ghost, data.ghost[i].x, data.ghost[i].y, this);
					}
				}
			}
			for (int i = 0; i < Var.nrOfPills; i++) {
				if (data.pill[i] != null) {
					g.drawImage(Var.pille_img, data.pill[i].x, data.pill[i].y, this);
				}
			}
			for (int i = 0; i < Var.nrOfWalls; i++) {
				g.setColor(Color.MAGENTA);
				g.fillRect(data.wall[i].x, data.wall[i].y, Var.BLOCK_SIZE, Var.BLOCK_SIZE);
			}
			g2d.setColor(Color.BLUE);
			g2d.fillRect(Var.BLOCK_SIZE * Var.ELEMENTS_X + 1, Var.BLOCK_SIZE, Var.BLOCK_SIZE * Var.ELEMENTS_X + 200, 3 * Var.BLOCK_SIZE);
			String p = "Punkte: " + data.punkte;
			String l = "Leben: " + data.leben;
			Font small = new Font("Helvetica", Font.BOLD, 30);
			g2d.setColor(Color.WHITE);
			g2d.setFont(small);
			g2d.drawString(p, Var.BLOCK_SIZE * Var.ELEMENTS_X + 2, 110);
			g2d.drawString(l, Var.BLOCK_SIZE * Var.ELEMENTS_X + 2, 210);
			for (int i = 0; i < Var.nrOfAllPoints; i++) {
				final int START_X = 22, START_Y = 21, SIZE = 4;
				if (data.foodPoint[i] != null) {
					g.setColor(Color.ORANGE);
					g.fillOval(data.foodPoint[i].x + START_X, data.foodPoint[i].y + START_Y, SIZE, SIZE);
				}
			}
			g.drawImage(Var.getPacmanImage(data.pacman.dir, data.pacman.open), data.pacman.x, data.pacman.y, this);
			g.drawLine(Var.BLOCK_SIZE * Var.ELEMENTS_X, 0, Var.BLOCK_SIZE * Var.ELEMENTS_X, Var.BLOCK_SIZE * Var.ELEMENTS_Y);
		}
		Toolkit.getDefaultToolkit().sync();
	}

	public void drawGameOver(Graphics2D g2d) {
		String message = null;
		Color textColor = null;
		if (data.gewonnen) {
			textColor = Color.GREEN;
			message = "Du hast mit " + data.punkte + " Punkten gewonnen.";
			Sound.playWin();
		} else {
			textColor = Color.RED;
			message = "Du hast mit " + data.punkte + " Punkten verloren.";
			Sound.playLost();
		}
		Font small = new Font("Helvetica", Font.BOLD, 40);
		FontMetrics metr = this.getFontMetrics(small);
		g2d.setColor(textColor);
		g2d.setFont(small);
		g2d.drawString(message, (SCREEN_SIZE_X - metr.stringWidth(message)) / 2, SCREEN_SIZE_Y / 2);
	}

	@Override
	public void drawPacman(int x, int y, Dir dir, int open) {
		data.pacman.x = x;
		data.pacman.y = y;
		data.pacman.dir = dir;
		data.pacman.open = open;
		revalidate();
		repaint();
	}

	@Override
	public void drawGhost(int x, int y, int index) {
		data.ghost[index].x = x;
		data.ghost[index].y = y;
		revalidate();
		repaint();
	}

	@Override
	public void drawPill(int x, int y, int index) {
		data.pill[index].x = x;
		data.pill[index].y = y;
		revalidate();
		repaint();
	}

	@Override
	public void drawWall(int x, int y, int index) {
		data.wall[index].x = x;
		data.wall[index].y = y;
		revalidate();
		repaint();
	}

	@Override
	public void drawData(int punkte) {
		data.punkte = punkte;
		revalidate();
		repaint();
	}

	@Override
	public void drawGameOver(boolean gewonnen, int punkte) {
		data.gewonnen = gewonnen;
		data.punkte = punkte;
		data.gameOver = true;
		revalidate();
		repaint();
	}

	@Override
	public void drawFoodPoint(int x, int y, int index) {
		data.foodPoint[index].x = x;
		data.foodPoint[index].y = y;
		revalidate();
		repaint();
	}
}