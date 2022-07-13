package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.AllOrdersBackupDao;
import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 *
 * @author adrees
 */
public class FlooringMasteryServiceImpl implements FlooringMasteryService {

    private OrderDao orderDao;
    private TaxDao taxDao;
    private ProductDao productDao;
    private AllOrdersBackupDao orderBackupDao;

    public FlooringMasteryServiceImpl(OrderDao orderDao, TaxDao taxDao, ProductDao productDao, AllOrdersBackupDao orderBackupDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.orderBackupDao = orderBackupDao;
    }

    @Override
    public List<Order> getAllOrders(LocalDate date) throws FilePersistenceException {
        return orderDao.getOrdersByDate(date);
    }

    @Override
    public Order getSingleOrder(LocalDate date, int orderNumber) throws FilePersistenceException {
        return orderDao.getOrderByNumber(date, orderNumber);
    }

    @Override
    public List<Tax> getAllTaxes() throws FilePersistenceException {
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Product> getAllProducts() throws FilePersistenceException {
        return productDao.getAllProducts();
    }

    @Override
    public Order createOrder(Order order) throws FilePersistenceException, OrderValidationException {
        validateOrder(order);
        return orderDao.createOrder(order);
    }

    @Override
    public Order updateOrder(Order order) throws FilePersistenceException, OrderValidationException {
        validateOrder(order);
        return orderDao.updateOrder(order);
    }

    @Override
    public Order removeOrder(Order order) throws FilePersistenceException {
        LocalDate orderDate = order.getOrderDate();
        int orderNumber = order.getOrderNumber();
        return orderDao.deleteOrder(orderDate, orderNumber);
    }

    @Override
    public void makeBackupFile() throws FilePersistenceException {
        List<Order> allOrders = orderDao.getAllOrders();
        orderBackupDao.writeBackupEntry(allOrders);
    }

    @Override
    public Order calculateOrder(Order order) throws FilePersistenceException {
        BigDecimal area = order.getArea();

        Tax tax = order.getTaxInfo();
        //Tax allTaxInfo = taxDao.getTaxInfo(tax.getState());
        Tax allTaxInfo = taxDao.getTaxInfo(order.getTaxInfo().getState());
        BigDecimal taxRate = allTaxInfo.getTaxRate();

        Product product = order.getProductInfo();
        //Product allProductInfo = productDao.getProductInfo(product.getProductType());
        Product allProductInfo = productDao.getProductInfo(order.getProductInfo().getProductType());
        BigDecimal costPerSqFt = allProductInfo.getCostPerSqFt();
        BigDecimal laborCostPerSqFt = allProductInfo.getLaborCostPerSqFt();
        //Calculate
        BigDecimal orderMaterialCost = calculateMaterialCost(area, costPerSqFt);
        BigDecimal orderLaborCost = calculateLaborCost(area, laborCostPerSqFt);
        BigDecimal orderTax = calculateTax(orderMaterialCost, orderLaborCost, taxRate);
        BigDecimal orderTotal = calculateTotal(orderMaterialCost, orderLaborCost, orderTax);

        order.setProductInfo(allProductInfo);
        order.setTaxInfo(allTaxInfo);
        order.setMaterialCost(orderMaterialCost);
        order.setLaborCost(orderLaborCost);
        order.setTax(orderTax);
        order.setTotal(orderTotal);

        return order;
    }

    @Override
    public List<Order> getAllDateOrders() throws FilePersistenceException {
        return orderDao.getAllOrders();
    }

    private BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSqFt) {
        BigDecimal materialCost = area.multiply(costPerSqFt);
        return materialCost;
    }

    private BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSqFt) {
        BigDecimal laborCost = area.multiply(laborCostPerSqFt);
        return laborCost;
    }

    private BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) {
        BigDecimal tax = (materialCost.add(laborCost).multiply(taxRate.divide(new BigDecimal("100")))).setScale(2, RoundingMode.HALF_UP);
        return tax;
    }

    private BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxTotal) {
        BigDecimal total = materialCost.add(laborCost).add(taxTotal);
        return total;
    }

    private void validateOrder(Order order) throws OrderValidationException {

        if (order.getProductInfo() == null) {
            throw new OrderValidationException("that isn't a valid product type");
        }
        if (order.getTaxInfo() == null) {
            throw new OrderValidationException("that isn't a valid state");
        }

        if (order.getArea().compareTo(new BigDecimal("100")) == -1 || order.getArea().compareTo(new BigDecimal("10000")) == 1) {
            throw new OrderValidationException("area must be more than 100, and less than 10000");
        }
        
        LocalDate orderDate = order.getOrderDate();
        Period diffMin = Period.between(LocalDate.now().plusDays(1), orderDate);
        Period diffMax = Period.between(LocalDate.now().plusDays(1).plusYears(1), orderDate);
        if (diffMin.isNegative() || !diffMax.isNegative()) {
            throw new OrderValidationException("date must be in the future, up to a year in advance");
        }
        
    }

}
