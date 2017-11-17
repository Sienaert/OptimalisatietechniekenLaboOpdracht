import java.util.ArrayList;
import java.util.List;

public class Car {

    private int carId;
    private CarType carType;
    private List<Request> acceptedRequests;

    public Car(int carId) {
        this.carId = carId;
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
        car0, car1, car2, car3, car4, car5;
    }

	@Override
	public String toString() {
		return "Car [carId=" + carId + ", carType=" + carType + ", acceptedRequests=" + acceptedRequests + "]";
	}
    
    
}
