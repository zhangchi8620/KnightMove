package KnightMove;

import java.util.ArrayList;

public class Layer {
	int level;
	ArrayList<Step> steps = new ArrayList<Step>();
	
	public Layer(int i, ArrayList<Step> steps){
		this.level = i;
		for (Step s : steps)
			this.steps.add(s);
	}	
	
}
