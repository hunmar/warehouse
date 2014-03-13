import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by maxim on 3/12/14.
 */
public class WarehouseSimulator extends JFrame implements ActionListener {
    private JProgressBar progressBarWET;
    private JProgressBar progressBarDRY;
    private JProgressBar progressBarPERISH;
    private JPanel panel1;
    private JButton trainButton;
    private JButton truckWETButton;
    private JButton truckDRYButton;
    private JButton truckPERISHButton;

    public static void main(String[] args) {
        System.out.println("Fuck you");
        WarehouseSimulator whs = new WarehouseSimulator();
    }

    WarehouseSimulator() {
        trainButton.addActionListener(this);
        truckWETButton.addActionListener(this);
        truckDRYButton.addActionListener(this);
        truckPERISHButton.addActionListener(this);

        Warehouse.getInstance();

        setContentPane(panel1);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    updateCounts();
                    Thread.yield();
                    // Проверяем состояние раз в полсекунды
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();
    }

    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        if (src == trainButton) {
            Warehouse.addTrain(4);
        }
        if (src == truckWETButton) {
            Warehouse.addTruck(StuffType.WET);
        }
        if (src == truckDRYButton) {
            Warehouse.addTruck(StuffType.DRY);
        }
        if (src == truckPERISHButton) {
            Warehouse.addTruck(StuffType.PERISH);
        }
    }

    public void updateCounts()
    {
        progressBarWET.setValue((int)(Warehouse.getStorage(StuffType.WET).size()/3500.0*100.0));
        progressBarDRY.setValue((int)(Warehouse.getStorage(StuffType.DRY).size()/3500.0*100.0));
        progressBarPERISH.setValue((int)(Warehouse.getStorage(StuffType.PERISH).size()/3500.0*100.0));
    }
}
