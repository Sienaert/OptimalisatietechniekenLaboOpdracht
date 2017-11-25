import java.util.List;
import java.util.Random;

public class Solution {
	int cost;

	public Solution(List<Request> requestList, List<Zone> zoneList, List<Car> carList, int days) {
		// TODO Auto-generated constructor stub
		//make initial solution
		Zone z=zoneList.get(0);
		List <Car> cl=z.getCarList();
		cl.addAll(carList);
		
		cost=0;
		for(Zone zone:zoneList) {	
			zone.calculateCost();
			cost=cost+zone.getLatestCost();
		}
		
		
	}

	public int getCost() {
		// TODO Auto-generated method stub
		return cost;
	}

	public Solution getNeighbour(Random random) {
		// TODO Auto-generated method stub
		return null;
	}

}
