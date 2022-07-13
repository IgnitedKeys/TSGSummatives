
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.AllOrdersBackupDao;
import com.sg.flooringmastery.dao.FilePersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author adrees
 */
public class AllOrdersBackupDaoStubImpl implements AllOrdersBackupDao {

    @Override
    public void writeBackupEntry(List<Order> entries) throws FilePersistenceException {
        //do nothing!
    }
    
}
