package snake_gameboard;

public class Snake_gameboard {
	private int[][] gameboard;
	private int size;
	
	public Snake_gameboard(int size) {
		this.size = size;
		gameboard = new int[size][size];
	}
	
	public void displayBoard() {
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++) {
				System.out.print("□");
			}
			System.out.println();
		}
	}
}
