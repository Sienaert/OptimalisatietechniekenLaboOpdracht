public class Interval{
    private int low, high;

    public Interval(int low, int high){
        if (low >= high) throw new IllegalArgumentException("The end " + high + " should be greater than start " + low);
        this.low = low;
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }
}