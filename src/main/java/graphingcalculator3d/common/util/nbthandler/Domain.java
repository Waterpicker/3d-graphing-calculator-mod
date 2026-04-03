package graphingcalculator3d.common.util.nbthandler;

public class Domain {
    private final double min;
    private final double max;
    private final double length;

    public Domain(double min, double max) {
        this.min = min;
        this.max = max;
        this.length = max - min;
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public double length() {
        return length;
    }
}