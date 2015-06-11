package KnightMove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

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
	public final static ArrayList<Move> moves = new ArrayList<Move>();

	public static void main(String[] args){			
		System.out.println(args[0].toCharArray());
		moves.add(new Move(-2, -1));
		moves.add(new Move(-2, 1));
		moves.add(new Move(-1, -2));
		moves.add(new Move(-1, 2));
		moves.add(new Move(1, -2));
		moves.add(new Move(1, 2));
		moves.add(new Move(2, -1));
		moves.add(new Move(2, 1));
		
		switch (args[0]){
			case "level1":
				ArrayList<Move> steps = new ArrayList<Move>();
				for (int i = 1; i < args.length; i=i+2){
					steps.add(new Move(Integer.parseInt(args[i]), Integer.parseInt(args[i+1])));
				}
				System.out.println("Given sequence:");
				for (Move m : steps)
					m.printMove();
				// Q1
				questionOne(steps);	
				steps.clear();
				break;
				
			case "level2":
				startX = Integer.parseInt(args[1]);
				startY = Integer.parseInt(args[2]);
				endX = Integer.parseInt(args[3]);
				endY = Integer.parseInt(args[4]);
				System.out.printf("start square [%d, %d], end square [%d, %d]\n", startX, startY, endX, endY);
				// Q2
				questionTwo();
				break;
				
			case "level3":
				startX = Integer.parseInt(args[1]);
				startY = Integer.parseInt(args[2]);
				endX = Integer.parseInt(args[3]);
				endY = Integer.parseInt(args[4]);
				System.out.printf("start square [%d, %d], end square [%d, %d]\n", startX, startY, endX, endY);				
				questionThree();
				break;
			case "level4":
				startX = Integer.parseInt(args[1]);
				startY = Integer.parseInt(args[2]);
				endX = Integer.parseInt(args[3]);
				endY = Integer.parseInt(args[4]);
				System.out.printf("start square [%d, %d], end square [%d, %d]\n", startX, startY, endX, endY);				
				questionFour();
				break;
			case "level5":
				startX = Integer.parseInt(args[1]);
				startY = Integer.parseInt(args[2]);
				endX = Integer.parseInt(args[3]);
				endY = Integer.parseInt(args[4]);
				System.out.printf("start square [%d, %d], end square [%d, %d]\n", startX, startY, endX, endY);
				questionFive();
				break;
		}
}

	private static void questionOne(ArrayList<Move> steps){
		System.out.println("=========== Level 1 ===========\n");
		size = 8;
		startX = 2;
		startY = 1;
		endX = 3;
		endY = 4;
		
		// initialize board and knight
		knight = new Knight(startX, startY);
		board = new Board(size, knight);
		board.setStartEnd(startX, startY, endX, endY);
		board.printInitialBoard();
		
		// check a sequence of moves
		checkSeq(steps);
	}
	
	// check if given sequence is valid
	
	private static void checkSeq(ArrayList<Move> steps){
		int c = 1;
		// set knight to start point
		knight.currentX = startX;
		knight.currentY = startY;
		knight.lastX = Integer.MIN_VALUE;
		knight.lastY = Integer.MIN_VALUE;		
		for (Move s : steps){
			System.out.printf("\n***** Step %d *****\n", c++);		
			s.printMove();
			if (!knight.validMove(s, board)){
				System.out.println("Is Valide moves? No!");
				return;
			}
			else{
				knight.acceptMove(s, board);
				knight.printKnight();
				board.printBoard();
			}
		}
		System.out.println("Is Valide moves? Yes.");
	}
	
	// check if given sequence is valid, with constraints
	private static void checkSeqCons(ArrayList<Move> steps){
		knight.currentX = startX;
		knight.currentY = startY;
		knight.lastX = Integer.MIN_VALUE;
		knight.lastY = Integer.MIN_VALUE;
		int c = 1;
		for (Move s : steps){
			System.out.printf("\n***** Step %d *****\n", c++);		
			s.printMove();
			if (!knight.validMoveCons(s, board)){
				System.out.println("Is Valide moves? No!");
				return;
			}
			else{
				knight.acceptMoveCons(s, board);
				knight.printKnight();
				board.setConstrains();
				board.printBoard();
			}
		}
		System.out.println("Is Valide moves? Yes.");
	}
	
	private static void questionTwo(){
		// initialize board and knight
		size = 8;		
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
		checkSeq(moves);

	}
	
	// convert steps (Square) to moves (Move)
	private static ArrayList<Move> steps2moves(int startx, int starty, ArrayList<Square> steps) {
		ArrayList<Move> moves = new ArrayList<Move>();
		steps.add(0, new Square(startx, starty));
		for (int i = 0; i < steps.size()-1; i++){
			Move m = new Move(steps.get(i+1).x-steps.get(i).x, steps.get(i+1).y-steps.get(i).y);
			moves.add(m);
		}
		return moves;
	}

	// find one validate path
	private static boolean findValidPath(ArrayList<Square> steps, int startX, int startY, int endX, int endY, int count){
		knight.currentX = startX;
		knight.currentY = startY;
		
		if (knight.currentX == endX && knight.currentY == endY){
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
	
	// find neighbors

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

	// find neighbors, with constraints
	private static ArrayList<Square> neighborsCons(Knight knight) {
		ArrayList<Square> nbrs = new ArrayList<Square>();
		boolean ifTransfer = knight.transfer2Square(board);
		
		if (ifTransfer){
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
	
	// check if n is in k's parent squares
	
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
		size = 8;		
		startX = 1;
		startY = 2;
		endX = 7;
		endY = 8;
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
		checkSeq(moves);
	}

	
	// check if any square in q is the end point
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

	// check end with constraints
	private static boolean checkEndCons(ArrayList<Square> steps, ArrayList<Square> q, int endX, int endY, int count) {
		for (Square s: q){
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
	
	// set square's count by constraints
	private static int contCons(Square s) {
		if (s.getSquareStatus() == "W")
			return 1;
		if (s.getSquareStatus() == "L")
			return 4;
		return 0;
	}

	// find shortest path
	private static boolean findShortestPath(ArrayList<Square> steps,int startX, int startY, int endX, int endY, int count)
	{
		knight.currentX = startX;
		knight.currentY = startY;
		
		ArrayList<Square> nbrs = new ArrayList<Square>();
		nbrs.addAll(neighborsCons(knight));
		
		if(!nbrs.isEmpty()){
			knight.printKnight();				
			while (!checkEnd(steps, nbrs, endX, endY, ++count)){
//				board.printBoardCount();
				ArrayList<Square> q = new ArrayList<Square>();
				for(Square nb : nbrs){
					knight.currentX = nb.x;
					knight.currentY = nb.y;
					q.addAll(neighbors(knight));
				}
				nbrs = q;
			}
			board.printBoardCount();
			return true;
		}
		return false;
	}
	
	// find shortest path with constraints
	private static boolean findShortestPathCons(ArrayList<Square> steps,int startX, int startY, int endX, int endY, int count)
	{
		knight.currentX = startX;
		knight.currentY = startY;
		
		ArrayList<Square> nbrs = new ArrayList<Square>();
		nbrs.addAll(neighborsCons(knight));
		
		if(!nbrs.isEmpty()){
			knight.printKnight();				
			while (!checkEndCons(steps, nbrs, endX, endY, ++count)){
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
		knight = new Knight(startX, startY);		
		board = new Board(size, knight);
		board.setSquareCount(startX, startY, 1);
		board.setStartEnd(startX, startY, endX, endY);
		board.setConstrains();
		board.printInitialBoard();
		
		ArrayList<Square> steps = new ArrayList<Square>();		
		steps.clear();
		findShortestPathCons(steps, startX, startY, endX, endY, 1);
		
	    Collections.reverse(steps);
		System.out.println("Find Shortest Path with Cons:");
		int c = 1;
		for (Square m : steps){
			System.out.printf("%d: %d, %d\n", c++, m.x, m.y);
		}
		
		ArrayList<Move> moves = steps2moves(startX, startY, steps);
		
		checkSeqCons(moves);
		
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
			board.printBoardCount();

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
		
		checkSeqCons(moves);
	}
		
}