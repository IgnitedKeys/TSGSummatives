package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface ProductDao {

    public List<Product> getAllProducts() throws FilePersistenceException;

    public Product getProductInfo(String productType) throws FilePersistenceException;
}
