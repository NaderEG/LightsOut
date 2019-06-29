// Author: Gabriel Granata, Nader El-Ghotmi
// Student number: 300057462, 300051343
// Course: ITI 1121
// Assignment: 3
// Question: 2
import java.util.Random;
import java.util.ArrayList;

public class GameModel {

	private boolean[][] gameState;
	private int width;
	private int height;
	private int steps;
	private Solution solution;

	public GameModel(int width, int height) {

		this.width = width;
		this.height = height;
		this.steps = 0;

		this.gameState = new boolean[height][width];
		this.setSolution();
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public boolean isON(int i, int j) {
		return gameState[i][j];
	}

	public void reset() {

		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				this.gameState[i][j] = false;
			}
		}
		setSolution();
		this.steps = 0;
	}
	public void set(int i, int j, boolean value) {
		this.gameState[j][i] = value;
	}

	public void click (int i, int j){
	    //increments value of the board at specified position and all adjacent positions
		gameState[i][j] = !gameState[i][j];

	  if(i==0 && j==0) {
	  	gameState[i][j+1] = !gameState[i][j+1];
	    gameState[i+1][j] = !gameState[i+1][j];
	  }
	  else if(i==this.height-1 && j==0) {
	  	gameState[i][j+1] = !gameState[i][j+1];
	    gameState[i-1][j] = !gameState[i-1][j];
	  }
	  else if(i==0 && j==this.width-1) {
	    gameState[i+1][j] = !gameState[i+1][j];
	   	gameState[i][j-1] = !gameState[i][j-1];
	  }
	  else if(i==this.height-1 && j==this.width-1) {
	     gameState[i-1][j] = !gameState[i-1][j];
	     gameState[i][j-1] = !gameState[i][j-1];
		}
		else if(i==0) {
	   	 gameState[i][j-1] = !gameState[i][j-1];
	     gameState[i][j+1] = !gameState[i][j+1];
	     gameState[i+1][j] = !gameState[i+1][j];
	  }
	  else if(i==this.height-1) {
	     gameState[i-1][j] = !gameState[i-1][j];
	     gameState[i][j-1] = !gameState[i][j-1];
	     gameState[i][j+1] = !gameState[i][j+1];
		}
	  else if(j==0) {
	   	 gameState[i-1][j] = !gameState[i-1][j];
	     gameState[i+1][j] = !gameState[i+1][j];
	     gameState[i][j+1] = !gameState[i][j+1];
	  }
	  else if(j==this.width-1) {
	     gameState[i-1][j] = !gameState[i-1][j];
	     gameState[i+1][j] = !gameState[i+1][j];
	     gameState[i][j-1] = !gameState[i][j-1];
	  }
	  else{
	     gameState[i-1][j] = !gameState[i-1][j];
	     gameState[i+1][j] = !gameState[i+1][j];
	     gameState[i][j-1] = !gameState[i][j-1];
	     gameState[i][j+1] = !gameState[i][j+1];
	  }
		steps++;
	}


	public int getNumberOfSteps(){
		return steps;
	}

	public boolean isFinished(){
		for (int i = 0; i<height; i++){
			for (int j = 0; j<width; j++){
				if (gameState[i][j]==false){
					return false;
				}
			}
		}
		return true;
	}

	public void randomize(){
		Random magicBox = new Random();
		int n;
		steps = 0;
		for (int i = 0; i<height; i++){
			for (int j = 0; j<width; j++){
				n = magicBox.nextInt(2);
				if (n==0){
					gameState[i][j] = true;
				}
				else{
					gameState[i][j] = false;
				}
			}
		}
		solution = LightsOut.solveShortest(this);
		if (solution == null){
			randomize();
		}
	}

	public void setSolution(){

		solution = LightsOut.solveShortest(this);


	}

	public boolean solutionSelects(int i, int j){

		if (solution!=null){
			if (solution.get(i, j)){
				return true;
			}
		}
		return false;
	}

	public String toString(){

		String arrayStr = "[";

		for (int i = 0; i<height; i++){
				for (int j = 0; j<width; j++){
						if (j==0){
								arrayStr += "[";
						}
						if (i == height-1 && j == width-1){
								arrayStr += (Boolean.valueOf(gameState[i][j]) + "]");
						} else if (j != width-1){
								arrayStr += (Boolean.valueOf(gameState[i][j]) + ",");
						} else if (j==width-1 && i != height-1){
								arrayStr += (Boolean.valueOf(gameState[i][j]) + "],\n");
						}
				}
		}
		arrayStr += "]";
		return arrayStr;
	}
}
