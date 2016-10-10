package com.bobby.circle.utils;

import processing.core.PConstants;

import java.awt.*;
import java.util.Random;

public class Utils {
    public static float bounds(float amt) {
        if(amt < 0) return PConstants.TWO_PI+amt;
        return amt%(PConstants.TWO_PI);
    }

    public static int getInverseColor(int rgb) {
        Color invert = new Color(rgb);
//        return generateRandomColor(invert).getRGB();
        return new Color(255 - invert.getRed(), 255 - invert.getGreen(), 255 - invert.getBlue()).getRGB();
    }

    public static Color generateRandomColor(Color mix) {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // mix the color
        if (mix != null) {
            red = (red + mix.getRed()) / 2;
            green = (green + mix.getGreen()) / 2;
            blue = (blue + mix.getBlue()) / 2;
        }

        return new Color(red, green, blue);
    }
}
