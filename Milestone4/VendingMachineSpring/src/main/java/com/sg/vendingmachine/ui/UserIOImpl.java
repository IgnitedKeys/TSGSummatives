
package com.sg.vendingmachine.ui;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author angeladrees
 */
public class UserIOImpl implements UserIO {
    
     Scanner userInput = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String response = userInput.nextLine();
        return response;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        boolean isValidInput = false;
        int response = 0;
        do{
            try {
                response = Integer.parseInt(userInput.nextLine());
                isValidInput = true;
            } catch (NumberFormatException ex){
                print("That isn't a number dumdum!");
            }
        }while(!isValidInput);
        
        return response;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        //System.out.println(prompt);
        int response;
        do {
            response = readInt(prompt);
            
            if(response < min || response > max){ 
                print("Number must be between " + min + " and " + max + ".");
            }
        } while (response < min || response > max);
        return response;
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        boolean isValidInput = false;
        double response = 0;
        do {
            try {
                response = Double.parseDouble(userInput.nextLine());
                isValidInput = true;
            } catch (NumberFormatException ex) {
                print("That isn't a number!");
            }
        }while(!isValidInput);
        
        return response;

    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        //System.out.println(prompt);
        double response;
        do {
            response = readDouble(prompt);
            if(response < min || response > max) {
                print("Number must be between " + min + " and " + max + ".");
            }
        } while (response < min || response > max);
        return response;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        float response = 0;
        boolean isValidInput = false;
        do {
            try{
                response = Float.parseFloat(userInput.nextLine());
                isValidInput = true;
            } catch(NumberFormatException ex) {
                print("That isn't a number!");
            }
        }while(!isValidInput);
        
        return response;

    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        //System.out.println(prompt);
        float response;
        do {
            response = readFloat(prompt);
            if(response < min || response > max){
                print("Number must be between " + min + " and " + max + ".");
            }
        } while (response < min || response > max);
        return response;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        long response = 0;
        boolean isValidInput = false;
        do {
            try{
                response = Long.parseLong(userInput.nextLine());
                isValidInput = true;
            } catch (NumberFormatException ex) {
                print("That isn't a number!");
            }
        } while(!isValidInput);
        
        return response;

    }

    @Override
    public long readLong(String prompt, long min, long max) {
        //System.out.println(prompt);
        long response;
        do {
            response = readLong(prompt);
            if(response < min || response > max){
                print("Number must be between " + min + " and " + max + ".");
            }
        } while (response < min || response > max);
        return response;

    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        System.out.println(prompt);
        BigDecimal response = new BigDecimal("0");
        boolean isValidInput = false;
        do {
            try {
                response = new BigDecimal(userInput.nextLine());
                isValidInput = true;
            } catch(NumberFormatException ex) {
                print("That isn't a number!");
            }
        } while(!isValidInput);
        
        return response;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        //System.out.println(prompt);
        BigDecimal response;
        do {
            response = readBigDecimal(prompt);
            if(response.compareTo(min)== -1 || response.compareTo(max) == 1){
                print("Number must be between " + min + " and " + max + ".");
            }
        } while (response.compareTo(min)== -1 || response.compareTo(max) == 1);
        return response;
    }
}
