package com.miet.ipovs.mp45.myalin.lab1;

import java.util.Random;

/**
 * Created by maxim on 3/13/14.
 */
public class TruckManager extends Thread {
    public void run() {
        try {
            while (!isInterrupted()) {

                if (Warehouse.getInstance().getCurrentTruck() == null || Warehouse.getInstance().getCurrentTruck().isEmpty()) {
                    if (!Warehouse.getInstance().getTruckQueue().isEmpty()) {
                        Warehouse.getInstance().setReadyToLoadFromTruck(false);
                        getNextTruck();
                    } else {
                        if (Warehouse.getInstance().getCurrentTruck() != null) {
                            //TODO: Тут грузовик должен как-то отъехать
                            Truck currentTruck = Warehouse.getInstance().getCurrentTruck();
                            Thread t = WarehouseSimulator.instance.depatureTruckTask(currentTruck);
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                Thread.sleep(new Random().nextInt(700) + 200);
            }
        } catch (InterruptedException e) {

        }
    }

    private void getNextTruck() {
        if (Warehouse.getInstance().getCurrentTruck() != null) {
            //TODO: Тут грузовик должен как-то отъехать
            Truck currentTruck = Warehouse.getInstance().getCurrentTruck();
            Thread t = WarehouseSimulator.instance.depatureTruckTask(currentTruck);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            //TODO: Тут грузовик должен как-то подъехать
            Truck currentTruck = Warehouse.getInstance().getTruckQueue().take();
            Warehouse.getInstance().setCurrentTruck(currentTruck);
            Thread t = WarehouseSimulator.instance.arriveTruckTask(currentTruck);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Warehouse.getInstance().setReadyToLoadFromTruck(true);
            placeTruckIntoStuffManagerQueue(Warehouse.getInstance().getCurrentTruck());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void placeTruckIntoStuffManagerQueue(Truck truck) {
        Warehouse.getInstance().getStuffManager().addWork(truck);
    }
}
