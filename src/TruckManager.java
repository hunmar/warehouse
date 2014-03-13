import java.util.Random;

/**
 * Created by maxim on 3/13/14.
 */
public class TruckManager extends Thread {
    public void run()
    {
        try {
            for (int i = 0; i < 30; i++) {
                Thread.sleep(new Random().nextInt(300) + 100);
                // загружаем новый товар
                Stuff stuff = new Stuff(i+1);
                Warehouse.getStorage().put(stuff);
                System.out.println(String.format("Товар №%d загружен", stuff.getNumber()));
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Загрузка товаров окончена");
    }
}
