/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.guessthenumber.service;

/**
 *
 * @author adrees
 */
public class GameNullException extends Exception{
    public GameNullException(String message) {
        super(message);
    }
    
    public GameNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
