package Snake;
import Snake.Direction;
import Snake.BlockType;

class Block {
	//each block type, direction of snake body, x and y position 
	private BlockType type;
	private Direction dir;
	private int xpos, ypos;
	
	//constructor
	public Block (BlockType type, int xpos, int ypos) { this(xpos, ypos); this.type = type; }
	public Block (int xpos, int ypos) { this.type = BlockType.BLANK; this.xpos = xpos; this.ypos=ypos; }
	
	//set and get field
	public void setBlockData (BlockType type) { this.type = type; }
	public void setBlockData (Direction dir) { this.dir = dir; }
	public BlockType getBlockType () { return type; }
	public Direction getDirection () { return dir; }
	public int getxpos () { return xpos; }
	public int getypos () { return ypos; }
}

public class Snake_gameboard {
	private Block[][] gameBoard;
	private int width, height;
	
	//constructor
	public Snake_gameboard(int height, int width) {
		this.width = width += 2; // add 2 considering wall on all four sides
		this.height = height += 2;
		gameBoard = new Block[this.height][this.width];
		initializeGameBoard();
	}
	
	//first game board setting
	private void initializeGameBoard() {
		
		//set blanks
		for(int i=1; i<height; i++) {
			for(int j=1; j<width; j++)
				gameBoard[i][j] = new Block(i, j);
		}
		
		//set walls
		for(int i=0; i<height; i++) {
			gameBoard[i][0] = new Block(BlockType.WALL, i, 0);
			gameBoard[i][width-1] = new Block(BlockType.WALL, i, width-1);
		}
		
		for(int i=1; i<width-1; i++) {
			gameBoard[0][i] = new Block(BlockType.WALL, 0, i);
			gameBoard[height-1][i] = new Block(BlockType.WALL, height-1, i);
		}
		
		//put head on center - goes right by default
		gameBoard[(height-1)/2][(width-1)/2].setBlockData(BlockType.HEAD);
		gameBoard[(height-1)/2][(width-1)/2].setBlockData(Direction.RIGHT);
		//put tail(body) on the left of head
		gameBoard[(height-1)/2][(width-1)/2 - 1].setBlockData(BlockType.BODY);
		gameBoard[(height-1)/2][(width-1)/2 - 1].setBlockData(Direction.RIGHT);
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	//gives block
	public Block getBlock(int x, int y) { return gameBoard[x][y]; }
	
	//gets and changes block data
	public void setBlock(int x, int y, BlockType type) { gameBoard[x][y].setBlockData(type); }
	public void setBlock(int x, int y, Direction dir) { gameBoard[x][y].setBlockData(dir); }

	//whether snake can exist or not
	public boolean isValidHead(int x, int y) {
		if(0 < x && x < height-1 && 0 < y && y < width-1 && gameBoard[x][y].getBlockType() != BlockType.BODY)
			return true;
		else
			return false;
	}
	
	public boolean isValidBlank(int x, int y) {
		return 0 < x && x < height-1 && 0 < y && y < width-1 && gameBoard[x][y].getBlockType() == BlockType.BLANK;
	}
	
	public Direction getPastDirection (Block b) {
		int x = b.getxpos();
		int y = b.getypos();
		
		if(gameBoard[x-1][y].getDirection() == Direction.DOWN)
			return Direction.DOWN;
		else if(gameBoard[x+1][y].getDirection() == Direction.UP)
			return Direction.UP;
		else if(gameBoard[x][y-1].getDirection() == Direction.RIGHT)
			return Direction.RIGHT;
		else if(gameBoard[x][y+1].getDirection() == Direction.LEFT)
			return Direction.LEFT;
		else
			return Direction.NULL;
	}
	
	//for debugging
	public void displayBoard() {
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++) {
				System.out.print(convertBlock(gameBoard[i][j]));
			}
			System.out.println();
		}
	}
	
	//for debugging
	public char convertBlock(Block n) {
		switch(n.getBlockType()) {
		case BLANK: return '□'; //blank
		case WALL: return '▨'; //wall
		case BODY: return '■'; //body
		case HEAD: return '●'; //head
		case APPLE: return '▲'; //apple
		default: return 'X';
		}
	}

	
}
