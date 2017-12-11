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

		//TODO: make better initial solution
/*		for(Car car : Problem.carList){
			Zone zone = zones.get(Problem.random.nextInt(zones.size()));
			zone.addCar(car);
		}*/
		
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

	public int getCost() {
		for(Zone zone : zones){
			zone.handleRequests();
			
		}
		
		for(Zone zone : zones){
			zone.calculateCost();
			
		}
		
		calculateCost();


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
	public Solution getNeighbour() {

//		System.out.println("Requesting new neighbour");
		//use Problem.random!
		//neighbour has one car moved from one zone to another
		//method is not allowed to alter parameters from the this object!--> only parameters from neighbour should be altered.


		//Added car usage refreshment with each new solution
		Solution neighbour=new Solution(this);

		List<Zone> neigbourZones = neighbour.getZones();

		//pick 2 zones
		//move 1 car from zone A to B

		List<Zone> zonesWithCars = new ArrayList<>();

		for(Zone zone : neigbourZones){
			if(zone.getCarList().size() > 0)
				zonesWithCars.add(zone);
		}

		//Random zone which will lose a car
		Zone randomZoneWithCar = zonesWithCars.get(Problem.random.nextInt(zonesWithCars.size()));
		neigbourZones.remove(randomZoneWithCar);

		Zone randomToZone = neigbourZones.remove(Problem.random.nextInt(neigbourZones.size()));

		//Random car from zone with cars
		Car randomCar = randomZoneWithCar.getCarList().get(Problem.random.nextInt(randomZoneWithCar.getCarList().size()));

		randomZoneWithCar.removeCar(randomCar);
		randomZoneWithCar.setChanged(true);
		randomToZone.addCar(randomCar);
		randomToZone.setChanged(true);

//		System.out.println("Moving " + randomCar.getCarId() + " from " + randomZoneWithCar + " to " + randomToZone);

		neigbourZones.add(randomZoneWithCar);
		neigbourZones.add(randomToZone);

		for(Zone zone : neigbourZones){
			zone.handleRequests();
		}

		//Set zonelist from neighbour
		neighbour.setZones(neigbourZones);


		neighbour.calculateCost();


		return neighbour;
	}

	private void calculateCost() {
		// TODO Auto-generated method stub
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
