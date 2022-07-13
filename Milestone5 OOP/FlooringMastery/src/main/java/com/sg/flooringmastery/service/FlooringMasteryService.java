
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface FlooringMasteryService {
    
    public List<Order> getAllOrders(LocalDate date) throws FilePersistenceException;
    
    public Order getSingleOrder(LocalDate date, int orderNumber) throws FilePersistenceException;
    
    public List<Tax> getAllTaxes() throws FilePersistenceException;
    
    public List<Order> getAllDateOrders() throws FilePersistenceException;
    
    public List<Product> getAllProducts() throws FilePersistenceException;
    
    public Order createOrder(Order order) throws FilePersistenceException, OrderValidationException;
    
    public Order updateOrder(Order order) throws FilePersistenceException, OrderValidationException;
    
    public Order removeOrder(Order order) throws FilePersistenceException;
    
    public void makeBackupFile() throws FilePersistenceException;
    
    public Order calculateOrder(Order order) throws FilePersistenceException;
}
