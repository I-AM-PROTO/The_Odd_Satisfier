package snake_gameboard;

enum Direction {
	LEFT(0,-1), RIGHT(0, 1), UP(-1, 0), DOWN(1, 0);
	
	private int xmove, ymove;
	private Direction(int xmove, int ymove) { this.xmove = xmove; this.ymove = ymove; }
}

public class Snake_game {
	final private double defaultInterval = 500;
	private double speed;
	private double inGameInterval; //interval between snake moves
	private int gameWidth, gameHeight; // where snake can move (excludes walls)
	private int growthLen; // turn left for snake to grow (3 each apple, i guess)
	private Snake_gameboard gameBoard;
	private Block head, tail;
	
	//settings method - board size, speed, etc
	
	//getting input method
	
	//true:ded false:alive
	boolean moveSnake () {
		
		
		return false;
	}
	
	//true:Victory, game ends false:still ingame
	boolean checkIfWin() {
		return false;
	}
	
	//get settings, create board, put head in center, direction right
	public Snake_game(double speed, int gameWidth, int gameHeight) {
		this.speed = speed;
		this.inGameInterval = defaultInterval / speed;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		gameBoard = new Snake_gameboard(gameWidth, gameHeight);
	}
	
	void run() {
		
		//infinite loop
		/*
		 * if no input -> keep direction (default right)
		 * else if input -> change direction to input 
		 * 
		 * move & check if lost
		 * check if victory == check if full snake
		 */
		//end loop
	}
	
	public static void main (String[] args) {
		Snake_gameboard game = new Snake_gameboard(15, 10);
		game.displayBoard();
	}
}
