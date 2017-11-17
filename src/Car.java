import java.util.ArrayList;
import java.util.List;

public class Car {

    private int carId;
    private List<Request> acceptedRequests;

    public Car(int carId) {
        this.carId = carId;
        acceptedRequests = new ArrayList<>();
    }

    public int getCarId() {
        return carId;
    }

    public List<Request> getAcceptedRequests() {
        return acceptedRequests;
    }

	@Override
	public String toString() {
		return "Car [carId=" + carId + ", acceptedRequests=" + acceptedRequests + "]";
	}
    
    
}
