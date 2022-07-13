package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author adrees
 */
public class OrderDaoImpl implements OrderDao {

    private final String FOLDER;
    private final String DELIMITER = ",";

    Map<LocalDate, Map<Integer, Order>> allOrdersByDate = new HashMap<>();

    public OrderDaoImpl() {
        FOLDER = "Orders/";
    }

    public OrderDaoImpl(String folderName) {
        FOLDER = folderName;
    }

    @Override
    public Order createOrder(Order order) throws FilePersistenceException {
        loadOrders();
        Map<Integer, Order> orders = new HashMap<>();
        Order newOrder = orders.put(order.getOrderNumber(), order);
        allOrdersByDate.put(order.getOrderDate(), orders);
        Map<Integer, Order> orderByDate = allOrdersByDate.get(order.getOrderDate());
        List<Order> ordersList = new ArrayList<Order>(orderByDate.values());
        saveOrders(ordersList);
        return newOrder;

    }

    @Override
    public List<Order> getOrdersByDate(LocalDate orderDate) throws FilePersistenceException {
        loadOrders();
        Map<Integer, Order> orderByDate = allOrdersByDate.get(orderDate);

        List<Order> orders;
        if (orderByDate != null) {
            orders = new ArrayList<>(orderByDate.values());
        } else {
            orders = new ArrayList<>();
        }

        return orders;
    }

    @Override
    public Order getOrderByNumber(LocalDate orderDate, int orderNumber) throws FilePersistenceException {
        loadOrders();
        Map<Integer, Order> orderByDate = allOrdersByDate.get(orderDate);

        if (orderByDate != null) {
            Order singleOrder = orderByDate.get(orderNumber);
            return singleOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() throws FilePersistenceException {
        loadOrders();
        Set<LocalDate> keys = allOrdersByDate.keySet();
        List<Order> orders = new ArrayList<>();
        //get all values for each date in HashMap
        for (LocalDate key : keys) {
            Map<Integer, Order> ordersByDate = allOrdersByDate.get(key);
            for (Order order : ordersByDate.values()) {
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public Order deleteOrder(LocalDate orderDate, int orderNum) throws FilePersistenceException {
        loadOrders();
        Order deleteOrder = allOrdersByDate.get(orderDate).remove(orderNum);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String date = orderDate.format(formatter);
        //remove old file
        File file = new File(FOLDER + "order_" + date + ".txt");
        file.delete();
        //save new file without old order
        Map<Integer, Order> ordersByDate = allOrdersByDate.get(orderDate);
        List<Order> orders = new ArrayList<Order>(ordersByDate.values());

        saveOrders(orders);
        return deleteOrder;
    }

    @Override
    public Order updateOrder(Order order) throws FilePersistenceException {
        loadOrders();
        Map<Integer, Order> newOrder = new HashMap<>();
        Order prevOrder = newOrder.put(order.getOrderNumber(), order);
        allOrdersByDate.put(order.getOrderDate(), newOrder);
        LocalDate orderDate = order.getOrderDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String date = orderDate.format(formatter);
        File file = new File("Orders/order_" + date + ".txt");
        file.delete();

        Map<Integer, Order> orderByDate = allOrdersByDate.get(order.getOrderDate());
        List<Order> orders = new ArrayList<Order>(orderByDate.values());
        saveOrders(orders);
        return prevOrder;
    }

    private void loadOrders() throws FilePersistenceException {
        Scanner scanner;
        File folder = new File(FOLDER);
        File[] listOfFiles = folder.listFiles();
        Map<Integer, Order> mapOrder;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                mapOrder = new HashMap<>();
                String fileName;
                /*
                if (FOLDER.equals("Test/test.txt")) {
                    fileName = file.toString();
                } else {
                    fileName = file.getName().substring(6, 14);
                }
                */
                fileName = file.getName().substring(6,14);

                try {
                    scanner = new Scanner(new BufferedReader(new FileReader(file)));
                    scanner.nextLine();
                    String currentLine;
                    Order currentOrder;

                    while (scanner.hasNextLine()) {
                        currentLine = scanner.nextLine();
                        LocalDate date = LocalDate.parse(fileName, formatter);
                        currentOrder = unmarshallOrder(currentLine, date);

                        mapOrder.put(currentOrder.getOrderNumber(), currentOrder);
                        allOrdersByDate.put(date, mapOrder);

                    }

                } catch (FileNotFoundException e) {
                    throw new FilePersistenceException("Could not load file", e);
                }
            }
        }
    }

    private Order unmarshallOrder(String orderAsString, LocalDate fileDate) {
        //String[] orderToken = orderAsString.split(DELIMITER);
        String[] orderToken = orderAsString.split(",(?=(?:[^\']*\'[^\']*\')*[^\']*$)", -1);
        int orderNumber = Integer.parseInt(orderToken[0]);
        Order orderFromFile = new Order(orderNumber);
        
        if(orderToken[1].contains("'")){
            orderToken[1] = orderToken[1].replace("'", "");
        }
        
        orderFromFile.setCustomerName(orderToken[1]);
        Tax tax = new Tax(orderToken[2]);
        tax.setTaxRate(new BigDecimal(orderToken[3]));
        orderFromFile.setTaxInfo(tax);
        Product product = new Product(orderToken[4]);
        product.setCostPerSqFt(new BigDecimal(orderToken[6]));
        product.setLaborCostPerSqFt(new BigDecimal(orderToken[7]));
        orderFromFile.setProductInfo(product);
        orderFromFile.setArea(new BigDecimal(orderToken[5]));
        orderFromFile.setMaterialCost(new BigDecimal(orderToken[8]));
        orderFromFile.setLaborCost(new BigDecimal(orderToken[9]));
        orderFromFile.setTax(new BigDecimal(orderToken[10]));
        orderFromFile.setTotal(new BigDecimal(orderToken[11]));
        orderFromFile.setOrderDate(fileDate);

        return orderFromFile;
    }

    private void saveOrders(List<Order> orderList) throws FilePersistenceException {
        PrintWriter out;
        File file;
        if (!orderList.isEmpty()) {
            Order firstOrder = orderList.get(0);
            LocalDate date = firstOrder.getOrderDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
            String formatOrderDate = date.format(formatter);

            try {
                /*
                if (FOLDER.equals("Test/test.txt")) {
                    file = new File(FOLDER);
                } else {
                    String fileName = FOLDER + "order_" + formatOrderDate + ".txt";
                    file = new File(fileName);
                }
                 */
                String fileName = FOLDER + "order_" + formatOrderDate + ".txt";
                file = new File(fileName);
                if (!file.exists()) {

                    String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
                    file.createNewFile();
                    out = new PrintWriter(new FileWriter(file, true));
                    out.println(header);
                    out.flush();

                }
                //out = new PrintWriter(new FileWriter(file, true));

                file.createNewFile();
                out = new PrintWriter(new FileWriter(file, true));
                String orderString;

                for (Order order : orderList) {
                    orderString = marshallOrder(order);
                    out.println(orderString);
                    out.flush();
                }

            } catch (IOException e) {
                throw new FilePersistenceException("Could not save order date", e);
            }
        }

    }

    private String marshallOrder(Order order) {
        Tax tax = order.getTaxInfo();
        Product product = order.getProductInfo();

        String orderString = order.getOrderNumber() + DELIMITER;
        String name = "'" + order.getCustomerName() + "'";
//        if(order.getCustomerName().contains(",") && !order.getCustomerName().startsWith("'")){
//            name = "'" + order.getCustomerName() +"'";
//        }
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
        orderString += order.getTotal();

        return orderString;
    }

}
