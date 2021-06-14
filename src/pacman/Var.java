package pacman;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Var implements Konstanten{

	public static int nrOfAllPoints = 0;
	public static int nrOfGhosts = 0;
	public static int nrOfPills = 0;
	public static int nrOfWalls = 0;

	public static Dir dir = Dir.WAIT;
	public static final int ELEMENTS_X = 23, ELEMENTS_Y = 19;

	public static char[][] co = new char[ELEMENTS_Y][ELEMENTS_X];
	public static final int BLOCK_SIZE = 60;
	private static int pacman_default_position_x, pacman_default_position_y;
	
	public static Image ghost, goodGoast,
	pille_img, pacman1,
	pacman2up, pacman2down, pacman2left, pacman2right,
	pacman3up, pacman3down, pacman3left, pacman3right,
	pacman4up, pacman4down, pacman4left, pacman4right,
	wormhole1, wormhole2, wormhole3;
	
	public static Image getWormholeImage(int anniNr) {
		Image im = null;
		switch (anniNr) {
		case 0:
			im = wormhole1;
			break;
		case 1:
			im = wormhole2;
			break;
		case 2:
			im = wormhole3;
			break;
		default:
			System.out.println("ERROR: Ungueltiger parameter in public static Image getWormholeImage(int anniNr)");
		}
		return im;
	}
	
	public static Image getPacmanImage(Dir dir, int anniNr) {
		Image im = null;
		switch (dir) {
		case UP:
			switch (anniNr) {
			case 0:
				im = pacman1;
				break;
			case 1:
				im = pacman2up;
				break;
			case 2:
				im = pacman3up;
				break;
			case 3:
				im = pacman4up;
				break;
			default:
				System.out.println("ERROR: Ungueltiger parameter in public static Image getPacman(Dir dir, int anniNr)");
			}
			break;
		case LEFT:
			switch (anniNr) {
			case 0:
				im = pacman1;
				break;
			case 1:
				im = pacman2left;
				break;
			case 2:
				im = pacman3left;
				break;
			case 3:
				im = pacman4left;
				break;
			default:
				System.out.println("ERROR: Ungueltiger parameter in public static Image getPacman(Dir dir, int anniNr)");
			}
			break;
		case RIGHT:
			switch (anniNr) {
			case 0:
				im = pacman1;
				break;
			case 1:
				im = pacman2right;
				break;
			case 2:
				im = pacman3right;
				break;
			case 3:
				im = pacman4right;
				break;
			default:
				System.out.println("ERROR: Ungueltiger parameter in public static Image getPacman(Dir dir, int anniNr)");
			}
			break;
		case DOWN:
			switch (anniNr) {
			case 0:
				im = pacman1;
				break;
			case 1:
				im = pacman2down;
				break;
			case 2:
				im = pacman3down;
				break;
			case 3:
				im = pacman4down;
				break;
			default:
				System.out.println("ERROR: Ungueltiger parameter in public static Image getPacman(Dir dir, int anniNr)");
			}
			break;
		case WAIT:
			im = pacman1;
			break;
		}
		return im;
	}
	
	public static int get_pacman_default_position_x() {
		return pacman_default_position_x;
	}
	
	public static int get_pacman_default_position_y() {
		return pacman_default_position_y;
	}
	
	Count[] nrs = {
			new Count() {
				@Override
				public void increment() {
					nrOfAllPoints++;
				}
				@Override
				public char getChar() {
					return FRESSPUNKT;
				}
			},
			new Count() {
				@Override
				public void increment() {
					nrOfGhosts++;
				}
				@Override
				public char getChar() {
					return GEIST;
				}
			},
			new Count() {
				@Override
				public void increment() {
					nrOfPills++;
				}
				@Override
				public char getChar() {
					return PILLE;
				}
			},
			new Count() {
				@Override
				public void increment() {
					nrOfWalls++;
				}
				@Override
				public char getChar() {
					return MAUER;
				}
			}
		};
	
	Var() {
		for (Count c : nrs) {
			c.count();
		}
	}
	
	public static void initVars() {
		loadImages();
		loadLyb();
		new Var();
		findPacman_default_position();
	}
	
	private static void findPacman_default_position() {
		for (int y = 0; y < ELEMENTS_Y; y++) {
			for (int x = 0; x < ELEMENTS_X; x++) {
				if (co[y][x]==PACMAN) {
					pacman_default_position_x = x;
					pacman_default_position_y = y;
				}
			}
		}
	}

	abstract class Count{
		public void count() {
			for (int y = 0; y < ELEMENTS_Y; y++) {
				for (int x = 0; x < ELEMENTS_X; x++) {
					if (co[y][x] == getChar()) {
						increment();
					}
				}
			}
		}
		public abstract void increment();
		public abstract char getChar();
	}
	
	private static void loadImages() {
		String p = "res/img/pacman/", w = "res/img/Wormhole/";
		ghost = new ImageIcon("res/img/ghost.png").getImage();
		goodGoast = new ImageIcon("res/img/goodGoast.png").getImage();
		pille_img = new ImageIcon("res/img/pille.png").getImage();
		pacman1 = new ImageIcon(p + "pacman.png").getImage();
		pacman2up = new ImageIcon(p + "up1.png").getImage();
		pacman3up = new ImageIcon(p + "up2.png").getImage();
		pacman4up = new ImageIcon(p + "up3.png").getImage();
		pacman2down = new ImageIcon(p + "down1.png").getImage();
		pacman3down = new ImageIcon(p + "down2.png").getImage();
		pacman4down = new ImageIcon(p + "down3.png").getImage();
		pacman2left = new ImageIcon(p + "left1.png").getImage();
		pacman3left = new ImageIcon(p + "left2.png").getImage();
		pacman4left = new ImageIcon(p + "left3.png").getImage();
		pacman2right = new ImageIcon(p + "right1.png").getImage();
		pacman3right = new ImageIcon(p + "right2.png").getImage();
		pacman4right = new ImageIcon(p + "right3.png").getImage();
		wormhole1 = new ImageIcon(w + "1.png").getImage();
		wormhole2 = new ImageIcon(w + "2.png").getImage();
		wormhole3 = new ImageIcon(w + "3.png").getImage();
	}
	
	private static ArrayList<String> readFile(String FileUrl) {
		ArrayList<String> list = new ArrayList<String>();
		FileReader fr;
		try {
			fr = new FileReader(FileUrl);
			BufferedReader br = new BufferedReader(fr);
			String zeile = "";
			while ((zeile = br.readLine()) != null) {
				list.add(zeile);
			}
			br.close();
		} catch (FileNotFoundException e1) {
			System.exit(0);
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return list;
	}
	
	private static void loadLyb() {
		ArrayList<String> list = readFile("res/Lybyrinth.txt");
		int ix;
		for (int y = 0; y < ELEMENTS_Y; y++) {
			ix = 0;
			for (int x = 0; x < ELEMENTS_X * 2; x++) {
				if (x%2==0) {
					co[y][ix] = list.get(y).charAt(x);
					ix++;
				}
			}
		}
	}
}
