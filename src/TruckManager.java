import java.util.ArrayList;
import java.util.Random;

/**
 * Created by maxim on 3/13/14.
 */
public class TruckManager extends Thread {
    public void run()
    {
        try {
            while (!isInterrupted()) {

                if (Warehouse.getCurrentTruck() == null || Warehouse.getCurrentTruck().isEmpty())
                {
                    if (!Warehouse.getTruckQueue().isEmpty())
                    {
                        Warehouse.setReadyToLoadFromTruck(false);
                        getNextTruck();
                    }
                }

                Thread.sleep(new Random().nextInt(700) + 200);
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Разгрузка товаров окончена");
    }

    private void getNextTruck()
    {
        if (Warehouse.getCurrentTruck() != null)
        {
            //TODO: Тут грузовик должен как-то отъехать
        }

        try {
            Warehouse.setCurrentTruck(Warehouse.getTruckQueue().take());
            //TODO: Тут грузовик должен как-то подъехать
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
