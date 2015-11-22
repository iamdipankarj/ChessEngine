package chessengine;
import java.util.*;

import javax.swing.JFrame;

public class Launcher {
    
    public static final boolean DEBUG = false;  
    public static final String title = "ChessMania";
    public static final int width = 512;
    public static final int height = 512;
    
    public static char[] players = {'R','K','B','Q','A','B','K','R'};
    
    public static char[][] board = {
            {'r','k','b','q','a','b','k','r'},
            {'p','p','p','p','p','p','p','p'},
            {' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' '},
            {'P','P','P','P','P','P','P','P'},
            {'R','K','B','Q','A','B','K','R'}};
    
    public static Position kingPositionC, kingPositionL;
    public static int kingPositionCC, kingPositionLL;
    public static int globalDepth = 3;
    public static int willPlayAgain = -1;
    
    public static String[] option = {"Yes", "No"};
    
    public static void resetBoard() {
    	
    	makeEmpty();
    	
    	for (int i = 0; i < 8; i++) {
			board[0][i] = Character.toLowerCase(players[i]);
			board[1][i] = 'p';
		}
    	
    	for (int i = 7; i >= 0; i--) {
    		board[7][i] = Character.toUpperCase(players[i]);
    		board[6][i] = 'P';
		}
    }
    
    public static void makeEmpty() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = ' ';
            }
        }
    }
    
    public static void main(String[] args) {
    	
    	while ( 'A' != board[kingPositionCC/8][kingPositionCC%8]) {kingPositionCC++;}
        while ( 'a' != board[kingPositionLL/8][kingPositionLL%8]) {kingPositionLL++;}
        
        Asset.init("/images/ChessPieces.png");
        
        updateKingPositions();
        //print();  
        JFrame frame = new JFrame(title);
        GUI gui = new GUI();
        
        frame.setSize(width, height);
        frame.add(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);        
        frame.pack();
        
        /*Launcher.willPlayAgain = JOptionPane.showOptionDialog(null, "Checkmate! Wanna play again?", "Computer has won!", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, Launcher.option, Launcher.option[1]);
		
		if (Launcher.willPlayAgain == 0) {
			System.out.println("Yes I will");
		}*/
    }
    
    public static void print() {
        
        for (int i = 0; i < board.length; i++) {
            System.out.println(i + " " + Arrays.toString(board[i]));
        }
        System.out.print("   ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + "  ");
        }
        System.out.println("\n");
    }

    public static void updateKingPositions() {
        
        boolean cDone = false, hDone = false;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'A') {
                    kingPositionC = new Position(i, j);
                    cDone = true;
                    break;
                }
            }
            if (cDone) break;
        }
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'a') {
                    kingPositionL = new Position(i, j);
                    hDone = true;
                    break;
                }
            }
            if (hDone) break;
        }
    }
    
    public static List<Move> getPossibleMoves() {

        List<Move> result = new LinkedList<>();
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                switch(board[i][j]) {
                    case 'R' : {
                        for(Move m : getRookMoves(i, j)) {
                            result.add(m);
                        }
                        break;
                    }
                    case 'B' : {
                        for(Move m : getBishopMoves(i, j)) {
                            result.add(m);
                        }
                        break;
                    }
                    case 'A' : {
                        for(Move m : getKingMoves(i, j)) {
                            result.add(m);
                        }
                        break;
                    }
                    case 'K' : {
                        for(Move m : getKnightMoves(i, j)) {
                            result.add(m);
                        }
                        break;
                    }
                    case 'Q' : {
                        for(Move m : getQueenMoves(i, j)) {
                            result.add(m);
                        }
                        break;
                    }
                    case 'P' : {
                        for(Move m : getPawnMoves(i, j)) {
                            result.add(m);
                        }
                        break;
                    }
                }
            }
        }
        
        return result;
    }
        
    public static List<Move> getRookMoves(int r, int c) {
        if (r < 0 || c < 0 || r > 7 || c > 7) return null;      
        List<Move> result = new LinkedList<>();
        
        int row = r, col = c;
        
        // Go left
        while(col > 0) {

            if (board[row][col-1] == ' ') {
                if (isKingSafe(new Move(r, c, row, col-1, ' ')))
                    result.add(new Move(r, c, row, col-1, ' '));
            } else if (Character.isLowerCase(board[row][col-1]) ) {
                if (isKingSafe(new Move(r, c, row, col-1, board[row][col-1])))
                    result.add(new Move(r, c, row, col-1, board[row][col-1]));
                break;
            } else if (board[row][col-1] != ' ' && Character.isUpperCase(board[row][col-1]) ) {
                break;
            }

            col--;
        }

        row = r; col = c;
        
        // Go right
        while(col < 7) {

            if (board[row][col+1] == ' ') {
                if (isKingSafe(new Move(r, c, row, col+1, ' ')))
                    result.add(new Move(r, c, row, col+1, ' '));
                
            } else if (Character.isLowerCase(board[row][col+1]) ) {
                if (isKingSafe(new Move(r, c, row, col+1, board[row][col+1])))
                    result.add(new Move(r, c, row, col+1, board[row][col+1]));
                break;
            } else if (board[row][col+1] != ' ' && Character.isUpperCase(board[row][col+1]) ) {
                break;
            }

            col++;
        }
        
        row = r; col = c;
        
        // Go up
        while(row > 0) {

            if (board[row-1][col] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col, ' ')))
                    result.add(new Move(r, c, row-1, col, ' '));
            } else if (Character.isLowerCase(board[row-1][col]) ) {
                if (isKingSafe(new Move(r, c, row-1, col, board[row-1][col])))
                    result.add(new Move(r, c, row-1, col, board[row-1][col]));
                break;
            } else if (board[row-1][col] != ' ' && Character.isUpperCase(board[row-1][col]) ) {
                break;
            }

            row--;
        }
        
        row = r; col = c;
        
        // Go down
        while(row < 7) {

            if (board[row+1][col] == ' ') {
                if (isKingSafe(new Move(r, c, row+1, col, ' ')))
                    result.add(new Move(r, c, row+1, col, ' '));
            } else if (Character.isLowerCase(board[row+1][col]) ) {
                if (isKingSafe(new Move(r, c, row+1, col, board[row+1][col])))
                    result.add(new Move(r, c, row+1, col, board[row+1][col]));
                break;
            } else if (board[row+1][col] != ' ' && Character.isUpperCase(board[row+1][col]) ) {
                break;
            }

            row++;
        }
        
        return result;
    }

    public static List<Move> getBishopMoves(int r, int c) {
        if (r < 0 || c < 0 || r > 7 || c > 7) return null;
        List<Move> result = new LinkedList<>();
        
        int row = r, col = c;
        
        
        // Top left
        while(row > 0 && col > 0) {
            
            if (board[row-1][col-1] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col-1, ' ')))
                    result.add(new Move(r, c, row-1, col-1, ' '));
            } else if (Character.isLowerCase(board[row-1][col-1]) ) {
                if (isKingSafe(new Move(r, c, row-1, col-1, board[row-1][col-1])))
                    result.add(new Move(r, c, row-1, col-1, board[row-1][col-1]));
                break;
            } else if (board[row-1][col-1] != ' ' && Character.isUpperCase(board[row-1][col-1]) ) {
                break;
            }
            
            row--; col--;           
        }
        
        row = r; col = c;
        
        // Top right
        while(row > 0 && col < 7) {
            
            if (board[row-1][col+1] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col+1, ' ')))
                    result.add(new Move(r, c, row-1, col+1, ' '));
            } else if (Character.isLowerCase(board[row-1][col+1]) ) {
                if (isKingSafe(new Move(r, c, row-1, col+1, board[row-1][col+1])))
                    result.add(new Move(r, c, row-1, col+1, board[row-1][col+1]));
                break;
            } else if (board[row-1][col+1] != ' ' && Character.isUpperCase(board[row-1][col+1]) ) {
                break;
            }
            
            row--; col++;
        }
        
        row = r; col = c;

        // Bottom right
        while(row < 7 && col < 7) {
            
            if (board[row+1][col+1] == ' ') {
                if (isKingSafe(new Move(r, c, row+1, col+1, ' ')))
                    result.add(new Move(r, c, row+1, col+1, ' '));
            } else if (Character.isLowerCase(board[row+1][col+1]) ) {
                if (isKingSafe(new Move(r, c, row+1, col+1, board[row+1][col+1])))
                    result.add(new Move(r, c, row+1, col+1, board[row+1][col+1]));
                break;
            } else if (board[row+1][col+1] != ' ' && Character.isUpperCase(board[row+1][col+1]) ) {
                break;
            }
            
            row++; col++;
        }

        row = r; col = c;

        // Bottom left
        while(row < 7 && col > 0) {
            
            if (board[row+1][col-1] == ' ') {
                if (isKingSafe(new Move(r, c, row+1, col-1, ' ')))
                    result.add(new Move(r, c, row+1, col-1, ' '));
            } else if (Character.isLowerCase(board[row+1][col-1]) ) {
                if (isKingSafe(new Move(r, c, row+1, col-1, board[row+1][col-1])))
                    result.add(new Move(r, c, row+1, col-1, board[row+1][col-1]));
                break;
            } else if (board[row+1][col-1] != ' ' && Character.isUpperCase(board[row+1][col-1]) ) {
                break;
            }
            
            row++; col--;
        }
        
        return result;
    }
    
    public static List<Move> getQueenMoves(int r, int c) {
        if (r < 0 || c < 0 || r > 7 || c > 7) return null;
        
        
        List<Move> result = getBishopMoves(r, c);
        result.addAll(getRookMoves(r, c));
        
        return result;
    }
    
    public static List<Move> getKnightMoves(int r, int c) {
        if (r < 0 || c < 0 || r > 7 || c > 7) return null;
        List<Move> result = new LinkedList<>();
        
        
        int row = r, col = c;
        // Top left-1
        if (row - 2 >= 0 && col - 1 >= 0) {
            if (board[row-2][col-1] == ' ') {
                if (isKingSafe(new Move(r, c, row-2, col-1, ' ')))
                    result.add(new Move(r, c, row-2, col-1, ' '));
            } else if ( Character.isLowerCase(board[row-2][col-1]) ) {
                if (isKingSafe(new Move(r, c, row-2, col-1, board[row-2][col-1])))
                    result.add(new Move(r, c, row-2, col-1, board[row-2][col-1]));
            }
        }

        // Top-left-2
        if (row - 1 >= 0 && col - 2 >= 0) {
            if (board[row-1][col-2] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col-2, ' ')))
                    result.add(new Move(r, c, row-1, col-2, ' '));
            } else if ( Character.isLowerCase(board[row-1][col-2]) ) {
                if (isKingSafe(new Move(r, c, row-1, col-2, board[row-1][col-2])))
                    result.add(new Move(r, c, row-1, col-2, board[row-1][col-2]));
            }
        }
        
        // Top-right 1
        if (row - 2 >= 0 && col + 1 <= 7) {
            if (board[row-2][col+1] == ' ') {
                if (isKingSafe(new Move(r, c, row-2, col+1, ' ')))
                    result.add(new Move(r, c, row-2, col+1, ' '));
            } else if ( Character.isLowerCase(board[row-2][col+1]) ) {
                if (isKingSafe(new Move(r, c, row-2, col+1, board[row-2][col+1])))
                    result.add(new Move(r, c, row-2, col+1, board[row-2][col+1]));
            }
        }
        
        // Top right 2
        if (row - 1 >= 0 && col + 2 <= 7) {
            if (board[row-1][col+2] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col+2, ' ')))
                    result.add(new Move(r, c, row-1, col+2, ' '));
            } else if ( Character.isLowerCase(board[row-1][col+2]) ) {
                if (isKingSafe(new Move(r, c, row-1, col+2, board[row-1][col+2])))
                    result.add(new Move(r, c, row-1, col+2, board[row-1][col+2]));
            }
        }
        
        // Bottom right 1
        if (row + 1 <= 7 && col + 2 <= 7) {
            if (board[row+1][col+2] == ' ') {
                if (isKingSafe(new Move(r, c, row+1, col+2, ' ')))
                    result.add(new Move(r, c, row+1, col+2, ' '));
            } else if ( Character.isLowerCase(board[row+1][col+2]) ) {
                if (isKingSafe(new Move(r, c, row+1, col+2, board[row+1][col+2])))
                    result.add(new Move(r, c, row+1, col+2, board[row+1][col+2]));
            }
        }
        
        // Bottom right 2
        if (row + 2 <= 7 && col + 1 <= 7) {
            if (board[row+2][col+1] == ' ') {
                if (isKingSafe(new Move(r, c, row+2, col+1, ' ')))
                    result.add(new Move(r, c, row+2, col+1, ' '));
            } else if ( Character.isLowerCase(board[row+2][col+1]) ) {
                if (isKingSafe(new Move(r, c, row+2, col+1, board[row+2][col+1])))
                    result.add(new Move(r, c, row+2, col+1, board[row+2][col+1]));
            }
        }
        
        // Bottom left 1
        if (row + 2 <= 7 && col - 1 >= 0) {
            if (board[row+2][col-1] == ' ') {
                if (isKingSafe(new Move(r, c, row+2, col-1, ' ')))
                    result.add(new Move(r, c, row+2, col-1, ' '));
            } else if ( Character.isLowerCase(board[row+2][col-1]) ) {
                if (isKingSafe(new Move(r, c, row+2, col-1, board[row+2][col-1])))
                    result.add(new Move(r, c, row+2, col-1, board[row+2][col-1]));
            }
        }
        
        // Bottom left 2
        if (row + 1 <= 7 && col - 2 >= 0) {
            if (board[row+1][col-2] == ' ') {
                if (isKingSafe(new Move(r, c, row+1, col-2, ' ')))
                    result.add(new Move(r, c, row+1, col-2, ' '));
            } else if ( Character.isLowerCase(board[row+1][col-2]) ) {
                if (isKingSafe(new Move(r, c, row+1, col-2, board[row+1][col-2])))
                    result.add(new Move(r, c, row+1, col-2, board[row+1][col-2]));
            }
        }
        
        return result;
    }
    
    public static List<Move> getPawnMoves(int r, int c) {
        if (r < 0 || c < 0 || r > 7 || c > 7) return null;
        List<Move> result = new LinkedList<>();
        
        
        int row = r, col = c;
        
        // Capture top left
        if (row > 0 && col > 0) {
            if (board[row-1][col-1] != ' ' && Character.isLowerCase(board[row-1][col-1])) {
                if (isKingSafe(new Move(r, c, row-1, col-1, board[row-1][col-1])))
                    result.add(new Move(r, c, row-1, col-1, board[row-1][col-1]));
            }
        }
        
        // Capture top right
        if (row > 0 && col < 7) {
            if (board[row-1][col+1] != ' ' && Character.isLowerCase(board[row-1][col+1])) {
                if (isKingSafe(new Move(r, c, row-1, col+1, board[row-1][col+1])))
                    result.add(new Move(r, c, row-1, col+1, board[row-1][col+1]));
            }
        }
        
        // Move one up
        if (row > 0) {
            if (board[row-1][col] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col, board[row-1][col])))
                    result.add(new Move(r, c, row-1, col, board[row-1][col]));
            }
        }
        
        // Move two up
        if (row == 6) {
            if (board[row-1][col] == ' ' && board[row-2][col] == ' ') {
                if (isKingSafe(new Move(r, c, row-2, col, ' ')))
                    result.add(new Move(r, c, row-2, col, ' '));
            }
        }
        
        return result;
    }
    
    public static List<Move> getKingMoves(int r, int c) {
        if (r < 0 || c < 0 || r > 7 || c > 7) return null;
        List<Move> result = new LinkedList<>();
        
        
        int row = r, col = c;
        
        // Go left
        if (col > 0) {
            if (board[row][col-1] == ' ') {
                if (isKingSafe(new Move(r, c, row, col-1, ' ')))
                    result.add(new Move(r, c, row, col-1, ' '));
            } else if (board[row][col-1] != ' ' && Character.isLowerCase(board[row][col-1])) {
                if (isKingSafe(new Move(r, c, row, col-1, board[row][col-1])))
                    result.add(new Move(r, c, row, col-1, board[row][col-1]));
            }
        }
        
        // Go right
        if (col < 7) {
            if (board[row][col+1] == ' ') {
                if (isKingSafe(new Move(r, c, row, col+1, ' ')))
                    result.add(new Move(r, c, row, col+1, ' '));
            } else if (board[row][col+1] != ' ' && Character.isLowerCase(board[row][col+1])) {
                if (isKingSafe(new Move(r, c, row, col+1, board[row][col+1])))
                    result.add(new Move(r, c, row, col+1, board[row][col+1]));
            }
        }
        
        // Go up
        if (row > 0) {
            if (board[row-1][col] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col, ' ')))
                    result.add(new Move(r, c, row-1, col, ' '));
            } else if (board[row-1][col] != ' ' && Character.isLowerCase(board[row-1][col])) {
                if (isKingSafe(new Move(r, c, row-1, col, board[row-1][col])))
                    result.add(new Move(r, c, row-1, col, board[row-1][col]));
            }
        }

        // Go down
        if (row < 7) {
            if (board[row+1][col] == ' ') {
                if (isKingSafe(new Move(r, c, row+1, col, ' ')))
                    result.add(new Move(r, c, row+1, col, ' '));
            } else if (board[row+1][col] != ' ' && Character.isLowerCase(board[row+1][col])) {
                if (isKingSafe(new Move(r, c, row+1, col, board[row+1][col])))
                    result.add(new Move(r, c, row+1, col, board[row+1][col]));
            }
        }
        
        // Go top-left
        if (row > 0 && col > 0) {
            if (board[row-1][col-1] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col-1, ' ')))
                    result.add(new Move(r, c, row-1, col-1, ' '));
            } else if (board[row-1][col-1] != ' ' && Character.isLowerCase(board[row-1][col-1])) {
                if (isKingSafe(new Move(r, c, row-1, col-1, board[row-1][col-1])))
                    result.add(new Move(r, c, row-1, col-1, board[row-1][col-1]));
            }
        }
        
        // Go top-right
        if (row > 0 && col < 7) {
            if (board[row-1][col+1] == ' ') {
                if (isKingSafe(new Move(r, c, row-1, col+1, ' ')))
                    result.add(new Move(r, c, row-1, col+1, ' '));
            } else if (board[row-1][col+1] != ' ' && Character.isLowerCase(board[row-1][col+1])) {
                if (isKingSafe(new Move(r, c, row-1, col+1, board[row-1][col+1])))
                    result.add(new Move(r, c, row-1, col+1, board[row-1][col+1]));
            }
        }
        
        // Go bottom-right
        if (row < 7 && col < 7) {
            if (board[row+1][col+1] == ' ') {
                if (isKingSafe(new Move(r, c, row+1, col+1, ' ')))
                    result.add(new Move(r, c, row+1, col+1, ' '));
            } else if (board[row+1][col+1] != ' ' && Character.isLowerCase(board[row+1][col+1])) {
                if (isKingSafe(new Move(r, c, row+1, col+1, board[row+1][col+1])))
                    result.add(new Move(r, c, row+1, col+1, board[row+1][col+1]));
            }
        }
        
        // Go bottom-left
        if (row < 7 && col > 0) {
            if (board[row+1][col-1] == ' ') {
                if (isKingSafe(new Move(r, c, row+1, col-1, ' ')))
                    result.add(new Move(r, c, row+1, col-1, ' '));
            } else if (board[row+1][col-1] != ' ' && Character.isLowerCase(board[row+1][col-1])) {
                if (isKingSafe(new Move(r, c, row+1, col-1, board[row+1][col-1])))
                    result.add(new Move(r, c, row+1, col-1, board[row+1][col-1]));  
            }
        }
        
        return result;
    }
    
    public static void flipBoard() {
    	char temp;
        for (int i=0;i<32;i++) {
            int r=i/8, c=i%8;
            if (Character.isUpperCase(board[r][c])) {
                temp=Character.toLowerCase(board[r][c]);
            } else {
                temp=Character.toUpperCase(board[r][c]);
            }
            if (Character.isUpperCase(board[7-r][7-c])) {
            	board[r][c]=Character.toLowerCase(board[7-r][7-c]);
            } else {
            	board[r][c]=Character.toUpperCase(board[7-r][7-c]);
            }
            board[7-r][7-c]=temp;
        }
        int kingTemp=kingPositionCC;
        kingPositionCC=63-kingPositionLL;
        kingPositionLL=63-kingTemp;
        updateKingPositions();
    }
    
    public static void makeMove(Move move) {
        if (move == null) return;
        board[move.r2][move.c2] = board[move.r1][move.c1];
        board[move.r1][move.c1] = ' ';
    }
    
    public static void undoMove(Move move) {
        if (move == null) return;
        char temp = board[move.r2][move.c2];
        board[move.r2][move.c2] = move.capture;
        board[move.r1][move.c1] = temp;
    }
    
    public static boolean kingSafe() {
        return isKingSafe(null);
    }
    
    public static boolean kingSafe2() {
        //bishop/queen
        int temp=1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(' ' == (board[kingPositionCC/8+temp*i][kingPositionCC%8+temp*j])) {temp++;}
                    if ('b' == (board[kingPositionCC/8+temp*i][kingPositionCC%8+temp*j]) ||
                            'q' == (board[kingPositionCC/8+temp*i][kingPositionCC%8+temp*j])) {
                        return false;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        //rook/queen
        for (int i=-1; i<=1; i+=2) {
            try {
                while(' ' == (board[kingPositionCC/8][kingPositionCC%8+temp*i])) {temp++;}
                if ('r' == (board[kingPositionCC/8][kingPositionCC%8+temp*i]) ||
                        'q' == (board[kingPositionCC/8][kingPositionCC%8+temp*i])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(' ' == (board[kingPositionCC/8+temp*i][kingPositionCC%8])) {temp++;}
                if ('r' == (board[kingPositionCC/8+temp*i][kingPositionCC%8]) ||
                        'q' == (board[kingPositionCC/8+temp*i][kingPositionCC%8])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
        }
        //knight
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ('k' == (board[kingPositionCC/8+i][kingPositionCC%8+j*2])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ('k' == (board[kingPositionCC/8+i*2][kingPositionCC%8+j])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
        //pawn
        if (kingPositionCC>=16) {
            try {
                if ('p' == (board[kingPositionCC/8-1][kingPositionCC%8-1])) {
                    return false;
                }
            } catch (Exception e) {}
            try {
                if ('p' == (board[kingPositionCC/8-1][kingPositionCC%8+1])) {
                    return false;
                }
            } catch (Exception e) {}
            //king
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ('a' == (board[kingPositionCC/8+i][kingPositionCC%8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        }
        return true;
    }
    
    public static boolean isKingSafe(Move move) {
        
        makeMove(move);

        // From queen/rook
        int row = kingPositionC.r, col = kingPositionC.c;
        
        //top
        while(row > 0) {
            if (board[row-1][col] != ' ') break;
            row--;
        }
        if (row > 0) {
            if(board[row-1][col] == 'q' || board[row-1][col] == 'r') {
                undoMove(move);
                return false;
            }
        }
        
        row = kingPositionC.r; col = kingPositionC.c;

        // bottom
        while(row < 7) {
            if (board[row+1][col] != ' ') break;
            row++;
        }
        if (row < 7) {
            if(board[row+1][col] == 'q' || board[row+1][col] == 'r') {
                undoMove(move);
                return false;
            }
        }
        
        row = kingPositionC.r; col = kingPositionC.c;
        
        
        // left
        while(col > 0) {
            if (board[row][col-1] != ' ') break;
            col--;
        }
        if (col > 0) {
            if(board[row][col-1] == 'q' || board[row][col-1] == 'r') {
                undoMove(move);
                return false;
            }
        }
        
        row = kingPositionC.r; col = kingPositionC.c;
        
        // right
        while(col < 7) {
            if (board[row][col+1] != ' ') break;
            col++;
        }
        if (col < 7) {
            if(board[row][col+1] == 'q' || board[row][col+1] == 'r') {
                undoMove(move);
                return false;
            }
        }
        
        // From queen/bishop
        row = kingPositionC.r; col = kingPositionC.c;

        // top left
        while(row > 0 && col > 0) {
            if (board[row-1][col-1] != ' ') break;
            row--;
            col--;
        }
        if (row > 0 && col > 0) {
            if(board[row-1][col-1] == 'q' || board[row-1][col-1] == 'b') {
                undoMove(move);
                return false;
            }
        }
        
        row = kingPositionC.r; col = kingPositionC.c;

        // top right
        while(row > 0 && col < 7) {
            if (board[row-1][col+1] != ' ') break;
            row--;
            col++;
        }
        if (row > 0 && col < 7) {
            if(board[row-1][col+1] == 'q' || board[row-1][col+1] == 'b') {
                undoMove(move);
                return false;
            }
        }
        
        row = kingPositionC.r; col = kingPositionC.c;

        // bottom right
        while(row < 7 && col < 7) {
            if (board[row+1][col+1] != ' ') break;
            row++;
            col++;
        }
        if (row < 7 && col < 7) {
            if(board[row+1][col+1] == 'q' || board[row+1][col+1] == 'b') {
                undoMove(move);
                return false;
            }
        }
        
        row = kingPositionC.r; col = kingPositionC.c;

        // bottom left
        while(row < 7 && col > 0) {
            if (board[row+1][col-1] != ' ') break;
            row++;
            col--;
        }
        if (row < 7 && col > 0) {
            if(board[row+1][col-1] == 'q' || board[row+1][col-1] == 'b') {
                undoMove(move);
                return false;
            }
        }
        
        // From knight
        row = kingPositionC.r; col = kingPositionC.c;
        // Top left-1
        if (row - 2 >= 0 && col - 1 >= 0) {
            if(board[row - 2][col - 1] == 'k') {
                undoMove(move);
                return false;
            }
        }

        // Top-left-2
        if (row - 1 >= 0 && col - 2 >= 0) {
            if(board[row - 1][col - 2] == 'k') {
                undoMove(move);
                return false;
            }
        }
        
        // Top-right 1
        if (row - 2 >= 0 && col + 1 <= 7) {
            if(board[row - 2][col + 1] == 'k') {
                undoMove(move);
                return false;
            }
        }
        
        // Top right 2
        if (row - 1 >= 0 && col + 2 <= 7) {
            if(board[row - 1][col + 2] == 'k') {
                undoMove(move);
                return false;
            }
        }
        
        // Bottom right 1
        if (row + 1 <= 7 && col + 2 <= 7) {
            if(board[row + 1][col + 2] == 'k') {
                undoMove(move);
                return false;
            }
        }
        
        // Bottom right 2
        if (row + 2 <= 7 && col + 1 <= 7) {
            if(board[row + 2][col + 1] == 'k') {
                undoMove(move);
                return false;
            }
        }
        
        // Bottom left 1
        if (row + 2 <= 7 && col - 1 >= 0) {
            if(board[row + 2][col - 1] == 'k') {
                undoMove(move);
                return false;
            }
        }
        
        // Bottom left 2
        if (row + 1 <= 7 && col - 2 >= 0) {
            if(board[row + 1][col - 2] == 'k') {
                undoMove(move);
                return false;
            }
        }
        
        row = kingPositionC.r; col = kingPositionC.c;
        // pawn/king
        // top left
        if (row - 1 >= 0 && col - 1 >= 0) {
            if (board[row-1][col-1] == 'p' || board[row-1][col-1] == 'a') {
                undoMove(move);
                return false;
            }
        }
        
        // top right
        if (row - 1 >= 0 && col + 1 <= 7) {
            if (board[row-1][col+1] == 'p' || board[row-1][col+1] == 'a') {
                undoMove(move);
                return false;
            }
        }
        
        // bottom right
        if (row + 1 <= 7 && col + 1 <= 7) {
            if (board[row+1][col+1] == 'a') {
                undoMove(move);
                return false;
            }
        }
        
        // bottom left
        if (row + 1 <= 7 && col - 1 >= 0) {
            if (board[row+1][col-1] == 'a') {
                undoMove(move);
                return false;
            }
        }
        
        // top
        if (row-1 >= 0) {
            if (board[row-1][col] == 'a') {
                undoMove(move);
                return false;
            }
        }
        
        // left
        if (col-1 >= 0) {
            if (board[row][col-1] == 'a') {
                undoMove(move);
                return false;
            }
        }
        
        // right
        if (col+1 <= 7) {
            if (board[row][col+1] == 'a') {
                undoMove(move);
                return false;
            }
        }
        
        // bottom
        if (row+1 <= 7) {
            if (board[row+1][col] == 'a') {
                undoMove(move);
                return false;
            }
        }
        
        undoMove(move);
        return true;
    }
    
    public static Move alphaBeta(int depth, int beta, int alpha, Move move, int player) {
        List<Move> moves = getPossibleMoves();
        if (depth == 0 || moves.size() == 0) {
            return new Move(move, Rate.rating(moves.size() * 5, depth)*(player*2-1));
        }
        player = 1 - player;//either 1 or 0
        for(Move m : moves) {
            makeMove(m);
            flipBoard();
            Move returnMove = alphaBeta(depth-1, beta, alpha, m, player);
            int value = returnMove.value;
            flipBoard();
            undoMove(m);
            if (player == 0) {
                if (value <= beta) {
                    beta = value;
                    if (depth == globalDepth) {
                        move = new Move(returnMove);
                    }
                }
            } else {
                if (value > alpha) {
                    alpha = value;
                    if (depth == globalDepth) {
                        move = new Move(returnMove);
                    }
                }
            }
            if (alpha >= beta) {
                if (player == 0) {
                    return new Move(move, beta);
                } else {
                    return new Move(move, alpha);
                }
            }
        }
        
        if (player == 0) {
            return new Move(move, beta);
        } else {
            return new Move(move, alpha);
        }
    }
    
    public static boolean isCheckmate() {
        return isKingSafe(null);
    }

}





