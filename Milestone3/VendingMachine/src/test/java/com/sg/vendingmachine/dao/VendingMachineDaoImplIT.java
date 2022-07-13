/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gavinlinnihan
 */
public class VendingMachineDaoImplIT {

    VendingMachineDao testDao;

    public VendingMachineDaoImplIT() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testVending.txt";

        new FileWriter(testFile);
        testDao = new VendingMachineDaoImpl(testFile);
    }

    @Test
    public void testCreateGetItem() throws VendingMachinePersistenceException {

        int itemId = 111;
        VendingItem item = new VendingItem(itemId);
        item.setItemName("Flaming Hot Cheetos");
        item.setItemCost(new BigDecimal("2.66"));
        item.setInventoryCount(3);

        testDao.createItem(itemId, item);

        VendingItem retrievedItem = testDao.getItemById(itemId);

        assertEquals(item.getItemId(), retrievedItem.getItemId(), "Checking itemId");
        assertEquals(item.getItemCost(), retrievedItem.getItemCost(), "Checking itemCost");
    }

    @Test
    public void testUpdateInventory() throws VendingMachinePersistenceException {
        
        int itemId = 111;
        VendingItem item1 = new VendingItem(itemId);
        item1.setItemName("Flaming Hot Cheetos");
        item1.setItemCost(new BigDecimal("2.66"));
        item1.setInventoryCount(3);

        testDao.createItem(item1.getItemId(), item1);
        VendingItem retrievedItem = testDao.getItemById(itemId);

        assertEquals(3, retrievedItem.getInventoryCount(), "3 items should be in the machine");

        item1.setInventoryCount(2);

        testDao.updateInventory(item1.getItemId(), item1);
        retrievedItem = testDao.getItemById(itemId);

        assertEquals(2, retrievedItem.getInventoryCount(), "There should now be 2 items in the machine");
    }

    @Test
    public void testGetAllItemsList() throws VendingMachinePersistenceException {

        VendingItem item1 = new VendingItem(111);
        item1.setItemName("Flaming Hot Cheetos");
        item1.setItemCost(new BigDecimal("2.66"));
        item1.setInventoryCount(3);

        VendingItem item2 = new VendingItem(222);
        item2.setItemName("Animal Crackers");
        item2.setItemCost(new BigDecimal("2.74"));
        item2.setInventoryCount(1);

        testDao.createItem(item1.getItemId(), item1);
        testDao.createItem(item2.getItemId(), item2);

        List<VendingItem> allItems = testDao.getAllItems();

        assertNotNull(allItems, "The list of items is not null");
        assertEquals(2, allItems.size(), "The list should have 2 items in it");
        assertTrue(testDao.getAllItems().contains(item1), "Flaming Hot Cheetos should be in the list");
        assertTrue(testDao.getAllItems().contains(item2), "Animal Crackers should be in the list");
    }

}
