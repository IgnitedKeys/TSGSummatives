
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface OrderDao {
    
    public Order createOrder(Order order) throws FilePersistenceException;
    
    public List<Order> getOrdersByDate(LocalDate orderDate) throws FilePersistenceException;
    
    public Order getOrderByNumber(LocalDate orderDate,int orderNumber) throws FilePersistenceException;
    
    public List<Order> getAllOrders() throws FilePersistenceException;
    
    public Order deleteOrder(LocalDate orderDate, int orderNum) throws FilePersistenceException;
    
    public Order updateOrder(Order order) throws FilePersistenceException;
}
