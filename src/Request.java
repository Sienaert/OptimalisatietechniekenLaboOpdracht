import java.util.List;

public class Request {
    private int requestId;
    private int zoneId;
    private int dayIndex, startTime, duration;
    private List<Car.carType> possibleVehicleTypes;
    private int penalty1, penalty2;

    public Request(int requestId, int zoneId, int dayIndex, int startTime, int duration, List<Car.carType> possibleVehicleTypes, int penalty1, int penalty2) {
        this.requestId = requestId;
        this.zoneId = zoneId;
        this.dayIndex = dayIndex;
        this.startTime = startTime;
        this.duration = duration;
        this.possibleVehicleTypes = possibleVehicleTypes;
        this.penalty1 = penalty1;
        this.penalty2 = penalty2;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public List<Car.carType> getPossibleVehicleTypes() {
        return possibleVehicleTypes;
    }

    public int getPenalty1() {
        return penalty1;
    }

    public int getPenalty2() {
        return penalty2;
    }
}
