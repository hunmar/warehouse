/**
 * Created by maxim on 3/12/14.
 */
public class Wagon {

    final static int MAX_FILLING=1000;
    StuffType type;
    int currentLoad=0;

    public Wagon(StuffType random) {
        this.type=random;
    }

    public void loadStuff()
    {
        currentLoad++;
    }

    public boolean isLoaded()
    {
        return currentLoad >= MAX_FILLING ? true : false;
    }
}
