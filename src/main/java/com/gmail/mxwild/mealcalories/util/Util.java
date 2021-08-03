package com.gmail.mxwild.mealcalories.util;

public class Util {

    private Util() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T start, T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }
}
