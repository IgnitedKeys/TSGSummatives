package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author adrees
 */
public class TaxDaoImpl implements TaxDao {

    private final String TAX_FILE;
    private final String DELIMITER = ",";

    Map<String, Tax> taxes = new HashMap<>();

    public TaxDaoImpl() {
        TAX_FILE = "Data/Taxes.txt";
    }

    @Override
    public List<Tax> getAllTaxes() throws FilePersistenceException{
        loadTaxes();
        return new ArrayList(taxes.values());
    }

    @Override
    public Tax getTaxInfo(String stateString) throws FilePersistenceException {
        loadTaxes();
        return taxes.get(stateString);
    }

    private void loadTaxes() throws FilePersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FilePersistenceException("Could not load file", e);
        }
        //skip header
        scanner.nextLine();
        String currentLine;
        Tax currentTax;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxes.put(currentTax.getState(), currentTax);
        }
        scanner.close();
    }

    private Tax unmarshallTax(String taxString) {
        String[] taxTokens = taxString.split(DELIMITER);
        String stateAbb = taxTokens[0];
        Tax taxFromFile = new Tax(stateAbb);
        taxFromFile.setStateName(taxTokens[1]);
        taxFromFile.setTaxRate(new BigDecimal(taxTokens[2]));

        return taxFromFile;
    }
}
