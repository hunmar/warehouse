package com.miet.ipovs.mp45.myalin.lab1;

/**
 * Created by maxim on 3/12/14.
 */
public class Truck {

    final static int MAX_FILLING=500;
    StuffType type;
    int currentLoad=MAX_FILLING;

    public Truck(StuffType random) {

        this.type=random;
    }

    public Stuff unloadStaff()
    {
        currentLoad--;
        return new Stuff();
    }

    public boolean isEmpty()
    {
        return currentLoad == 0 ? true : false;
    }

}
