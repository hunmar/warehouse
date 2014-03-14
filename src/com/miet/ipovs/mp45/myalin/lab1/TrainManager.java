package com.miet.ipovs.mp45.myalin.lab1;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by maxim on 3/13/14.
 */
public class TrainManager extends Thread {
    public void run()
    {
        try {
            while (!isInterrupted()) {

                if (Warehouse.getInstance().getCurrentTrain() == null || Warehouse.getInstance().getCurrentTrain().isFullyLoaded())
                {
                    if (Warehouse.getInstance().getCurrentTrain() != null)
                    {
                        System.out.println("Погрузка в поезд завершена");
                        //TODO: Тут поезд должен как-то отъехать
                        Train currentTrain = Warehouse.getInstance().getCurrentTrain();
                        Warehouse.getInstance().setCurrentTrain(currentTrain);
                        Thread t = WarehouseSimulator.instance.depatureTrainTask(currentTrain);
                        t.start();
                        try {
                            t.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Warehouse.getInstance().setReadyToUnloadToTrain(false);
                    getNextTrain();
                }

                Thread.sleep(new Random().nextInt(700) + 200);
            }
        } catch (InterruptedException e) {

        }

    }

    private void getNextTrain()
    {
        if (Warehouse.getInstance().getCurrentTrain() != null)
        {
            //TODO: Тут поезд должен как-то отъехать
            Train currentTrain = Warehouse.getInstance().getCurrentTrain();
            Warehouse.getInstance().setCurrentTrain(currentTrain);
            Thread t = WarehouseSimulator.instance.depatureTrainTask(currentTrain);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            //TODO: Тут поезд должен как-то подъехать
            Train currentTrain = Warehouse.getInstance().getTrainQueue().take();
            Warehouse.getInstance().setCurrentTrain(currentTrain);
            Thread t = WarehouseSimulator.instance.arriveTrainTask(currentTrain);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Warehouse.getInstance().setReadyToUnloadToTrain(true);
            placeWagonsIntoStuffManagerQueue(Warehouse.getInstance().getCurrentTrain().getWagons());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void placeWagonsIntoStuffManagerQueue(ArrayList<Wagon> wagons) {
        for (Wagon wagon : wagons)
        {
            Warehouse.getInstance().getStuffManager().addWork(wagon);
        }
    }


}
