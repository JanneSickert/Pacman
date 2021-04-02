package pacman;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Hier befindet sich die Variablenverwaltung die in
 * folgenden Schritten durchgefürt wird:
 * Bilder werden geladen,
 * Lybyrinth wird geladen,
 * Fresspunkte werden gezählt,
 * Geister werden erstellt.
 * 
 * @author Janne
 *
 */
public class Var implements Konstanten{

	public static boolean inGame = true;
	public static Dir dir = Dir.WAIT;
	public static final int ELEMENTS_X = 23, ELEMENTS_Y = 19;
	// Hier ist das Lybyrinth gespeichert.
	public static char[][] co = new char[ELEMENTS_Y][ELEMENTS_X];
	public static final int BLOCK_SIZE = 50;
	public static int nrOfAllPoints = 0;
	public static int leben = 3;
	private static int pacman_default_position_x, pacman_default_position_y;
	public static int nrKollidierterGeist;
	public static Ghost[] geister = new Ghost[3];
	public static char[] untergrund = {VOID, VOID, VOID};
	public static Image ghost, goodGoast, pacman1, pille_img, fresspunkt,
	pacman2up, pacman2down, pacman2left, pacman2right,
	pacman3up, pacman3down, pacman3left, pacman3right,
	pacman4up, pacman4down, pacman4left, pacman4right;
	
	public static int get_pacman_default_position_x() {
		return pacman_default_position_x;
	}
	
	public static int get_pacman_default_position_y() {
		return pacman_default_position_y;
	}
	
	public static void initVars() {
		loadImages();
		loadLyb();
		countPoints();
		findPacman_default_position();
		createGhostArray();
	}
	
	private static void createGhostArray() {
		int ghost_default_position_x[] = new int[3];
		int ghost_default_position_y[] = new int[3];
		int index = 0;
		for (int y = 0; y < ELEMENTS_Y; y++) {
			for (int x = 0; x < ELEMENTS_X; x++) {
				if (co[y][x]==GEIST) {
					ghost_default_position_x[index] = x;
					ghost_default_position_y[index] = y;
					index++;
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			geister[i] = new Ghost(i, ghost_default_position_x[i], ghost_default_position_y[i]);
		}
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

	private static void countPoints() {
		for (int y = 0; y < ELEMENTS_Y; y++) {
			for (int x = 0; x < ELEMENTS_X; x++) {
				if (co[y][x]==FRESSPUNKT) {
					nrOfAllPoints++;
				}
			}
		}
	}
	
	private static void loadImages() {
		String p = "res/img/pacman/";
		ghost = new ImageIcon("res/img/ghost.png").getImage();
		goodGoast = new ImageIcon("res/img/goodGoast.png").getImage();
		pille_img = new ImageIcon("res/img/pille.png").getImage();
		fresspunkt = new ImageIcon("res/img/FP.png").getImage();
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
