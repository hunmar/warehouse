import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by maxim on 3/12/14.
 */
public class WarehouseSimulator extends JFrame implements ActionListener {
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JProgressBar progressBar3;
    private JPanel panel1;
    private JButton trainButton;
    private JButton truckButton;

    public static void main(String[] args) {
        System.out.println("Fuck you");
        WarehouseSimulator whs = new WarehouseSimulator();
    }

    WarehouseSimulator() {
        trainButton.addActionListener(this);
        truckButton.addActionListener(this);

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
        if (src == truckButton) {
            Warehouse.addTruck();
        }
    }

    public void updateCounts()
    {
        progressBar1.setValue((int)(Warehouse.getStorage(StuffType.DRY).size()/3500.0*100.0));
        progressBar2.setValue((int)(Warehouse.getStorage(StuffType.WET).size()/3500.0*100.0));
        progressBar3.setValue((int)(Warehouse.getStorage(StuffType.PERISH).size()/3500.0*100.0));
    }
}
