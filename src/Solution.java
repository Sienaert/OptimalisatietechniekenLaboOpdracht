import java.util.ArrayList;
import java.util.List;


public class Solution {
	int cost;
	private List <Zone> zones;

	public Solution() {
		//make initial solution

		this.zones = new ArrayList<>();
		
		
		//add zones to zones
		for(Zone zone:Problem.zoneList) {
			this.zones.add(zone);
		}


		//add all cars to 1 zone
		zones.get(0).setCarList(Problem.carList);
		
		cost = Integer.MAX_VALUE;

		//Handle all requests
		for(Zone zone:zones) {	
			zone.handleRequests();
		}
		calculateCost();

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

	
	public void process() {
		
		for(Zone zone : zones){
			zone.handleRequests();
			
		}
		
		for(Zone zone : zones){
			zone.calculateCost();
			
		}
		
		calculateCost();
	}
	public int getCost() {
		


		return cost;
	}

	
	//deep copy
	public Solution(Solution s) {
		this.cost=Integer.MAX_VALUE;

		zones=new ArrayList<>();
		
		//add zones to zones
		for(Zone zone:s.getZones()) {	
			zones.add(new Zone(zone));
		}

	}
	
	

	//generating random neighbour
	public Solution getNeighbour(int amountOfCars) {

//		System.out.println("Requesting new neighbour");
		//use Problem.random!
		//neighbour has one car moved from one zone to another
		//method is not allowed to alter parameters from the this object!--> only parameters from neighbour should be altered.


		//Added car usage refreshment with each new solution
		Solution neighbour=new Solution(this);
		
		
		List<Zone> neigbourZones = neighbour.getZones();
		
		//fix references to adj zones
		List<Zone>oldAdjacent;
		List <Zone>properAdjacent=new ArrayList<>();
		String id;
		
		//for all zones
		for(Zone z:neigbourZones) {
			
			//get oldAdjacent and replace them with proper zone with ok id
			oldAdjacent=z.getAdjacentZones();
			
			for(Zone zo:oldAdjacent) {
				
				id=zo.getZoneId();
				for(Zone proper:neigbourZones) {
					
					//find proper zone with same id and add to properAdjacent
					if(proper.getZoneId().equals(id)) {
						
						properAdjacent.add(proper);
						
						break;
					}
					
					
				}
				
				
				
			}
			
			z.setAdjacentZones(properAdjacent);
			properAdjacent=new ArrayList<>();
			
		}
		
		

		

		//TODO implementeren voor meerdere auto's te verzetten
		
		//pick 2 zones
		//move 1 car from zone A to B

		List<Zone> zonesWithCars = new ArrayList<>();

		

//		System.out.println("Moving " + randomCar.getCarId() + " from " + randomZoneWithCar + " to " + randomToZone);

		
		
			for (Zone zone : neigbourZones) {
				if (zone.getCarList().size() > 0)
					zonesWithCars.add(zone);
			}
			
			int iterations=amountOfCars/2;
			
			for(int i=0;i<iterations&&zonesWithCars.size()>0;i++){
			//Random zone which will lose a car
			Zone randomZoneWithCar = zonesWithCars.get(Problem.random.nextInt(zonesWithCars.size()));
			neigbourZones.remove(randomZoneWithCar);
			Zone randomToZone = neigbourZones.remove(Problem.random.nextInt(neigbourZones.size()));
			//Random car from zone with cars
			Car randomCar = randomZoneWithCar.getCarList()
					.get(Problem.random.nextInt(randomZoneWithCar.getCarList().size()));
			randomZoneWithCar.removeCar(randomCar);
			randomZoneWithCar.setChanged(true);
			randomToZone.addCar(randomCar);
			randomToZone.setChanged(true);
			neigbourZones.add(randomZoneWithCar);
			neigbourZones.add(randomToZone);
			if(randomZoneWithCar.getCarList().size()<1) {
				zonesWithCars.remove(randomZoneWithCar);
			}
			/*if(!zonesWithCars.contains(randomToZone)) {
				
				zonesWithCars.add(randomToZone);
			}*/
			
			}
			
		for(Zone zone : neigbourZones){
			zone.handleRequests();
		}

		//Set zonelist from neighbour
		neighbour.setZones(neigbourZones);


		neighbour.calculateCost();


		return neighbour;
	}

	private void calculateCost() {
		cost = 0;
		
		for(Zone z:zones) {
			
			cost += z.getLatestCost();
			
		}
		
	}

	@Override
	public String toString() {
		return "\nSolution{" +
				"cost=" + cost +
				", zones=" + zones +
				"}\n\n";
	}

	
}
