package KnightMove;

public class Square{
	public int x;
	public int y;
	public String status;
	public int count;
	
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
	
//	public int[] getSquareLocation(){
//		int[] l = new int[2];
//		l[0] = this.x;
//		l[1] = this.y;
//		return l;
//	}
	
	public enum Status {
		EMPTY, OCCUPY, BARRIER, WATER, ROCK, TELEPORT, LAVA 
	}
}