package uk.ac.cam.erm67.prejava.ex2;

public class TinyLife {
	public static void main(String[] args) throws java.io.IOException {
		play(Long.decode(args[0]));
	}
	
	public static boolean getCell(long world, int col, int row) {
		if (col < 0 || col > 7 || row < 0 || row > 7) {
			return false;
		}
		else {
			return PackedLong.get(world, 8 * row + col);
		}
	}
	
	public static long setCell(long world, int col, int row, boolean newVal) {
		if (col < 0 || col > 7 || row < 0 || row > 7) {
			return world;
		}
		else {
			return PackedLong.set(world, 8 * row + col, newVal);
		}
	}
	
	public static void print(long world) { 
		System.out.println("-");
		for (int row = 0; row < 8; row++) { 
			for (int col = 0; col < 8; col++) {
				System.out.print(getCell(world, col, row) ? "#" : "_"); 
			}
		System.out.println();
		}
	}
	
	public static int countNeighbours(long world, int col, int row) {
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
	
	public static boolean computeCell(long world,int col, int row) {
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
	
	public static long nextGeneration(long world) {
		long nextWorld = 0;
		
		for (int jRow = 0; jRow < 8; jRow++) {
			for (int jCol = 0; jCol < 8; jCol ++) {
				nextWorld = setCell(nextWorld, jCol, jRow, computeCell(world, jCol, jRow));
			}
		}
		
		return nextWorld;
	}
	
	public static void play(long world) throws java.io.IOException {
		int userResponse = 0;
		while (userResponse != 'q') {
			print(world);
			userResponse = System.in.read();
			world = nextGeneration(world);
		}
	}
}