package com.miet.ipovs.mp45.myalin.lab1;

import java.util.ArrayList;

/**
 * Created by maxim on 3/12/14.
 */
public class Train {

    ArrayList<Wagon> wagons = new ArrayList<Wagon>();

    public Train(int wagonCount) {
        for (int i = 0; i < wagonCount; i++) {
            wagons.add(new Wagon(StuffType.random()));
        }
    }

    public boolean isFullyLoaded()
    {
        for (Wagon wagon : wagons)
        {
            if (wagon.currentLoad < wagon.MAX_FILLING)
                return false;
        }
        return true;
    }

    public ArrayList<Wagon> getWagons() {
        return wagons;
    }
}
