package KnightMove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.lang.Math;
import java.util.*;


public class Questions {
	// Initialize board, knight, startPint, endPoint
	public static Knight knight = new Knight();
	public static Board board = new Board();
	
	// Define legal moves
	public final static ArrayList<Move> moves = new ArrayList<Move>();

	public static void main(String[] args){				
		moves.add(new Move(-2, -1));
		moves.add(new Move(-2, 1));
		moves.add(new Move(-1, -2));
		moves.add(new Move(-1, 2));
		moves.add(new Move(1, -2));
		moves.add(new Move(1, 2));
		moves.add(new Move(2, -1));
		moves.add(new Move(2, 1));
				

		// Q1		
//		questionOne();
		
		// Q2
		questionTwo();
}
	

	private static void questionOne(){
		// initialize board and knight
		int size = 8;		
		int startX = 3;
		int startY = 3;
		int endX = 5;
		int endY = 6;
		knight = new Knight(startX, startY);
		board = new Board(size, knight);
		board.setStartEnd(startX, startY, endX, endY);
		board.printInitialBoard();
				
		// input in a sequence of steps 
		ArrayList<Move> steps = new ArrayList<Move>();
		steps.clear();			
		steps.add(new Move(-1,2)); 			
		steps.add(new Move(-1,2)); 		
		steps.add(new Move(1,-2)); 	

		for (Move s : steps){
			System.out.print("\n*********** Step **********\n");			
			if (!checkMove(s)){
				System.out.println("Q1 Answer: Invalide sequence.");
				return;
			}
		}
		System.out.println("Q1 Answer: Valid sequence");
	}
	
	private static boolean checkMove(Move move){
			knight.printKnight();
			move.printMove();
		
			if (knight.validMove(move, board)){
				knight.acceptMove(move);
				knight.printKnight();
				board.updateKnightOnBoard(knight);
				board.printBoard();				
			}
			else 
				return false;
			
		return true;
	}
	
	private static void questionTwo(){
		// initialize board and knight
		int size = 8;		
		int startX = 1;
		int startY = 1;
		int endX = 7;
		int endY = 6;
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		board.printInitialBoard();
				
		// create a sequence of steps 
//		ArrayList<Move> steps = new ArrayList<Move>();
				
		findValidPath(startX, startY, endX, endY, 1);
		board.printBoardCount();
		System.out.println("Valid Path:");
	}
	
	private static boolean findValidPath(int startX, int startY, int endX, int endY, int count){
		if (knight.currentX == endX && knight.currentY == endY){
			board.printBoardCount();
			return true;
		}
		
		int total = board.size * board.size;
		
		ArrayList<Square> nbrs = neighbors(startX, startY);
		
		if (nbrs.isEmpty() && count > total)
			return false;

		
		for(Square nb : nbrs) {
			board.setSquareCount(nb.x, nb.y, count);
			if(findValidPath(nb.x, nb.y, endX, endY, count+1))
				return true;
			board.setSquareCount(nb.x, nb.y, 0);
		}
		
		return false;
	}
	
	private static boolean orphanDetected(int count, int x, int y) {
		int total = board.size * board.size;
		if(count < total-1){
			ArrayList<Square> nbrs = neighbors(x, y);
			for(Square s : nbrs){
				if (countNeighbors(s.x, s.y) == 0)
					return true;
			}
		}
		return false;
	}

	private static ArrayList<Square> neighbors(int x, int y) {
		ArrayList<Square> nbrs = new ArrayList<Square>();
		
		for(Move m : moves){
			if (checkMove(m)){
				Square s = board.getSquare(x+m.x, y+m.y);
				if (s.count == 0){
					int num = countNeighbors(x+m.x, y+m.y);
					System.out.printf("# of neighbors %d\n", num);
					board.setSquareCount(x+m.x, y+m.y, num);
					nbrs.add(s);
				}
			}
		}
		return nbrs;
	}

	private static int countNeighbors(int x, int y) {
		int num = 0;
		for (Move m : moves){
			System.out.printf("%d, %d, %d, %d\n",x,y, m.x, m.y);
			
			if(checkMove(m)){
				Square s = board.getSquare(x+m.x, y+m.y);
				if(s.count == 0)
					num++;
			}
		}
		return num;
	}

}
