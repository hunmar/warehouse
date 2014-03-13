package com.miet.ipovs.mp45.myalin.lab1;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by maxim on 3/14/14.
 */
public class WarehouseTest extends TestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        Warehouse.getInstance();
    }

    @Test
    public void testIsReadyToLoadFromTruck() throws Exception {
        assertEquals("Изначально нельзя", false, Warehouse.isReadyToLoadFromTruck());
        Warehouse.setReadyToLoadFromTruck(true);
        assertEquals("Теперь можно", true, Warehouse.isReadyToLoadFromTruck());
    }

    @Test
    public void testIsReadyToUnloadToTrain() throws Exception {
        assertEquals("Изначально нельзя", false, Warehouse.isReadyToUnloadToTrain());
        Warehouse.setReadyToUnloadToTrain(true);
        assertEquals("Теперь можно", true, Warehouse.isReadyToUnloadToTrain());
    }

    @Test
    public void testIsFullyLoaded() throws Exception {
        StuffType stuffType = StuffType.random();
        assertEquals("Изначально склад пустой", false, Warehouse.isFullyLoaded(stuffType));
        for (int i =0; i < 3500; i++)
            Warehouse.storages.get(stuffType).put(new Stuff());
        assertEquals("А теперь склад должен быть заполнен", true, Warehouse.isFullyLoaded(stuffType));
    }

    @Test
    public void testAddTruck() throws Exception {
        Warehouse.addTruck(StuffType.random());

        assertEquals("В очереди должен оказаться один грузовик", 1, Warehouse.getTruckQueue().size());
    }

    @Test
    public void testAddTrain() throws Exception {
        Warehouse.addTrain(6);

        assertEquals("В очереди должен оказаться один поезд", 1, Warehouse.getTrainQueue().size());
    }
}
