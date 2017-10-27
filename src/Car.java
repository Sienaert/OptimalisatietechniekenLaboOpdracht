import java.util.ArrayList;
import java.util.List;

public class Car {

    private int carId;
    private CarType carType;
    private List<Request> acceptedRequests;

    public Car(int carId, CarType carType) {
        this.carId = carId;
        this.carType = carType;
        acceptedRequests = new ArrayList<>();
    }

    public int getCarId() {
        return carId;
    }

    public CarType getCarType() {
        return carType;
    }

    public List<Request> getAcceptedRequests() {
        return acceptedRequests;
    }

    public enum CarType{
        TYPE1;
    }
}
