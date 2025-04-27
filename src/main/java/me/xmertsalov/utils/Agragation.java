package me.xmertsalov.utils;

public class Agragation {

    public Agragation() {}

    public static <T extends Number> T getRandomNumber(T min, T max) {
        double minVal = min.doubleValue();
        double maxVal = max.doubleValue();
        double result = Math.random() * (maxVal - minVal) + minVal;

        return switch (min) {
            case Integer _ -> (T) Integer.valueOf((int) result);
            case Double _ -> (T) Double.valueOf(result);
            case Float _ -> (T) Float.valueOf((float) result);
            case Long _ -> (T) Long.valueOf((long) result);
            case Short _ -> (T) Short.valueOf((short) result);
            case Byte _ -> (T) Byte.valueOf((byte) result);
            default -> throw new IllegalArgumentException("Unsupported numeric type: " + min.getClass());
        };
    }
}
