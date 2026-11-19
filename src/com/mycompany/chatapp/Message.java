/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.chatapp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    // ===== The five required arrays =====
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIDs = new ArrayList<>();

    private static final Random RANDOM = new Random();
    private static final String STORE_FILE = "storedMessages.json";
    private static final String SENDER = "Developer";

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String messageHash;

    public Message(String messageID, int messageNumber, String recipient, String text) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageHash = createMessageHash();
    }

    public Message(int messageNumber, String recipient, String text) {
        this(generateMessageID(), messageNumber, recipient, text);
    }

    // ===================== Part 1 / 2 =====================

    public boolean checkMessageID() {
        return messageID != null && messageID.matches("\\d{1,10}");
    }

    public String checkRecipientCell() {
        return validateRecipient(recipient);
    }

    public final String createMessageHash() {
        String firstTwo = "";
        if (messageID != null) {
            firstTwo = messageID.substring(0, Math.min(2, messageID.length()));
        }

        String firstWord = "";
        String lastWord = "";

        if (text != null && !text.trim().isEmpty()) {
            String[] words = text.trim().split("\\s+");
            firstWord = cleanWord(words[0]);
            lastWord = cleanWord(words[words.length - 1]);
        }

        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                populate(this, "Sent");
                return "Message successfully sent.";
            case 2:
                populate(this, "Disregard");
                return "Press 0 to delete the message.";
            case 3:
                try {
                    storeMessage();
                    return "Message successfully stored.";
                } catch (IOException e) {
                    return "Unable to store message: " + e.getMessage();
                }
            default:
                return "Invalid option. Please choose 1, 2 or 3.";
        }
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder output = new StringBuilder();
        for (Message message : sentMessages) {
            output.append(message.details()).append(System.lineSeparator());
        }
        return output.toString();
    }

    public static List<String> returnAllMessageHashes() {
        List<String> hashes = new ArrayList<>();
        for (Message message : sentMessages) {
            hashes.add(message.getMessageHash());
        }
        return hashes;
    }

    public static List<String> returnAllMessageHash() {
        return returnAllMessageHashes();
    }

    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    public static String validateMessageLength(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "Message cannot be empty.";
        }
        if (text.length() <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (text.length() - 250)
                + "; please reduce the size.";
    }

    public static String validateRecipient(String number) {
        if (number != null && number.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. "
                + "Please correct the number and try again.";
    }

    public static String generateMessageID() {
        long number = (long) (RANDOM.nextDouble() * 10000000000L);
        return String.format("%010d", number);
    }

    public String details() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + text + "\n";
    }

    // ===================== Part 3: arrays =====================

    /** Adds a message to the correct array. flag = Sent, Stored or Disregard. */
    public static void populate(Message m, String flag) {
        String f = flag.trim().toLowerCase();
        if (f.equals("sent")) {
            sentMessages.add(m);
        } else if (f.equals("stored")) {
            if (!storedMessages.contains(m)) {
                storedMessages.add(m);
            }
        } else if (f.equals("disregard")) {
            disregardedMessages.add(m);
        } else {
            throw new IllegalArgumentException("Unknown flag: " + flag);
        }
        if (!messageHashes.contains(m.messageHash)) {
            messageHashes.add(m.messageHash);
        }
        if (!messageIDs.contains(m.messageID)) {
            messageIDs.add(m.messageID);
        }
    }

    public void storeMessage() throws IOException {
        populate(this, "Stored");
        saveStoredMessages();
    }

    /** Writes the stored messages array to the JSON file. */
    public static void saveStoredMessages() throws IOException {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            json.append("  {\n");
            json.append("    \"messageID\": \"").append(escape(m.messageID)).append("\",\n");
            json.append("    \"messageNumber\": ").append(m.messageNumber).append(",\n");
            json.append("    \"messageHash\": \"").append(escape(m.messageHash)).append("\",\n");
            json.append("    \"recipient\": \"").append(escape(m.recipient)).append("\",\n");
            json.append("    \"message\": \"").append(escape(m.text)).append("\"\n");
            json.append("  }");
            if (i < storedMessages.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]\n");

        Files.write(Paths.get(STORE_FILE), json.toString().getBytes(StandardCharsets.UTF_8));
    }

    /** Reads the JSON file back into the stored messages array. */
    public static void loadStoredMessages() {
        Path path = Paths.get(STORE_FILE);
        if (!Files.exists(path)) {
            return;
        }
        try {
            String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            Pattern objectPattern = Pattern.compile("\\{(?:\"(?:\\\\.|[^\"\\\\])*\"|[^{}\"])*\\}");
            Matcher objects = objectPattern.matcher(json);
            while (objects.find()) {
                String obj = objects.group();
                String id = stringField(obj, "messageID");
                String number = numberField(obj, "messageNumber");
                String recipient = stringField(obj, "recipient");
                String message = stringField(obj, "message");
                if (id.isEmpty() || number.isEmpty() || messageIDs.contains(id)) {
                    continue;
                }
                populate(new Message(id, Integer.parseInt(number), recipient, message), "Stored");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not read stored messages: " + e.getMessage());
        }
    }

    // a. Display the sender and recipient of all stored messages
    public static String displaySenderAndRecipient() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: ").append(SENDER)
              .append(" | Recipient: ").append(m.recipient).append("\n");
        }
        return sb.toString();
    }

    // b. Display the longest stored message
    public static String longestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        Message longest = storedMessages.get(0);
        for (Message m : storedMessages) {
            if (m.text.length() > longest.text.length()) {
                longest = m;
            }
        }
        return longest.text;
    }

    // c. Search for a message ID (returns null if not found)
    public static Message searchByMessageID(String id) {
        List<Message> all = new ArrayList<>(sentMessages);
        all.addAll(storedMessages);
        all.addAll(disregardedMessages);
        for (Message m : all) {
            if (m.messageID.equals(id)) {
                return m;
            }
        }
        return null;
    }

    // d. Search all messages sent or stored for a particular recipient
    public static List<String> searchByRecipient(String recipient) {
        List<String> results = new ArrayList<>();
        for (Message m : sentMessages) {
            if (m.recipient.equals(recipient)) {
                results.add(m.text);
            }
        }
        for (Message m : storedMessages) {
            if (m.recipient.equals(recipient)) {
                results.add(m.text);
            }
        }
        return results;
    }

    // e. Delete a stored message using its hash
    public static String deleteByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.messageHash.equals(hash)) {
                storedMessages.remove(i);
                messageHashes.remove(m.messageHash);
                messageIDs.remove(m.messageID);
                String shown = m.text.replaceAll("[.]$", "");
                return "Message: \"" + shown + "\" successfully deleted.";
            }
        }
        return "Message with hash " + hash + " not found.";
    }

    // f. Report listing the full details of all stored messages
    public static String displayReport() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder("===== STORED MESSAGES REPORT =====\n");
        for (Message m : storedMessages) {
            sb.append(m.details()).append("----------------------------------\n");
        }
        return sb.toString();
    }

    // ===================== Getters =====================

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getText() { return text; }

    public static List<Message> getSentMessages() { return sentMessages; }
    public static List<Message> getDisregardedMessages() { return disregardedMessages; }
    public static List<Message> getStoredMessages() { return storedMessages; }
    public static List<String> getMessageHashes() { return messageHashes; }
    public static List<String> getMessageIDs() { return messageIDs; }

    public static void clearAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
    }

    // ===================== Helpers =====================

    private static String cleanWord(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private static String unescape(String value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '\\' && i + 1 < value.length()) {
                i++;
                char next = value.charAt(i);
                if (next == 'n') {
                    sb.append('\n');
                } else if (next == 'r') {
                    sb.append('\r');
                } else {
                    sb.append(next);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String stringField(String obj, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"").matcher(obj);
        if (m.find()) {
            return unescape(m.group(1));
        }
        return "";
    }

    private static String numberField(String obj, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+)").matcher(obj);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}

   
  
