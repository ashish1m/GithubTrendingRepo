package com.example.githubtrendingrepo.util;

public class Utils {

    public static String parseColor(String color) {
        StringBuilder parsedColor = new StringBuilder();
        if (color.length() == 4 && color.charAt(0) == '#') {
            parsedColor.append("#");
            for (int i = 1; i < color.length(); i++) {
                parsedColor.append(color.charAt(i));
                parsedColor.append(color.charAt(i));
            }
        } else {
            parsedColor.append(color);
        }
        return parsedColor.toString();
    }
}
