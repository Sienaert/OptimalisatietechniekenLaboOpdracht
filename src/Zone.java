import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Zone {

    private String zoneId;
    private List<Car> carList;
    private List<Request> requestList;
    private List<Request> redirectedRequests;
    private List<Zone> adjacentZones;
    boolean changed;
    int latestCost=Integer.MAX_VALUE;

    //TODO: make intervalTree from requests -> to determine overlapping timeframes

	public int getLatestCost() {
		return latestCost;
	}

	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}

	public List<Request> getRedirectedRequests() {
		return redirectedRequests;
	}

	public void setRedirectedRequests(List<Request> redirectedRequests) {
		this.redirectedRequests = redirectedRequests;
	}

	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed the changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public Zone(String zoneId) {
        this.zoneId = zoneId;

        carList = new ArrayList<>();
        requestList = new ArrayList<>();
        adjacentZones = new ArrayList<>();
        changed=false;
        redirectedRequests = new ArrayList<>();

    }
	
	//deep copy
	public Zone(Zone z) {
		this.zoneId = z.zoneId;
		this.redirectedRequests = z.getRedirectedRequests();

		//TODO deep copy of Cars
		carList = new ArrayList<>();
		for(Car car:z.getCarList()) {
			
			carList.add(new Car(car));
			
		}
		/*
        carList = new ArrayList<>(z.getCarList());
        //Refresh each car usage
		for(Car car : carList) car.setTimeUsed(new IntervalTree());
*/
       
		
		
		//TODO deep copy of requests
		requestList = new ArrayList<>();
		for(Request request : z.getRequestList()){
			
			requestList.add(new Request(request));
		}
		
		
		/*
		requestList = new ArrayList<>(z.getRequestList());

        //reset requests
      
		for(Request request : requestList){
			request.setRedirected(false);
			request.setAssigned(false);
			request.setCarID(null);
		}
		*/

		
		
		//TODO klopt de lijn hieronder?------------------------------------------------------------
        adjacentZones = new ArrayList<>(z.getAdjacentZones());
        //
        latestCost=z.getLatestCost();
        this.changed=z.changed;
    }


    public List<Car> getCarList() {
        return carList;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public List<Zone> getAdjacentZones() {
        return adjacentZones;
    }

    public void setAdjacentZones(List<Zone> adjacentZones) {
        this.adjacentZones = adjacentZones;
    }

	public void addRequest(Request q) {
		// TODO Auto-generated method stub
		requestList.add(q);
	}

	

	public String getAssignmentsString() {

		StringBuilder sb=new StringBuilder("");

		for (Car c:carList) {
		sb.append(c.getCarId()+";"+zoneId);
		}
		return sb.toString();

	}

	public void handleRequests(){
		for(Request request : requestList){
			//Check if a possible car is in current zone
			List<Car> possibleCars = request.getPossibleVehicleTypes();

			boolean requestIsBeingProcessed = true;
			int i = 0;

			while(requestIsBeingProcessed && i<possibleCars.size()){
				Car car = possibleCars.get(i);

				//If requested car is in current zone
				if(carList.contains(car)){
					//Try to assign car to request
					if(car.assignCar(request)){
						//If assigned successful, break out of while-loop and handle next request
						request.setAssigned(true);
						request.setRedirected(false);
						request.setCarID(car.getCarId());
						requestIsBeingProcessed = false;
					}
				}

				//Try next possible car
				i++;
			}

			//even in comment zetten
			//If no car found, request is still being processed
//			if(requestIsBeingProcessed){
//				//Push request to each neighbour => return boolean if handled
//				for(Zone adjacentZone : adjacentZones){
//					//If adjacent zone can handle the request, end loop
//
//					if(requestIsBeingProcessed){
//						if(adjacentZone.handleRedirectedRequest(request)){
//							adjacentZone.setChanged(true);
//							request.setRedirected(true);
//							requestIsBeingProcessed = false;
//							break;
//						}
//					}
//				}
//			}

			//Still no luck, request not assigned
			if(requestIsBeingProcessed){
				request.setAssigned(false);
			}

		}

		calculateCost();
	}


	public void handleRequestPart2(){
		for(Request request : requestList){
			int i = 0;
			boolean isNotAssigned = !request.isAssigned();
			while(i<adjacentZones.size() && isNotAssigned){
				Zone adjacentZone = adjacentZones.get(i);

				if(adjacentZone.handleRedirectedRequest(request)){
					isNotAssigned = false;
				}
				i++;
			}
		}
	}

	public boolean handleRedirectedRequest(Request redirectedRequest){
		List<Car> possibleCars = redirectedRequest.getPossibleVehicleTypes();

		boolean requestIsBeingProcessed = true;
		int i = 0;

		while(requestIsBeingProcessed && i<possibleCars.size()){
			Car car = possibleCars.get(i);

			//If requested car is in current zone
			if(carList.contains(car)){
				//Try to assign car to request
				if(car.assignCar(redirectedRequest)){
					//If assigned successful, break out of while-loop and handle next request
					redirectedRequest.setRedirected(true);
					redirectedRequest.setCarID(car.getCarId());
					requestIsBeingProcessed = false;
					redirectedRequests.add(redirectedRequest);
				}
			}

			//Try next possible car
			i++;
		}

		return !requestIsBeingProcessed;
	}
	
	
	public void calculateCost() {
		int result=0;

		//calculate costs
		for(Request request : requestList){
			if (!request.isAssigned() && !request.isRedirected()){
				result += request.getPenalty1();
            }
            else if(request.isRedirected()){
				result += request.getPenalty2();
			}
        }

		//set latest cost
		latestCost=result;
		
		//set changed
		changed=false;
	}

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\nZone [zoneId=" + zoneId + "\n, carList=" + carList + "\n, requestList=" + requestList  +/* ", changed=" + changed + */"\n, latestCost=" + latestCost + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */

	public String getZoneId() {
		return zoneId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Zone zone = (Zone) o;

		return zoneId.equals(zone.zoneId);
	}
	
	
	

	@Override
	public int hashCode() {
		return zoneId.hashCode();
	}

	public void addAdjacentZone(Zone zone){
		adjacentZones.add(zone);
	}

	public void addCar(Car car){
		carList.add(car);
	}

	public void removeCar(Car car){
		carList.remove(car);
	}
}
