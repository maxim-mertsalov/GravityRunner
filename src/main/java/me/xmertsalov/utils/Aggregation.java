package me.xmertsalov.utils;

/**
 * Utility class for performing aggregation-related operations.
 * This class provides a method to generate a random number within a specified range.
 */
public class Aggregation {

    /**
     * Default constructor for the Aggregation class.
     */
    public Aggregation() {}

    /**
     * Generates a random number within the specified range [min, max].
     * The type of the returned number matches the type of the input parameters.
     *
     * @param <T>  The numeric type of the input and output (e.g., Integer, Double, Float, etc.).
     * @param min  The minimum value of the range (inclusive).
     * @param max  The maximum value of the range (inclusive).
     * @return A random number of type T within the range [min, max].
     * @throws IllegalArgumentException If the numeric type is unsupported.
     */
    public static <T extends Number> T getRandomNumber(T min, T max) {
        double minVal = min.doubleValue();
        double maxVal = max.doubleValue();
        double result = Math.random() * (maxVal - minVal) + minVal;

        return switch (min) {
            case Integer i -> (T) Integer.valueOf((int) result);
            case Double d -> (T) Double.valueOf(result);
            case Float f -> (T) Float.valueOf((float) result);
            case Long l -> (T) Long.valueOf((long) result);
            case Short s -> (T) Short.valueOf((short) result);
            case Byte b -> (T) Byte.valueOf((byte) result);
            default -> throw new IllegalArgumentException("Unsupported numeric type: " + min.getClass());
        };
    }
}
