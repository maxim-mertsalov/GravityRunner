package me.xmertsalov.utils;

public class Agragation {

    public Agragation() {}

    public static <T extends Number> T getRandomNumber(T min, T max) {
        double minVal = min.doubleValue();
        double maxVal = max.doubleValue();
        double result = Math.random() * (maxVal - minVal) + minVal;

        return switch (min) {
            case Integer i -> (T) Integer.valueOf((int) result);
            case Double v -> (T) Double.valueOf(result);
            case Float v -> (T) Float.valueOf((float) result);
            case Long l -> (T) Long.valueOf((long) result);
            case Short i -> (T) Short.valueOf((short) result);
            case Byte b -> (T) Byte.valueOf((byte) result);
            default -> throw new IllegalArgumentException("Unsupported numeric type: " + min.getClass());
        };
    }
}
