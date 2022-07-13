
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.VendingItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author drees
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao{

    public VendingItem onlyItem;
    
    public VendingMachineDaoStubImpl() {
        onlyItem = new VendingItem(11);
        onlyItem.setItemName("Almond Joy");
        onlyItem.setItemCost(new BigDecimal("2.66"));
        onlyItem.setInventoryCount(1);
    }
    
    public VendingMachineDaoStubImpl(VendingItem testItem){
        this.onlyItem = testItem;
    }
    @Override
    public VendingItem createItem(int itemId, VendingItem item) {
        if(itemId == onlyItem.getItemId()) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public VendingItem updateInventory(int itemId, VendingItem item) throws VendingMachinePersistenceException {
        if(item.getItemId() == onlyItem.getItemId()){
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public VendingItem getItemById(int itemId) throws VendingMachinePersistenceException {
        if(itemId == onlyItem.getItemId()){
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<VendingItem> getAllItems() throws VendingMachinePersistenceException {
       List<VendingItem> itemList = new ArrayList<>();
       itemList.add(onlyItem);
       return itemList;
    }
    
}
