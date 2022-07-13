package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author adrees
 */
public class AllOrdersBackupDaoImpl implements AllOrdersBackupDao {

    public static final String BACKUP_FILE = "Backup/DataExport.txt";
    public static final String DELIMITER = ",";

    @Override
    public void writeBackupEntry(List<Order> entries) throws FilePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(BACKUP_FILE));
        } catch (IOException e) {
            throw new FilePersistenceException("Could not parse orders", e);
        }
        String orderString;
        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,Date";
        out.println(header);
        out.flush();

        for (Order entry : entries) {
            orderString = marshallOrder(entry);
            out.println(orderString);
            out.flush();
        }
    }

    private String marshallOrder(Order order) {
        Tax tax = order.getTaxInfo();
        Product product = order.getProductInfo();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formatOrderDate = order.getOrderDate().format(formatter);

        String orderString = order.getOrderNumber() + DELIMITER;
        
        String name = order.getCustomerName();
        if(order.getCustomerName().contains(",") && !order.getCustomerName().startsWith("'")){
            name = "'" + order.getCustomerName() +"'";
        }
        orderString += name + DELIMITER;
        orderString += tax.getState() + DELIMITER;
        orderString += tax.getTaxRate() + DELIMITER;
        orderString += product.getProductType() + DELIMITER;
        orderString += order.getArea() + DELIMITER;
        orderString += product.getCostPerSqFt() + DELIMITER;
        orderString += product.getLaborCostPerSqFt() + DELIMITER;
        orderString += order.getMaterialCost() + DELIMITER;
        orderString += order.getLaborCost() + DELIMITER;
        orderString += order.getTax() + DELIMITER;
        orderString += order.getTotal() + DELIMITER;
        orderString += formatOrderDate;

        return orderString;
    }
}

