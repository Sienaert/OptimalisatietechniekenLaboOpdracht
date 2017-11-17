import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Zone {

    private int zoneId;
    private List<Car> carList=new ArrayList<Car>();
    private List<Request> requestList=new ArrayList<Request>();
    private List<Request> redirectedRequestList=new ArrayList<Request>();;
    private List<Zone> adjacentZones=new ArrayList<Zone>();
    int latestCost=-1;

    //TODO: make intervalTree from requests -> to determine overlapping timeframes



    public Zone(int zoneId) {
        this.zoneId = zoneId;
       
        
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

    public List<Request> getRedirectedRequestList() {
        return redirectedRequestList;
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
	
	
	public void calculateCost() {
		//TODO implement
		int result=0;
		
		
		
		latestCost=result;
	}


}
