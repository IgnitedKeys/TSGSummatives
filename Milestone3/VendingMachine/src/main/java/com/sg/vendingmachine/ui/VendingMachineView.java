package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author angeladrees
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public int mainMenuSelection(List<VendingItem> items) {
        
        printVendingItems(items);
        
        io.print("== Please Select Option ==");
        io.print("1. Add Money");
        io.print("2. Exit");
        return io.readInt("->",1,2);
    }

    public void unknownCommand() {
        io.print("UnknownCommand");
    }

    public BigDecimal getCash() {
        //BigDecimal userCash = io.readBigDecimal("Please enter cash", new BigDecimal("0"), new BigDecimal("100"));
        io.print("== Please enter cash amount ==");
        BigDecimal userCash = io.readBigDecimal("$",new BigDecimal("1"), new BigDecimal("100"));
        return userCash;
    }

    public void printVendingItems(List<VendingItem> items) {
        io.print("==== VENDING MACHINE ====\n");
        //iterate through list and print to screen
        for (VendingItem item : items) {
            io.print("\t" + item.getItemId() + "--" + item.getItemName());
            io.print("\t$" + item.getItemCost().toString());
            io.print("\t" + item.getInventoryCount() + " left\n");
        }

    }

    public int getItemChoice() {
        io.print("== Please enter item number ==");
        int itemId = io.readInt("#");
        
        return itemId;
    }

    public void printChangeAmount(Change change) {
        io.print("== Please take your change ==");
        int numOfQuarters = change.getNumOfQuarters();
        int numOfDimes = change.getNumOfDimes();
        int numOfNickels = change.getNumOfNickels();
        int numOfPennies = change.getNumOfPennies();

        
        if (numOfQuarters == 1) {
            io.print(numOfQuarters + " quarter");
        } else if (numOfQuarters > 0) {
            io.print(numOfQuarters + " quarters");
        }
        
        if (numOfDimes == 1) {
            io.print(numOfDimes + " dime");
        } else if (numOfDimes > 0) {
            io.print(numOfDimes + " dimes");
        }
        if (numOfNickels == 1) {
            io.print(numOfNickels + " nickel");
        } else if (numOfNickels > 0) {
            io.print(numOfNickels + " nickels");
        }
        if (numOfPennies == 1) {
            io.print(numOfPennies + " penny");
        } else if(numOfPennies > 0){
            io.print(numOfPennies + " pennies");
        }
        if(numOfQuarters == 0 && numOfDimes == 0 && numOfNickels ==0 && numOfPennies ==0) {
            io.print("EXACT CHANGE");
        }

    }
    public void printSuccess() {
        String userInput = " ";
        while(!userInput.equals("")){
            userInput = io.readString("Press enter to continue");
        }
        
    }
    
    public void displayErrorMessage(String errorMessage) {
        io.print(errorMessage);
    }
}
