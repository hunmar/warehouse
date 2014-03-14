package com.miet.ipovs.mp45.myalin.lab1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by maxim on 2/27/14.
 */
public class Warehouse {

    static Warehouse instance;

    private final int MAX_TRAINS = 10;
    private final int MAX_TRUCKS = 10;

    private Map<StuffType, ArrayBlockingQueue<Stuff>> storages = new HashMap<StuffType, ArrayBlockingQueue<Stuff>>();
    private Map<StuffType, Integer> MAX_FILLING = new HashMap<StuffType, Integer>();

    //Stuff section
    private StuffManager stuffManager = new StuffManager();

    //Trucks section
    private TruckManager truckManager = new TruckManager();
    private Truck currentTruck = null;
    boolean readyToLoadFromTruck = false;
    ArrayBlockingQueue<Truck> truckQueue = new ArrayBlockingQueue<Truck>(MAX_TRUCKS);

    //Trains section
    private TrainManager trainManager = new TrainManager();
    Train currentTrain = null;
    boolean readyToUnloadToTrain = false;
    ArrayBlockingQueue<Train> trainQueue = new ArrayBlockingQueue<Train>(MAX_TRAINS);

    private Warehouse()
    {
        // Забиваем размеры склада
        for (StuffType stuffType : StuffType.values()) {
            MAX_FILLING.put(stuffType, 3500);
            storages.put(stuffType, new ArrayBlockingQueue<Stuff>(MAX_FILLING.get(stuffType)));
        }

        // запускаем потоки погрузки и разгрузки
        truckManager.start();
        trainManager.start();
        stuffManager.start();
    }

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }

    public ArrayBlockingQueue<Stuff> getStorage(StuffType stuffType) {
        return storages.get(stuffType);
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public ArrayBlockingQueue<Truck> getTruckQueue() {
        return truckQueue;
    }

    public Train getCurrentTrain() {
        return currentTrain;
    }

    public ArrayBlockingQueue<Train> getTrainQueue() {
        return trainQueue;
    }

    public void setCurrentTruck(Truck _currentTruck) {
        currentTruck = _currentTruck;
    }

    public void setCurrentTrain(Train _currentTrain) {
        currentTrain = _currentTrain;
    }

    public boolean isReadyToLoadFromTruck() {
        return readyToLoadFromTruck;
    }

    public void setReadyToLoadFromTruck(boolean _readyToLoadFromTruck) {
        readyToLoadFromTruck = _readyToLoadFromTruck;
    }

    public boolean isReadyToUnloadToTrain() {
        return readyToUnloadToTrain;
    }

    public void setReadyToUnloadToTrain(boolean _readyToUnloadToTrain) {
        readyToUnloadToTrain = _readyToUnloadToTrain;
    }

    public StuffManager getStuffManager() {
        return stuffManager;
    }

    public boolean isFullyLoaded(StuffType stuffType) {
        return storages.get(stuffType).size() >= MAX_FILLING.get(stuffType) ? true : false;
    }

    public void addTrain(int wagonsNumber) {
        if (trainQueue.size() < MAX_TRAINS) {
            final Train train = new Train(wagonsNumber);
            try {
                trainQueue.put(train);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Депо не резиновое!");
        }
    }

    public void addTruck(StuffType stuffType) {
        if (truckQueue.size() < MAX_TRUCKS) {
            final Truck truck = new Truck(stuffType);
            try {
                System.out.println("В очредь на разгрузку добавлен грузовик " + truck.type);
                truckQueue.put(truck);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("На парковке нет места");
        }
    }
}
