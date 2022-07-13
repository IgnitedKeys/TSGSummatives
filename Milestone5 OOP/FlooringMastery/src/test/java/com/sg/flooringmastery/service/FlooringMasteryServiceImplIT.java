package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author adrees
 */
public class FlooringMasteryServiceImplIT {

    private FlooringMasteryService service;

    public FlooringMasteryServiceImplIT() {
        /*
        OrderDao orderDao = new OrderDaoStubImpl();
        TaxDao taxDao = new TaxDaoStubImpl();
        ProductDao productDao = new ProductDaoStubImpl();
        AllOrdersBackupDao backupDao = new AllOrdersBackupDaoStubImpl();

        service = new FlooringMasteryServiceImpl(orderDao, taxDao, productDao, backupDao);
        */
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("service", FlooringMasteryServiceImpl.class);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetOrdersByDate() throws FilePersistenceException {
        //should only be one order
        //should be Ada
        LocalDate date = LocalDate.parse("2022-03-14");
        Order testOrder = new Order(1);
        testOrder.setOrderDate(date);
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);

        assertEquals(1, service.getAllOrders(date).size(), "there should be 1 order");
        assertTrue(service.getAllOrders(date).contains(testOrder), "the order should be Ada");

    }

    @Test
    public void testGetOrdersByNumber() throws FilePersistenceException {
        //should only be one order
        //should be Ada
        //should not be null
        LocalDate date = LocalDate.parse("2022-03-14");
        Order testOrder = new Order(1);
        testOrder.setOrderDate(date);
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);

        Order shouldBeAda = service.getSingleOrder(date, 1);
        assertNotNull(shouldBeAda, "ada should be the order");

        Order shouldBeNull = service.getSingleOrder(date, 4);
        assertNull(shouldBeNull, "no order should be with order 4");

        Order shouldAlsoBeNull = service.getSingleOrder(LocalDate.parse("2022-01-14"), 1);
        assertNull(shouldAlsoBeNull, "no order should be with that date");

    }

    @Test
    public void testGetTaxInfo() throws FilePersistenceException {
        //should only be one
        //should be CA
        Tax testTax = new Tax("CA");
        testTax.setStateName("California");
        testTax.setTaxRate(new BigDecimal("25.00"));

        assertEquals(1, service.getAllTaxes().size(), "only 1 tax should be in the list");
        assertTrue(service.getAllTaxes().contains(testTax), "CA should be the tax state");
        //asssertTrue

    }

    @Test
    public void testGetProductInfo() throws FilePersistenceException {
        //should only be one
        //should be Tile
        Product testProduct = new Product("Tile");
        testProduct.setCostPerSqFt(new BigDecimal("3.50"));
        testProduct.setLaborCostPerSqFt(new BigDecimal("4.15"));

        assertEquals(1, service.getAllProducts().size(), "only 1 product should be in the list");
        assertTrue(service.getAllProducts().contains(testProduct), "Tile should be the product type");
    }

    @Test
    public void testCreateOrder() throws FilePersistenceException, OrderValidationException {
        //check file name??
        //validate State 
        //validate Product
        //duplicates??
        LocalDate date = LocalDate.parse("2022-03-14");
        Order testOrder = new Order(1);
        testOrder.setOrderDate(date);
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);

        Order newOrder = service.createOrder(testOrder);
        assertEquals(service.createOrder(newOrder), testOrder, "Order should be order 1 Ada");
        assertEquals(newOrder.getTaxInfo(), testOrder.getTaxInfo(), "should be CA");
        assertEquals(newOrder.getProductInfo(), testOrder.getProductInfo(), "should be Tile");

    }
    
    @Test
    public void testValidateCreateOrder() throws FilePersistenceException{
        LocalDate date = LocalDate.parse("2022-03-14");
        Order testOrder = new Order(1);
        testOrder.setOrderDate(date);
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);
        
        try{
            service.createOrder(testOrder);
        }catch(OrderValidationException e){
            fail("Order was valid");
        }
    }
    
    @Test 
    public void testValidateUpdateOrder() throws FilePersistenceException{
        LocalDate date = LocalDate.parse("2022-03-14");
        Order testOrder = new Order(1);
        testOrder.setOrderDate(date);
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);

        testOrder.setCustomerName("Erica");
        
        try{
            service.updateOrder(testOrder);
        }catch (OrderValidationException e){
            fail("Order was valid");
        }
        
    }

    @Test
    public void testUpdateOrder() throws FilePersistenceException, OrderValidationException {
        LocalDate date = LocalDate.parse("2022-03-14");
        Order testOrder = new Order(1);
        testOrder.setOrderDate(date);
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);

        testOrder.setCustomerName("Erica");
        Order updatedOrder = service.updateOrder(testOrder);

        assertEquals(updatedOrder.getCustomerName(), "Erica", "order name should be Erica");

        testOrder.setArea(new BigDecimal("300"));
        updatedOrder = service.updateOrder(testOrder);
        assertEquals(updatedOrder.getArea(), new BigDecimal("300"), "area should be 300");

    }

    @Test
    public void testRemoveOrder() throws FilePersistenceException {
        //should be there
        //should be null when removed
        LocalDate date = LocalDate.parse("2022-03-14");
        Order testOrder = new Order(1);
        testOrder.setOrderDate(date);
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);

        Order removedOrder = service.removeOrder(testOrder);
        assertNotNull(removedOrder, "removing Ada should not be null");
        assertEquals(removedOrder, testOrder, "removed order should be Ada");

        Order order2 = new Order(2);
        order2.setOrderDate(LocalDate.parse("2022-01-04"));
        Order shouldBeNull = service.removeOrder(order2);
        assertNull(shouldBeNull, "removing order 2 should be null");

    }

    @Test
    public void testCalculateOrder() throws FilePersistenceException {
        //validations!!
        Order testOrder = new Order(1);
        testOrder.setOrderDate(LocalDate.parse("2022-03-14"));
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));
        //set Tax

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);

        Order calculatedOrder = service.calculateOrder(testOrder);
        
        //check TaxRate
        assertEquals(calculatedOrder.getTaxInfo().getTaxRate(), new BigDecimal("25.00"));
        //check materialCost
        assertEquals(calculatedOrder.getMaterialCost(), new BigDecimal("871.50"));
        //check laborCost
        assertEquals(calculatedOrder.getLaborCost(), new BigDecimal("1033.35"));
        //check taxCost
        assertEquals(calculatedOrder.getTax(), new BigDecimal("476.21"));
        //check totalCost
        assertEquals(calculatedOrder.getTotal(), new BigDecimal("2381.06"));
        
        
    }

    @Test
    public void testGetAllOrders() throws FilePersistenceException {
        Order testOrder = new Order(1);
        testOrder.setOrderDate(LocalDate.parse("2022-03-14"));
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setArea(new BigDecimal("249"));
        //set Tax

        Tax tax = new Tax("CA");
        tax.setStateName("California");
        tax.setTaxRate(new BigDecimal("25.00"));
        //set Product
        Product product = new Product("Tile");
        product.setCostPerSqFt(new BigDecimal("3.50"));
        product.setLaborCostPerSqFt(new BigDecimal("4.15"));
        //set tax and product
        testOrder.setTaxInfo(tax);
        testOrder.setProductInfo(product);

        assertEquals(1, service.getAllDateOrders().size(), "Should only have 1 order");
        assertTrue(service.getAllDateOrders().contains(testOrder), "the order should be Ada");
    }
                                   }
