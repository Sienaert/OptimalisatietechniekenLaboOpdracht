import java.util.List;

public class Request implements Comparable{
    private int requestId;
    private Zone zone;
    private int dayIndex, startTime, duration;
    private List<Car> possibleVehicleTypes;
    private int penalty1, penalty2;
    private boolean redirected;
    private boolean assigned;

    public Request(int requestId, int dayIndex, int startTime, int duration, int penalty1, int penalty2) {
        this.requestId = requestId;
        this.dayIndex = dayIndex;
        this.startTime = startTime;
        this.duration = duration;
        this.penalty1 = penalty1;
        this.penalty2 = penalty2;
        this.redirected = false;
        this.assigned=false;
    }

    public boolean isRedirected() {
        return redirected;
    }

    public void setRedirected(boolean redirected) {
        this.redirected = redirected;
    }

    public int getRequestId() {
        return requestId;

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

    public List<Car> getPossibleVehicleTypes() {
        return possibleVehicleTypes;
    }

    public int getPenalty1() {
        return penalty1;
    }

    public int getPenalty2() {
        return penalty2;
    }
    

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
	
	

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setPossibleVehicleTypes(List<Car> possibleVehicleTypes) {
        this.possibleVehicleTypes = possibleVehicleTypes;
    }

	@Override
	public String toString() {
		return "Request [requestId=" + requestId + ", zone=" + zone + ", dayIndex=" + dayIndex + ", startTime="
				+ startTime + ", duration=" + duration + ", possibleVehicleTypes=" + possibleVehicleTypes
				+ ", penalty1=" + penalty1 + ", penalty2=" + penalty2 + ", redirected=" + redirected + ", assigned="
				+ assigned + "]";
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		int result;
		if(assigned) {
			return -1;
		}
		return 1;
	}


}
