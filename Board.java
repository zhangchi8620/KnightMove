package KnightMove;

public class Board {
	public int size;
	private int startX, startY, endX, endY;
	private Square[][] squares;
	public Knight knight;
	
	public Board(){
		this.size = 0;
		this.knight = null;
	}
	
	public Board(int size, Knight knight) {				
		this.size = size;
		this.knight = knight;
		
		squares = new Square[this.size][this.size];
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				squares[i][j] = new Square(i+1, j+1);
			}
		}				
	}
	
	public void setStartEnd(int startX, int startY, int endX, int endY){
		this.startX = startX-1;
		this.startY = startY-1;	
		this.endX = endX-1;
		this.endY = endY-1;		
		squares[this.startX][this.startY].setSquareStatus("S");
		squares[this.endX][this.endY].setSquareStatus("E");
	}
	
	
	protected void updateKnightOnBoard(Knight knight){
		if (knight.lastX != Integer.MIN_VALUE){
			squares[knight.lastX-1][knight.lastY-1].setSquareStatus(".");		
			squares[this.startX][this.startY].setSquareStatus("S");			
			squares[knight.currentX-1][knight.currentY-1].setSquareStatus("K");		}
	}
	
	public void printInitialBoard(){
		System.out.println("Initial board");
		for (int row = 0 ; row < this.size; row++){
			for (int col = 0; col < this.size; col++){
			System.out.printf("%s ", squares[row][col].status);
		}
		System.out.printf("\n");
	}
		System.out.printf("\n");

	}
	
	public void printBoard(){
		updateKnightOnBoard(this.knight);		
		for (int row = 0 ; row < this.size; row++){
			for (int col = 0; col < this.size; col++){
			System.out.printf("%s ", squares[row][col].status);
		}
		System.out.printf("\n");
	}
		System.out.printf("\n");
	}
	
	public void printBoardCount(){
		for (Square[] row : squares){
			for (Square s : row){
				System.out.printf("%s ", s.count);
			}
		System.out.println("\n");
		}
		System.out.println("\n");
	}
	
	
	public void setSquareCount(int x, int y, int count){
		squares[x-1][y-1].count = count;
	}
	
	public void setSquareStatus(int x, int y, String str){
		squares[x-1][y-1].status = str;
	}
	
	public Square getSquare(int x, int y){
		return squares[x-1][y-1];
	}
	
}
