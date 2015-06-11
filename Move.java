package KnightMove;


public class Move {
	int x;
	int y;
	
	public Move(int x, int y){
		this.x = x;
		this.y = y;
	}

	public boolean validMove(){
		if(this.x * this.y != 0 && Math.abs(this.x) + Math.abs(this.y) == 3){
//			System.out.println("\tValid step.");
			return true;
		}
		else{
			System.out.printf("\tWrong moving rule! [%d, %d]\n", this.x, this.y);
			return false;
		}
	}

	public void printMove(){
		System.out.printf("Move: \t   [%d, %d]\n", x, y);
	}
	
}
