/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package poepart2;

import com.mycompany.chatapp.Message;
import java.util.Scanner;

public class PoePart2 {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Message msg = new Message(scan);
        int option = 0;

        do {
            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1. Send message");
            System.out.println("2. Coming soon");
            System.out.println("3. Exit");
            System.out.print("Enter your option: ");

            String choice = scan.nextLine().trim();

            try {
                option = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (option) {
                case 1:
                    msg.sendingMessages();
                    break;

                case 2:
                    System.out.println("Coming soon.");
                    break;

                case 3:
                    System.out.println("Thank you for using QuickChat!");
                    break;

                default:
                    System.out.println("Invalid option. Choose 1, 2 or 3.");
            }

        } while (option != 3);

        scan.close();
    }
}


