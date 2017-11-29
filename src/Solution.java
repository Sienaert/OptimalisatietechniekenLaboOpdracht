import java.util.ArrayList;
import java.util.List;


public class Solution {
	int cost;
	List <Zone> zones;

	public Solution() {
		//make initial solution
		
		
		zones=new ArrayList<>();
		
		//add zones to zones
		for(Zone zone:Problem.zoneList) {
			zones.add(zone);
		}
		
		//add all cars to 1 zone
		Zone z = zones.get(0);
		z.setCarList(Problem.carList);

		//Handle requests in initial zone
		z.handleRequests();
		
		cost=0;
		for(Zone zone:zones) {	
			zone.calculateCost();
			cost=cost+zone.getLatestCost();
		}

	}

	/**
	 * @return the zones
	 */
	public List<Zone> getZones() {
		return zones;
	}

	/**
	 * @param zones the zones to set
	 */
	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public int getCost() {
		int tempCost = 0;

		for(Zone zone : zones){
			zone.calculateCost();
			tempCost+=zone.getLatestCost();
		}

		cost = tempCost;

		return cost;
	}

	
	//deep copy
	public Solution(Solution s) {
		
		this.cost=s.cost;
		zones=new ArrayList<Zone>();
		
		//add zones to zones
		for(Zone zone:s.getZones()) {	
			zones.add(new Zone(zone));
		}
		
		
	}
	
	

	//generating random neighbour
	public Solution getNeighbour() {
		//use Problem.random!
		//neighbour has one car moved from one zone to another
		//method is not allowed to alter parameters from the this object!--> only parameters from neighbour should be altered.


		//Added car usage refreshment with each new solution
		Solution neighbour=new Solution(this);
		
		//pick 2 zones 
		//move 1 car from zone A to B
		
		
		List <Zone>AZones =new ArrayList<Zone>();
		List <Zone>BZones =new ArrayList<Zone>();
		for(Zone z:neighbour.getZones()) {
			
			if(z.getCarList().size()>0) {
				
				AZones.add(z);
				
			}
			
			BZones.add(z);
			
		}
		
		//pick zone A --> needs at least 1 car (see code above)
		

		int a=Problem.random.nextInt(AZones.size());
		
		Zone AZone=AZones.get(a);
		
		//pick zone B
		int b;
		
		BZones.remove(AZone);
		b=Problem.random.nextInt(BZones.size());
		Zone BZone=BZones.get(b);
		
		//get car from Zone A
		List <Car> from=AZone.getCarList();
		
		Car toMove=from.remove(Problem.random.nextInt(from.size()));
		
		
		//add Car to zone B
		BZone.getCarList().add(toMove);
		
		//calculate costs
		//remove old costs from total
		cost=cost-AZone.getLatestCost()-BZone.getLatestCost();
		
		//calculate new costs
		AZone.calculateCost();
		BZone.calculateCost();
		
		//add costs to total

		//Handle all requests in each zone
		for(Zone zone : neighbour.zones){
			zone.handleRequests();
		}
		
		neighbour.calculateCost();
		
		
		return neighbour;
	}

	private void calculateCost() {
		// TODO Auto-generated method stub
		cost =0;
		
		for(Zone z:zones) {
			
			cost=cost+z.getLatestCost();
			
		}
		
	}

	@Override
	public String toString() {
		return "Solution{" +
				"cost=" + cost +
				", zones=" + zones +
				'}';
	}

	public void assignRequests(){
		for(Zone zone : zones){

		}

	}
}
