package snake_gameboard;
import java.util.Arrays;

enum BlockType {
	BLANK, BODY, HEAD, APPLE, WALL;
}

class Block {
	//each block type, direction of snake body, x and y position 
	private BlockType type;
	private Direction dir;
	private int xpos, ypos;
	
	//constructor
	public Block (BlockType type, int xpos, int ypos) { this(xpos, ypos); this.type = type; }
	public Block (int xpos, int ypos) { this.type = BlockType.BLANK; this.xpos = xpos; this.ypos=ypos; }
	
	//set and get field
	public void setBlockType (BlockType type) { this.type = type; }
	public void setDirection (Direction dir) { this.dir = dir; }
	public BlockType getBlockType () { return type; }
	public Direction getDirection () { return dir; }
	public int getxpos () { return xpos; }
	public int getypos () { return ypos; }
}

public class Snake_gameboard { // 0:빈칸  1:몸통  2:머리  3:사과  -1:벽?
	private Block[][] gameBoard;
	private int width, height;
	
	//constructor
	public Snake_gameboard(int width, int height) {
		this.width = width += 2; // added 2 considering wall on all four sides
		this.height = height += 2;
		gameBoard = new Block[this.height][this.width];
		initializeGameBoard();
	}
	
	//first game board setting - blanks, walls and head in the center
	private void initializeGameBoard() {
		for(int i=1; i<height; i++) {
			for(int j=1; j<width; j++)
				gameBoard[i][j] = new Block(i, j);
		}
		
		for(int i=0; i<height; i++) {
			gameBoard[i][0] = new Block(BlockType.WALL, i, 0);
			gameBoard[i][width-1] = new Block(BlockType.WALL, i, width-1);
		}
		
		for(int i=1; i<width-1; i++) {
			gameBoard[0][i] = new Block(BlockType.WALL, 0, i);
			gameBoard[height-1][i] = new Block(BlockType.WALL, height-1, i);
		}
		
		gameBoard[(height-1)/2][(width-1)/2].setBlockType(BlockType.HEAD);
		gameBoard[(height-1)/2][(width-1)/2].setDirection(Direction.RIGHT);
	}
	
	//displays board - have to change to GUI later
	public void displayBoard() {
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++) {
				System.out.print(convertBlock(gameBoard[i][j]));
			}
			System.out.println();
		}
	}

	//gives block type
	public Block getBlock(int x, int y) { return gameBoard[x][y]; }
	
	//gets and changes block type
	public void setBlock(int x, int y, Block type) { gameBoard[x][y] = type; }
	
	//whether snake can exist or not
	public boolean isValid(int x, int y) {
		if(0 < x && x < width-1 && 0 < y && y < height-1 && gameBoard[x][y].getBlockType() != BlockType.BODY)
			return true;
		else
			return false;
	}

	//have to change into GUI later
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
