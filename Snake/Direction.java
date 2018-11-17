package Snake;

public enum Direction {
	LEFT(0,-1), RIGHT(0, 1), UP(-1, 0), DOWN(1, 0), NULL(1000, 1000);

	private int xmove, ymove;
	private Direction(int xmove, int ymove) { this.xmove = xmove; this.ymove = ymove; }
	public int getXdir () { return xmove; } 
	public int getYdir () { return ymove; } 
}