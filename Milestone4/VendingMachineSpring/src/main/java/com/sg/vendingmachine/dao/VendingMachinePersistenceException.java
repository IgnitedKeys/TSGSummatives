package com.sg.vendingmachine.dao;

/**
 *
 * @author drees
 */
public class VendingMachinePersistenceException extends Exception {

    VendingMachinePersistenceException(String message) {
        super(message);
    }

    VendingMachinePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
