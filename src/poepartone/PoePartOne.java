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
        String username = null;
        
        String loginResult = register.returnLoginStatus(register.loginUser(username, passWord));
        System.out.println(loginResult);
        
        if (loginResult.contains("successful")){
            Message msg = new Message();
            Scanner menuScan = new Scanner(System.in);
            int option;
            
            do{
                System.out.println("=====Select any of the options below=====");
                System.out.println("Option 1 -> Send Message");
                System.out.println("Option 2 -> Coming soon");
                System.out.println("Option 3 -> Exit");
                option = menuScan.nextInt();
                
                switch (option){
                    case 1 -> msg.msg_sendingMessages();
                    case 2 -> System.out.println("Coming soon");
                    case 3 -> System.out.println("Thank you for using app");
                    default -> System.out.println("Invalid option");
                            }
            }while (option != 3);
    
        } 
                
        
        
        
    }
        

        
         
    }
    
    

