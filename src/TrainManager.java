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
        }

        try {
            Warehouse.setCurrentTrain(Warehouse.getTrainQueue().take());
            //TODO: Тут поезд должен как-то подъехать
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
