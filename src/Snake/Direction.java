package Snake;

public enum Direction {
	LEFT(0,-1, 2), RIGHT(0, 1, 3), UP(-1, 0, 0), DOWN(1, 0, 1), NULL(1000, 1000, -1);

	private int xmove, ymove, ID;
	private Direction(int xmove, int ymove, int ID) { this.xmove = xmove; this.ymove = ymove; this.ID = ID; }
	public int getXdir () { return xmove; } 
	public int getYdir () { return ymove; } 
	public int getID () { return ID; }
}