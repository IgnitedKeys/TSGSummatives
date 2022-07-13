package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author drees
 */
public class VendingMachineServiceImplIT {

    private VendingMachineService service;

    public VendingMachineServiceImplIT() {
        //VendingMachineDao dao = new VendingMachineDaoStubImpl();
        //VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        //service = new VendingMachineServiceImpl(dao, auditDao);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("service", VendingMachineService.class);
    }

    //test getting all items in list/ getting item by id are as expected
    //test not enough money
    //test no item in inventory
    @Test
    public void testGetAllItems() throws Exception {

        VendingItem testItem = new VendingItem(11);
        testItem.setItemName("Almond Joy");
        testItem.setItemCost(new BigDecimal("2.66"));
        testItem.setInventoryCount(1);
        BigDecimal insertedCash = new BigDecimal("3");

        assertEquals(1, service.getAllItems().size(), "List should only have 1 item");
        assertTrue(service.getAllItems().contains(testItem), "The one item should be Almond Joy");

        //buy item to change inventoryCount
        service.checkFundsAndStock(insertedCash, testItem.getItemId());
        assertEquals(0, service.getAllItems().size(), "No items in list");
    }

    @Test
    public void testGetItem() throws Exception {

        VendingItem testItem = new VendingItem(11);
        testItem.setItemName("Almond Joy");
        testItem.setItemCost(new BigDecimal("2.66"));
        testItem.setInventoryCount(1);

        VendingItem shouldBeAlmondJoy = service.getItemById(11);
        assertNotNull(shouldBeAlmondJoy, "AlmondJoy should not be null");
        VendingItem shouldBeNull = service.getItemById(12);
        assertNull(shouldBeNull, "ItemId 12 should be null");

    }

    @Test
    public void testNotEnoughFunds() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {

        VendingItem testItem = new VendingItem(11);
        testItem.setItemName("Almond Joy");
        testItem.setItemCost(new BigDecimal("2.66"));
        testItem.setInventoryCount(1);

        BigDecimal insertedCash = new BigDecimal("2");
        try {
            service.checkFundsAndStock(insertedCash, testItem.getItemId());
            fail("Expected Insufficient Funds Exception to be thrown");
        } catch (NoItemInventoryException | VendingMachinePersistenceException e) {
            fail("Incorrect exception thrown");
        } catch (InsufficientFundsException e) {
            return;
        }
    }

    @Test
    public void testOutOfStockItem() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        VendingItem testItem = new VendingItem(11);
        testItem.setItemName("Almond Joy");
        testItem.setItemCost(new BigDecimal("2.66"));
        testItem.setInventoryCount(1);

        BigDecimal insertedCash = new BigDecimal("3");
        service.checkFundsAndStock(insertedCash, testItem.getItemId());

        try {
            service.checkFundsAndStock(insertedCash, testItem.getItemId());
            fail("Expected No Item Inventory Exception to be thrown");
        } catch (InsufficientFundsException | VendingMachinePersistenceException e) {
            fail("Incorrect exception thrown");
        } catch (NoItemInventoryException e) {
            return;
        }
    }

    @Test
    void testPurchaseNonexistItem() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        BigDecimal insertedCash = new BigDecimal("3");
        try {
            service.checkFundsAndStock(insertedCash, 33);
        } catch (InsufficientFundsException | VendingMachinePersistenceException e) {
            fail("Incorrect exception thrown");
        } catch (NoItemInventoryException e) {
            return;
        }

    }

    @Test
    void testReturnCorrectChange() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        VendingItem testItem = new VendingItem(11);
        testItem.setItemName("Almond Joy");
        testItem.setItemCost(new BigDecimal("2.66"));
        testItem.setInventoryCount(1);

        BigDecimal insertedCash = new BigDecimal("3");

        //returns .34
        try {
            Change change = service.checkFundsAndStock(insertedCash, testItem.getItemId());
            assertEquals(1, change.getNumOfQuarters(), "One quarter should be returned");
            assertEquals(1, change.getNumOfNickels(), "One nickel should be returned");
            assertEquals(4, change.getNumOfPennies(), "Four pennies should be returned");
            assertEquals(0, change.getNumOfDimes(), "No dimes should be returned");
        } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException e) {
            fail("Item should be bought successfully. No exception should be thrown");
        }

    }

    @Test
    void testUpdateInventoryStock() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        int itemId = 11;
        VendingItem testItem = new VendingItem(itemId);
        testItem.setItemName("Almond Joy");
        testItem.setItemCost(new BigDecimal("2.66"));
        testItem.setInventoryCount(1);
        
        

        BigDecimal insertedCash = new BigDecimal("3");
        try {
            service.checkFundsAndStock(insertedCash, testItem.getItemId());

            VendingItem updatedItem = service.getItemById(itemId);
            assertEquals(0, updatedItem.getInventoryCount(), "Updated Inventory should be 0");
            assertEquals(0, service.getAllItems().size(), "No item should show in list");
            
        } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException e) {
            fail("Item should be bought successfully. No exception should be thrown");
        }
    }
}
