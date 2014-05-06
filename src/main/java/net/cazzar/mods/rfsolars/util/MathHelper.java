package net.cazzar.mods.rfsolars.util;

public class MathHelper {
    public static double clamp(double value, double max, double min) {
        return value > max ? max : value < min ? min : value;
    }
}
