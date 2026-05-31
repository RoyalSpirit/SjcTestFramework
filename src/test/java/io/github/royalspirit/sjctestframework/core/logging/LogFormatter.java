package io.github.royalspirit.sjctestframework.core.logging;

import io.github.royalspirit.sjctestframework.core.GetPropertyValues;

public final class LogFormatter {

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";
    private static final String COLOR_ENABLED_PROPERTY = "logs.color.enabled";

    private LogFormatter() {
    }

    public static String red(String value) {
        return color(value, RED);
    }

    public static String green(String value) {
        return color(value, GREEN);
    }

    public static String blue(String value) {
        return color(value, BLUE);
    }

    public static String yellow(String value) {
        return color(value, YELLOW);
    }

    public static String purple(String value) {
        return color(value, PURPLE);
    }

    public static String cyan(String value) {
        return color(value, CYAN);
    }

    private static String color(String value, String color) {
        return isColorEnabled() ? color + value + RESET : value;
    }

    private static boolean isColorEnabled() {
        return GetPropertyValues.getBooleanProperty(COLOR_ENABLED_PROPERTY, true);
    }
}
