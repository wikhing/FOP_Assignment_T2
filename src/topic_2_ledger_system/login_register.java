/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.util.Scanner;
/**
 * 
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun
 *
 */
public class login_register {
    private Scanner input = new Scanner(System.in);
    
    public void initialize(){
        int typeOfUser = -1;
        promptUser(typeOfUser);
        
        while(typeOfUser != 1 || typeOfUser != 2){
            System.out.println("Error! Please login or register again!\n");
            promptUser(typeOfUser);
        }
        
    }
    
    private int promptUser(int typeOfUser){
        System.out.print("== The One Ledger System ==\nLogin or Register:\n1. Login\n2. Register\n\n>");
        
        typeOfUser = input.nextInt();
        
        return typeOfUser;
    }
    
    private void loginORregister(int typeOfUser){
        System.out.println("== Please fill in the form ==");
        
        System.out.println("Name: ");
        String user_name = input.nextLine();
        
        System.out.println("Email: ");
        String e_mail = input.nextLine();
        
        System.out.println("Password: ");
        String password = input.nextLine();
        
        boolean passwordValid = passwordValidity(password);
        while (passwordValid == false){
            invalidPassword(password);                  //reenter password
            passwordValid = passwordValidity(password);
        }    
        
        System.out.println("Confirmation password: ");
        String confirmationPassword = input.nextLine();
        
        boolean passwordMatch = comfirmPassword(password, confirmationPassword);
        if (passwordMatch == false){
            password = unmatchedPassword(password, confirmationPassword);
        }
    }
    
    private static boolean passwordValidity(String password) {
        char pwdCheck;
        int upperCaseCount = 0, lowerCaseCount = 0, numCount = 0,specialCharCount = 0;
        int minLength = 8;
        
            
        for (int i = 0; i< password.length(); ++i){
                
            pwdCheck = password.charAt(i);
                
            if ( pwdCheck >= 'A' && pwdCheck <= 'Z'){
                ++ upperCaseCount;
            }
                
            else if ( pwdCheck >= 'a' && pwdCheck <= 'z'){
                    ++ lowerCaseCount;
            }
                
            else if ( pwdCheck >= '0' && pwdCheck <= '9'){
                    ++ numCount;
            }
                
            else if ( (pwdCheck >= '!' && pwdCheck <= '~')){
                    ++specialCharCount;
            }
            }
        
        
        if (password.length() >= minLength && upperCaseCount >= 1 && lowerCaseCount >=1 && numCount >= 1 && specialCharCount >=1){
            return true;
        }
        else{
            return false;
                    
        } 
        
    }
    
    private static String invalidPassword(String password){
        Scanner input = new Scanner (System.in);
        System.out.println("Invalid Password");
        System.out.print("Password: ");
        password = input.nextLine();
        return password;
    }
    
    private static boolean comfirmPassword(String password, String confirmationPassword) {
        if (password.equals(confirmationPassword)) {
            return true;
        } else {
            return false;
        }
    }
    
    private static String unmatchedPassword(String password, String confirmationPassword) {
        Scanner input = new Scanner(System.in);
        while (!password.equals(confirmationPassword)){
            System.out.println("Password didn't match");
            System.out.println("Password: ");
            boolean passwordValid = passwordValidity(password);
            while (passwordValid == false){
                password = invalidPassword(password);
                passwordValid = passwordValidity(password);
            }
        
            password = input.nextLine();
        
            System.out.println("Confirmation password: ");
            confirmationPassword = input.nextLine();
        }
            return password;
    }
    
 
    
    private boolean validateEmail(String e_mail) {
        
        return true;
    }
}
