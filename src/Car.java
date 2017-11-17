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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + carId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (carId != other.carId)
			return false;
		return true;
	}

	
    
}
