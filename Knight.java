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
	
	public boolean validMove(Move step, Board board){
		int nX = this.currentX + step.x;
		int nY = this.currentY + step.y;
		
		if (step.validMove() && nX > 0 && nX < board.size+1 && nY > 0 && nY < board.size+1){
			this.validMove = true;
		}
		else
			this.validMove = false;
		return this.validMove;
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
	
	public void printKnight(){
		System.out.printf("Knight's location:  [%d, %d]\n", this.currentX, this.currentY);
	}
}
