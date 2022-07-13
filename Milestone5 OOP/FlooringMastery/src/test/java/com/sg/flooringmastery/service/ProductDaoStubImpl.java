
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrees
 */
public class ProductDaoStubImpl implements ProductDao {
    public Product onlyProduct;
    
    public ProductDaoStubImpl(){
        onlyProduct = new Product("Tile");
        onlyProduct.setCostPerSqFt(new BigDecimal("3.50"));
        onlyProduct.setLaborCostPerSqFt(new BigDecimal("4.15"));
    }
    
    public ProductDaoStubImpl(Product testProduct){
        this.onlyProduct = testProduct;
    }
    @Override
    public List<Product> getAllProducts() throws FilePersistenceException {
        List<Product> productList = new ArrayList<>();
        productList.add(onlyProduct);
        return productList;
    }

    @Override
    public Product getProductInfo(String productType) throws FilePersistenceException {
        if(productType.equals(onlyProduct.getProductType())){
            return onlyProduct;
        } else {
            return null;
        }
    }
    
}
