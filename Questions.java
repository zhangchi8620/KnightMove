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
//		questionFour();
		
		// Q5
		questionFive();
}
	
	private static void questionOne(ArrayList<Move> steps){
		System.out.println("=========== Level 1 ===========\n");
		// initialize board and knight
		knight = new Knight(startX, startY);
		board = new Board(size, knight);
		
		board.setStartEnd(startX, startY, endX, endY);
		board.setConstrains();
		board.printInitialBoard();
				
		
		// input in a sequence of steps 	
		int c = 1;
		for (Move s : steps){
			System.out.printf("\n***** Step %d *****\n", c++);		
			s.printMove();
			if (!knight.validMoveCons(s, board)){
				System.out.println("Is Valide moves? No!");
				return;
			}
			else{
				knight.acceptMove(s, board);
				knight.printKnight();
//				System.out.printf("count: %d\n", board.getSquare(knight.currentX, knight.currentY).getSquareCount());
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
		boolean ifTransfer = knight.transfer2Square(board);
		
		if (ifTransfer){
//			System.out.println(board.getSquare(knight.lastX, knight.lastY).getSquareCount());
			board.setSquareCount(knight.currentX, knight.currentY, board.getSquare(knight.lastX, knight.lastY).getSquareCount());
			pathMap.put(board.getSquare(knight.currentX, knight.currentY), board.getSquare(knight.lastX, knight.lastY));
		}
		
		Square k = board.getSquare(knight.currentX, knight.currentY);
		
		for(Move m : moves){
			if (knight.validMoveCons(m, board)){
				Square s = board.getSquare(knight.currentX+m.x, knight.currentY+m.y);
				if (s.count == 0 || s.count > k.getSquareCount()+contCons(k)){
					board.setSquareCount(s.x, s.y, k.getSquareCount()+1+contCons(s));	
				
					pathMap.put(s, board.getSquare(knight.currentX, knight.currentY));
					nbrs.add(s);
				}
			}
		}

		return nbrs;
	}
	
	
	private static ArrayList<Square> neighborsLongest(Knight knight) {
		ArrayList<Square> nbrs = new ArrayList<Square>();
		boolean ifTransfer = knight.transfer2Square(board);
		
		if (ifTransfer){
//			System.out.println(board.getSquare(knight.lastX, knight.lastY).getSquareCount());
			board.setSquareCount(knight.currentX, knight.currentY, board.getSquare(knight.lastX, knight.lastY).getSquareCount());
			pathMap.put(board.getSquare(knight.currentX, knight.currentY), board.getSquare(knight.lastX, knight.lastY));
		}
		
		Square k = board.getSquare(knight.currentX, knight.currentY);
		
		for(Move m : moves){
			if (knight.validMoveCons(m, board)){
				Square s = board.getSquare(knight.currentX+m.x, knight.currentY+m.y);
				if (s.count == 0 || s.count < k.getSquareCount()+contCons(k) && s.count != 1 && !checkLoop(knight, s)){
					board.setSquareCount(s.x, s.y, k.getSquareCount()+1+contCons(s));	
				
					pathMap.put(s, board.getSquare(knight.currentX, knight.currentY));
					nbrs.add(s);
				}
			}
		}

		return nbrs;
	}
	
	private static boolean checkLoop(Knight k, Square n) {
		Square par = board.getSquare(k.currentX, k.currentY);
		while(par.count != 1){
			if (par.x == n.x && par.y == n.y)
				return true;
			par = pathMap.get(par);
		}
		return false;
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
			if (s.x == endX && s.y == endY){
				steps.add(s);
				Square par = pathMap.get(s);
				
				while(par.count != 1){
					steps.add(par);
					par = pathMap.get(par);
				}
				return true;
			}
		}
		return false;
	}

	private static boolean checkEndCons(ArrayList<Square> steps, ArrayList<Square> q, int endX, int endY, int count) {
		for (Square s: q){
//			board.setSquareCount(s.x, s.y, count+contCons(s));		
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
	
	private static int contCons(Square s) {
		if (s.getSquareStatus() == "W")
			return 1;
		if (s.getSquareStatus() == "L")
			return 4;
		return 0;
	}

	private static boolean findShortestPath(ArrayList<Square> steps,int startX, int startY, int endX, int endY, int count)
	{
		knight.currentX = startX;
		knight.currentY = startY;
		
		ArrayList<Square> nbrs = new ArrayList<Square>();
		nbrs.addAll(neighborsCons(knight));
		
		if(!nbrs.isEmpty()){
			knight.printKnight();				
			while (!checkEnd(steps, nbrs, endX, endY, ++count)){
				board.printBoardCount();
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
	
	private static boolean findShortestPathCons(ArrayList<Square> steps,int startX, int startY, int endX, int endY, int count)
	{
		knight.currentX = startX;
		knight.currentY = startY;
		
		ArrayList<Square> nbrs = new ArrayList<Square>();
		nbrs.addAll(neighborsCons(knight));
		
		if(!nbrs.isEmpty()){
			knight.printKnight();				
			while (!checkEndCons(steps, nbrs, endX, endY, ++count)){
//				board.printBoardCount();
				ArrayList<Square> q = new ArrayList<Square>();
				for(Square nb : nbrs){
					knight.currentX = nb.x;
					knight.currentY = nb.y;
					q.addAll(neighborsCons(knight));
				}
				nbrs = q;
			}
//			board.printBoardCount();

			return true;
		}
		return false;
	}
	
	private static void questionFour(){
		System.out.println("\n=========== Level 4 ===========");
		size = 32;		
		startX = 2;
		startY = 12;
		endX = 28;
		endY = 28;
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		board.setConstrains();
		board.printInitialBoard();
		
		ArrayList<Square> steps = new ArrayList<Square>();		
		ArrayList<Square> q = new ArrayList<Square>();		
		steps.clear();
		q.add(board.getSquare(startX, startY));
		findShortestPathCons(steps, startX, startY, endX, endY, 1);
//		board.printBoardCount();
		
	    Collections.reverse(steps);
		System.out.println("Find Shortest Path with Cons:");
		int c = 1;
		for (Square m : steps){
			System.out.printf("%d: %d, %d\n", c++, m.x, m.y);
		}
		
		ArrayList<Move> moves = steps2moves(startX, startY, steps);
		
		questionOne(moves);
		
	}
	
	private static boolean findLongestPathCons(ArrayList<Square> steps,int startX, int startY, int endX, int endY, int count)
	{
		knight.currentX = startX;
		knight.currentY = startY;
		
		ArrayList<Square> nbrs = new ArrayList<Square>();
		nbrs.addAll(neighborsCons(knight));
		int c = 1;
		while(!nbrs.isEmpty() && c < board.size*board.size){
			System.out.printf("c: %d\n", c);
//			board.printBoardCount();

			knight.printKnight();				
			
			ArrayList<Square> q = new ArrayList<Square>();
			for(Square nb : nbrs){
				knight.currentX = nb.x;
				knight.currentY = nb.y;
				q.addAll(neighborsLongest(knight));
//				board.printBoardCount();
			}
			nbrs = q;
			++c;
//			board.printBoardCount();
		}
		
		Square par = pathMap.get(board.getSquare(endX, endY));
		steps.add(par);

		while (par.count != 1){
			par = pathMap.get(par);
			steps.add(par);
		}
		board.printBoardCount();
		return false;
	}
	
	public static void questionFive(){
		System.out.println("\n=========== Level 5 ===========");
		size = 32;		
		startX = 32;
		startY = 1;
		endX = 5;
		endY = 5;
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		board.setConstrains();
		board.printInitialBoard();
		
		ArrayList<Square> steps = new ArrayList<Square>();		
		ArrayList<Square> q = new ArrayList<Square>();		
		steps.clear();
		q.add(board.getSquare(startX, startY));
		findLongestPathCons(steps, startX, startY, endX, endY, 1);
		
	    Collections.reverse(steps);
		System.out.println("Find Longest Path with Cons:");
		int c = 1;
		for (Square m : steps){
			System.out.printf("%d: %d, %d\n", c++, m.x, m.y);
		}
		
		steps.remove(0);
		ArrayList<Move> moves = steps2moves(startX, startY, steps);
		
		questionOne(moves);
	}
		
}