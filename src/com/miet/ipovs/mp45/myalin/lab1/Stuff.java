package com.miet.ipovs.mp45.myalin.lab1;

/**
 * Created by maxim on 3/12/14.
 */
public class Stuff {
    private static int number = 0;
    private int weight = 1;

    public Stuff()
    {
        number++;
    }

    public int getNumber()
    {
        return number;
    }

    public int getWeight() {
        return weight;
    }
}
