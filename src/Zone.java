import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Zone {

    private int zoneId;
    private List<Car> carList;
    private List<Request> requestList;
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

	public Zone(int zoneId) {
        this.zoneId = zoneId;

        carList = new ArrayList<>();
        requestList = new ArrayList<>();
        adjacentZones = new ArrayList<>();
        changed=false;

    }
	
	//deep copy
	public Zone(Zone z) {
        this.zoneId = z.zoneId;

        carList = new ArrayList<>(z.getCarList());
        //Refresh each car usage
		for(Car car : carList) car.setTimeUsed(new IntervalTree());

        requestList = new ArrayList<>(z.getRequestList());
        adjacentZones = new ArrayList<>(z.getAdjacentZones());
        latestCost=z.getLatestCost();
        this.changed=z.changed;
    }

    public int getZoneId() {
        return zoneId;
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
						request.setCarID(car.getCarId());
						requestIsBeingProcessed = false;
					}
				}

				//Try next possible car
				i++;
			}

			//If no car found, request is still being processed
			if(requestIsBeingProcessed){
				//Push request to each neighbour => return boolean if handled
				for(Zone adjacentZone : adjacentZones){
					//If adjacent zone can handle the request, end loop
					if(adjacentZone.handleRedirectedRequest(request)){
						adjacentZone.setChanged(true);
						request.setRedirected(true);
						requestIsBeingProcessed = false;
						break;
					}
				}
			}

			//Still no luck, request not assigned
			if(requestIsBeingProcessed){
				request.setAssigned(false);
			}




			//If (not) handled, set redirected
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
					redirectedRequest.setAssigned(true);
					redirectedRequest.setCarID(car.getCarId());
					requestIsBeingProcessed = false;
				}
			}

			//Try next possible car
			i++;
		}

		return !requestIsBeingProcessed;
	}
	
	
	public void calculateCost() {
		//TODO implement
		int result=0;

		//use algorithm to place cars
		
		
		
		//calculate costs
		for(Request request : requestList){
		    if(request.isRedirected()){
		        result += request.getPenalty2();
            }
            else if(!request.isAssigned()){
		        result += request.getPenalty1();
            }
        }
		
		
		//set latest cost
		latestCost=result;
		
		//set changed
		changed=false;
	}

    @Override
    public String toString() {
        return "Zone{" +
                "zoneId=" + zoneId +
                '}';
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + zoneId;
		return result;
	}
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zone other = (Zone) obj;
		if (zoneId != other.zoneId)
			return false;
		return true;
	}
}
