package me.xmertsalov.utils;

import java.util.ArrayList;

/**
 * Utility class for performing operations on lists.
 * This class provides a method to safely retrieve an element from a list with a default value.
 */
public class ListUtils {

    /**
     * Retrieves an element from the specified list at the given index.
     * If the index is out of bounds, the provided default value is returned.
     *
     * @param <T>          The type of elements in the list.
     * @param list         The list from which to retrieve the element.
     * @param index        The index of the element to retrieve.
     * @param defaultValue The default value to return if the index is out of bounds.
     * @return The element at the specified index, or the default value if the index is out of bounds.
     */
    public static <T> T getOrDefault(ArrayList<T> list, int index, T defaultValue) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return defaultValue;
    }
}
