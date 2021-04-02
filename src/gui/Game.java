package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import pacman.Dir;
import java.awt.Toolkit;
import pacman.Ghost;
import pacman.Konstanten;
import pacman.Sound;
import pacman.Tastatur;
import pacman.Var;

/**
 * Hauptklasse des Spiels,
 * beinhaltet größtenteils Methoden von Pacman
 * und die Game Clock.
 * 
 * @author Janne
 *
 */
public class Game extends JPanel implements ActionListener, Konstanten{
	
	private static final long serialVersionUID = -6546498884937008127L;
	private Timer timer;
	private final int SCREEN_SIZE = Var.BLOCK_SIZE * Var.ELEMENTS_X, SCREEN_SIZE_Y = Var.ELEMENTS_Y * Var.BLOCK_SIZE;
	private boolean unfuckable = false;
	private int anniNr = 0;
	private int punkte = 0;
	private int pacman_y, pacman_x;
	private final int ZEITEINHEIT = 10;
	private int takteUnfuckable = 30 * ZEITEINHEIT;
	
	public Game() {
		addKeyListener(new Tastatur());
		setFocusable(true);
		setBackground(Color.WHITE);
		timer = new Timer(40, this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDraw(g);
	}
	
	private void doDraw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (Var.inGame) {
			g2d.drawLine(Var.BLOCK_SIZE * Var.ELEMENTS_X, 0,
					     Var.BLOCK_SIZE * Var.ELEMENTS_X, Var.BLOCK_SIZE * Var.ELEMENTS_Y);
			showData(g2d);
			drawLyb(g2d);
			if (unfuckable) {
				takteUnfuckable--;
				if (takteUnfuckable == 0) {
					unfuckable = false;
					takteUnfuckable = 30 * ZEITEINHEIT;
				}
			}
		} else {
			if (Var.nrOfAllPoints == 0) {
				ende(true, g2d);
			} else {
				ende(false, g2d);
			}
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void agresiverGeist() {
		for (Ghost gh : Var.geister) {
			gh.setPacmanPosition(pacman_x, pacman_y);
			gh.move(true);
		}
	}

	private void vorsichtigerGeist() {
		for (Ghost gh : Var.geister) {
			gh.setPacmanPosition(pacman_x, pacman_y);
			gh.move(false);
		}
	}

	private void showData(Graphics2D g2d) {
		g2d.setColor(Color.GRAY);
		g2d.fillRect(Var.BLOCK_SIZE * Var.ELEMENTS_X + 1, Var.BLOCK_SIZE, Var.BLOCK_SIZE * Var.ELEMENTS_X + 200, 3 * Var.BLOCK_SIZE);
		String p = "Punkte: " + punkte;
		String l = "Leben: " + Var.leben;
		Font small = new Font("Helvetica", Font.BOLD, 30);
		g2d.setColor(Color.WHITE);
		g2d.setFont(small);
		g2d.drawString(p, Var.BLOCK_SIZE * Var.ELEMENTS_X + 2, 80);
		g2d.drawString(l, Var.BLOCK_SIZE * Var.ELEMENTS_X + 2, 180);
	}
	
	/**
	 * Annotierte Methoden dienen der Annimation von Pacman.
	 */
	private @interface Animiert{
		String richtung();
	}
	
	@Animiert(richtung="UP")
	private void drawPacmanUp(Graphics2D g2d) {
		switch (anniNr) {
		case 1:
			g2d.drawImage(Var.pacman2up, pacman_x, pacman_y, this);
			break;
		case 2:
			g2d.drawImage(Var.pacman3up, pacman_x, pacman_y, this);
			break;
		case 3:
			g2d.drawImage(Var.pacman4up, pacman_x, pacman_y, this);
			break;
		default:
			g2d.drawImage(Var.pacman1, pacman_x, pacman_y, this);
			break;
		}
	}

	@Animiert(richtung="DOWN")
	private void drawPacmanDown(Graphics2D g2d) {
		switch (anniNr) {
		case 1:
			g2d.drawImage(Var.pacman2down, pacman_x, pacman_y, this);
			break;
		case 2:
			g2d.drawImage(Var.pacman3down, pacman_x, pacman_y, this);
			break;
		case 3:
			g2d.drawImage(Var.pacman4down, pacman_x, pacman_y, this);
			break;
		default:
			g2d.drawImage(Var.pacman1, pacman_x, pacman_y, this);
			break;
		}
	}

	@Animiert(richtung="LEFT")
	private void drawPacnanLeft(Graphics2D g2d) {
		switch (anniNr) {
		case 1:
			g2d.drawImage(Var.pacman2left, pacman_x, pacman_y, this);
			break;
		case 2:
			g2d.drawImage(Var.pacman3left, pacman_x, pacman_y, this);
			break;
		case 3:
			g2d.drawImage(Var.pacman4left, pacman_x, pacman_y, this);
			break;
		default:
			g2d.drawImage(Var.pacman1, pacman_x, pacman_y, this);
			break;
		}
	}

	@Animiert(richtung="RIGHT")
	private void drawPacmanRight(Graphics2D g2d) {
		switch (anniNr) {
		case 1:
			g2d.drawImage(Var.pacman2right, pacman_x, pacman_y, this);
			break;
		case 2:
			g2d.drawImage(Var.pacman3right, pacman_x, pacman_y, this);
			break;
		case 3:
			g2d.drawImage(Var.pacman4right, pacman_x, pacman_y, this);
			break;
		default:
			g2d.drawImage(Var.pacman1, pacman_x, pacman_y, this);
			break;
		}
	}
	
	@Animiert(richtung="keine")
	private void drawPacmanWait(Graphics2D g2d) {
		g2d.drawImage(Var.pacman1, pacman_x, pacman_y, this);
	}
	
	@SuppressWarnings("incomplete-switch")
	private void move(Graphics2D g, int sum_x, int sum_y, Dir lookAt) {
		int pacman_x = (this.pacman_x/Var.BLOCK_SIZE);
		int pacman_y = (this.pacman_y/Var.BLOCK_SIZE);
		if (Var.ELEMENTS_Y > (pacman_y+sum_y) && Var.ELEMENTS_X > (pacman_x+sum_x) &&
			0 <= (pacman_y+sum_y) && 0 <= (pacman_x+sum_x)) {
			if (Var.co[pacman_y+sum_y][pacman_x+sum_x]==MAUER) {
				Var.dir = Dir.WAIT;
			} else {
				switch (lookAt) {
				case UP: drawPacmanUp(g); break;
				case LEFT: drawPacnanLeft(g); break;
				case RIGHT: drawPacmanRight(g); break;
				case DOWN: drawPacmanDown(g); break;
				}
				if (anniNr==2) {
					if (Var.co[pacman_y+sum_y][pacman_x+sum_x]==GEIST) {
						if (unfuckable) {
							punkte+=100;
							Var.co[pacman_y+sum_y][pacman_x+sum_x] = PACMAN;
							Var.co[pacman_y][pacman_x] = VOID;
						} else {
							Var.leben--;
							if (Var.leben == 0) {
								Var.inGame = false;
							} else {
								Var.co[pacman_y+sum_y][pacman_x+sum_x] = Var.untergrund[Var.nrKollidierterGeist];
								Var.co[pacman_y][pacman_x] = VOID;
								Var.co[Var.get_pacman_default_position_y()][Var.get_pacman_default_position_x()] = PACMAN;
							}
						}
					} else if (Var.co[pacman_y+sum_y][pacman_x+sum_x]==PILLE) {
						unfuckable = true;
						punkte+=10;
						Var.co[pacman_y+sum_y][pacman_x+sum_x] = PACMAN;
						Var.co[pacman_y][pacman_x] = VOID;
					} else if (Var.co[pacman_y+sum_y][pacman_x+sum_x]==FRESSPUNKT) {
						punkte++;
						Var.nrOfAllPoints--;
						if (Var.nrOfAllPoints==0) {
							Var.inGame = false;
						}
						Var.co[pacman_y+sum_y][pacman_x+sum_x] = PACMAN;
						Var.co[pacman_y][pacman_x] = VOID;
					} else if (Var.co[pacman_y+sum_y][pacman_x+sum_x]==VOID) {
						Var.co[pacman_y+sum_y][pacman_x+sum_x] = PACMAN;
						Var.co[pacman_y][pacman_x] = VOID;
					}
				}
			}
		}
	}
	
	private void ende(boolean gewonnen, Graphics2D g2d) {
		String message = null;
		Color textColor = null;
		if (gewonnen) {
			textColor = Color.GREEN;
			message = "Du hast mit " + punkte + " Punkten gewonnen.";
			Sound.playWin();
		} else {
			textColor = Color.RED;
			message = "Du hast mit " + punkte + " Punkten verloren.";
			Sound.playLost();
		}
		Font small = new Font("Helvetica", Font.BOLD, 40);
		FontMetrics metr = this.getFontMetrics(small);
		g2d.setColor(textColor);
		g2d.setFont(small);
		g2d.drawString(message, (SCREEN_SIZE - metr.stringWidth(message)) / 2, SCREEN_SIZE_Y / 2);
	}
	
	private void drawPacman(Graphics2D g) {
		switch (Var.dir) {
		case WAIT: drawPacmanWait(g); break;
		case UP: move(g, 0, -1, Dir.UP); break;
		case LEFT: move(g, -1, 0, Dir.LEFT); break;
		case RIGHT: move(g, 1, 0, Dir.RIGHT); break;
		case DOWN: move(g, 0, 1, Dir.DOWN); break;
		}
	}
	
	private void drawGoast(Graphics2D g, int x, int y) {
		if (unfuckable) {
			g.drawImage(Var.goodGoast, x, y, this);
		} else {
			g.drawImage(Var.ghost, x, y, this);
		}
	}

	private void drawLyb(Graphics2D g) {
		int ghostIndex = 0;
		for (int y = 0; y < (Var.BLOCK_SIZE * Var.ELEMENTS_Y); y+=Var.BLOCK_SIZE) {
			for (int x = 0; x < (Var.BLOCK_SIZE * Var.ELEMENTS_X); x+=Var.BLOCK_SIZE) {
				if (Var.co[y/Var.BLOCK_SIZE][x/Var.BLOCK_SIZE]==PACMAN) {
					pacman_x = x;
					pacman_y = y;
					drawPacman(g);
				} else if (Var.co[y/Var.BLOCK_SIZE][x/Var.BLOCK_SIZE]==GEIST) {
					drawGoast(g, x, y);
					Var.geister[ghostIndex].setGhostLocation(x, y);
					ghostIndex++;
				} else if (Var.co[y/Var.BLOCK_SIZE][x/Var.BLOCK_SIZE]==PILLE) {
					g.drawImage(Var.pille_img, x, y, this);
				} else if (Var.co[y/Var.BLOCK_SIZE][x/Var.BLOCK_SIZE]==FRESSPUNKT) {
					g.drawImage(Var.fresspunkt, x, y, this);
				} else if (Var.co[y/Var.BLOCK_SIZE][x/Var.BLOCK_SIZE]==MAUER) {
					g.setColor(Color.RED);
					g.fillRect(x, y, Var.BLOCK_SIZE, Var.BLOCK_SIZE);
				} else {
					g.setColor(Color.WHITE);
					g.fillRect(x, y, Var.BLOCK_SIZE, Var.BLOCK_SIZE);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		revalidate();
		repaint();
		if (anniNr==4) {
			anniNr = 0;
			if (unfuckable) {
				vorsichtigerGeist();
			} else {
				agresiverGeist();
			}
		} else {
			anniNr++;
		}
	}
}
