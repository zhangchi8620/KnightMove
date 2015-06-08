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
	public static int size;
	public static int startX;
	public static int startY;
	public static int endX;
	public static int endY;
	
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
//		questionTwo();
		
		// Q3
		questionThree();
}
	
	private static void questionOne(ArrayList<Move> steps){
		// initialize board and knight
		
		knight = new Knight(startX, startY);
		board = new Board(size, knight);
		startX = 1;
		startY = 1;
		endX = 8;
		endY = 8;
		board.setStartEnd(startX, startY, endX, endY);
		board.printInitialBoard();
				
		// input in a sequence of steps 
//		ArrayList<Move> steps = new ArrayList<Move>();
//		steps.clear();			
//		steps.add(new Move(-1,2)); 			
//		steps.add(new Move(-1,2)); 		
//		steps.add(new Move(2,-1)); 	
		int c = 1;
		for (Move s : steps){
			System.out.printf("\n*********** Step %d **********\n", c++);			
			if (!knight.validMove(s, board)){
				System.out.println("Q1 Answer: Invalide sequence.");
				return;
			}
			else{
				knight.acceptMove(s, board);
				knight.printKnight();
				s.printMove();
				board.printBoard();
			}
		}
		System.out.println("Q1 Answer: Valid sequence");
	}
	
	private static void questionTwo(){
		// initialize board and knight
		size = 8;		
		startX = 1;
		startY = 1;
		endX = 8;
		endY = 8;
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		board.printInitialBoard();
				
		// create a sequence of steps 
		ArrayList<Square> steps = new ArrayList<Square>();		
		
		findValidPath(steps, startX, startY, endX, endY, 2);
		board.printBoardCount();
		
	    Collections.reverse(steps);
		System.out.println("Find Path:");
		int c = 1;
		for (Square m : steps){
			System.out.printf("%d: %d, %d\n", c++, m.x, m.y);
		}
		
		ArrayList<Move> moves = steps2moves(startX, startY, steps);
//		questionOne(moves);

	}
	
	private static ArrayList<Move> steps2moves(int startx, int starty, ArrayList<Square> steps) {
		ArrayList<Move> moves = new ArrayList<Move>();
		steps.add(0, new Square(startx, starty));
		int c = 1;
		for (int i = 0; i < steps.size()-1; i++){
			Move m = new Move(steps.get(i+1).x-steps.get(i).x, steps.get(i+1).y-steps.get(i).y);
//			System.out.printf("%d: ", c++);
//			m.printMove();
			moves.add(m);
		}
		return moves;
	}

	private static boolean findValidPath(ArrayList<Square> steps, int startX, int startY, int endX, int endY, int count){
//		board.printBoardCount();
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
				Square m = new Square(nb.x, nb.y);				
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

	private static void questionThree(){
		size = 8;		
		startX = 1;
		startY = 1;
		endX = 4;
		endY = 4;
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		board.printInitialBoard();
		
		ArrayList<Square> steps = new ArrayList<Square>();		
		ArrayList<Square> q = new ArrayList<Square>();		
		
		q.add(board.getSquare(startX, startY));
		findShortestPath(steps, startX, startY, endX, endY, 1);
		board.printBoardCount();
		
	    Collections.reverse(steps);
		System.out.println("Find Shortest Path:");
		int c = 1;
		for (Square m : steps){
			System.out.printf("%d: %d, %d\n", c++, m.x, m.y);
		}
		
		ArrayList<Move> moves = steps2moves(startX, startY, steps);
//		questionOne(moves);
	}

	
	private static boolean checkEnd(ArrayList<Square> steps, ArrayList<Square> q, int endX, int endY, int count) {
		for (Square s: q){
			board.setSquareCount(s.x, s.y, count);		
			board.printBoardCount();
			if (s.x == endX && s.y == endY){
				steps.add(s);
				board.printBoardCount();
				return true;
			}
		}
		board.printBoardCount();
		return false;
	}

	private static boolean findShortestPath(ArrayList<Square> steps,int startX, int startY, int endX, int endY, int count)
	{
		board.printBoardCount();
		knight.currentX = startX;
		knight.currentY = startY;
		
		ArrayList<Square> nbrs = new ArrayList<Square>();
		nbrs.addAll(neighbors(knight));
		
		if(!nbrs.isEmpty()){
			knight.printKnight();				
			while (!checkEnd(steps, nbrs, endX, endY, ++count)){
				ArrayList<Square> q = new ArrayList<Square>();
				for(Square nb : nbrs){
					knight.currentX = nb.x;
					knight.currentY = nb.y;
					q.addAll(neighbors(knight));
				}
				nbrs = q;
			}
			return true;
		}
		return false;
	}
	
	
	
}