
package com.sg.vendingmachine.service;

/**
 *
 * @author drees
 */
public class InsufficientFundsException extends Exception{

public InsufficientFundsException(String message) {
    super(message);
}    
public InsufficientFundsException(String message, Throwable cause) {
    super(message, cause);
}
}
