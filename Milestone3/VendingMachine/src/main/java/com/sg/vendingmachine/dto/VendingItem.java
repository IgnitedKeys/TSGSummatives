package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author drees
 */
public class VendingItem {

    private int itemId;
    private String itemName;
    private BigDecimal itemCost;
    private int inventoryCount;

    public VendingItem(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @Override 
    public String toString(){
        return "VendingItem{itemId=" + itemId + ", itemName=" + itemName +", itemCost=" + itemCost + ", inventoryCount=" + inventoryCount + "}";
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.itemId;
        hash = 89 * hash + Objects.hashCode(this.itemName);
        hash = 89 * hash + Objects.hashCode(this.itemCost);
        hash = 89 * hash + this.inventoryCount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VendingItem other = (VendingItem) obj;
        if (this.itemId != other.itemId) {
            return false;
        }
        if (this.inventoryCount != other.inventoryCount) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.itemCost, other.itemCost)) {
            return false;
        }
        return true;
    }
    
}
