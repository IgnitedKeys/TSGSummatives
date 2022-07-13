package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import java.util.List;

/**
 *
 * @author drees
 */
public interface VendingMachineDao {

    /**
     * Creates a VendingItem to the Machine(for UnitTesting)
     * @param itemId of VendingItem
     * @param item contains information on VendingItem(name, cost,inventoryCount)
     * @return VendingItem associated with itemId
     */
    public VendingItem createItem(int itemId, VendingItem item);

    /**
     * To update inventoryCount of VendingItem
     * @param itemId of VendingItem
     * @param item 
     * @return updated VendingItem associated with itemId
     */
    public VendingItem updateInventory(int itemId, VendingItem item) throws VendingMachinePersistenceException;

    /**
     * Returns VendingItem info from given itemId
     * @param itemId
     * @return VendingItem associated with itemId
     */
    public VendingItem getItemById(int itemId) throws VendingMachinePersistenceException;

    /**
     * Returns a list of all VendingItems in the machine.
     * @return List of VendingItems
     */
    public List<VendingItem> getAllItems() throws VendingMachinePersistenceException;
}
