import java.util.ArrayList;
import java.util.Random;

/**
 * Created by maxim on 3/13/14.
 */
public class TruckManager extends Thread {
    public void run() {
        try {
            while (!isInterrupted()) {

                if (Warehouse.getCurrentTruck() == null || Warehouse.getCurrentTruck().isEmpty()) {
                    if (!Warehouse.getTruckQueue().isEmpty()) {
                        Warehouse.setReadyToLoadFromTruck(false);
                        getNextTruck();
                    } else {
                        if (Warehouse.getCurrentTruck() != null) {
                            //TODO: Тут грузовик должен как-то отъехать
                            Truck currentTruck = Warehouse.getCurrentTruck();
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
        System.out.println("Разгрузка товаров окончена");
    }

    private void getNextTruck() {
        if (Warehouse.getCurrentTruck() != null) {
            //TODO: Тут грузовик должен как-то отъехать
            Truck currentTruck = Warehouse.getCurrentTruck();
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
            Truck currentTruck = Warehouse.getTruckQueue().take();
            Warehouse.setCurrentTruck(currentTruck);
            Thread t = WarehouseSimulator.instance.arriveTruckTask(currentTruck);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Warehouse.setReadyToLoadFromTruck(true);
            placeTruckIntoStuffManagerQueue(Warehouse.getCurrentTruck());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void placeTruckIntoStuffManagerQueue(Truck truck) {
        Warehouse.getStuffManager().addWork(truck);
    }
}
