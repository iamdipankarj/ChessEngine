package chessengine;

public class Animation {

	public int x1, y1;
	public int x2, y2;
	private boolean running = false;
	
	public void prepare(Move move) {
		x1 = move.r1 * 64;
		y1 = move.c1 * 64;
		x2 = move.r2;
		y2 = move.c2;
		running = true;
		//System.out.println("From anim, automove is: " + move);
	}
	
	private void tick() {
		
	}
	
	public void render(GUI panel) {

		panel.getGraphics().drawRect(y1++, x1++, 50, 50);
		panel.repaint();
		
	}

	public void run(GUI panel) {
		
		int fps = 120;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		//int ticks = 0;
		
		while(running) {
			
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if (delta >= 1) {			
				tick();
				render(panel);
				//ticks++;
				delta--;
			}
			
			if (timer >= 1000000000) {
				//ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	public void stop() {
		if ( ! running ) return;		
		running = false;
	}

}
