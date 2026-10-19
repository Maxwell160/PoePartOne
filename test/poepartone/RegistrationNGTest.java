/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package poepartone;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Student
 */
public class RegistrationNGTest {
    
    public RegistrationNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of checkUserName method, of class Registration.
     */
    @Test
    public void testCheckUserName() {
        System.out.println("checkUserName");
        String username = "";
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.checkUserName(username);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.        fail("The test case is a prototype.");
    }

    /**
     * Test of checkPasswordComplexity method, of class Registration.
     */
    @Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        String password = "";
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of checkCellPhoneNumber method, of class Registration.
     */
    @Test
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");
        String cellphone = "";
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber(cellphone);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of registerUser method, of class Registration.
     */
    @Test
    public void testRegisteruser() {
        System.out.println("registeruser");
        String username = "";
        String password = "";
        String cellphone = "";
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.registeruser(username, password, cellphone);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of registrationStatus method, of class Registration.
     */
    @Test
    public void testRegistrationstatus() {
        System.out.println("registrationstatus");
        String username = "";
        String password = "";
        String cellphone = "";
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.registrationstatus(username, password, cellphone);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
