package com.krysinski.restapiw3.model.parse;

import com.krysinski.restapiw3.model.Color;

public class ColorParser {

    public static Color convertStringToColor(String string){
        String color = string.toUpperCase();

        if (color.equals("BLACK")){
            return Color.BLACK;
        }else if (color.equals("WHITE")){
            return Color.WHITE;
        }else if (color.equals("RED")){
            return Color.RED;
        }else if (color.equals("GREEN")){
            return Color.GREEN;
        }else if (color.equals("BLUE")) {
            return Color.BLUE;
        }else if (color.equals("YELLOW")){
            return Color.YELLOW;
        }
        return null;
    }
}
