package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author drees
 */
public class VendingMachineServiceImpl implements VendingMachineService {

    private VendingMachineAuditDao auditDao;
    private VendingMachineDao dao;

    public VendingMachineServiceImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<VendingItem> getAllItems() throws VendingMachinePersistenceException {
        return showInstockItems();
    }

    @Override
    public Change checkFundsAndStock(BigDecimal cashAmount, int itemId) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {

        VendingItem item = dao.getItemById(itemId);
        if(item == null) {
            throw new NoItemInventoryException("That item doesn't exist");
        }
        BigDecimal price = item.getItemCost().multiply(new BigDecimal("100"));
        int numOfQuarters = 0;
        int numOfDimes = 0;
        int numOfNickels = 0;
        int numOfPennies = 0;

        //check if item in inventory
        if (item.getInventoryCount() == 0) {
            throw new NoItemInventoryException("That item is out of stock!");
        }
        

        BigDecimal penniesOwed = cashAmount.multiply(new BigDecimal("100")).subtract(price);

        //check if enough money
        if (penniesOwed.compareTo(BigDecimal.ZERO) == -1) {
            throw new InsufficientFundsException("You need more money to buy that! Returning $" + cashAmount.toString() + " back to you!");
        }

        //write amount added to machine in auditLog
        auditDao.writeAuditEntry("$" + cashAmount + " added to machine");

        //find number of each coins
        while (penniesOwed.compareTo(new BigDecimal("0")) > 0) {
            if (penniesOwed.compareTo(new BigDecimal("24")) == 1) {
                penniesOwed = penniesOwed.subtract(new BigDecimal("25"));
                numOfQuarters++;
            } else if (penniesOwed.compareTo(new BigDecimal("9")) == 1) {
                penniesOwed = penniesOwed.subtract(new BigDecimal("10"));
                numOfDimes++;
            } else if (penniesOwed.compareTo(new BigDecimal("4")) == 1) {
                penniesOwed = penniesOwed.subtract(new BigDecimal("5"));
                numOfNickels++;
            } else {

                numOfPennies = penniesOwed.intValue();
                penniesOwed = penniesOwed.subtract(new BigDecimal(numOfPennies));
            }
        }

        Change change = new Change();
        change.setNumOfQuarters(numOfQuarters);
        change.setNumOfDimes(numOfDimes);
        change.setNumOfNickels(numOfNickels);
        change.setNumOfPennies(numOfPennies);

        //write amount returned to user in auditLog
        BigDecimal cashReturned = cashReturned(change);
        auditDao.writeAuditEntry("$" + cashReturned.toString() + " in change returned");

        //write item sold in auditLog
        updateInventory(itemId);
        auditDao.writeAuditEntry("Item: " + item.getItemName() + "-" + item.getItemId() + " sold");
        return change;

    }

    //filters out items that are out of stock
    private List<VendingItem> showInstockItems() throws VendingMachinePersistenceException {
        List<VendingItem> items = dao.getAllItems();
        List<VendingItem> itemsInStock = items.stream().filter((i) -> i.getInventoryCount() >= 1).collect(Collectors.toList());
        return itemsInStock;
    }

    private VendingItem updateInventory(int itemId) throws VendingMachinePersistenceException {

        VendingItem item = dao.getItemById(itemId);
        int itemCount = item.getInventoryCount();

        itemCount--;
        item.setInventoryCount(itemCount);
        VendingItem updatedItem = dao.updateInventory(itemId, item);

        return updatedItem;
    }

    //find total amount returned for auditLog
    private BigDecimal cashReturned(Change change) {
        BigDecimal quarters = new BigDecimal(change.getNumOfQuarters()).multiply(new BigDecimal("0.25"));
        BigDecimal dimes = new BigDecimal(change.getNumOfDimes()).multiply(new BigDecimal("0.10"));
        BigDecimal nickels = new BigDecimal(change.getNumOfNickels()).multiply(new BigDecimal("0.05"));
        BigDecimal pennies = new BigDecimal(change.getNumOfPennies()).multiply(new BigDecimal("0.01"));

        BigDecimal cashChange = quarters.add(dimes).add(nickels).add(pennies);
        return cashChange;
    }

    @Override
    public VendingItem getItemById(int itemId) throws VendingMachinePersistenceException {
        VendingItem item = dao.getItemById(itemId);
        return item;
    }

}
