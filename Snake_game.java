package snake_gameboard;
import java.util.Scanner;

enum Direction {
	LEFT(0,-1), RIGHT(0, 1), UP(-1, 0), DOWN(1, 0), NULL(1000, 1000);

	private int xmove, ymove;
	private Direction(int xmove, int ymove) { this.xmove = xmove; this.ymove = ymove; }
	public int getXdir () { return xmove; } 
	public int getYdir () { return ymove; } 
}

public class Snake_game {
	final private double defaultInterval = 1000;
	private double speed;
	private long inGameInterval; //interval between snake moves
	private int gameWidth, gameHeight; // where snake can move (excludes walls)
	private int growthLen, snakeLen, goalLen; // turn left for snake to grow (3 each apple, i guess)
	private Snake_gameboard gameBoard;
	private Block head, tail;
	private boolean gameResult; //true = win , false = lose 

	//settings method - board size, speed, etc

	//getting input method - return direction or NULL
	private Direction getDirInput () {
		Scanner scanner = new Scanner(System.in);
		int choice;
		System.out.print("Where from now? (l;1 r;2 u;3 d;4) >> ");
		//interval();
		if (scanner.hasNextInt())
			choice = scanner.nextInt();
		else 
			return Direction.NULL;

		switch (choice) {
		case 1: return Direction.LEFT;
		case 2: return Direction.RIGHT;
		case 3: return Direction.UP;
		case 4: return Direction.DOWN;
		}

		return Direction.NULL;
	}

	//true:game not ended , false:game ended
	private boolean moveSnakeAndCheck () {
		Block temp;
		Direction newdir;

		if(growthLen > 0) {
			snakeLen++;
			growthLen--;
		} else {
			temp = getNextBlock(tail);
			gameBoard.setBlock(tail.getxpos(), tail.getypos(), BlockType.BLANK);
			gameBoard.setBlock(tail.getxpos(), tail.getypos(), Direction.NULL);
			tail = temp;
		}

		if ((newdir = getDirInput()) != Direction.NULL) //new direction for current head
			gameBoard.setBlock(head.getxpos(), head.getypos(), newdir);
		else //no direction input = keep direction
			newdir = head.getDirection();

		gameBoard.setBlock(head.getxpos(), head.getypos(), BlockType.BODY);
		head = getNextBlock(head);

		if(!gameBoard.isValidHead(head.getxpos(), head.getypos())) { //gameover - lose
			gameResult = false;
			return false;
		} else if (snakeLen == goalLen) { //gameover - win
			gameResult = true;
			return false;
		} else if (head.getBlockType() == BlockType.APPLE) {
			growthLen += 3;
			createApple();
		}

		gameBoard.setBlock(head.getxpos(), head.getypos(), BlockType.HEAD);
		gameBoard.setBlock(head.getxpos(), head.getypos(), newdir);

		return true;
	}

	private Block getNextBlock(Block b) {
		int x = b.getxpos() + b.getDirection().getXdir();
		int y = b.getypos() + b.getDirection().getYdir();
		return gameBoard.getBlock(x, y);
	}

	//create new apple on random position
	private void createApple () {
		int x, y;
		do {
			x = (int)(Math.random() * gameHeight + 1);
			y = (int)(Math.random() * gameWidth + 1);
		}while(!gameBoard.isValidBlank(x, y));
		gameBoard.setBlock(x, y, BlockType.APPLE);
	}

	void interval() {
		try {
			Thread.sleep(inGameInterval);
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	//get settings, create board, put head in center, direction right
	public Snake_game(double speed, int gameHeight, int gameWidth) {
		this.speed = speed;
		this.inGameInterval = (long)(defaultInterval / speed);
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.snakeLen = 2; // 1head + 1body
		this.goalLen = gameHeight * gameWidth;

		gameBoard = new Snake_gameboard(gameHeight, gameWidth);
		head = gameBoard.getBlock((gameHeight+1)/2, (gameWidth+1)/2);
		tail = gameBoard.getBlock((gameHeight+1)/2, (gameWidth+1)/2 - 1);
	}

	public void run() {
		growthLen=0;
		gameResult=true;
		createApple();
		while(true) {
			gameBoard.displayBoard();
			if(!moveSnakeAndCheck()) {
				if(gameResult) {
					System.out.println("VIKTOOORY ROOOOYALE!!! *default dances*");
					break;
				} else {
					System.out.println("Gameover");
					break;
				}
			}
		}

		gameBoard.displayBoard();

	}

	public static void main (String[] args) {
		Snake_game game = new Snake_game(1, 3, 3);
		game.run();
	}
}
