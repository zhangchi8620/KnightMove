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
			this.setConstrains();
			squares[this.startX][this.startY].setSquareStatus("S");			
			squares[knight.currentX-1][knight.currentY-1].setSquareStatus("K");
			}
	}
	
	public void printInitialBoard(){
		System.out.println("Initial this");
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
	
	private void setConstrains(){
		// creat "B"
		for (int i = 1; i < 9; i++)
			this.setSquareStatus(i, 9, "B");
		for(int i = 11; i < 21; i++)
			this.setSquareStatus(10, i, "B");
		this.setSquareStatus(8, 10, "B");
		this.setSquareStatus(9, 10, "B");
		this.setSquareStatus(9, 11, "B");
		for (int i = 10; i < 16; i++)
			this.setSquareStatus(i, 20, "B");
		for (int i = 15; i < 20; i++)
			this.setSquareStatus(i, 16, "B");
		this.setSquareStatus(15, 17, "B");
		this.setSquareStatus(15, 18, "B");
		this.setSquareStatus(15, 19, "B");
		
		this.setSquareStatus(19, 17, "B");
		this.setSquareStatus(19, 18, "B");

		this.setSquareStatus(20, 18, "B");
		this.setSquareStatus(21, 18, "B");

		for (int i = 22; i < 29; i++)
			this.setSquareStatus(i, 12, "B");
		this.setSquareStatus(22, 14, "B");
		this.setSquareStatus(22, 13, "B");

		for (int i = 29; i < 33; i++)
			this.setSquareStatus(18, i, "B");
		this.setSquareStatus(19, 29, "B");

		for(int i = 25; i < 30; i++)
			this.setSquareStatus(20, i, "B");
		this.setSquareStatus(21, 25, "B");
		this.setSquareStatus(22, 25, "B");

		for (int i = 22;i<26;i++)
			this.setSquareStatus(i, 26, "B");

		// create "W"
		for(int i = 9; i < 15; i++)
			this.setSquareStatus(i, 9, "W");
		for(int i = 10; i < 15; i++)
			this.setSquareStatus(i, 10, "W");
		
		this.setSquareStatus(15, 4, "W");
		this.setSquareStatus(15, 5, "W");
		this.setSquareStatus(16, 4, "W");
		this.setSquareStatus(16, 5, "W");

		for (int i = 1; i < 5; i++)
			this.setSquareStatus(17, i, "W");

		for(int i = 4; i < 11; i++)
			this.setSquareStatus(13, i, "W");
		for(int i = 4; i < 11; i++)
			this.setSquareStatus(14, i, "W");
		for(int i = 4; i < 11; i++)
			this.setSquareStatus(18, i, "W");
		for(int i = 4; i < 11; i++)
			this.setSquareStatus(19, i, "W");
		for(int i = 4; i < 11; i++)
			this.setSquareStatus(20, i, "W");
		for(int i = 4; i < 11; i++)
			this.setSquareStatus(21, i, "W");
		
		for(int i = 26; i < 33; i++)
			this.setSquareStatus(15, i, "W");	
		this.setSquareStatus(16, 26, "W");	

		for(int i = 20; i < 27; i++)
			this.setSquareStatus(17, i, "W");	
		
		for(int i = 19; i < 25; i++)
			this.setSquareStatus(20, i, "W");		
		for(int i = 19; i < 25; i++)
			this.setSquareStatus(21, i, "W");		
		
		// create "L"
		for(int i = 1; i < 7; i++)
			for (int j = 13; j < 16; j++)
				this.setSquareStatus(i, j, "L");	
		
		for(int i = 19; i <22; i++)
			this.setSquareStatus(3, i, "L");	
		
		for(int i = 18; i <21; i++)
			this.setSquareStatus(4, i, "L");
		
		for(int i = 16; i <21; i++)
			this.setSquareStatus(5, i, "L");	
		for(int i = 16; i <19; i++)
			this.setSquareStatus(6, i, "L");	
		
		// create "R"
		for(int i = 10; i <12; i++)
			for (int j = 4; j < 6; j++)
				this.setSquareStatus(i, j, "R");	
		
		for(int i = 23; i <25; i++)
			for (int j = 6; j < 8; j++)
				this.setSquareStatus(i, j, "R");	
		
		for(int i = 25; i <27; i++)
			for (int j = 18; j < 20; j++)
				this.setSquareStatus(i, j, "R");	
		
		for(int i = 27; i <29; i++)
			for (int j = 23; j < 25; j++)
				this.setSquareStatus(i, j, "R");
		
		for(int i = 14; i <16; i++)
			for (int j = 23; j < 25; j++)
				this.setSquareStatus(i, j, "R");	
		
		for(int i = 7; i <9; i++)
			for (int j = 22; j < 24; j++)
				this.setSquareStatus(i, j, "R");	
		
		for(int i = 4; i <6; i++)
			for (int j = 24; j < 26; j++)
				this.setSquareStatus(i, j, "R");	
		
		// create "T"
		this.setSquareStatus(12, 27, "T");	
		this.setSquareStatus(24, 28, "T");	
	}
	
	
}
