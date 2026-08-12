/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepartone;

/**
 *
 * @author Student
 */
public class Login extends Registration{// start of login class
    
   public boolean loginUser(String name, String password){// start of login method
       if(name.equals(userName) && password.equals(password)){ //start of if statement
         return true;
       }else{
           return false;
           
           
       }
   }// end of if else statement
    public String returnLoginStatus(boolean status){
        if (status){
            return "Login success";
        }else{
            return "Login failed";
        }
    
    }    
//       passWord ="aZ2@"; 
   }//end of login method
    

