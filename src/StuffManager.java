import java.util.Random;

/**
 * Created by maxim on 3/13/14.
 */
public class StuffManager extends Thread{
    public void run()
    {
        try {
            while (!isInterrupted()) {

                if (Warehouse.getCurrentTrain() == null || Warehouse.getCurrentTrain().isFullyLoaded())
                {
                    Warehouse.setReadyToUnloadToTrain(false);
                    getNextTrain();
                }

                Thread.sleep(new Random().nextInt(700) + 200);
                System.out.println(String.format("Товар №%d разгружен", stuff.getNumber()));
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Разгрузка товаров окончена");
    }

    public void addWork(Wagon wagon)
    {
        System.out.println("В очредь на погрузку добавлен вагон" + wagon.type);
    }

    public void addWork(Truck truck)
    {
        System.out.println("В очредь на разгрузку добавлен грузовик" + truck.type);
    }

    private void doSomeWork()
    {
        Stuff stuff;
        // разгружаем товар
        if (Warehouse.isLoadFinished())
            stuff = Warehouse.getStorage().poll();
        else
            stuff = Warehouse.getStorage().take();

        if (stuff == null) // разгузка закончилась и в очереди больше ничего нет
            break;
    }
}
