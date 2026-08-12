/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poepartone;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class PoePartOne {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Login register = new Login();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\n=====Register=====");
        
        //Username input and checker
        System.out.println("Please enter username: ");
        String name = scan.nextLine();
        
   
        //password input and checker
        System.out.println("Enter password");
        String password = scan.nextLine(); 
        
        //promt user to enter cellphone if incorrect show message
        System.out.println("Enter cellphone number");
        String cellphone = scan.nextLine();
        
        System.out.println(register.registeruser(name, password, cellphone));
        
        if (!register.registrationstatus(name, password, cellphone)){
        return;
        
        }
        
        System.out.println("\n=====Login=====");
        
        System.out.println("Please enter the username to login: ");
        String userName = scan.nextLine();
        
        System.out.println("Enter password to login: ");
        String passWord = scan.nextLine();
        
        System.out.println(register.returnLoginStatus(register.loginUser(userName, passWord)));
        
        
        
        }
        

        
         
    }
    
    

