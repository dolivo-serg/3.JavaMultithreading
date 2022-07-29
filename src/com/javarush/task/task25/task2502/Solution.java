package com.javarush.task.task25.task2502;

import java.util.ArrayList;
import java.util.List;

/* 
Машину на СТО не повезем!
*/

public class Solution {
    public static enum Wheel {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    }

    public static class Car {
        protected List<Wheel> wheels;

        public Car() throws IllegalArgumentException {
            List<Wheel> list = new ArrayList<>();
            String[] wheelNames = loadWheelNamesFromDB();
            for (int i = 0; i < wheelNames.length; i++){
                list.add(Wheel.valueOf(wheelNames[i]));
            }
            if(list.size() != 4) throw new IllegalArgumentException();
            this.wheels = list;
        }

        protected String[] loadWheelNamesFromDB() {
            //this method returns mock data
            return new String[]{"FRONT_LEFT", "FRONT_RIGHT", "BACK_LEFT", "BACK_RIGHT"};
        }
    }

    public static void main(String[] args) {
    }
}
