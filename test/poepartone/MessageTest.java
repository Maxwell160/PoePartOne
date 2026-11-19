/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package poepartone;

import com.mycompany.chatapp.Message;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MessageTest {

    private static final Path STORE_PATH = Paths.get("storedMessages.json");
    private static byte[] originalStoreFile = null;

    // Part 1 / 2 test messages
    private Message msg1;
    private Message msg2;

    // Part 3 test data messages (from the brief)
    private Message m1;
    private Message m2;
    private Message m3;
    private Message m4;
    private Message m5;

    // Back up the real JSON file so the tests do not destroy your stored messages
    @BeforeClass
    public static void backupStoreFile() throws IOException {
        if (Files.exists(STORE_PATH)) {
            originalStoreFile = Files.readAllBytes(STORE_PATH);
        }
    }

    @AfterClass
    public static void restoreStoreFile() throws IOException {
        if (originalStoreFile != null) {
            Files.write(STORE_PATH, originalStoreFile);
        } else {
            Files.deleteIfExists(STORE_PATH);
        }
    }

    @BeforeMethod
    public void setUp() {
        Message.clearAll();

        msg1 = new Message("0012345678", 0, "+27718693002",
                "Hi Max, can you join us for dinner tonight?");
        msg2 = new Message("0098765432", 1, "08575975889",
                "Hi MKhabela, did you receive the payment?");

        m1 = new Message("1000000001", 1, "+27834557896", "Did you get the cake?");
        m2 = new Message("1000000002", 2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        m3 = new Message("1000000003", 3, "+27834484567", "Yohoooo, I am at your gate.");
        m4 = new Message("0838884567", 4, "0838884567", "It is dinner time!");
        m5 = new Message("1000000005", 5, "+27838884567", "Ok, I am leaving without you.");
    }

    private static String repeatA(int count) {
        return new String(new char[count]).replace('\0', 'a');
    }

    // Loads test messages 1-4 into the arrays using their flags
    private void loadTestData() {
        Message.populate(m1, "Sent");
        Message.populate(m2, "Stored");
        Message.populate(m3, "Disregard");
        Message.populate(m4, "Sent");
    }

    // ===================== Part 1 / 2 tests =====================

    @Test
    public void messageLength_success() {
        Assert.assertEquals(Message.validateMessageLength(repeatA(250)),
                "Message ready to send.");
    }

    @Test
    public void messageLength_failure() {
        Assert.assertEquals(Message.validateMessageLength(repeatA(260)),
                "Message exceeds 250 characters by 10; please reduce the size.");
    }

    @Test
    public void recipient_success() {
        Assert.assertEquals(msg1.checkRecipientCell(),
                "Cell phone number successfully captured.");
    }

    @Test
    public void recipient_failure() {
        Assert.assertEquals(msg2.checkRecipientCell(),
                "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.");
    }

    @Test
    public void messageHash_correct() {
        Assert.assertEquals(msg1.getMessageHash(), "00:0:HITONIGHT");
        Assert.assertEquals(msg2.getMessageHash(), "00:1:HIPAYMENT");
    }

    @Test
    public void messageID_created() {
        Assert.assertTrue(msg1.checkMessageID());
        Assert.assertEquals("Message ID generated: " + msg1.getMessageID(),
                "Message ID generated: 0012345678");
    }

    @Test
    public void generatedID_isTenDigits() {
        Message message = new Message(0, "+27718693002", "Hello there");
        Assert.assertTrue(message.getMessageID().matches("\\d{10}"));
    }

    @Test
    public void sentMessage_send() {
        Assert.assertEquals(msg1.sentMessage(1), "Message successfully sent.");
    }

    @Test
    public void sentMessage_disregard() {
        Assert.assertEquals(msg2.sentMessage(2), "Press 0 to delete the message.");
    }

    @Test
    public void sentMessage_store() {
        Assert.assertEquals(msg1.sentMessage(3), "Message successfully stored.");
    }

    @Test
    public void totalMessages_countsOnlySent() {
        msg1.sentMessage(1);
        msg2.sentMessage(2);
        Assert.assertEquals(Message.returnTotalMessages(), 1);
    }

    @Test
    public void printMessages_listsSent() {
        msg1.sentMessage(1);
        String out = Message.printMessages();
        Assert.assertTrue(out.contains("00:0:HITONIGHT"));
        Assert.assertTrue(out.contains("+27718693002"));
    }

    // ===================== Part 3 tests (from the brief) =====================

    // Test: Sent Messages array correctly populated (test data messages 1-4)
    @Test
    public void sentMessagesArray_correctlyPopulated() {
        loadTestData();
        Assert.assertEquals(
                Arrays.asList(
                        Message.getSentMessages().get(0).getText(),
                        Message.getSentMessages().get(1).getText()),
                Arrays.asList("Did you get the cake?", "It is dinner time!"));
    }

    // Test: Display the longest message
    @Test
    public void displayLongestMessage() {
        loadTestData();
        Assert.assertEquals(Message.longestStoredMessage(),
                "Where are you? You are late! I have asked you to be on time.");
    }

    // Test: Search for messageID (message 4, "0838884567")
    @Test
    public void searchForMessageID() {
        loadTestData();
        Message found = Message.searchByMessageID("0838884567");
        Assert.assertNotNull(found);
        Assert.assertEquals(found.getText(), "It is dinner time!");
    }

    // Test: Search all messages sent or stored for a particular recipient
    @Test
    public void searchMessagesForRecipient() {
        loadTestData();
        Message.populate(m5, "Stored");
        Assert.assertEquals(Message.searchByRecipient("+27838884567"),
                Arrays.asList(
                        "Where are you? You are late! I have asked you to be on time.",
                        "Ok, I am leaving without you."));
    }

    // Test: Delete a message using a message hash (test message 2)
    @Test
    public void deleteMessageUsingHash() {
        loadTestData();
        Assert.assertEquals(Message.deleteByHash(m2.getMessageHash()),
                "Message: \"Where are you? You are late! I have asked you to be on time\" successfully deleted.");
        Assert.assertTrue(Message.getStoredMessages().isEmpty());
    }

    // Test: Display report
    @Test
    public void displayReport() {
        loadTestData();
        String report = Message.displayReport();
        Assert.assertTrue(report.contains(m2.getMessageHash()));
        Assert.assertTrue(report.contains(m2.getRecipient()));
        Assert.assertTrue(report.contains(m2.getText()));
    }

    // Extra checks: the five arrays hold the right items
    @Test
    public void disregardedArray_containsMessage3() {
        loadTestData();
        Assert.assertEquals(Message.getDisregardedMessages().size(), 1);
        Assert.assertEquals(Message.getDisregardedMessages().get(0).getText(),
                "Yohoooo, I am at your gate.");
    }

    @Test
    public void hashAndIdArrays_containAllMessages() {
        loadTestData();
        Assert.assertEquals(Message.getMessageHashes().size(), 4);
        Assert.assertEquals(Message.getMessageIDs().size(), 4);
        Assert.assertTrue(Message.getMessageIDs().contains("0838884567"));
    }
}