package KnightMove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
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
    public static Hashtable<Square, Square> pathMap = new Hashtable<Square, Square>();

	
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
		size = 8;
		startX = 2;
		startY = 1;
		endX = 3;
		endY = 4;
		ArrayList<Move> steps = new ArrayList<Move>();
		steps.clear();			
		steps.add(new Move(-1,2)); 			
		steps.add(new Move(-1,2)); 		
		steps.add(new Move(12,-1)); 
//		questionOne(steps);	// q1 solution
		steps.clear();
		
		// Q2
//		questionTwo();
		
		// Q3
//		questionThree();
		
		// Q4
		questionFour();
}
	
	private static void questionOne(ArrayList<Move> steps){
		System.out.println("=========== Level 1 ===========\n");
		// initialize board and knight
		knight = new Knight(startX, startY);
		board = new Board(size, knight);
		
		board.setStartEnd(startX, startY, endX, endY);
		setConstrains();
		board.printInitialBoard();
				
		
		// input in a sequence of steps 	
		int c = 1;
		for (Move s : steps){
			System.out.printf("\n***** Step %d *****\n", c++);			
			if (!knight.validMove(s, board)){
				System.out.println("Is Valide moves? No!");
				return;
			}
			else{
				knight.acceptMove(s, board);
				knight.printKnight();
				s.printMove();
				board.printBoard();
			}
		}
		System.out.println("Is Valide moves? Yes.");
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
					pathMap.put(s, board.getSquare(knight.currentX, knight.currentY));
					nbrs.add(s);
				}
			}
		}

		return nbrs;
	}

	
	private static ArrayList<Square> neighborsCons(Knight knight) {
		ArrayList<Square> nbrs = new ArrayList<Square>();
		
		for(Move m : moves){
			if (knight.validMoveCons(m, board)){
				Square s = board.getSquare(knight.currentX+m.x, knight.currentY+m.y);
				if (s.count == 0){
					pathMap.put(s, board.getSquare(knight.currentX, knight.currentY));
					nbrs.add(s);
				}
			}
		}

		return nbrs;
	}
	
	private static void questionThree(){
		System.out.println("\n=========== Level 3 ===========");
		size = 32;		
		startX = 8;
		startY = 6;
		endX = 31;
		endY = 18;
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		board.printInitialBoard();
		
		ArrayList<Square> steps = new ArrayList<Square>();		
		ArrayList<Square> q = new ArrayList<Square>();		
		steps.clear();
		q.add(board.getSquare(startX, startY));
		findShortestPath(steps, startX, startY, endX, endY, 1);
//		board.printBoardCount();
		
	    Collections.reverse(steps);
		System.out.println("Find Shortest Path:");
		int c = 1;
		for (Square m : steps){
			System.out.printf("%d: %d, %d\n", c++, m.x, m.y);
		}
		
		ArrayList<Move> moves = steps2moves(startX, startY, steps);
		questionOne(moves);
	}

	private static boolean checkEnd(ArrayList<Square> steps, ArrayList<Square> q, int endX, int endY, int count) {
		for (Square s: q){
			board.setSquareCount(s.x, s.y, count);		
//			board.printBoardCount();
			if (s.x == endX && s.y == endY){
				steps.add(s);
//				board.printBoardCount();
				Square par = pathMap.get(s);
				
				while(par.count != 1){
					steps.add(par);
//					System.out.printf("Path: [%d, %d]\n", par.x, par.y);
					par = pathMap.get(par);
				}
				
				return true;
			}
		}
//		board.printBoardCount();
		return false;
	}

	private static boolean findShortestPath(ArrayList<Square> steps,int startX, int startY, int endX, int endY, int count)
	{
//		board.printBoardCount();
		knight.currentX = startX;
		knight.currentY = startY;
		
		ArrayList<Square> nbrs = new ArrayList<Square>();
		nbrs.addAll(neighborsCons(knight));
		
		if(!nbrs.isEmpty()){
			knight.printKnight();				
			while (!checkEnd(steps, nbrs, endX, endY, ++count)){
				ArrayList<Square> q = new ArrayList<Square>();
				for(Square nb : nbrs){
					knight.currentX = nb.x;
					knight.currentY = nb.y;
					q.addAll(neighborsCons(knight));
				}
				nbrs = q;
			}
			return true;
		}
		return false;
	}
	
	private static void questionFour(){
		System.out.println("\n=========== Level 4 ===========");
		size = 32;		
		startX = 10;
		startY = 3;
		endX = 12;
		endY = 7;
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		setConstrains();
		board.printInitialBoard();
		
		ArrayList<Square> steps = new ArrayList<Square>();		
		ArrayList<Square> q = new ArrayList<Square>();		
		steps.clear();
		q.add(board.getSquare(startX, startY));
		findShortestPath(steps, startX, startY, endX, endY, 1);
//		board.printBoardCount();
		
	    Collections.reverse(steps);
		System.out.println("Find Shortest Path:");
		int c = 1;
		for (Square m : steps){
			System.out.printf("%d: %d, %d\n", c++, m.x, m.y);
		}
		
		ArrayList<Move> moves = steps2moves(startX, startY, steps);
		questionOne(moves);
		
	}
	
	private static void setConstrains(){
		// creat "B"
		for (int i = 1; i < 9; i++)
			board.setSquareStatus(i, 9, "B");
		for(int i = 11; i < 21; i++)
			board.setSquareStatus(10, i, "B");
		board.setSquareStatus(8, 10, "B");
		board.setSquareStatus(9, 10, "B");
		board.setSquareStatus(9, 11, "B");
		for (int i = 10; i < 16; i++)
			board.setSquareStatus(i, 20, "B");
		for (int i = 15; i < 20; i++)
			board.setSquareStatus(i, 16, "B");
		board.setSquareStatus(15, 17, "B");
		board.setSquareStatus(15, 18, "B");
		board.setSquareStatus(15, 19, "B");
		
		board.setSquareStatus(19, 17, "B");
		board.setSquareStatus(19, 18, "B");

		board.setSquareStatus(20, 18, "B");
		board.setSquareStatus(21, 18, "B");

		for (int i = 22; i < 29; i++)
			board.setSquareStatus(i, 12, "B");
		board.setSquareStatus(22, 14, "B");
		board.setSquareStatus(22, 13, "B");

		for (int i = 29; i < 33; i++)
			board.setSquareStatus(18, i, "B");
		board.setSquareStatus(19, 29, "B");

		for(int i = 25; i < 30; i++)
			board.setSquareStatus(20, i, "B");
		board.setSquareStatus(21, 25, "B");
		board.setSquareStatus(22, 25, "B");

		for (int i = 22;i<26;i++)
			board.setSquareStatus(i, 26, "B");

		// create "W"
		for(int i = 9; i < 15; i++)
			board.setSquareStatus(i, 9, "W");
		for(int i = 10; i < 15; i++)
			board.setSquareStatus(i, 10, "W");
		
		board.setSquareStatus(15, 4, "W");
		board.setSquareStatus(15, 5, "W");
		board.setSquareStatus(16, 4, "W");
		board.setSquareStatus(16, 5, "W");

		for (int i = 1; i < 5; i++)
			board.setSquareStatus(17, i, "W");

		for(int i = 4; i < 11; i++)
			board.setSquareStatus(13, i, "W");
		for(int i = 4; i < 11; i++)
			board.setSquareStatus(14, i, "W");
		for(int i = 4; i < 11; i++)
			board.setSquareStatus(18, i, "W");
		for(int i = 4; i < 11; i++)
			board.setSquareStatus(19, i, "W");
		for(int i = 4; i < 11; i++)
			board.setSquareStatus(20, i, "W");
		for(int i = 4; i < 11; i++)
			board.setSquareStatus(21, i, "W");
		
		for(int i = 26; i < 33; i++)
			board.setSquareStatus(15, i, "W");	
		board.setSquareStatus(16, 26, "W");	

		for(int i = 20; i < 27; i++)
			board.setSquareStatus(17, i, "W");	
		
		for(int i = 19; i < 25; i++)
			board.setSquareStatus(20, i, "W");		
		for(int i = 19; i < 25; i++)
			board.setSquareStatus(21, i, "W");		
		
		// create "L"
		for(int i = 1; i < 7; i++)
			for (int j = 13; j < 16; j++)
				board.setSquareStatus(i, j, "L");	
		
		for(int i = 19; i <22; i++)
			board.setSquareStatus(3, i, "L");	
		
		for(int i = 18; i <21; i++)
			board.setSquareStatus(4, i, "L");
		
		for(int i = 16; i <21; i++)
			board.setSquareStatus(5, i, "L");	
		for(int i = 16; i <19; i++)
			board.setSquareStatus(6, i, "L");	
		
		// create "R"
		for(int i = 10; i <12; i++)
			for (int j = 4; j < 6; j++)
				board.setSquareStatus(i, j, "R");	
		
		for(int i = 23; i <25; i++)
			for (int j = 6; j < 8; j++)
				board.setSquareStatus(i, j, "R");	
		
		for(int i = 25; i <27; i++)
			for (int j = 18; j < 20; j++)
				board.setSquareStatus(i, j, "R");	
		
		for(int i = 27; i <29; i++)
			for (int j = 23; j < 25; j++)
				board.setSquareStatus(i, j, "R");
		
		for(int i = 14; i <16; i++)
			for (int j = 23; j < 25; j++)
				board.setSquareStatus(i, j, "R");	
		
		for(int i = 7; i <9; i++)
			for (int j = 22; j < 24; j++)
				board.setSquareStatus(i, j, "R");	
		
		for(int i = 4; i <6; i++)
			for (int j = 24; j < 26; j++)
				board.setSquareStatus(i, j, "R");	
		
		// create "T"
		board.setSquareStatus(12, 27, "T");	
		board.setSquareStatus(24, 28, "T");	
	}
	
}