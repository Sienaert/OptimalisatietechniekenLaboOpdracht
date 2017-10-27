import java.util.List;
import java.util.Set;

public class Zone {

    private int zoneId;
    private List<Car> carList;
    private List<Request> requestList;
    private List<Request> redirectedRequestList;
    private Set<Zone> adjacentZones;
    //TODO: make intervalTree from requests -> to determine overlapping timeframes

    public Zone(int zoneId, Set<Zone> adjacentZones) {
        this.zoneId = zoneId;
        this.adjacentZones = adjacentZones;
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

    public Set<Zone> getAdjacentZones() {
        return adjacentZones;
    }
}
