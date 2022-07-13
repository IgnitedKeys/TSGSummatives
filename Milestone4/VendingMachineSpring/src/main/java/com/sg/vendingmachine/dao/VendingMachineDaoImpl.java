package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author drees
 */
public class VendingMachineDaoImpl implements VendingMachineDao {

    Map<Integer, VendingItem> items = new HashMap<>();

    private final String VENDING_FILE;

    public static final String DELIMITER = "::";

    public VendingMachineDaoImpl() {
        VENDING_FILE = "vendingMachine.txt";
    }

    public VendingMachineDaoImpl(String fileName) {
        VENDING_FILE = fileName;
    }

    @Override
    public VendingItem createItem(int itemId, VendingItem item) {
        VendingItem addItem = items.put(itemId, item);
        return addItem;
    }

    @Override
    public VendingItem updateInventory(int itemId, VendingItem item) throws VendingMachinePersistenceException {
        loadItems();
        VendingItem editedItem = items.put(itemId, item);
        writeItems();
        return editedItem;
    }

    @Override
    public VendingItem getItemById(int itemId) throws VendingMachinePersistenceException {
        loadItems();
        return items.get(itemId);
    }

    @Override
    public List<VendingItem> getAllItems() throws VendingMachinePersistenceException {
        loadItems();
        return new ArrayList(items.values());
    }

    private VendingItem unmarshallItem(String itemString) {
        String[] itemTokens = itemString.split(DELIMITER);
        int id = Integer.parseInt(itemTokens[0]);

        VendingItem itemFromFile = new VendingItem(id);
        itemFromFile.setItemName(itemTokens[1]);
        itemFromFile.setItemCost(new BigDecimal(itemTokens[2]));
        itemFromFile.setInventoryCount(Integer.parseInt(itemTokens[3]));

        return itemFromFile;
    }

    private void loadItems() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(VENDING_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("Could not load file", e);
        }
        String currentLine;
        VendingItem currentItem;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getItemId(), currentItem);
        }
        scanner.close();
    }

    private void writeItems() throws VendingMachinePersistenceException {

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(VENDING_FILE));
        } catch(IOException e) {
            throw new VendingMachinePersistenceException("Could not save vending machine data",e);
        }
        String itemString;
        List<VendingItem> itemList = this.getAllItems();
        for(VendingItem item :itemList) {
            itemString = marshallItem(item);
            out.println(itemString);
            out.flush();
        }
    }

    private String marshallItem(VendingItem anItem) {
        String anItemString = anItem.getItemId() + DELIMITER;
        anItemString += anItem.getItemName() + DELIMITER;
        anItemString += anItem.getItemCost() + DELIMITER;
        anItemString += anItem.getInventoryCount();
        
        return anItemString;
    }

}
