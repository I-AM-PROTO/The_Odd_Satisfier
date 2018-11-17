package Snake;

public class Snake_Autoplay extends Snake_game {
	private Block head;
	private int gameWidth, gameHeight;
	private int mode;
	private boolean cntr;
	
	public Snake_Autoplay () { super(4, 4); }

	public void getRandomSize () {
		gameHeight = (int)(Math.random() * 8) + 3;
		if(gameHeight % 2 == 1) do {
			gameWidth = (int)(Math.random() * 8) + 3;
		} while(gameWidth % 2 == 1);
		else gameWidth = (int)(Math.random() * 8) + 3;
	}

	public void getMode () {
		if(gameHeight % 2 == 0 && gameWidth % 2 == 0) {
			mode = 0;
		} else if(gameHeight % 2 == 1 && gameWidth % 2 == 1) {
			mode = 1;
		} else if(gameHeight % 2 == 0 && gameWidth % 2 == 1) {
			mode = 2;
			cntr = true;
		} else {
			int temp = this.gameWidth;
			this.gameWidth = this.gameHeight;
			this.gameHeight = temp;
			mode = 3;
		}
	}

	public void runAutoPlay() {
		while(true) {
			getRandomSize();
			resetGame(gameHeight, gameWidth);
			getMode();
			autoRun();
		}
	}

	
	private Direction EvenEven (int x, int y) {
		if(y == 1) {
			if (x == gameHeight) return Direction.RIGHT;
			else return Direction.DOWN;
		} else if(x == 1) {
			if (y == 1) return Direction.DOWN;
			return Direction.LEFT;
		} else if(x % 2 == 0) {
			if(y == gameWidth) return Direction.UP;
			else return Direction.RIGHT;
		} else if(x % 2 == 1) {
			if(y == 2) return Direction.UP;
			else return Direction.LEFT;
		} else return Direction.NULL;
	}

	private Direction OddOdd (int x, int y) {
		if(cntr) {
			if(x == 1 && y == 1) 
				cntr = false;
			if(y == 1) {
				if (x == gameHeight) return Direction.RIGHT;
				else return Direction.DOWN;
			} else if(x == gameHeight) {
				if(y == gameWidth) return Direction.UP;
				else return Direction.RIGHT;
			} else if(y == gameWidth - 1) {
				if(x == 1) return Direction.LEFT;
				else if (x % 2 == 0) return Direction.UP;
				else return Direction.RIGHT;
			} else if(y == gameWidth) {
				if (x % 2 == 0) return Direction.LEFT;
				else return Direction.UP;
			} else if(y % 2 == 0) {
				if(x == 1) return Direction.LEFT;
				else return Direction.UP;
			} else if(y % 2 == 1) {
				if (x == gameHeight - 1) return Direction.LEFT;
				else return Direction.DOWN;
			}
		} else {
			if(x == 1 && y == 1) 
				cntr = true;
			if(y == 1) {
				if (x == gameHeight) return Direction.RIGHT;
				else return Direction.DOWN;
			} else if(x == gameHeight) {
				if(y == gameWidth - 1) return Direction.UP;
				else return Direction.RIGHT;
			} else if(y == gameWidth) {
				if (x % 2 == 0) return Direction.UP;
				else return Direction.LEFT;
			} else if(y == gameWidth - 1) {
				if(x == 1) return Direction.LEFT;
				else if (x % 2 == 0) return Direction.RIGHT;
				else return Direction.UP;
			} else if(y % 2 == 0) {
				if(x == 1) return Direction.LEFT;
				else return Direction.UP;
			} else if(y % 2 == 1) {
				if (x == gameHeight - 1) return Direction.LEFT;
				else return Direction.DOWN;
			}
		}
		return Direction.NULL;
	}

	private Direction EvenOdd (int x, int y) {
		if(y == 1) {
			if (x == gameHeight) return Direction.RIGHT;
			else return Direction.DOWN;
		} else if(x == gameHeight) {
			if(y == gameWidth) return Direction.UP;
			else return Direction.RIGHT;
		} else if(y == gameWidth - 1) {
			if(x == 1) return Direction.LEFT;
			else if (x % 2 == 1) return Direction.UP;
			else return Direction.RIGHT;
		} else if(y == gameWidth) {
			if (x % 2 == 1) return Direction.LEFT;
			else return Direction.UP;
		} else if(y % 2 == 0) {
			if(x == 1) return Direction.LEFT;
			else return Direction.UP;
		} else if(y % 2 == 1) {
			if (x == gameHeight - 1) return Direction.LEFT;
			else return Direction.DOWN;
		} else return Direction.NULL;
	}
	
	private Direction OddEven (int x, int y) {
		y = gameHeight - y + 1;
		int temp = x;
		x = y;
		y = temp;
		
		if(y == 1) {
			if (x == gameHeight) return Direction.DOWN;
			else return Direction.LEFT;
		} else if(x == gameHeight) {
			if(y == gameWidth) return Direction.RIGHT;
			else return Direction.DOWN;
		} else if(y == gameWidth - 1) {
			if(x == 1) return Direction.UP;
			else if (x % 2 == 1) return Direction.RIGHT;
			else return Direction.DOWN;
		} else if(y == gameWidth) {
			if (x % 2 == 1) return Direction.UP;
			else return Direction.RIGHT;
		} else if(y % 2 == 0) {
			if(x == 1) return Direction.UP;
			else return Direction.RIGHT;
		} else if(y % 2 == 1) {
			if (x == gameHeight - 1) return Direction.UP;
			else return Direction.LEFT;
		} else return Direction.NULL;
	}
	
	@Override
	protected Direction getDirInput () {
		head = super.getHead();
		int x = head.getxpos();
		int y = head.getypos();

		//interval();
		switch(mode) {
		case 0: return EvenEven(x, y);
		case 1: return OddOdd(x, y);
		case 2: return EvenOdd(x, y);
		case 3: return OddEven(x, y);
		}
		
		return Direction.NULL;
	}

	public static void main (String[] args) {
		Snake_Autoplay game = new Snake_Autoplay();
		game.runAutoPlay();
	}
}
