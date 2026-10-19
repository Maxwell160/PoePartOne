/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepartone;

/**
 *
 * @author Student
 */
class Registration { //start of class

    String userName;
    String passWord;
    String cellPhone;

    public boolean checkUserName(String username) {//start of user name method
        if (username.contains("@") && username.length() >= 8) {
            //start for if
            return true;
        } else {
            return false;

        }
    }//end of user name method

    public boolean checkPasswordComplexity(String password) { //start of password method
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[^a-zA-Z0-9].*");

    } //end of password method

    public boolean checkCellPhoneNumber(String cellphone) { //start of cellphone method
        if (cellphone.matches(".*\\+27[0-9]{9}$")
                || cellphone.matches(".*^0[0-9]{9}")) {
            return true;
        }
    

    
        else {
        return false;
        }
    }

    public String registeruser(String username, String password, String cellphone) { //start of cellphone method

        if (checkUserName(username)
                && checkPasswordComplexity(password)
                && checkCellPhoneNumber(cellphone)) {

            this.userName = username;
            this.passWord = password;
            this.cellPhone = cellphone;

            return (" Registration successfull"); //add comment 
        } else {
            return ("Registration was unsuccessful");

        }
    }// end of cellphone ,method

    public boolean registrationstatus(String username, String password, String cellphone) {
        return checkUserName(username)
                && checkPasswordComplexity(password)
                && checkCellPhoneNumber(cellphone);
    }

}//end of class

