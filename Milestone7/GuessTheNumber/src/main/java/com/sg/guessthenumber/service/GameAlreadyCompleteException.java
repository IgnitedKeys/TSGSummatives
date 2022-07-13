/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.service;

/**
 *
 * @author gavinlinnihan
 */
public class GameAlreadyCompleteException extends Exception {

    public GameAlreadyCompleteException(String message) {

        super(message);
    }

    public GameAlreadyCompleteException(String message, Throwable cause) {
        super(message, cause);
    }
}


