import javax.swing.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by maxim on 3/12/14.
 */
public class WarehouseSimulator extends JFrame {
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JProgressBar progressBar3;
    private JPanel panel1;

    public static void main(String[] args) {
        System.out.println("Fuck you");
        WarehouseSimulator whs = new WarehouseSimulator();
    }

    WarehouseSimulator() {

        Warehouse.getInstance();

        setContentPane(panel1);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
}
