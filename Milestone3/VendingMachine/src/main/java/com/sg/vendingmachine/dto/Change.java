package com.sg.vendingmachine.dto;

/**
 *
 * @author drees
 */
public class Change {

    int[] numOfCoins;

    public Change() {
       numOfCoins = new int[4];
    }
    
    public int getNumOfQuarters() {
        //return numOfCoins[Coins.QUARTER.getValue()];
        return numOfCoins[Coins.QUARTER.ordinal()];
    }

    public void setNumOfQuarters(int numOfQuarters) {
        numOfCoins[Coins.QUARTER.ordinal()] = numOfQuarters;
    }

    public int getNumOfDimes() {
        return numOfCoins[Coins.DIME.ordinal()];
    }

    public void setNumOfDimes(int numOfDimes) {
        numOfCoins[Coins.DIME.ordinal()] = numOfDimes;
    }

    public int getNumOfNickels() {
        return numOfCoins[Coins.NICKEL.ordinal()];
    }

    public void setNumOfNickels(int numOfNickels) {
        numOfCoins[Coins.NICKEL.ordinal()] = numOfNickels;
    }

    public int getNumOfPennies() {
        return numOfCoins[Coins.PENNY.ordinal()];
    }

    public void setNumOfPennies(int numOfPennies) {
        numOfCoins[Coins.PENNY.ordinal()] = numOfPennies;
    }

}
