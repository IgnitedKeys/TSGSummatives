
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface UserIO {

    public void print(String message);
    
    public String readString(String prompt);
    
    public String readString(String prompt, String regexOptions);
    
    public int readInt(String prompt);
    
    public int readInt(String prompt, int min, int max);
    
    public BigDecimal readBigDecimal(String prompt);
    
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max);
    
    public LocalDate readLocalDate(String prompt);
    
    public LocalDate readLocalDate(String prompt, LocalDate min, LocalDate max);
    
    public boolean readBoolean(String prompt);
    
    public String getProductChoice(String prompt, List<Product> listOfProducts);
    
    public String getStateChoice(String prompt, List<Tax> listOfStates);
    
    
}
