package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author adrees
 */
public class Order {

    private int orderNumber;
    private LocalDate orderDate;
    private String customerName;
    private Tax taxInfo;
    private BigDecimal area;
    private Product productInfo;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    @Override
    public String toString() {
        return "Order {" + "orderNumber=" + orderNumber + ", orderDate=" + orderDate
                + ", customerName=" + customerName + ", StateAbb=" + taxInfo.getState() + ", taxRate=" + taxInfo.getTaxRate() + ", area=" + area + ", productType=" + productInfo.getProductType() + ", costPerSqFt="
                + productInfo.getCostPerSqFt() + ", laborCostPerSqFt=" + productInfo.getLaborCostPerSqFt() + ", materialCost=" + materialCost
                + ", laborCost=" + laborCost + ", tax=" + tax + ", total=" + total + "}";
    }

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Tax getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(Tax taxInfo) {
        this.taxInfo = taxInfo;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal Tax) {
        this.tax = Tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.orderNumber;
        hash = 17 * hash + Objects.hashCode(this.orderDate);
        hash = 17 * hash + Objects.hashCode(this.customerName);
        hash = 17 * hash + Objects.hashCode(this.taxInfo);
        hash = 17 * hash + Objects.hashCode(this.area);
        hash = 17 * hash + Objects.hashCode(this.productInfo);
        hash = 17 * hash + Objects.hashCode(this.materialCost);
        hash = 17 * hash + Objects.hashCode(this.laborCost);
        hash = 17 * hash + Objects.hashCode(this.tax);
        hash = 17 * hash + Objects.hashCode(this.total);
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
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        /*
        if (!Objects.equals(this.taxInfo, other.taxInfo)) {
            return false;
        }
*/
        //tax stateAbb/ taxRate
        if(!Objects.equals(this.taxInfo.getState(), other.taxInfo.getState())){
            return false;
        }
        if(!Objects.equals(this.taxInfo.getTaxRate(), other.taxInfo.getTaxRate())){
            return false;
        }
        
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.productInfo, other.productInfo)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }

}
