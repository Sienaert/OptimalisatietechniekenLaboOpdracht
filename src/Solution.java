import java.util.List;
import java.util.Random;

public class Solution {
	int cost;

	public Solution() {
		// TODO Auto-generated constructor stub
		//make initial solution
		Zone z=Problem.zoneList.get(0);
		List <Car> cl=z.getCarList();
		cl.addAll(Problem.carList);
		
		cost=0;
		for(Zone zone:Problem.zoneList) {	
			zone.calculateCost();
			cost=cost+zone.getLatestCost();
		}
		
		
	}

	public int getCost() {
		// TODO Auto-generated method stub
		return cost;
	}

	public Solution getNeighbour() {
		// TODO Auto-generated method stub
		//use Problem.random!
		
		Solution neighbour=null;
		
		
		
		
		
		
		
		return neighbour;
	}

}
