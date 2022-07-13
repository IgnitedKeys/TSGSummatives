package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.service.OrderValidationException;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author adrees
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryService service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws FilePersistenceException, OrderValidationException {
        boolean keepGoing = true;
        int userChoice = 0;
        while (keepGoing) {
            userChoice = pickMenuItem();
            switch (userChoice) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exportAllData();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }
        }
        exitMessage();
    }

    private void displayOrders() throws FilePersistenceException {
        LocalDate orderDate = view.getOrderDate();
        if (orderDate != null) {
            List<Order> listOfOrders = service.getAllOrders(orderDate);
            view.displayOrderList(listOfOrders);
        } else {
            view.displayErrorMessage("No order for that order date!");
        }

    }

    /*
        BigDecimal Tax decimal place
        debug user input
        Fix display-make pretty and easier to read
        Format Local Date
        Work on changing the order Number
        Validation for LocalDate!
     */
    private void addOrder() throws FilePersistenceException, OrderValidationException {
        List<Product> listOfProducts = service.getAllProducts();
        List<Tax> listOfStates = service.getAllTaxes();
        List<Order> allOrders = service.getAllDateOrders();
        //generate an orderNumber
        int orderNumber = 0;
        for (Order order : allOrders) {
            if (order.getOrderNumber() > orderNumber) {
                orderNumber = order.getOrderNumber();
            }
        }
        orderNumber++;
        Order newOrder = view.getNewOrderInfo(listOfProducts, listOfStates, orderNumber);
        //calculate other parts of order
        service.calculateOrder(newOrder);
        //and then show summary of order
        String saveAction = "Would you like to create this order? y/n";
        boolean answer = view.confirmOrderSave(newOrder, saveAction);
        //if true save, if false, return to main menu
        if (answer == true) {
            service.createOrder(newOrder);
        }
    }

    private void editOrder() throws FilePersistenceException, OrderValidationException {
        LocalDate date = view.getOrderDate();
        int orderNumber = view.getOrderNumber();
        Order order = service.getSingleOrder(date, orderNumber);
        List<Product> productList = service.getAllProducts();
        List<Tax> taxList = service.getAllTaxes();
        Order editedOrder = view.EditOrderInfo(order, productList, taxList);
        if (editedOrder != null) {
            service.calculateOrder(editedOrder);
            String saveAction = "Would you like to save the order with these changes? y/n";
            boolean answer = view.confirmOrderSave(editedOrder, saveAction);
            if (answer == true) {
                service.updateOrder(editedOrder);
            }
        }
    }

    private void removeOrder() throws FilePersistenceException {
        LocalDate date = view.getOrderDate();
        int orderNumber = view.getOrderNumber();
        Order order = service.getSingleOrder(date, orderNumber);

        if (order != null) {
            String saveAction = "Would you like to remove this order? y/n";
            boolean answer = view.confirmOrderSave(order, saveAction);
            if (answer == true) {
                service.removeOrder(order);
            }
        } else {
            view.displayErrorMessage("No order exists!");
        }

    }

    private void exportAllData() throws FilePersistenceException {
        service.makeBackupFile();
        view.displayExportAllOrdersSuccess();
    }

    private void exitMessage() {
        view.displayExitMessage();
    }

    private int pickMenuItem() {
        return view.getMenuChoice();
    }

    private void unknownCommand() {
        view.displayErrorMessage("Unknown Command");
    }
}
