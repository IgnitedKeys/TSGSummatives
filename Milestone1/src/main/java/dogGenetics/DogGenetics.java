package dogGenetics;

/**
 * @author angeladrees date: 2021-09-22
 */
import java.util.Scanner;
import java.util.Random;

public class DogGenetics {

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);

        System.out.println("What is your dog's name? ");
        String dogName = userInput.nextLine();

        System.out.println("Well then, I have this highly reliable report on " + dogName + "'s prestigous background right here");

        //create array of dog breeds
        String[] breedNames = {"American Pit Bull", "Beagle", "Border Collie",
            "Chihuahua", "Doberman", "French Bulldog", "German Shepherd",
            "Golden Retriever", "Labrador", "Poodle", "Shih Tzu", "Siberian Husky"};

        //create array to hold random percentages
        //total needs to equal 100
        int[] breedPercentage = new int[5];
        int total = 0;
        for (int i = 0; i < breedPercentage.length - 1; i++) {
            breedPercentage[i] = randomizePercentage(total);
            total += breedPercentage[i];
        }
        breedPercentage[breedPercentage.length - 1] = 100 - total;

        //create array to hold random breed names
        //don't repeat previously used breed
        String[] breedsUsed = new String[5];
        for (int i = 0; i < breedsUsed.length; i++) {
            if (i == 0) {
                breedsUsed[i] = randomizeBreed(breedNames);
            } else {
                do {
                    breedsUsed[i] = randomizeBreed(breedNames);
                } while (true == checkArray(breedsUsed, breedsUsed[i], i));
            }
        }
        //I'm choosing not to print, if % == 0
        System.out.println(dogName + " is: ");
        for (int i = 0; i < breedPercentage.length; i++) {
            if (breedPercentage[i] != 0) {
                System.out.println(breedPercentage[i] + "% " + breedsUsed[i]);
            }

        }
        System.out.println("\nWow, that's QUITE the dog!");

    }

    public static int randomizePercentage(int x) {
        Random numberGenerator = new Random();
        int percentage = numberGenerator.nextInt((100 - x));

        return percentage;
    }

    public static String randomizeBreed(String[] array) {
        Random randomizer = new Random();
        int index = randomizer.nextInt(array.length);
        return array[index];
    }

    //compare current index with previous element in the array;
    public static boolean checkArray(String[] array, String currentElement, int currentIndex) {
        boolean isUsed = false;
        for (int i = 0; i < currentIndex; i++) {
            if ((array[i]).equals(currentElement)) {
                isUsed = true;
                break;
            }
        }
        return isUsed;
    }
}
