package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
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
public class ProductDaoImpl implements ProductDao {

    private final String PRODUCT_FILE;
    private final String DELIMITER = ",";

    Map<String, Product> products = new HashMap<>();

    public ProductDaoImpl() {
        PRODUCT_FILE = "Data/Products.txt";
    }

    @Override
    public List<Product> getAllProducts() throws FilePersistenceException {
        loadProducts();
        return new ArrayList(products.values());
    }

    @Override
    public Product getProductInfo(String productType) throws FilePersistenceException {
        loadProducts();
        return products.get(productType);
    }

    private void loadProducts() throws FilePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FilePersistenceException("Could not load file", e);
        }
        scanner.nextLine();
        String currentLine;
        Product currentProduct;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }

    private Product unmarshallProduct(String productString) {

        String[] productTokens = productString.split(DELIMITER);
        String productType = productTokens[0];
        Product productFromFile = new Product(productType);
        productFromFile.setCostPerSqFt(new BigDecimal(productTokens[1]));
        productFromFile.setLaborCostPerSqFt(new BigDecimal(productTokens[2]));

        return productFromFile;
    }
}
