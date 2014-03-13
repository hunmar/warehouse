import java.util.Random;

/**
 * Created by maxim on 3/13/14.
 */
public enum StuffType {

    DRY, WET, PERISH;

    public static StuffType random() {
        Random random = new Random();
        int i = random.nextInt(3);
        if (i == 0) {
            return StuffType.DRY;
        } else if (i == 1) {
            return StuffType.WET;
        } else {
            return StuffType.PERISH;
        }
    }
}
