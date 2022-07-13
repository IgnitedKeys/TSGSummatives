package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author adrees
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
    public String readString(String prompt, String regexOptions) {
        String response = "";
        boolean validInput = false;
        do {
            response = readString(prompt);
            Pattern pattern = Pattern.compile(regexOptions);
            Matcher matcher = pattern.matcher(response);

            while (matcher.find()) {
                validInput = true;
            }
            if (response.isEmpty()) {
                validInput = true;
            }
            if (!validInput) {
                print("Not a valid name. Can use a-z, 0-9, comma, period and cannot be blank.");
            }

        } while (!validInput);
//        if(response.contains(",")){
//            response = "'"+response+"'";
//        }

        return response;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        boolean isValidInput = false;
        int response = 0;
        do {
            try {
                response = Integer.parseInt(userInput.nextLine());
                isValidInput = true;
            } catch (NumberFormatException e) {
                print("That isn't a number!");
            }
        } while (!isValidInput);
        return response;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int response;
        do {
            response = readInt(prompt);
            if (response < min || response > max) {
                print("Number must be between " + min + " and " + max + ".");
            }
        } while (response < min || response > max);
        return response;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        System.out.println(prompt);
        boolean isValidInput = false;
        BigDecimal response = new BigDecimal("0");

        do {
            try {
                String number = userInput.nextLine();
                if (number.equals("")) {
                    return response;
                }
                response = new BigDecimal(number);
                isValidInput = true;
            } catch (NumberFormatException e) {
                print("That isn't a valid area!");
            }
        } while (!isValidInput);
        return response;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        BigDecimal response;

        do {
            response = readBigDecimal(prompt);
            if (response.compareTo(BigDecimal.ZERO) == 0) {
                return response;
            }

            if (response.compareTo(min) == -1 || response.compareTo(max) == 1) {
                print("Number must be between " + min + " and " + max + ".");
            }
        } while (response.compareTo(min) == -1 || response.compareTo(max) == 1);
        return response;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        System.out.println(prompt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate response = LocalDate.now();
        boolean isValid = false;
        do {
            try {
                response = LocalDate.parse(userInput.nextLine(), formatter);
                isValid = true;
            } catch (DateTimeParseException e) {
                print("Not a valid date");
            }
        } while (!isValid);

        return response;
    }

    @Override
    public LocalDate readLocalDate(String prompt, LocalDate min, LocalDate max) {

        boolean isValidDate = false;
        LocalDate response;

        do {
            response = readLocalDate(prompt);
            Period diffMin = Period.between(min, response);

            Period diffMax = Period.between(max, response);
            if (diffMin.isNegative()) {
                print("Date must be in the future");
            } else if (!diffMax.isNegative()) {
                print("Cannot take orders over a year in advance.");
            } else {
                isValidDate = true;
            }
        } while (!isValidDate);
        return response;
    }

    @Override
    public boolean readBoolean(String prompt) {
        System.out.println(prompt);
        boolean isValidInput = false;
        String input;
        boolean response = false;
        do {
            input = userInput.nextLine();
            if (input.equalsIgnoreCase("y")) {
                response = true;
                isValidInput = true;
            } else if (input.equalsIgnoreCase("n")) {
                response = false;
                isValidInput = true;
            } else {
                print("not a valid input!");
            }
        } while (!isValidInput);
        return response;
    }

    @Override
    public String getProductChoice(String prompt, List<Product> productList) {
        System.out.println(prompt);
        boolean isValidChoice = false;
        String response = "";
        do {
            response = userInput.nextLine();
            for (Product product : productList) {
                if (product.getProductType().equals(response)) {
                    isValidChoice = true;
                }
                if (response.isEmpty()) {
                    isValidChoice = true;
                }
            }
            if(!isValidChoice){
                print("not a valid product type");
            }
        } while (!isValidChoice);
        
        return response;
    }

    @Override
    public String getStateChoice(String prompt, List<Tax> stateList) {
        System.out.println(prompt);
        boolean isValidChoice = false;
        String response = "";
        do {
            response = userInput.nextLine();
            for (Tax state : stateList) {
                if (state.getState().equals(response)) {
                    isValidChoice = true;
                }
                if (response.isEmpty()) {
                    isValidChoice = true;
                } 
                
            }
            if (!isValidChoice){
                    print("Not a valid state");
                }
        } while (!isValidChoice);
        return response;
    }
}
