import java.util.List;
import java.util.Set;

public class Zone {

    private int zoneId;
    private List<Car> carList;
    private List<Request> requestList;
    private List<Request> redirectedRequestList;
    private List<Zone> adjacentZones;
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
}
