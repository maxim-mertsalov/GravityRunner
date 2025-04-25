package me.xmertsalov.utils;

import java.util.ArrayList;

public class ListUtils {
    public static <T> T getOrDefault(ArrayList<T> list, int index, T defaultValue) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return defaultValue;
    }
}
