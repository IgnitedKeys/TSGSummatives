package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrees
 */
public class OrderDaoStubImpl implements OrderDao {

    public Order onlyOrder;

    public OrderDaoStubImpl() {
        onlyOrder = new Order(1);
        onlyOrder.setOrderDate(LocalDate.parse("2022-03-14"));
        onlyOrder.setCustomerName("Ada Lovelace");
        onlyOrder.setArea(new BigDecimal("249"));
        //set Tax
        
        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        onlyOrder.setTaxInfo(tax);
        onlyOrder.setProductInfo(product);
        
    }

    public OrderDaoStubImpl(Order testOrder) {
        this.onlyOrder = testOrder;
    }

    @Override
    public Order createOrder(Order order) throws FilePersistenceException {
        if (order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate orderDate) throws FilePersistenceException {
        List<Order> orderList = new ArrayList<>();
        if (orderDate.equals(onlyOrder.getOrderDate())) {
            orderList.add(onlyOrder);
            return orderList;
        } else {
            return null;
        }
    }

    @Override
    public Order getOrderByNumber(LocalDate orderDate, int orderNumber) throws FilePersistenceException {
        if (orderDate.equals(onlyOrder.getOrderDate()) && orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() throws FilePersistenceException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public Order deleteOrder(LocalDate orderDate, int orderNum) throws FilePersistenceException {
        if (orderDate.equals(onlyOrder.getOrderDate()) && orderNum == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order updateOrder(Order order) throws FilePersistenceException {
        if (order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            return order;
        } else {
            return null;
        }
    }

}
