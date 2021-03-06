package pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import gui.Gui;
import gui.MyFrame;

public class Pacman implements Konstanten {

	static MyFrame frame;

	public static void main(String[] args) {
		Var.initVars();
		Gui.initColor();
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

	public static class Index {
		public static int nrOfAllPoints = 0;
		public static int nrOfGhosts = 0;
		public static int nrOfPills = 0;
		public static int nrOfWalls = 0;
		public static int wormhole = 0;
	}
	
	public static int w1x = 0, w1y = 0, w2x = 0, w2y = 0;

	Pacman() {
		frame = new MyFrame();
		for (int y = 0; y < Var.co.length; y++) {
			for (int x = 0; x < Var.co[0].length; x++) {
				if (Var.co[y][x] == PACMAN) {
					frame.gui.drawPacman(gc(x), gc(y), Dir.WAIT, 0);
				} else if (Var.co[y][x] == GEIST) {
					frame.gui.drawGhost(gc(x), gc(y), Index.nrOfGhosts);
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
				} else if (Var.co[y][x] == WORMHOLE) {
					frame.gui.drawWormhole(gc(x), gc(y), 0, Index.wormhole);
					if (Index.wormhole == 0) {
						w1x = gc(x);
						w1y = gc(y);
					} else {
						w2x = gc(x);
						w2y = gc(y);
					}
					Index.wormhole++;
				} else {
					System.out.println("ERROR: Unexpected char at Var.co[" + x + "][" + y + "]");
				}
			}
		}
		new GameClock();
	}

	public static class GameClock implements ActionListener {

		Timer timer;
		int anniNr = 0, anniNrWormhole = 0;
		final int ANNI_LENGTH = 4;
		final int STEP = 15;
		private int takts = 0;
		private final int PILL_BONI = 32, GHOST_POINTS = 100;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkWall()) {
				movePacman();
				checkFood();
				checkPill();
			}
			if (takts > 0) {
				takts--;
			}
			if (takts == 0) {
				frame.gui.data.pacmanCanEatGhosts = false;
			}
			checkGhost();
			checkWinn();
			moveGhost();
			annimateWormhole();
			jumpThroughWormhole();
		}
		
		private void jumpThroughWormhole() {
			for (int x = 0; x < Var.BLOCK_SIZE; x++) {
				for (int y = 0; y < Var.BLOCK_SIZE; y++) {
					if (frame.gui.data.wormhole[0].x + x == frame.gui.data.pacman.x
							&& frame.gui.data.wormhole[0].y + y == frame.gui.data.pacman.y) {
						frame.gui.drawPacman(frame.gui.data.wormhole[1].x - Var.BLOCK_SIZE, frame.gui.data.wormhole[1].y, Dir.LEFT, 1);
						x = Var.BLOCK_SIZE;
						y = Var.BLOCK_SIZE;
						break;
					}
				}
			}
			for (int x = 0; x < Var.BLOCK_SIZE; x++) {
				for (int y = 0; y < Var.BLOCK_SIZE; y++) {
					if (frame.gui.data.wormhole[1].x + x == frame.gui.data.pacman.x
							&& frame.gui.data.wormhole[1].y + y == frame.gui.data.pacman.y) {
						frame.gui.drawPacman(frame.gui.data.wormhole[0].x + Var.BLOCK_SIZE, frame.gui.data.wormhole[0].y, Dir.RIGHT, 1);
						x = Var.BLOCK_SIZE;
						y = Var.BLOCK_SIZE;
						break;
					}
				}
			}
		}
		
		private void annimateWormhole() {
			frame.gui.drawWormhole(w1x, w1y, anniNrWormhole, 0);
			frame.gui.drawWormhole(w2x, w2y, anniNrWormhole, 1);
			anniNrWormhole++;
			if (anniNrWormhole > 2) {
				anniNrWormhole = 0;
			}
		}
		
		private Dir getRandomDir() {
			final Dir[] dirArr = {Dir.UP, Dir.LEFT, Dir.RIGHT, Dir.DOWN, Dir.WAIT};
			int randomNr = (int) Math.round((Math.random() * 5) - 1);
			if (randomNr < 0) {
				randomNr = 0;
			}
			return (dirArr[randomNr]);
		}

		private void moveGhost() {
			Dir[] ghostDirection = new Dir[Var.nrOfGhosts];
			boolean bb = frame.gui.data.pacmanCanEatGhosts;
			for (int i = 0; i < Var.nrOfGhosts; i++) {
				if (frame.gui.data.ghost[i] != null) {
					int dif_x = frame.gui.data.ghost[i].x - frame.gui.data.pacman.x;
					int dif_y = frame.gui.data.ghost[i].y - frame.gui.data.pacman.y;
					if (dif_x < Var.BLOCK_SIZE * 2 && dif_x > ( - (Var.BLOCK_SIZE * 2)) || (dif_y < Var.BLOCK_SIZE * 2 && dif_y > ( - (Var.BLOCK_SIZE * 2)))) {
						ghostDirection[i] = getRandomDir();
					} else if (dif_x > Var.BLOCK_SIZE * 8 || dif_x < ( - (Var.BLOCK_SIZE * 8)) || (dif_y > Var.BLOCK_SIZE * 8 || dif_y < ( - (Var.BLOCK_SIZE * 8)))) {
						ghostDirection[i] = getRandomDir();
					} else {
						if (dif_x < 0) {
							if (bb) {
								ghostDirection[i] = Dir.LEFT;
							} else {
								ghostDirection[i] = Dir.RIGHT;
							}
						}
						if (dif_x > 0) {
							if (bb) {
								ghostDirection[i] = Dir.RIGHT;
							} else {
								ghostDirection[i] = Dir.LEFT;
							}
						}
						if (dif_x == 120 || dif_x == -120) {
							if (dif_y < 0) {
								if (bb) {
									ghostDirection[i] = Dir.UP;
								} else {
									ghostDirection[i] = Dir.DOWN;
								}
							} else if (dif_y > 0) {
								if (bb) {
									ghostDirection[i] = Dir.DOWN;
								} else {
									ghostDirection[i] = Dir.UP;
								}
							} else {
								ghostDirection[i] = getRandomDir();
							}
						}
					}
					int limit = 0;
					while ((!checkWallGhost(ghostDirection[i], frame.gui.data.ghost[i].x, frame.gui.data.ghost[i].y))
							&& limit < 10 
							|| isGhostThere(ghostDirection[i], frame.gui.data.ghost[i].x, frame.gui.data.ghost[i].y, i)
							&& limit < 10) {
						ghostDirection[i] = getRandomDir();
						limit++;
					}
					if (limit == 10) {
						ghostDirection[i] = Dir.WAIT;
					}
				}
			}
			for (int i = 0; i < Var.nrOfGhosts; i++) {
				if (ghostDirection[i] != null) {
					switch (ghostDirection[i]) {
					case UP:
						frame.gui.drawGhost(frame.gui.data.ghost[i].x, frame.gui.data.ghost[i].y - STEP, i);
						break;
					case LEFT:
						frame.gui.drawGhost(frame.gui.data.ghost[i].x - STEP, frame.gui.data.ghost[i].y, i);
						break;
					case RIGHT:
						frame.gui.drawGhost(frame.gui.data.ghost[i].x + STEP, frame.gui.data.ghost[i].y, i);
						break;
					case DOWN:
						frame.gui.drawGhost(frame.gui.data.ghost[i].x, frame.gui.data.ghost[i].y + STEP, i);
						break;
					case WAIT:
						frame.gui.drawGhost(frame.gui.data.ghost[i].x, frame.gui.data.ghost[i].y, i);
					}
				}
			}
		}

		private void checkWinn() {
			final int MAX_POINTS = Var.nrOfAllPoints + (Var.nrOfPills * PILL_BONI) + (Var.nrOfGhosts * GHOST_POINTS);
			if (frame.gui.data.punkte == MAX_POINTS) {
				frame.gui.drawGameOver(true);
			}
		}
		
		/**
		 * 
		 * @param directionGhost
		 * @return true if there is no wall in this direction.
		 */
		private boolean checkWallGhost(Dir directionGhost, int lx, int ly) {
			return (checkNextStep(directionGhost, lx, ly));
		}
		
		private boolean checkNextStep(Dir direction, int lx, int ly) {
			int next_x[] = { -1, -1 }, next_y[] = { -1, -1 };
			switch (direction) {
			case UP:
				next_x[0] = lx;
				next_x[1] = lx + 50;
				next_y[0] = ly - STEP;
				next_y[1] = ly - STEP;
				break;
			case LEFT:
				next_x[0] = lx - STEP;
				next_y[0] = ly;
				next_x[1] = lx - STEP;
				next_y[1] = ly + 50;
				break;
			case RIGHT:
				next_x[0] = lx + 50 + STEP;
				next_y[0] = ly;
				next_x[1] = lx + 50 + STEP;
				next_y[1] = ly + 50;
				break;
			case DOWN:
				next_x[0] = lx;
				next_y[0] = ly + 50 + STEP;
				next_x[1] = 50 + lx;
				next_y[1] = ly + 50 + STEP;
				break;
			case WAIT:
				next_x[0] = lx;
				next_x[1] = lx;
				next_y[0] = ly;
				next_y[1] = ly;
			}
			for (int i = 0; i < frame.gui.data.wall.length; i++) {
				for (int y = 0; y < Var.BLOCK_SIZE; y++) {
					for (int x = 0; x < Var.BLOCK_SIZE; x++) {
						for (int m = 0; m < 2; m++) {
							if (frame.gui.data.wall[i].x + x == next_x[m]
									&& frame.gui.data.wall[i].y + y == next_y[m]) {
								return false;
							}
						}
					}
				}
			}
			return true;
		}
		
		private boolean isGhostThere(Dir direction, int lx, int ly, int currentIndex) {
			int next_x[] = { -1, -1 }, next_y[] = { -1, -1 };
			switch (direction) {
			case UP:
				next_x[0] = lx;
				next_x[1] = lx + 50;
				next_y[0] = ly - STEP;
				next_y[1] = ly - STEP;
				break;
			case LEFT:
				next_x[0] = lx - STEP;
				next_y[0] = ly;
				next_x[1] = lx - STEP;
				next_y[1] = ly + 50;
				break;
			case RIGHT:
				next_x[0] = lx + 50 + STEP;
				next_y[0] = ly;
				next_x[1] = lx + 50 + STEP;
				next_y[1] = ly + 50;
				break;
			case DOWN:
				next_x[0] = lx;
				next_y[0] = ly + 50 + STEP;
				next_x[1] = 50 + lx;
				next_y[1] = ly + 50 + STEP;
				break;
			case WAIT:
				next_x[0] = lx;
				next_x[1] = lx;
				next_y[0] = ly;
				next_y[1] = ly;
			}
			for (int i = 0; i < frame.gui.data.ghost.length; i++) {
				for (int y = 0; y < Var.BLOCK_SIZE; y++) {
					for (int x = 0; x < Var.BLOCK_SIZE; x++) {
						for (int m = 0; m < 2; m++) {
							if (i != currentIndex && frame.gui.data.ghost[i] != null) {
								if (frame.gui.data.ghost[i].x + x == next_x[m]
										&& frame.gui.data.ghost[i].y + y == next_y[m]) {
									return true;
								}
							}
						}
					}
				}
			}
			return false;
		}

		private boolean checkWall() {
			int lx = frame.gui.data.pacman.x;
			int ly = frame.gui.data.pacman.y;
			return (checkNextStep(Var.dir, lx, ly));
		}

		private void checkGhost() {
			int l = 0;
			while (l < frame.gui.data.ghost.length) {
				if (frame.gui.data.ghost[l] != null) {
					for (int x = 0; x < Var.BLOCK_SIZE; x++) {
						for (int y = 0; y < Var.BLOCK_SIZE; y++) {
							if (frame.gui.data.pacman.x + x == frame.gui.data.ghost[l].x
									&& frame.gui.data.pacman.y + y == frame.gui.data.ghost[l].y) {
								if (frame.gui.data.pacmanCanEatGhosts) {
									frame.gui.data.ghost[l] = null;
									frame.gui.data.punkte += GHOST_POINTS;
								} else {
									frame.gui.data.leben--;
									frame.gui.drawPacman(gc(Var.get_pacman_default_position_x()),
											gc(Var.get_pacman_default_position_y()), Dir.WAIT, 0);
									if (frame.gui.data.leben == 0) {
										frame.gui.drawGameOver(false);
									}
								}
								y = Var.BLOCK_SIZE;
								x = Var.BLOCK_SIZE;
								break;
							}
						}
					}
				}
				l++;
			}
		}

		private void checkPill() {
			int l = 0;
			while (l < frame.gui.data.pill.length) {
				if (frame.gui.data.pill[l] != null) {
					for (int x = 0; x < Var.BLOCK_SIZE; x++) {
						for (int y = 0; y < Var.BLOCK_SIZE; y++) {
							if (frame.gui.data.pacman.x + x == frame.gui.data.pill[l].x
									&& frame.gui.data.pacman.y + y == frame.gui.data.pill[l].y) {
								frame.gui.data.pill[l] = null;
								frame.gui.data.pacmanCanEatGhosts = true;
								frame.gui.data.punkte += PILL_BONI;
								takts = 200;
								y = Var.BLOCK_SIZE;
								x = Var.BLOCK_SIZE;
							}
						}
					}
				}
				l++;
			}
		}

		private void checkFood() {
			int l = 0;
			while (l < frame.gui.data.foodPoint.length) {
				if (frame.gui.data.foodPoint[l] != null) {
					for (int x = 0; x < Var.BLOCK_SIZE; x++) {
						for (int y = 0; y < Var.BLOCK_SIZE; y++) {
							if (frame.gui.data.pacman.x + x == frame.gui.data.foodPoint[l].x
									&& frame.gui.data.pacman.y + y == frame.gui.data.foodPoint[l].y) {
								frame.gui.data.foodPoint[l] = null;
								frame.gui.data.punkte++;
								x = Var.BLOCK_SIZE;
								y = Var.BLOCK_SIZE;
								break;
							}
						}
					}
				}
				l++;
			}
		}

		private void movePacman() {
			int x = frame.gui.data.pacman.x;
			int y = frame.gui.data.pacman.y;
			switch (Var.dir) {
			case UP:
				frame.gui.drawPacman(x, y - STEP, Dir.UP, anniNr);
				break;
			case LEFT:
				frame.gui.drawPacman(x - STEP, y, Dir.LEFT, anniNr);
				break;
			case RIGHT:
				frame.gui.drawPacman(x + STEP, y, Dir.RIGHT, anniNr);
				break;
			case DOWN:
				frame.gui.drawPacman(x, y + STEP, Dir.DOWN, anniNr);
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
