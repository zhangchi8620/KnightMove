package KnightMove;

public class Square{
	public String status;
	public int count;
	public int x, y;
	
	public Square(int x, int y){
		this.status = ".";
		this.count = 0;
		this.x = x;
		this.y = y;
	}
	
	public void setSquareStatus(String str){
			this.status = str;
	}
	public String getSquareStatus(){
		return this.status;
	}
	
	public void setSquareCount(int i){
		this.count = i;
	}
	
	public int getSquareCount(){
		return this.count;
	}
	
	public void printSquare(){
		System.out.printf("[%d, %d]\n", x, y);
	}
	public enum Status {
		EMPTY, OCCUPY, BARRIER, WATER, ROCK, TELEPORT, LAVA 
	}
}