package ru.javawebinar.topjava.util;

public class CounterUtil {

    private static int counter = 0;

    public static int getCounter() {
        return ++counter;
    }
}
