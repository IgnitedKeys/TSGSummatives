package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author adrees
 */
public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int getMenuChoice() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Date");
        io.print("6. Quit");

        int response = io.readInt("Please choose an Option");
        return response;
    }

    public void displayOrderList(List<Order> listOfOrders) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        if (listOfOrders.isEmpty()) {
            io.print("No Orders for that date!");
        }
        for (Order order : listOfOrders) {
            Tax tax = order.getTaxInfo();
            Product product = order.getProductInfo();
            io.print("Order Number: " + order.getOrderNumber() + "\nDate: " + order.getOrderDate().format(formatter) + "\nName: " + order.getCustomerName() + "\nState: " + tax.getState());
            io.print("Tax Rate: " + tax.getTaxRate() + "%\nProduct Info: " + order.getProductInfo().getProductType() + "\nArea: " + order.getArea()
                    + "sq ft\nCost Per Sq Ft: $" + product.getCostPerSqFt() + "\nLabor Cost Per Sq Ft: $" + product.getLaborCostPerSqFt()
                    + "\nMaterial Cost: $" + order.getMaterialCost() + "\nLabor Cost: $" + order.getLaborCost() + "\nTax total: $" + order.getTax() + "\nTotal: $" + order.getTotal());
            io.print("\n");
        }
        this.getUserContinue("Press enter to continue");
    }

    public Order getNewOrderInfo(List<Product> productList, List<Tax> stateList, int orderNumber) {
        boolean isNull = true;
        boolean isValid = false;
        //LocalDate min and max
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate yearInFuture = tomorrow.plusYears(1);
        LocalDate date = io.readLocalDate("Please enter the date as MMddyyyy", tomorrow, yearInFuture);

        String customerName = "";
        do {
            customerName = io.readString("Please enter customer name", "^[a-zA-Z0-9,\\. ]+$");
            //Blank name is used later (editOrderInfo)
            if (!customerName.isBlank()) {
                isValid = true;
            }
        } while (!isValid);

        displayStateList(stateList);
        String stateAbb = "";
        do {
            stateAbb = io.getStateChoice("Please choose state by initals", stateList);
            if (!stateAbb.isEmpty()) {
                isNull = false;
            }
        } while (isNull);

        displayProductList(productList);
        String productType = "";
        isNull = true;
        do {
            productType = io.getProductChoice("Please choose product type", productList);
            if (!productType.isEmpty()) {
                isNull = false;
            }
        } while (isNull);

        BigDecimal area;
        isNull = true;
        do {
            //area min 100, max 10000
            area = io.readBigDecimal("Please give area in sq ft", new BigDecimal("100"), new BigDecimal("10000"));
            //BigDecimal.ZERO is used later for enter key(editOrderInfo)
            if (area.compareTo(BigDecimal.ZERO) == 0) {
                io.print("That isn't a valid area!");
                isNull = true;
            } else {
                isNull = false;
            }
        } while (isNull);

        Order newOrder = new Order(orderNumber);

        newOrder.setOrderDate(date);
        newOrder.setCustomerName(customerName);
        Tax tax = new Tax(stateAbb);
        newOrder.setTaxInfo(tax);
        newOrder.setArea(area);
        Product product = new Product(productType);
        newOrder.setProductInfo(product);

        return newOrder;
    }

    public LocalDate getOrderDate() {
        LocalDate response = io.readLocalDate("Please enter Order Date (MMddyyyy)");
        return response;
    }

    public int getOrderNumber() {
        int response = io.readInt("Please enter the Order Number", 1, 1000);
        return response;
    }

    public void displayErrorMessage(String errorMessage) {
        io.print(errorMessage);
        this.getUserContinue("Press enter to continue");
    }

    public boolean confirmOrderSave(Order order, String saveAction) {
        Tax tax = order.getTaxInfo();
        Product product = order.getProductInfo();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        io.print("Order Number: " + order.getOrderNumber() + "\nDate: " + order.getOrderDate().format(formatter) + "\nName: " + order.getCustomerName() + "\nState: " + tax.getState());
        io.print("Tax Rate: " + tax.getTaxRate() + "%\nProduct Info: " + order.getProductInfo().getProductType() + "\nArea: " + order.getArea()
                + "sq ft\nCost Per Sq Ft: $" + product.getCostPerSqFt() + "\nLabor Cost Per Sq Ft: $" + product.getLaborCostPerSqFt()
                + "\nMaterial Cost: $" + order.getMaterialCost() + "\nLabor Cost: $" + order.getLaborCost() + "\nTax total: $" + order.getTax() + "\nTotal: $" + order.getTotal());
        Boolean response = io.readBoolean(saveAction);

        return response;
    }

    public Order EditOrderInfo(Order order, List<Product> productList, List<Tax> stateList) {
        if (order != null) {
            Tax tax = order.getTaxInfo();
            Product product = order.getProductInfo();

            String editedName = io.readString("Enter customer name (" + order.getCustomerName() + "): ", "^[a-zA-Z0-9,\\. ]+$");
            if (editedName.isEmpty()) {
                editedName = order.getCustomerName();
            }

            displayStateList(stateList);
            String editedState = io.getStateChoice("Enter state (" + tax.getState() + "): ", stateList);
            if (editedState.isEmpty()) {
                editedState = tax.getState();

            }
            displayProductList(productList);
            String editedProductType = io.getProductChoice("Enter product type (" + product.getProductType() + "): ", productList);
            if (editedProductType.isEmpty()) {
                editedProductType = product.getProductType();

            }
            BigDecimal editedArea = io.readBigDecimal("Enter area in sq ft (" + order.getArea() + "): ", new BigDecimal("100"), new BigDecimal("10000"));
            if (editedArea.compareTo(BigDecimal.ZERO) == 0) {
                editedArea = order.getArea();
            }

            Order editedOrder = new Order(order.getOrderNumber());
            editedOrder.setOrderDate(order.getOrderDate());
            editedOrder.setCustomerName(editedName);
            Tax editedTax = new Tax(editedState);
            editedOrder.setTaxInfo(editedTax);
            Product editedProduct = new Product(editedProductType);
            editedOrder.setProductInfo(editedProduct);
            editedOrder.setArea(editedArea);

            return editedOrder;

        } else {
            io.print("That order number doesn't exist for that date!");
            this.getUserContinue("Press enter to continue");
            return order;

        }

    }

    public void displayExportAllOrdersSuccess() {
        io.print("File Created in Backup Folder");
        this.getUserContinue("Press enter to continue");
    }

    public void displayExitMessage() {
        io.print("Logging off....");
    }

    private void displayStateList(List<Tax> stateList) {
        if (stateList.isEmpty()) {
            io.print("no states in the list!\n");
        } else {
            io.print("<<State List>>");
            for (Tax currentState : stateList) {
                String stateInfo = String.format("%s %s",
                        currentState.getState(),
                        currentState.getStateName());
                io.print(stateInfo);
            }
        }

    }

    private void displayProductList(List<Product> productList) {
        if (productList.isEmpty()) {
            io.print("no product list!\n");
        } else {
            io.print("<<Product Type List>>");
            System.out.printf("%-10s | %-15s | %-15s%n", "Type", "Material Cost", "Labor Cost");
            //io.print("product type cost per sqft  labor cost")
            for (Product currentProduct : productList) {
                System.out.printf("%-10s | $%-14.2f | $%-14.2f%n", currentProduct.getProductType(), currentProduct.getCostPerSqFt(), currentProduct.getLaborCostPerSqFt());
                //io.print(currentProduct.getProductType() + " $" + currentProduct.getCostPerSqFt());
            }
        }
    }

    private void getUserContinue(String prompt) {
        String userInput;
        do {
            userInput = io.readString(prompt);
        } while (!userInput.equals(""));
    }
}
