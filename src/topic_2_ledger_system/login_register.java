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
        
        while (true) {
            System.out.println("Password: ");
            String password = input.nextLine();

            System.out.println("Confirmation password: ");
            String confirmationPassword = input.nextLine();
            
            boolean passwordMatch = confirmPassword(password, confirmationPassword);
            boolean passwordIsValid = isPasswordComplex(password);
            
            if (passwordMatch && passwordIsValid) {
                break;
            } 
            
            if (!passwordMatch) {
                continue;
            }
            
            if (!passwordIsValid) {
                System.out.println("Password is too simple.");
                continue;
            }
        }
        
    }
    
    private static boolean isPasswordComplex(String password) {
        char pwdCheck;
        int upperCaseCount = 0, lowerCaseCount = 0, numCount = 0,specialCharCount = 0;
        int minLength = 8;
        
            
        for (int i = 0; i< password.length(); ++i){
                
            pwdCheck = password.charAt(i);
            
            if ( pwdCheck >= 'A' && pwdCheck <= 'Z') {
                // Check for uppercase
                ++upperCaseCount;
            } else if ( pwdCheck >= 'a' && pwdCheck <= 'z') {
                // Check for lowercase
                ++lowerCaseCount;
            } else if ( pwdCheck >= '0' && pwdCheck <= '9') {
                // Check for numbers
                ++numCount;
            } else if ( (pwdCheck >= '!' && pwdCheck <= '~')) {
                // Check for special characters
                ++specialCharCount;
            }
            
        }
        
        return (password.length() >= minLength && upperCaseCount >= 1 && lowerCaseCount >=1 && numCount >= 1 && specialCharCount >=1);  
    }
    
    private static boolean confirmPassword(String password, String confirmationPassword) {
        return password.equals(confirmationPassword);
    }
    
    private boolean validateEmail(String e_mail) {   
        return true;
    }
}
//7/11
