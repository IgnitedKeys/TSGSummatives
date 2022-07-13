package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author drees
 */
public interface VendingMachineService {

    public List<VendingItem> getAllItems() throws VendingMachinePersistenceException;

    public VendingItem getItemById(int itemId) throws VendingMachinePersistenceException;

    public Change checkFundsAndStock(BigDecimal cashAmount, int itemId) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException;
}
