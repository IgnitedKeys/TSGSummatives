package com.sg.flooringmastery.dao;

/**
 *
 * @author adrees
 */
public class FilePersistenceException extends Exception {

    FilePersistenceException(String message) {
        super(message);
    }

    FilePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
