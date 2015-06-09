package KnightMove;

public class Knight{
	public int lastX, lastY, currentX, currentY;
	public boolean validMove;
	
	public Knight(){
		lastX = Integer.MIN_VALUE;
		lastY = Integer.MIN_VALUE;
		currentX = Integer.MIN_VALUE;
		currentY = Integer.MIN_VALUE;
		validMove = false;
	}
	
	public Knight(int x, int y){
		lastX = Integer.MIN_VALUE;
		lastY = Integer.MIN_VALUE;
		currentX = x;
		currentY = y;
		validMove = false;
		this.validMove = false;
	}
	
	public boolean validMove(Move move, Board board){
		int nX = this.currentX + move.x;
		int nY = this.currentY + move.y;
		
		if (move.validMove() && nX > 0 && nX < board.size+1 && nY > 0 && nY < board.size+1){
			this.validMove = true;
		}
		else
			this.validMove = false;
		return this.validMove;
	}

	public boolean validMoveCons(Move move, Board board){
		int nX = this.currentX + move.x;
		int nY = this.currentY + move.y;
		
		if (move.validMove() && nX > 0 && nX < board.size+1 && nY > 0 && nY < board.size+1 && noBarrierRock(move, board)){
			this.validMove = true;
		}
		else
			this.validMove = false;
		return this.validMove;
	}
	
	private boolean noBarrierRock(Move step, Board board){
		if (Math.abs(step.x) == 2){
			if (board.getSquare(this.currentX+step.x/2, this.currentY).getSquareStatus()=="B"
			|| (board.getSquare(this.currentX+step.x/2, this.currentY+step.y).getSquareStatus()=="B"))
				return false;
		}
		if (Math.abs(step.y) == 2){
			if (board.getSquare(this.currentX, this.currentY+step.y/2).getSquareStatus()=="B"
			|| (board.getSquare(this.currentX+step.x, this.currentY+step.y/2).getSquareStatus()=="B"))
				return false;
		}
		if (board.getSquare(this.currentX+step.x, this.currentY+step.y).getSquareStatus()=="B"
				|| board.getSquare(this.currentX+step.x, this.currentY+step.y).getSquareStatus()=="R")
			return false;
		return true;
	}
	
	public void acceptMove(Move s, Board board){
		if (this.validMove = true){
			lastX = currentX;
			lastY = currentY;
			currentX += s.x;
			currentY += s.y;
			board.updateKnightOnBoard(this);
		}
	}
	
	public boolean transfer2Square(Board board){
		if (board.getSquare(currentX, currentY).getSquareStatus() == "T" ){
			Square transferSquare = board.transfer2Square(currentX, currentY);
			lastX = currentX;
			lastY = currentY;
			currentX = transferSquare.x;
			currentY = transferSquare.y;
			board.updateKnightOnBoard(this);
			return true;
		}
		return false;
	}
	
	public void printKnight(){
		System.out.printf("Knight's location:  [%d, %d]\n", this.currentX, this.currentY);
	}
}
