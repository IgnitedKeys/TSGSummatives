package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface TaxDao {

    public List<Tax> getAllTaxes() throws FilePersistenceException;

    public Tax getTaxInfo(String stateString) throws FilePersistenceException;
}
