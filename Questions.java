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
	
	private static void questionOne(ArrayList<Move> steps){
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
//		ArrayList<Move> steps = new ArrayList<Move>();
//		steps.clear();			
//		steps.add(new Move(-1,2)); 			
//		steps.add(new Move(-1,2)); 		
//		steps.add(new Move(2,-1)); 	

		for (Move s : steps){
			System.out.print("\n*********** Step **********\n");			
			if (!knight.validMove(s, board)){
				System.out.println("Q1 Answer: Invalide sequence.");
				return;
			}
			else{
				knight.acceptMove(s, board);
				knight.printKnight();
				board.printBoard();
			}
		}
		System.out.println("Q1 Answer: Valid sequence");
	}
	
	private static void questionTwo(){
		// initialize board and knight
		int size = 8;		
		int startX = 1;
		int startY = 1;
		int endX = 8;
		int endY = 8;
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		board.printInitialBoard();
				
		// create a sequence of steps 
		ArrayList<Move> steps = new ArrayList<Move>();		
		
		findValidPath(steps, startX, startY, endX, endY, 2);
		board.printBoardCount();
		
	    Collections.reverse(steps);

		System.out.println("Valid Path:");
		for (Move m : steps){
			System.out.printf("%d, %d\n", m.x, m.y);
		}
		
		questionOne(steps);

	}
	
	private static boolean findValidPath(ArrayList<Move> steps, int startX, int startY, int endX, int endY, int count){
		board.printBoardCount();
		knight.currentX = startX;
		knight.currentY = startY;
		
		if (knight.currentX == endX && knight.currentY == endY){
			board.printBoardCount();
			return true;
		}
		
		int total = board.size * board.size;
		
		ArrayList<Square> nbrs = neighbors(knight);
		
		if (nbrs.isEmpty() && count > total)
			return false;

		
		for(Square nb : nbrs) {
			board.setSquareCount(nb.x, nb.y, count);
			if(findValidPath(steps, nb.x, nb.y, endX, endY, count+1)){
				Move m = new Move(nb.x, nb.y);				
				steps.add(m);
				return true;
			}
		}
		
		return false;
	}

	private static ArrayList<Square> neighbors(Knight knight) {
		ArrayList<Square> nbrs = new ArrayList<Square>();
		
		for(Move m : moves){
			if (knight.validMove(m, board)){
				Square s = board.getSquare(knight.currentX+m.x, knight.currentY+m.y);
				if (s.count == 0){
					nbrs.add(s);
				}
			}
		}
		return nbrs;
	}

}
