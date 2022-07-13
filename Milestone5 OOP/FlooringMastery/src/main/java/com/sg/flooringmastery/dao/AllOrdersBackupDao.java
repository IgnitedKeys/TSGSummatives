package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author adrees
 */
public interface AllOrdersBackupDao {

    public void writeBackupEntry(List<Order> entries) throws FilePersistenceException;
}
