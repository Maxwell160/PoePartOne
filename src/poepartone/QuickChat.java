/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class QuickChat {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        if (!loginSuccessful()) {
            System.out.println("Login failed. Exiting The Chat.");
            return;
        }

        System.out.println("Welcome to The Chat.");

        // Load previously stored messages from the JSON file into the array
        Message.loadStoredMessages();

        int maxMessages = readInt("How many messages do you want to enter? ", 1, 100);
        boolean messagesEntered = false;
        int option;

        do {
            System.out.println("\n1) Send Message");
            System.out.println("2) Show sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            option = readInt("Choose an option (1-4): ", 1, 4);

            switch (option) {
                case 1:
                    if (messagesEntered) {
                        System.out.println("You have already entered all " + maxMessages + " messages.");
                        break;
                    }
                    for (int i = 0; i < maxMessages; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + maxMessages + " ---");
                        processOneMessage(i);
                    }
                    messagesEntered = true;
                    System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
                    break;
                case 2:
                    System.out.println("\n==== Recently Sent Messages ====");
                    System.out.println(Message.printMessages());
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    break;
                case 3:
                    System.out.println("Enjoy!");
                    break;
                case 4:
                    storedMessagesMenu();
                    break;
                default:
                    break;
            }
        } while (option != 3);
    }

    private static void processOneMessage(int index) {

        String recipient;
        String result;
        do {
            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
            recipient = in.nextLine().trim();
            result = Message.validateRecipient(recipient);
            System.out.println(result);
        } while (!result.equals("Cell phone number successfully captured."));

        String text;
        do {
            System.out.print("Enter your message (max 250 characters): ");
            text = in.nextLine().trim();
            if (text.isEmpty()) {
                System.out.println("The message cannot be empty.");
                result = "empty";
            } else {
                result = Message.validateMessageLength(text);
                System.out.println(result);
            }
        } while (!result.equals("Message ready to send."));

        String messageID = Message.generateMessageID();
        Message msg = new Message(messageID, index + 1, recipient, text);

        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        int choice = readInt("Choose an option (1-3): ", 1, 3);
        System.out.println(msg.sentMessage(choice));

        if (choice == 2) {
            readInt("Press 0 to delete the message: ", 0, 0);
            System.out.println("Message deleted.");
        }

        if (choice == 1) {
            System.out.println("\n" + msg.details());
        }
    }

    private static void storedMessagesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Stored Messages ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search all messages for a recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("g) Back");
            System.out.print("Choose an option: ");
            String choice = in.nextLine().trim().toLowerCase();

            if (choice.equals("a")) {
                System.out.println(Message.displaySenderAndRecipient());
            } else if (choice.equals("b")) {
                System.out.println(Message.longestStoredMessage());
            } else if (choice.equals("c")) {
                System.out.print("Enter the message ID: ");
                Message found = Message.searchByMessageID(in.nextLine().trim());
                if (found == null) {
                    System.out.println("Message ID not found.");
                } else {
                    System.out.println("Recipient: " + found.getRecipient());
                    System.out.println("Message: " + found.getText());
                }
            } else if (choice.equals("d")) {
                System.out.print("Enter the recipient number: ");
                List<String> results = Message.searchByRecipient(in.nextLine().trim());
                if (results.isEmpty()) {
                    System.out.println("No messages found for that recipient.");
                } else {
                    for (String s : results) {
                        System.out.println("- " + s);
                    }
                }
            } else if (choice.equals("e")) {
                System.out.print("Enter the message hash: ");
                System.out.println(Message.deleteByHash(in.nextLine().trim()));
                try {
                    Message.saveStoredMessages();
                } catch (IOException e) {
                    System.out.println("Could not update JSON file: " + e.getMessage());
                }
            } else if (choice.equals("f")) {
                System.out.println(Message.displayReport());
            } else if (choice.equals("g")) {
                back = true;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private static int readInt(String prompt, int min, int max) {
        int number = min - 1;
        boolean valid;
        do {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            try {
                number = Integer.parseInt(line);
                valid = number >= min && number <= max;
            } catch (NumberFormatException e) {
                valid = false;
            } 
            if (!valid) {
                System.out.println("Invalid input. Enter a number between " + min + " and " + max + ".");
            }
        } while (!valid);
        return number;
    }

    private static boolean loginSuccessful() {
        System.out.print("Enter your Username: ");
        String user = in.nextLine().trim();
        System.out.print("Enter your Password: ");
        String pass = in.nextLine().trim();
        return !user.isEmpty() && !pass.isEmpty();
    }
}