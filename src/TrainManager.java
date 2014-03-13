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

                if (Warehouse.getCurrentTrain() == null || Warehouse.getCurrentTrain().isFullyLoaded())
                {
                    if (Warehouse.getCurrentTrain() != null)
                    {
                        System.out.println("Погрузка в поезд завершена");
                        //TODO: Тут поезд должен как-то отъехать
                        Train currentTrain = Warehouse.getCurrentTrain();
                        Warehouse.setCurrentTrain(currentTrain);
                        Thread t = WarehouseSimulator.instance.depatureTrainTask(currentTrain);
                        t.start();
                        try {
                            t.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Warehouse.setReadyToUnloadToTrain(false);
                    getNextTrain();
                }

                Thread.sleep(new Random().nextInt(700) + 200);
            }
        } catch (InterruptedException e) {

        }

    }

    private void getNextTrain()
    {
        if (Warehouse.getCurrentTrain() != null)
        {
            //TODO: Тут поезд должен как-то отъехать
            Train currentTrain = Warehouse.getCurrentTrain();
            Warehouse.setCurrentTrain(currentTrain);
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
            Train currentTrain = Warehouse.getTrainQueue().take();
            Warehouse.setCurrentTrain(currentTrain);
            Thread t = WarehouseSimulator.instance.arriveTrainTask(currentTrain);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Warehouse.setReadyToUnloadToTrain(true);
            placeWagonsIntoStuffManagerQueue(Warehouse.getCurrentTrain().getWagons());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void placeWagonsIntoStuffManagerQueue(ArrayList<Wagon> wagons) {
        for (Wagon wagon : wagons)
        {
            Warehouse.getStuffManager().addWork(wagon);
        }
    }


}
