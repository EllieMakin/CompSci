package uk.ac.cam.erm67.prejava.ex3;

public class ArrayLife {
	public static boolean getFromPackedLong(long packed, int position) {
		return ((packed >>> position) & 1) == 1;
	}
	
	public static void main(String[] args) throws java.io.IOException {
		int size = Integer.parseInt(args[0]);
		long initial = Long.decode(args[1]);
		boolean[][] world = new boolean[size][size];
		//place the long representation of the game board in the centre of "world"
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				world[i+size/2-4][j+size/2-4] = getFromPackedLong(initial,i*8+j);
			}
		}
		play(world);
	}
	
	public static boolean getCell(boolean[][] world, int col, int row) {
		if (row < 0 || row > world.length - 1 || col < 0 || col > world[row].length - 1){
			return false;
		}
		else {
			return world[row][col];
		}
	}
	
	public static void setCell(boolean[][] world, int col, int row, boolean newVal) {
		if (row >= 0 && row < world.length && col >= 0 && col < world[row].length){
			world[row][col] = newVal;
		}
	}
	
	public static void print(boolean[][] world) { 
		System.out.println("-");
		for (int jRow = 0; jRow < world.length; jRow++) { 
			for (int jCol = 0; jCol < world[jRow].length; jCol++) {
				System.out.print(getCell(world, jRow, jCol) ? "#" : "_"); 
			}
			System.out.println();
		}
	}
	
	public static int countNeighbours(boolean[][] world, int col, int row) {
		int total = 0;
		if (getCell(world, col, row)) {
			total = -1;
		}
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (getCell(world, col + dx, row + dy)){
					total++;
				}
			}
		}
		return total;
	}
	
	public static boolean computeCell(boolean[][] world,int col, int row) {
		boolean isLiveCell = getCell(world, col, row);
		int nNeighbours = countNeighbours(world, col, row);
		boolean nextState = false;
		
		if (isLiveCell && (nNeighbours == 2 || nNeighbours == 3)) {
			nextState = true;
		}
		else if (!isLiveCell && nNeighbours == 3) {
			nextState = true;
		}
		
		return nextState;
	}
	
	public static boolean[][] nextGeneration(boolean[][] world) {
		boolean[][] nextWorld = new boolean[world.length][world[0].length];
		
		for (int jRow = 0; jRow < world.length; jRow++) {
			for (int jCol = 0; jCol < world[jRow].length; jCol++) {
				nextWorld[jRow][jCol] = computeCell(world, jCol, jRow);
			}
		}
		
		return nextWorld;
	}
	
	public static void play(boolean[][] world) throws java.io.IOException {
		int userResponse = 0;
		while (userResponse != 'q') {
			print(world);
			userResponse = System.in.read();
			world = nextGeneration(world);
		}
	}
}