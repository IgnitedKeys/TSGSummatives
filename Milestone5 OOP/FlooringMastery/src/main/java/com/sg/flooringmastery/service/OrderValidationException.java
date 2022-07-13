package com.sg.flooringmastery.service;

/**
 *
 * @author adrees
 */
public class OrderValidationException extends Exception {

    OrderValidationException(String message) {
        super(message);
    }

    OrderValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
