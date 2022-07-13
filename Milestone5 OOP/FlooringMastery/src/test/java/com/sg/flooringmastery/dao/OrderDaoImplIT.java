package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author adrees
 */
public class OrderDaoImplIT {

    OrderDao testOrderDao;

    public OrderDaoImplIT() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFolder = "Test/";
        File folder = new File(testFolder);
        File[] listOfFiles = folder.listFiles();
        for(File testFile : listOfFiles){
            new FileWriter(testFile);
        }
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testOrderDao = ctx.getBean("dao", OrderDaoImpl.class);
        //testOrderDao = new OrderDaoImpl(testFile);

    }

    @AfterEach
    public void tearDown() {
        String testFolder = "Test/";
        File folder = new File(testFolder);
        File[] listOfFiles = folder.listFiles();
        for(File testFile : listOfFiles){
            testFile.delete();
        }
    }

    @Test
    public void testCreateOrder() throws FilePersistenceException {
        //Set up test Order
        int orderNumber = 1;
        Order newOrder = new Order(orderNumber);
        LocalDate date = LocalDate.parse("2022-01-10");
        String name = "Charlie Day";
        //Set up Tax info
        String stateAbb = "CA";
        Tax tax = new Tax(stateAbb);
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //Set up Product info
        String productType = "Tile";
        Product product = new Product(productType);
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        BigDecimal area = new BigDecimal("249");
        BigDecimal materialCost = new BigDecimal("871.50");
        BigDecimal laborCost = new BigDecimal("1033.35");
        BigDecimal taxTotal = new BigDecimal("476.21");
        BigDecimal total = new BigDecimal("2381.06");

        newOrder.setCustomerName(name);
        newOrder.setOrderDate(date);
        newOrder.setOrderNumber(orderNumber);
        newOrder.setProductInfo(product);

        newOrder.setTaxInfo(tax);
        newOrder.setArea(area);
        newOrder.setLaborCost(laborCost);
        newOrder.setMaterialCost(materialCost);
        newOrder.setTax(taxTotal);
        newOrder.setTotal(total);

        testOrderDao.createOrder(newOrder);
        //test getOrderByNumber
        Order retrievedOrder = testOrderDao.getOrderByNumber(date, orderNumber);

        assertEquals(newOrder.getCustomerName(), retrievedOrder.getCustomerName(), "Checking customerName");
        assertEquals(newOrder.getOrderDate(), retrievedOrder.getOrderDate(), "Checking orderDate");
        assertEquals(newOrder.getTaxInfo().getState(), retrievedOrder.getTaxInfo().getState(), "Checking Tax object");
        assertEquals(newOrder.getMaterialCost(), retrievedOrder.getMaterialCost(), "Checking materialCost");
    }

    @Test
    public void testGetOrderByDate() throws FilePersistenceException {
        int orderNumber = 1;
        Order newOrder = new Order(orderNumber);
        LocalDate date = LocalDate.parse("2022-01-10");
        String name = "Charlie Day";
        //Set up Tax info
        String stateAbb = "CA";
        Tax tax = new Tax(stateAbb);
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //Set up Product info
        String productType = "Tile";
        Product product = new Product(productType);
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        BigDecimal area = new BigDecimal("249");
        BigDecimal materialCost = new BigDecimal("871.50");
        BigDecimal laborCost = new BigDecimal("1033.35");
        BigDecimal taxTotal = new BigDecimal("476.21");
        BigDecimal total = new BigDecimal("2381.06");

        newOrder.setCustomerName(name);
        newOrder.setOrderDate(date);
        newOrder.setOrderNumber(orderNumber);
        newOrder.setProductInfo(product);

        newOrder.setTaxInfo(tax);
        newOrder.setArea(area);
        newOrder.setLaborCost(laborCost);
        newOrder.setMaterialCost(materialCost);
        newOrder.setTax(taxTotal);
        newOrder.setTotal(total);

        int orderNumber2 = 2;
        Order order2 = new Order(orderNumber2);
        //Set up Tax info
        Tax tax2 = new Tax("TX");
        tax2.setStateName("Texas");
        tax2.setTaxRate(new BigDecimal("4.45"));
        //Set up Product info
        Product product2 = new Product("Wood");
        product2.setCostPerSqFt(new BigDecimal("5.15"));
        product2.setLaborCostPerSqFt(new BigDecimal("4.75"));

        order2.setCustomerName("Rob McElhenney");
        order2.setOrderDate(LocalDate.parse("2022-02-10"));
        order2.setOrderNumber(orderNumber2);
        order2.setProductInfo(product2);

        order2.setTaxInfo(tax2);
        order2.setArea(new BigDecimal("500"));
        order2.setLaborCost(new BigDecimal("2375.00"));
        order2.setMaterialCost(new BigDecimal("2575.00"));
        order2.setTax(new BigDecimal("228.37"));
        order2.setTotal(new BigDecimal("5178.37"));

        //Add order to Dao
        testOrderDao.createOrder(newOrder);
        testOrderDao.createOrder(order2);

        List<Order> retrievedOrders = testOrderDao.getOrdersByDate(LocalDate.parse("2022-01-10"));
        List<Order> febOrders = testOrderDao.getOrdersByDate(LocalDate.parse("2022-02-10"));

        assertEquals(1, retrievedOrders.size(), "the list should have 1 order");
        assertEquals(1, febOrders.size(), "list should have 1 order");
        assertTrue(retrievedOrders.contains(newOrder));
        assertFalse(retrievedOrders.contains(order2));
    }

    @Test
    public void testGetAllOrders() throws FilePersistenceException {
        int orderNumber1 = 1;
        Order order1 = new Order(orderNumber1);
        //Set up Tax info
        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //Set up Product info
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));

        order1.setCustomerName("Charlie Day");
        order1.setOrderDate(LocalDate.parse("2022-01-10"));
        order1.setOrderNumber(orderNumber1);
        order1.setProductInfo(product);

        order1.setTaxInfo(tax);
        order1.setArea(new BigDecimal("249"));
        order1.setLaborCost(new BigDecimal("1033.35"));
        order1.setMaterialCost(new BigDecimal("871.50"));
        order1.setTax(new BigDecimal("476.21"));
        order1.setTotal(new BigDecimal("2381.06"));

        //second order
        int orderNumber2 = 2;
        Order order2 = new Order(orderNumber2);
        //Set up Tax info
        Tax tax2 = new Tax("TX");
        tax2.setStateName("Texas");
        tax2.setTaxRate(new BigDecimal("4.45"));
        //Set up Product info
        Product product2 = new Product("Wood");
        product2.setCostPerSqFt(new BigDecimal("5.15"));
        product2.setLaborCostPerSqFt(new BigDecimal("4.75"));

        order2.setCustomerName("Rob McElhenney");
        order2.setOrderDate(LocalDate.parse("2022-02-10"));
        order2.setOrderNumber(orderNumber2);
        order2.setProductInfo(product2);

        order2.setTaxInfo(tax2);
        order2.setArea(new BigDecimal("500"));
        order2.setLaborCost(new BigDecimal("2375.00"));
        order2.setMaterialCost(new BigDecimal("2575.00"));
        order2.setTax(new BigDecimal("228.37"));
        order2.setTotal(new BigDecimal("5178.37"));
        
        int orderNumber3 = 3;
        Order order3 = new Order(orderNumber3);
        //Set up Tax info
        Tax tax3 = new Tax("TX");
        tax3.setStateName("Texas");
        tax3.setTaxRate(new BigDecimal("4.45"));
        //Set up Product info
        Product product3 = new Product("Wood");
        product3.setCostPerSqFt(new BigDecimal("5.15"));
        product3.setLaborCostPerSqFt(new BigDecimal("4.75"));

        order3.setCustomerName("Glenn Howerton");
        order3.setOrderDate(LocalDate.parse("2022-02-10"));
        order3.setOrderNumber(orderNumber3);
        order3.setProductInfo(product3);

        order3.setTaxInfo(tax3);
        order3.setArea(new BigDecimal("500"));
        order3.setLaborCost(new BigDecimal("2375.00"));
        order3.setMaterialCost(new BigDecimal("2575.00"));
        order3.setTax(new BigDecimal("228.37"));
        order3.setTotal(new BigDecimal("5178.37"));

        //create orders
        testOrderDao.createOrder(order1);
        testOrderDao.createOrder(order2);
        testOrderDao.createOrder(order3);

        List<Order> allOrders = testOrderDao.getAllOrders();

        //assert
        assertNotNull(allOrders, "the list should not be null");
        assertEquals(3, allOrders.size(), "the list should have 3 orders");
        assertTrue(testOrderDao.getAllOrders().contains(order1), "Charlie's order should be in the list");
        assertTrue(testOrderDao.getAllOrders().contains(order2), "Rob's order should be in the list");
        assertTrue(testOrderDao.getAllOrders().contains(order3), "Glenn's order should be in the list");
    }

    @Test
    public void testDeleteOrder() throws FilePersistenceException {
        int orderNumber1 = 1;
        Order order1 = new Order(orderNumber1);
        //Set up Tax info
        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //Set up Product info
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));

        order1.setCustomerName("Charlie Day");
        order1.setOrderDate(LocalDate.parse("2022-01-10"));
        order1.setOrderNumber(orderNumber1);
        order1.setProductInfo(product);

        order1.setTaxInfo(tax);
        order1.setArea(new BigDecimal("249"));
        order1.setLaborCost(new BigDecimal("1033.35"));
        order1.setMaterialCost(new BigDecimal("871.50"));
        order1.setTax(new BigDecimal("476.21"));
        order1.setTotal(new BigDecimal("2381.06"));

        //second order
        int orderNumber2 = 2;
        Order order2 = new Order(orderNumber2);
        //Set up Tax info
        Tax tax2 = new Tax("TX");
        tax2.setStateName("Texas");
        tax2.setTaxRate(new BigDecimal("4.45"));
        //Set up Product info
        Product product2 = new Product("Wood");
        product2.setCostPerSqFt(new BigDecimal("5.15"));
        product2.setLaborCostPerSqFt(new BigDecimal("4.75"));

        order2.setCustomerName("Rob McElhenney");
        order2.setOrderDate(LocalDate.parse("2022-02-10"));
        order2.setOrderNumber(orderNumber2);
        order2.setProductInfo(product2);

        order2.setTaxInfo(tax2);
        order2.setArea(new BigDecimal("500"));
        order2.setLaborCost(new BigDecimal("2375.00"));
        order2.setMaterialCost(new BigDecimal("2575.00"));
        order2.setTax(new BigDecimal("228.37"));
        order2.setTotal(new BigDecimal("5178.37"));

        //create orders
        testOrderDao.createOrder(order1);
        testOrderDao.createOrder(order2);

        List<Order> allOrders = testOrderDao.getAllOrders();
        assertEquals(2, allOrders.size(), "the list should have 2 orders");
        //delete an order
        Order deletedOrder = testOrderDao.deleteOrder(LocalDate.parse("2022-02-10"), orderNumber2);

        assertEquals(deletedOrder, order2, "Rob should be deleted order");

        allOrders = testOrderDao.getAllOrders();

        assertEquals(1, allOrders.size(), "The list should have 1 order");
        assertTrue(testOrderDao.getAllOrders().contains(order1), "Charlie should still be in the list");
        assertFalse(testOrderDao.getAllOrders().contains(order2), "Rob should not be in the list");
        assertNull(testOrderDao.getOrderByNumber(LocalDate.parse("2022-02-10"), orderNumber2), "Should be removed");
    }

    @Test
    public void testUpDateOrder() throws FilePersistenceException {
        int orderNumber1 = 1;
        Order order1 = new Order(orderNumber1);
        //Set up Tax info
        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //Set up Product info
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));

        LocalDate date = LocalDate.parse("2022-01-10");
        order1.setCustomerName("Charlie Day");
        order1.setOrderDate(date);
        order1.setOrderNumber(orderNumber1);
        order1.setProductInfo(product);

        order1.setTaxInfo(tax);
        order1.setArea(new BigDecimal("249"));
        order1.setLaborCost(new BigDecimal("1033.35"));
        order1.setMaterialCost(new BigDecimal("871.50"));
        order1.setTax(new BigDecimal("476.21"));
        order1.setTotal(new BigDecimal("2381.06"));
        
        //updated
        Order updatedOrder = new Order(orderNumber1);
        updatedOrder.setCustomerName("Fred");
        updatedOrder.setOrderDate(date);
        updatedOrder.setOrderNumber(orderNumber1);
        updatedOrder.setProductInfo(product);

        updatedOrder.setTaxInfo(tax);
        updatedOrder.setArea(new BigDecimal("249"));
        updatedOrder.setLaborCost(new BigDecimal("1033.35"));
        updatedOrder.setMaterialCost(new BigDecimal("871.50"));
        updatedOrder.setTax(new BigDecimal("476.21"));
        updatedOrder.setTotal(new BigDecimal("2381.06"));
        
        testOrderDao.createOrder(order1);
        
        //assert
        Order retrievedOrder = testOrderDao.getOrderByNumber(date, orderNumber1);
        assertEquals(retrievedOrder.getCustomerName(), order1.getCustomerName(), "Name should be Charlie");
        
        testOrderDao.updateOrder(updatedOrder);
        Order order = testOrderDao.getOrderByNumber(date, orderNumber1);
        
        assertEquals(order.getArea(), new BigDecimal("249"), "Area should still be 249");
        assertEquals(order.getCustomerName(), "Fred", "Name should be Fred");
        assertNotEquals(order.getCustomerName(), "Charlie", "Name should not be Charlie");
    }
}
