package chessengine;

public class Position {
	public int r;
	public int c;
	public Position(int x, int y) {
		r = x;
		c = y;
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(r);
		buffer.append(c);
		return buffer.toString();
	}
}
