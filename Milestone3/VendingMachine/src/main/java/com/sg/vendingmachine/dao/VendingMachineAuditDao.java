package com.sg.vendingmachine.dao;

/**
 *
 * @author drees
 */
public interface VendingMachineAuditDao {

    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
