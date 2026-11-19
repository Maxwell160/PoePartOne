/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package poepartone;

import com.mycompany.chatapp.Message;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class MessageTest {

    private Message msg1;
    private Message msg2;

    @Test 
    void setUp() {
        assertEquals(
        
        Message.clearAll();

        msg1 = new Message(
                "0012345678",
                0,
                "+27718693002",
                "Hi Max, can you join us for dinner tonight?"
        );

        msg2 = new Message(
                "0098765432",
                1,
                "08575975889",
                "Hi MKhabela, did you receive the payment?"
        );
    )
    }

    @Test
    void messageLength_success() {
        assertEquals(
                "Message is ready to send.",
                Message.validateMessageLength("a".repeat(250))
        );
    }

    @Test
    void messageLength_failure() {
        assertEquals(
                "Message exceeds 250 characters ; please reduce the size.",
                Message.validateMessageLength("a".repeat(260))
        );
    }

    @Test
    void recipient_success() {
        assertEquals(
                "Cell phone number successfully captured.",
                msg1.checkRecipientCell()
        );
    }

    @Test
    void recipient_failure() {
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg2.checkRecipientCell()
        );
    }

    @Test
    void messageHash_correct() {
        assertEquals("00:0:HEYTONIGHT", msg1.getMessageHash());
        assertEquals("00:1:HEYPAYMENT", msg2.getMessageHash());
    }

    @Test
    void messageID_created() {
        assertTrue(msg1.checkMessageID());
        assertEquals(
                "Message ID generated: 0012345678",
                "Message ID generated: " + msg1.getMessageID()
        );
    }

    @Test
    void generatedID_isTenDigits() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hello there"
        );

        assertTrue(message.getMessageID().matches("\\d{10}"));
    }

    @Test
    void sentMessage_send() {
        assertEquals(
                "Message successfully sent.",
                msg1.sentMessage(1)
        );
    }

    @Test
    void sentMessage_disregard() {
        assertEquals(
                "Press 0 to delete the message.",
                msg2.sentMessage(2)
        );
    }

    @Test
    void sentMessage_store() {
        assertEquals(
                "Message successfully stored.",
                msg1.sentMessage(3)
        );
    }

    @Test
    void totalMessages_countsOnlySent() {
        msg1.sentMessage(1);
        msg2.sentMessage(2);

        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    void printMessages_listsSent() {
        msg1.sentMessage(1);

        String out = Message.printMessages();

        assertTrue(out.contains("00:0:HEYTONIGHT"));
        assertTrue(out.contains("+27718693002"));
    }


    private void assertEquals(String of, String returnAllMessageHashes) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
