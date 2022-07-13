package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrees
 */
public class TaxDaoStubImpl implements TaxDao{
    
    public Tax onlyTax;
    
    public TaxDaoStubImpl(){
        onlyTax = new Tax("CA");
        onlyTax.setStateName("California");
        onlyTax.setTaxRate(new BigDecimal("25.00"));
    }
    
    public TaxDaoStubImpl(Tax testTax){
        this.onlyTax = testTax;
    }

    @Override
    public List<Tax> getAllTaxes() throws FilePersistenceException {
        List<Tax> taxList = new ArrayList<>();
        taxList.add(onlyTax);
        return taxList;
    }

    @Override
    public Tax getTaxInfo(String stateString) throws FilePersistenceException {
        if(stateString.equals(onlyTax.getState())){
            return onlyTax;
        } else {
            return null;
        }
    }

}
