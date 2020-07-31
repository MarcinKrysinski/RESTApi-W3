package com.krysinski.restapiw3.model.parse;

import com.krysinski.restapiw3.model.Colors;

public class ColorParser {

    public static Colors convertStringToColor(String string){
        String color = string.toUpperCase();

        if (color.equals("BLACK")){
            return Colors.BLACK;
        }else if (color.equals("WHITE")){
            return Colors.WHITE;
        }else if (color.equals("RED")){
            return Colors.RED;
        }else if (color.equals("GREEN")){
            return Colors.GREEN;
        }else if (color.equals("BLUE")) {
            return Colors.BLUE;
        }else if (color.equals("YELLOW")){
            return Colors.YELLOW;
        }
        return null;
    }
}
