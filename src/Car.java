import java.util.ArrayList;
import java.util.List;

public class Car {

    private int carId;
    private List<Request> acceptedRequests;
    private IntervalTree timeUsed;

    public Car(int carId) {
        this.carId = carId;
        acceptedRequests = new ArrayList<>();
        timeUsed = new IntervalTree();
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

	//Currently unused
	public boolean isFree(int day, int startTime, int duration){
    	int absoluteStartTime = day * 1440 + startTime;
    	int absoluteEndTime = absoluteStartTime + duration;

    	return timeUsed.noOverlap(absoluteStartTime, absoluteEndTime);

	}

	/**
	 * Assigns car's used time for the request if it is free
	 * @param request
	 * @return True if assigned successful, false otherwise
	 */
	public boolean assignCar(Request request){
		int absoluteStartTime = request.getDayIndex()* 1440 + request.getStartTime();
		int absoluteEndTime = absoluteStartTime + request.getDuration();

		if(timeUsed.noOverlap(absoluteStartTime, absoluteEndTime)){
			timeUsed.add(new Interval(absoluteStartTime, absoluteEndTime));
			return true;
		} else
			return false;


	}


	public void setTimeUsed(IntervalTree timeUsed) {
		this.timeUsed = timeUsed;
	}
}
