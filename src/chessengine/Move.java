package chessengine;

public class Move {
	public int r1, c1;
	public int r2, c2;
	public char capture;
	public int value;
	public Move(int x1, int y1, int x2, int y2, char cap) {
		r1 = x1;
		c1 = y1;
		r2 = x2;
		c2 = y2;
		capture = cap;
		//value = 0;
	}
	
	public Move() {
		//value = 0;
		r1 = 0;
		c1 = 0;
		r2 = 0;
		c2 = 0;
	}
	
	public Move(Move m, int val) {
		r1 = m.r1;
		c1 = m.c1;
		r2 = m.r2;
		c2 = m.c2;
		capture = m.capture;
		value = val;
	}
	
	public Move(Move m) {
		r1 = m.r1;
		c1 = m.c1;
		r2 = m.r2;
		c2 = m.c2;
		capture = m.capture;
	}
	
	public Move(int x1, int y1, int x2, int y2) {
		r1 = x1;
		c1 = y1;
		r2 = x2;
		c2 = y2;
		capture = ' ';
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(r1);
		buffer.append(c1);
		buffer.append(r2);
		buffer.append(c2);
		buffer.append(capture);
		return buffer.toString();
	}
	
	public boolean equals(Move m) {
		return (r1 == m.r1) &&
			   (r2 == m.r2) && (c1 == m.c1) && (c2 == m.c2);
	}
}


