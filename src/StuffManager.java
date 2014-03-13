import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by maxim on 3/13/14.
 */
public class StuffManager extends Thread{

    static Map<StuffType, Thread> loadFromTruckThreads = new HashMap<StuffType, Thread>();
    static Map<StuffType, Thread> unloadToWagonThreads = new HashMap<StuffType, Thread>();

    static Map<StuffType, ArrayBlockingQueue<Wagon>> wagonQueue = new HashMap<StuffType, ArrayBlockingQueue<Wagon>>();
    static Map<StuffType, ArrayBlockingQueue<Truck>> truckQueue = new HashMap<StuffType, ArrayBlockingQueue<Truck>>();

    public void run()
    {
        try {

            for (StuffType stuffType : StuffType.values()) {
                wagonQueue.put(stuffType, new ArrayBlockingQueue<Wagon>(100));
                truckQueue.put(stuffType, new ArrayBlockingQueue<Truck>(10));

                final StuffType st = stuffType;

                Thread tl = new Thread(new Runnable() {
                    StuffType _stuffType = st;
                    @Override
                    public void run() {
                        while (true) {
                            loadFromTrucks(_stuffType);
                            Thread.yield();
                            // Проверяем состояние раз в полсекунды
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                loadFromTruckThreads.put(stuffType, tl);
                loadFromTruckThreads.get(stuffType).start();

                Thread tu = new Thread(new Runnable() {
                    StuffType _stuffType = st;
                    @Override
                    public void run() {
                        while (true) {
                            unloadToTrains(_stuffType);
                            Thread.yield();
                            // Проверяем состояние раз в полсекунды
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                unloadToWagonThreads.put(stuffType, tu);
                unloadToWagonThreads.get(stuffType).start();


            }

            while (!isInterrupted()) {
                Thread.sleep(new Random().nextInt(700) + 200);
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Разгрузка товаров окончена");
    }

    public void addWork(Wagon wagon)
    {
        try {
            System.out.println("В очредь на погрузку добавлен вагон " + wagon.type);
            wagonQueue.get(wagon.type).put(wagon);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addWork(Truck truck)
    {
        try {
            truckQueue.get(truck.type).put(truck);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void loadFromTrucks(StuffType stuffType)
    {
        try {
            Truck currentTruck = truckQueue.get(stuffType).take();
            System.out.println("Разгрузка из грузовика " + currentTruck.type);
            while(!currentTruck.isEmpty())
            {
                if (!Warehouse.isFullyLoaded(currentTruck.type))
                {
                    Warehouse.getStorage(currentTruck.type).put(currentTruck.unloadStaff());
                }
                Thread.sleep(1);
            }
            System.out.println("Разгрузка из грузовика " + currentTruck.type + " завершена");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unloadToTrains(StuffType stuffType)
    {
        try {
            Wagon currentWagon = wagonQueue.get(stuffType).take();
            System.out.println("Погрузка в вагон " + currentWagon.type);
            while(!currentWagon.isLoaded())
            {
                Stuff stuff = Warehouse.getStorage(currentWagon.type).take();
                currentWagon.loadStuff();
                Thread.sleep(1);
            }
            System.out.println("Погрузка в вагон " + currentWagon.type + " завершена");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
