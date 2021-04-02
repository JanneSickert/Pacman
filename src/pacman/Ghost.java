package pacman;

/**
 * Mit den Daten vom Game Object wird hier ermittelt, wie sich ein Geist, Pacman
 * nähern oder Flüchten soll.
 * 
 * @author Janne
 *
 */
public class Ghost implements Konstanten{

	private int pacman_x, pacman_y; // speichert den Index von Pacman.
	private int x, y; // speichert den Index des Geistes.
	private int nr; // wird für die untergrundvariable benötigt.
	private byte nrOfRandomMoves; // Stack entry counter
	private int default_x, default_y;

	public Ghost(int nr, int default_x, int default_y) {
		this.nr = nr;
		this.default_x = default_x;
		this.default_y = default_y;
	}

	public void setPacmanPosition(int pacman_x, int pacman_y) {
		this.pacman_x = pacman_x / Var.BLOCK_SIZE;
		this.pacman_y = pacman_y / Var.BLOCK_SIZE;
	}

	public void setGhostLocation(int x, int y) {
		this.x = x / Var.BLOCK_SIZE;
		this.y = y / Var.BLOCK_SIZE;
	}

	private boolean check(int sum_x, int sum_y) {
		boolean ret = true;
		if (Var.co[y + sum_y][x + sum_x] == MAUER) {
			ret = false;
		}
		if (Var.co[y + sum_y][x + sum_x] == GEIST) {
			ret = false;
		}
		if (Var.co[y + sum_y][x + sum_x] == PACMAN) {
			Var.leben--;
			if (Var.leben == 0) {
				Var.inGame = false;
			} else {
				Var.co[y][x] = Var.untergrund[nr];
				Var.nrKollidierterGeist = nr;
				Var.co[Var.get_pacman_default_position_y()][Var.get_pacman_default_position_x()] = PACMAN;
				Var.co[default_y][default_x] = GEIST;
			}
		}
		return ret;
	}

	private void moveGhost(int sum_x, int sum_y) {
		if (x + sum_x < Var.ELEMENTS_X && y + sum_y < Var.ELEMENTS_Y &&
				x + sum_x >= 0 && y + sum_y >= 0) {
			if (check(sum_x, sum_y)) {
				Var.untergrund[nr] = Var.co[y + sum_y][x + sum_x];
				Var.co[y + sum_y][x + sum_x] = GEIST;
				Var.co[y][x] = Var.untergrund[nr];
			} else {
				if (nrOfRandomMoves < 10) {
					randomMove();
				}
			}
		} else {
			moveGhost(sum_x*(-1), sum_y*(-1));
		}
	}

	private void randomMove() {
		int randomNr = (int) Math.round(Math.random() * 3);
		nrOfRandomMoves++;
		setMoveIndex(randomNr);
	}
	
	private void setMoveIndex(int nr) {
		switch (nr) {
			case 0: moveGhost(0, -1); break;
			case 1: moveGhost(-1, 0); break;
			case 2: moveGhost(1, 0);  break;
			default: moveGhost(0, 1);
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	private void ausrichten(Dir richtung) {
		switch (richtung) {
		case UP: setMoveIndex(0); break;
		case LEFT: setMoveIndex(1); break;
		case RIGHT: setMoveIndex(2); break;
		case DOWN: setMoveIndex(3); break;
		}
	}

	public void move(boolean zuPacman) {
		nrOfRandomMoves = 0;
		int delta_x, delta_y;
		boolean right, up; // Richtung in die der Geist gehen muss
		if (pacman_x < x) {
			delta_x = x - pacman_x;
			right = false;
		} else {
			delta_x = pacman_x - x;
			right = true;
		}
		if (pacman_y < y) {
			delta_y = y - pacman_y;
			up = true;
		} else {
			delta_y = pacman_y - y;
			up = false;
		}
		if (zuPacman) {
			if (delta_x < delta_y) {
				if (right) {
					ausrichten(Dir.RIGHT);
				} else {
					ausrichten(Dir.LEFT);
				}
			} else {
				if (up) {
					ausrichten(Dir.UP);
				} else {
					ausrichten(Dir.DOWN);
				}
			}
		} else {
			if (delta_x > delta_y) {
				if (!(right)) {
					ausrichten(Dir.RIGHT);
				} else {
					ausrichten(Dir.LEFT);
				}
			} else {
				if (!(up)) {
					ausrichten(Dir.UP);
				} else {
					ausrichten(Dir.DOWN);
				}
			}
		}
	}
}
