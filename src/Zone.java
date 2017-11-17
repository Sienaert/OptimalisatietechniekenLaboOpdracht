import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Zone {

    private int zoneId;
    private List<Car> carList;
    private List<Request> requestList;
    private List<Request> redirectedRequestList;
    private List<Zone> adjacentZones;
    int latestCost=-1;

    //TODO: make intervalTree from requests -> to determine overlapping timeframes

    public Zone(int zoneId) {
        this.zoneId = zoneId;
        carList = new ArrayList<>();
        requestList = new ArrayList<>();
        redirectedRequestList = new ArrayList<>();
        adjacentZones = new ArrayList<>();
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

	public void setCost(int i) {
		latestCost=i;
	}

	public String getAssignmentsString() {

		StringBuilder sb=new StringBuilder("");

		for (Car c:carList) {
		sb.append(c.getCarId()+";"+zoneId);
		}
		return sb.toString();

	}

    @Override
    public String toString() {
        return "Zone{" +
                "zoneId=" + zoneId +
                '}';
    }
}
