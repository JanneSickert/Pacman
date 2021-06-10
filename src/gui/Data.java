package gui;

import pacman.Dir;
import pacman.Var;

public class Data {
	
	class Pacman{
		int x;
		int y;
		Dir dir;
		int open;
	}
	
	class Ghost{
		int x;
		int y;
		boolean eatableGhost;
	}
	
	class Pill{
		int x, y;
	}
	
	class Wall{
		int x, y;
	}
	
	class Void{
		int x, y;
	}
	
	class DData{
		int punkte;
	}
	
	class FoodPoints{
		int x, y;
	}
	
	Pacman pacman = new Pacman();
	DData data = new DData();
	Ghost[] ghost = new Ghost[Var.geister.length];
	Pill[] pill = new Pill[Var.nrOfPills];
	FoodPoints[] foodPoint = new FoodPoints[Var.nrOfAllPoints];
	Wall[] wall = new Wall[Var.nrOfWalls];
	Void[] voidd = new Void[Var.nrOfVoids];
	boolean gameOver = false;
	boolean gewonnen = true;
	int punkte = 0;
	
	public Data() {
		for (int i = 0; i < Var.geister.length; i++) {
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