package chessengine;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GUI extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static int mouseX, mouseY, newMouseX, newMouseY;
    public static int squareSize = 64;

    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	this.setBackground(Color.darkGray);
    	this.addMouseListener(this);
        this.addMouseMotionListener(this);
        Color dark = new Color(140,82,66), light = new Color(255,255,206);
        
        for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					g.setColor(light);
				} else {
					g.setColor(dark);
				}
				g.fillRect(i*64, j*64, 64, 64);
			}
		}
        
        for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch(Launcher.board[i][j]) {
					case 'P' : g.drawImage(Asset.WHITE.pawn, j*64, i*64, null);
						break;
					case 'p' : g.drawImage(Asset.BLACK.pawn, j*64, i*64, null);
						break;
					case 'B' : g.drawImage(Asset.WHITE.bishop, j*64, i*64, null);
						break;
					case 'b' : g.drawImage(Asset.BLACK.bishop, j*64, i*64, null);
						break;
					case 'Q' : g.drawImage(Asset.WHITE.queen, j*64, i*64, null);
						break;
					case 'q' : g.drawImage(Asset.BLACK.queen, j*64, i*64, null);
						break;
					case 'A' : g.drawImage(Asset.WHITE.king, j*64, i*64, null);
						break;
					case 'a' : g.drawImage(Asset.BLACK.king, j*64, i*64, null);
						break;
					case 'K' : g.drawImage(Asset.WHITE.knight, j*64, i*64, null);
						break;
					case 'k' : g.drawImage(Asset.BLACK.knight, j*64, i*64, null);
						break;
					case 'R' : g.drawImage(Asset.WHITE.rook, j*64, i*64, null);
						break;
					case 'r' : g.drawImage(Asset.BLACK.rook, j*64, i*64, null);
						break;
				}
			}
		}
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Launcher.width, Launcher.height);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    
    @Override
	public void mousePressed(MouseEvent e) {
		if ( e.getX() < 8*squareSize && e.getY() < 8*squareSize) {
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }
	}
    
    int y = 64;

    @Override
	public void mouseReleased(MouseEvent e) {
		if  (e.getX() < 8*squareSize && e.getY() < 8*squareSize) {
			newMouseX = e.getX();
            newMouseY = e.getY();
            if (e.getButton() == MouseEvent.BUTTON1) {
            	// Regular move
            	Move dragMove = new Move(mouseY/squareSize, mouseX/squareSize, newMouseY/squareSize, newMouseX/squareSize, Launcher.board[newMouseY/squareSize][newMouseX/squareSize]);
            	
            	java.util.List<Move> userPosibilities = Launcher.getPossibleMoves();
            	for(Move m : userPosibilities) {
            		if (m.equals(dragMove)) {
                    	Launcher.makeMove(dragMove);
                    	Launcher.flipBoard();
                    	Move autoMove = Launcher.alphaBeta(Launcher.globalDepth, 1000000, -1000000, new Move(), 0);
                    	Launcher.makeMove(autoMove);
                    	Launcher.flipBoard();
                    	
                        repaint();
                        return;
            		}
            	}
            	
            	userPosibilities = Launcher.getPossibleMoves();
            	
            	if (userPosibilities.size() == 0) {
            		Launcher.willPlayAgain = JOptionPane.showOptionDialog(null, "Checkmate! Wanna play again?", "Computer has won!", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, Launcher.option, Launcher.option[1]);
            		
            		if (Launcher.willPlayAgain == 0) {
            			System.out.println("Yes I will");
            			Launcher.resetBoard();
            			repaint();
            		} else {
            			System.exit(0);
            		}
            	}
            }
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
