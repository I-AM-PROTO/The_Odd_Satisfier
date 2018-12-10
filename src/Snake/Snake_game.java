package Snake;

import Snake.Direction;
import Snake.BlockType;
import main_hub.MainHubFrame;

public class Snake_game {
	final private double defaultInterval = 1000;
	final private int defaultSpeed = 5;
	private long inGameInterval; //interval between snake moves
	private int gameWidth, gameHeight; // where snake can move (excludes walls)
	private int growthLen, snakeLen, goalLen; // turn left for snake to grow (3 each apple, i guess)
	private Snake_gameboard gameBoard;
	private Block head, tail;
	private boolean gameResult; //true = win , false = lose
	private MainFrame mainFrame;
	private MainHubFrame mhf;

	//get settings, create board, put head in center, direction right
	public Snake_game(int gameHeight, int gameWidth, MainHubFrame mhf) {
		this.mhf = mhf;
		this.inGameInterval = (long)(defaultInterval / defaultSpeed);
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.snakeLen = 2; // 1head + 1body
		this.growthLen = 0;
		this.goalLen = gameHeight * gameWidth;
		this.mainFrame = new MainFrame(this, defaultSpeed, gameHeight+2, gameWidth+2);

		gameBoard = new Snake_gameboard(gameHeight, gameWidth);
		head = gameBoard.getBlock((gameHeight+1)/2, (gameWidth+1)/2);
		tail = gameBoard.getBlock((gameHeight+1)/2, (gameWidth+1)/2 - 1);

		createApple();
	}

	private void setBodyID() {
		Block pastBlock = tail, currBlock = getNextBlock(tail);
		Direction pastDir, currDir;
		int id;
		
		tail.setBodyID(-1);
		for(int i=0; i<snakeLen-2; i++) {
			pastDir = pastBlock.getDirection();
			currDir = currBlock.getDirection();
			
			if(pastDir == currDir)
				if(currDir == Direction.UP || currDir == Direction.DOWN) id = 0; else id = 1;
			else if(pastDir == Direction.UP)
				if(currDir == Direction.LEFT) id = 2; else id = 3;
			else if(pastDir == Direction.DOWN)
				if(currDir == Direction.LEFT) id = 4; else id = 5;
			else if(pastDir == Direction.RIGHT)
				if(currDir == Direction.UP) id = 4; else id = 2;
			else
				if(currDir == Direction.UP) id = 5; else id = 3;
			
			currBlock.setBodyID(id);
			pastBlock = currBlock;
			currBlock = getNextBlock(currBlock);
		}
	}
	
	//getting input method - return direction or NULL
	protected Direction getDirInput () {
		//console input
		/*
		Scanner scanner = new Scanner(System.in);
		int choice;
		System.out.print("Where from now? (l;1 r;2 u;3 d;4) >> ");
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
		*/
		
		//GUI input
		return mainFrame.getInputDir();
	}
	
	protected Block getHead() { return head; }
	
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
			gameBoard.setBlock(head.getxpos(), head.getypos(), BlockType.HEAD);
			gameBoard.setBlock(head.getxpos(), head.getypos(), newdir);
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
	
	void interval(int time) {
		try {
			Thread.sleep(time);
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setSpeed(int speed) { inGameInterval = (long)(defaultInterval / speed); }
	
	public void resetGame() {
		this.snakeLen = 2; // 1head + 1body
		this.growthLen = 0;
		
		gameBoard.resetBoard();
		mainFrame.resetFrame();
		head = gameBoard.getBlock((gameHeight+1)/2, (gameWidth+1)/2);
		tail = gameBoard.getBlock((gameHeight+1)/2, (gameWidth+1)/2 - 1);
		
		createApple();
	}
	
	public void resetGame(int gameHeight, int gameWidth) {
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.snakeLen = 2;
		this.growthLen = 0;
		this.goalLen = gameHeight * gameWidth;
		mainFrame.resetFrame(gameHeight+2, gameWidth+2);

		gameBoard = new Snake_gameboard(gameHeight, gameWidth);
		head = gameBoard.getBlock((gameHeight+1)/2, (gameWidth+1)/2);
		tail = gameBoard.getBlock((gameHeight+1)/2, (gameWidth+1)/2 - 1);

		createApple();
	}
	
	private void infiniteInterval() {
		 while(mainFrame.getPause() && !mainFrame.getReplay())
			 interval(100);
	}
	
	public void run() {
		while(true) {
			setBodyID();
			mainFrame.repaintGameBoard(gameBoard);
			if(mainFrame.getPause())
				infiniteInterval();
			interval();

			if(mainFrame.getReplay()) {
				resetGame();
				continue;
			}
			
			if(mainFrame.getTerminate()) {
				break;
			}

			if(!moveSnakeAndCheck()) {
				if(gameResult) {
					//System.out.println("VIKTOOORY ROOOOYALE!!! *default dances*");
					mainFrame.repaintGameBoard(gameBoard);
				} else {
				    //System.out.println("Gameover");
					mainFrame.repaintGameBoard(gameBoard);
				}
				resetGame();
			}
		}
		mhf.reEnter();
	}
	
	protected void autoRun() {
		while(true) {
			setBodyID();
			mainFrame.repaintGameBoard(gameBoard);
			if(mainFrame.getPause())
				infiniteInterval();
			interval();

			if(mainFrame.getReplay()) {
				resetGame();
				continue;
			}
			
			if(mainFrame.getTerminate()) {
				break;
			}

			if(!moveSnakeAndCheck()) {
				return;
			}
		}
		mhf.reEnter();
	}
}