
package healthyHearts;

import java.util.Scanner;

/**
 * @author angeladrees
 * date: 2021-09-22
 */
public class HealthyHearts {
     public static void main(String[] args) {
        int age, maxHeart, minHeartZone, maxHeartZone;
        
        Scanner inputReader = new Scanner(System.in);
        
        System.out.println("What is your age? ");
        age = Integer.parseInt(inputReader.nextLine());
        
        maxHeart = 220 - age;
        
        //round percentage and keep an integer
        minHeartZone = (int) Math.round(maxHeart * 0.5);
        maxHeartZone = (int) Math.round(maxHeart * 0.85);
        
        System.out.println("Your maxium heart rate should be " + maxHeart + " beats per minute.");
        System.out.println("Your target HR zone is " + minHeartZone + " - " + maxHeartZone + " beats per minute.");
    }
    
}
