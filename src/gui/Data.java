package gui;

import pacman.Dir;
import pacman.Var;

public class Data {
	
	public class Pacman{
		public int x;
		public int y;
		public Dir dir;
		public int open;
	}
	
	public class Ghost{
		public int x;
		public int y;
		public boolean eatableGhost;
	}
	
	public class Pill{
		public int x, y;
	}
	
	public class Wall{
		public int x, y;
	}
	
	public class Void{
		public int x, y;
	}
	
	public class DData{
		public int punkte;
	}
	
	public class FoodPoints{
		public int x, y;
	}
	
	public Pacman pacman = new Pacman();
	public DData data = new DData();
	public Ghost[] ghost = new Ghost[Var.nrOfGhosts];
	public Pill[] pill = new Pill[Var.nrOfPills];
	public FoodPoints[] foodPoint = new FoodPoints[Var.nrOfAllPoints];
	public Wall[] wall = new Wall[Var.nrOfWalls];
	public Void[] voidd = new Void[Var.nrOfVoids];
	public boolean gameOver = false;
	public boolean gewonnen = true;
	public int punkte = 0;
	
	public Data() {
		for (int i = 0; i < Var.nrOfGhosts; i++) {
			ghost[i] = new Ghost();
		}
		for (int i = 0; i < Var.nrOfPills; i++) {
			pill[i] = new Pill();
		}
		for (int i = 0; i < Var.nrOfAllPoints; i++) {
			foodPoint[i] = new FoodPoints();
		}
		for (int i = 0; i < Var.nrOfWalls; i++) {
			wall[i] = new Wall();
		}
		for (int i = 0; i < Var.nrOfVoids; i++) {
			voidd[i] = new Void();
		}
	}
}