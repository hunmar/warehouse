package com.miet.ipovs.mp45.myalin.lab1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by maxim on 2/27/14.
 */
public class Warehouse {

    static Warehouse instance;

    private static final int MAX_TRAINS = 10;
    private static final int MAX_TRUCKS = 10;

    static Map<StuffType, ArrayBlockingQueue<Stuff>> storages = new HashMap<StuffType, ArrayBlockingQueue<Stuff>>();
    static Map<StuffType, Integer> MAX_FILLING = new HashMap<StuffType, Integer>();

    //Stuff section
    private static StuffManager stuffManager = new StuffManager();

    //Trucks section
    private static TruckManager truckManager = new TruckManager();
    static Truck currentTruck = null;
    static boolean readyToLoadFromTruck = false;
    static ArrayBlockingQueue<Truck> truckQueue = new ArrayBlockingQueue<Truck>(MAX_TRUCKS);

    //Trains section
    private static TrainManager trainManager = new TrainManager();
    static Train currentTrain = null;
    static boolean readyToUnloadToTrain = false;
    static ArrayBlockingQueue<Train> trainQueue = new ArrayBlockingQueue<Train>(MAX_TRAINS);

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();

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
        return instance;
    }

    public static ArrayBlockingQueue<Stuff> getStorage(StuffType stuffType) {
        return storages.get(stuffType);
    }

    public static Truck getCurrentTruck() {
        return currentTruck;
    }

    public static ArrayBlockingQueue<Truck> getTruckQueue() {
        return truckQueue;
    }

    public static Train getCurrentTrain() {
        return currentTrain;
    }

    public static ArrayBlockingQueue<Train> getTrainQueue() {
        return trainQueue;
    }

    public static void setCurrentTruck(Truck currentTruck) {
        Warehouse.currentTruck = currentTruck;
    }

    public static void setCurrentTrain(Train currentTrain) {
        Warehouse.currentTrain = currentTrain;
    }

    public static boolean isReadyToLoadFromTruck() {
        return readyToLoadFromTruck;
    }

    public static void setReadyToLoadFromTruck(boolean readyToLoadFromTruck) {
        Warehouse.readyToLoadFromTruck = readyToLoadFromTruck;
    }

    public static boolean isReadyToUnloadToTrain() {
        return readyToUnloadToTrain;
    }

    public static void setReadyToUnloadToTrain(boolean readyToUnloadToTrain) {
        Warehouse.readyToUnloadToTrain = readyToUnloadToTrain;
    }

    public static StuffManager getStuffManager() {
        return stuffManager;
    }

    public static boolean isFullyLoaded(StuffType stuffType) {
        return storages.get(stuffType).size() >= MAX_FILLING.get(stuffType) ? true : false;
    }

    public static void addTrain(int wagonsNumber) {
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

    public static void addTruck(StuffType stuffType) {
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
