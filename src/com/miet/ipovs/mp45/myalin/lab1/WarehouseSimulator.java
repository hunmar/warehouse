package com.miet.ipovs.mp45.myalin.lab1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxim on 3/12/14.
 */
public class WarehouseSimulator extends JFrame implements ActionListener {
    public static WarehouseSimulator instance;
    private JProgressBar progressBarWET;
    private JProgressBar progressBarDRY;
    private JProgressBar progressBarPERISH;
    private JPanel panel1;
    private JButton trainButton;
    private JButton truckWETButton;
    private JButton truckDRYButton;
    private JButton truckPERISHButton;
    private JProgressBar progressBarWETTruck;
    private JProgressBar progressBarDRYTruck;
    private JProgressBar progressBarPERISHTruck;
    private JProgressBar progressBarWagon1;
    private JProgressBar progressBarWagon2;
    private JProgressBar progressBarWagon3;
    private JProgressBar progressBarWagon4;
    private JProgressBar progressBarWagon5;
    private JProgressBar progressBarWagon6;
    private JLabel trainsLabel;
    private JLabel trucksLabel;
    private Map<StuffType, JProgressBar> progressBars = new HashMap<StuffType, JProgressBar>();
    private ArrayList<JProgressBar> progressBarWagons = new ArrayList<JProgressBar>();

    public static void main(String[] args) {
        WarehouseSimulator whs = new WarehouseSimulator();
    }

    WarehouseSimulator() {
        instance = this;

        trainButton.addActionListener(this);
        truckWETButton.addActionListener(this);
        truckDRYButton.addActionListener(this);
        truckPERISHButton.addActionListener(this);

        Warehouse.getInstance().getInstance();

        setContentPane(panel1);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        progressBarDRYTruck.setStringPainted(true);
        progressBarDRYTruck.setString("DRY");
        progressBarDRYTruck.setVisible(false);

        progressBarWETTruck.setStringPainted(true);
        progressBarWETTruck.setString("WETT");
        progressBarWETTruck.setVisible(false);

        progressBarPERISHTruck.setStringPainted(true);
        progressBarPERISHTruck.setString("PERISH");
        progressBarPERISHTruck.setVisible(false);

        progressBarWagons.add(0,progressBarWagon1);
        progressBarWagons.add(1,progressBarWagon2);
        progressBarWagons.add(2,progressBarWagon3);
        progressBarWagons.add(3,progressBarWagon4);
        progressBarWagons.add(4,progressBarWagon5);
        progressBarWagons.add(5,progressBarWagon6);

        for (JProgressBar progressBar : progressBarWagons)
        {
            progressBar.setStringPainted(true);
            progressBar.setVisible(false);
        }

        setVisible(true);

        progressBars.put(StuffType.DRY, progressBarDRYTruck);
        progressBars.put(StuffType.WET, progressBarWETTruck);
        progressBars.put(StuffType.PERISH, progressBarPERISHTruck);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    updateCounts();
                    Thread.yield();
                    try {
                        Thread.sleep(44);
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
            Warehouse.getInstance().addTrain(6);
        }
        if (src == truckWETButton) {
            Warehouse.getInstance().addTruck(StuffType.WET);
        }
        if (src == truckDRYButton) {
            Warehouse.getInstance().addTruck(StuffType.DRY);
        }
        if (src == truckPERISHButton) {
            Warehouse.getInstance().addTruck(StuffType.PERISH);
        }
    }

    public void updateCounts() {

        trainsLabel.setText("Trains: " + Warehouse.getInstance().getTrainQueue().size());
        trucksLabel.setText("Trucks: " + Warehouse.getInstance().getTruckQueue().size());

        progressBarWET.setValue((int) (Warehouse.getInstance().getStorage(StuffType.WET).size() / 3500.0 * 100.0));
        progressBarDRY.setValue((int) (Warehouse.getInstance().getStorage(StuffType.DRY).size() / 3500.0 * 100.0));
        progressBarPERISH.setValue((int) (Warehouse.getInstance().getStorage(StuffType.PERISH).size() / 3500.0 * 100.0));

        Truck currentTruck = Warehouse.getInstance().getCurrentTruck();
        if (currentTruck != null) {
            progressBars.get(currentTruck.type).setValue((int) (currentTruck.currentLoad / 500.0 * 100.0));
        }

        Train currentTrain = Warehouse.getInstance().getCurrentTrain();
        if (currentTrain != null)
        {
            ArrayList<Wagon> wagons = currentTrain.getWagons();

            int count = 0;
            for(Wagon wagon : wagons)
            {
                progressBarWagons.get(count).setString(wagon.type.toString());
                progressBarWagons.get(count).setValue((int) (wagon.currentLoad / 1000.0 * 100.0));
                count++;
            }
        }
    }

    public Thread arriveTruckTask(final Truck truck) {
        return new Thread() {
            public void run() {
                //TODO: Анимацию бы
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progressBars.get(truck.type).setVisible(true);
            }
        };
    }

    public Thread depatureTruckTask(final Truck truck) {

        return new Thread() {
            public void run() {
                //TODO: Анимацию бы
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progressBars.get(truck.type).setVisible(false);
            }
        };
    }

    public Thread arriveTrainTask(final Train train) {
        return new Thread() {
            public void run() {
                //TODO: Анимацию бы
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<Wagon> wagons = train.getWagons();

                int count = 0;
                for(Wagon wagon : wagons)
                {
                    progressBarWagons.get(count).setVisible(true);
                    count++;
                }

            }
        };
    }

    public Thread depatureTrainTask(final Train train) {
        return new Thread() {
            public void run() {
                //TODO: Анимацию бы
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<Wagon> wagons = train.getWagons();

                int count = 0;
                for(Wagon wagon : wagons)
                {
                    progressBarWagons.get(count).setVisible(false);
                    count++;
                }

            }
        };
    }
}
