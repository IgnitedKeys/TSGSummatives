package com.sg.dvdlibrary.ui;

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
        int response = Integer.parseInt(userInput.nextLine());
        return response;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        int response;
        do {
            response = Integer.parseInt(userInput.nextLine());
        } while (response < min && response > max);
        return response;
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        double response = Double.parseDouble(userInput.nextLine());
        return response;

    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt);
        double response;
        do {
            response = Double.parseDouble(userInput.nextLine());
        } while (response < min && response > max);
        return response;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        float response = Float.parseFloat(userInput.nextLine());
        return response;

    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt);
        float response;
        do {
            response = Float.parseFloat(userInput.nextLine());
        } while (response < min && response > max);
        return response;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        long response = Long.parseLong(userInput.nextLine());
        return response;

    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt);
        long response;
        do {
            response = Long.parseLong(userInput.nextLine());
        } while (response < min && response > max);
        return response;

    }
}
