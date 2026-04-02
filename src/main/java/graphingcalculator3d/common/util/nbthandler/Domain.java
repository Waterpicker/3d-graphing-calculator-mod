package graphingcalculator3d.common.util.nbthandler;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Domain {
    private final double min;
    private final double max;
    private final double length;

    public Domain(double min, double max) {
        this.min = min;
        this.max = max;
        this.length = max - min;
    }

    public static boolean parse(String text, Consumer<Domain> consumer) throws NumberFormatException {
        String[] array = text.split(",");

        if(array.length != 2) return false;


        try {
            consumer.accept(new Domain(Double.parseDouble(array[0]), Double.parseDouble(array[1])));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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