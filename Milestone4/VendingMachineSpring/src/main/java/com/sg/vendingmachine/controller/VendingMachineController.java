package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineService;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author drees
 */
public class VendingMachineController {

    private VendingMachineService service;
    private VendingMachineView view;

    public VendingMachineController(VendingMachineService service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        boolean keepGoing = true;
        int menuSelection = 0;

        while (keepGoing) {
            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    boolean hasErrors = false;
                    do {
                        try {
                            BigDecimal userMoney = getMoney();
                            int itemChoice = getItemChoice();
                            getChange(userMoney, itemChoice);
                            hasErrors = false;
                        } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException e) {
                            hasErrors = true;
                            view.displayErrorMessage(e.getMessage());
                        }
                    } while (hasErrors);

                    break;
                case 2:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }

        }
    }

    private int getMenuSelection() throws VendingMachinePersistenceException {
        List<VendingItem> itemList = service.getAllItems();
        return view.mainMenuSelection(itemList);
    }

    private void unknownCommand() {
        view.unknownCommand();
    }

    private BigDecimal getMoney() {
        BigDecimal userCash = view.getCash();
        return userCash;
    }

    private int getItemChoice() throws VendingMachinePersistenceException {
        List<VendingItem> itemList = service.getAllItems();
        view.printVendingItems(itemList);
        int itemChoice = view.getItemChoice();
        return itemChoice;
    }

    private void getChange(BigDecimal userMoney, int itemChoice) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {

        Change change = service.checkFundsAndStock(userMoney, itemChoice);
        view.printChangeAmount(change);
        view.printSuccess();
    }
}
