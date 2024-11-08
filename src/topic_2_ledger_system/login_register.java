/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
/**
 * Write your name here
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun
 *
 */
public class login_register {
    private static Scanner input = new Scanner(System.in);
    
    public static void initialize() throws IOException{
        int typeOfUser = promptUser();
        
        while(typeOfUser != 1 && typeOfUser != 2){
            System.out.println("\nError! Please login or register again!\n");
            typeOfUser = promptUser();
        }
        
        loginORregister(typeOfUser);
        
    }
    
    private static int promptUser(){
        System.out.print("== The One Ledger System ==\nLogin or Register:\n1. Login\n2. Register\n\n> ");
        
        int typeOfUser = input.nextInt();
        
        return typeOfUser;
    }
    
    private static void loginORregister(int typeOfUser) throws IOException{
        if(typeOfUser == 2){
            System.out.println("\n== Please fill in the form ==");
        
            System.out.print("Name: ");
            input.nextLine();
            String user_name = input.nextLine();
            
            System.out.print("Email: ");
            String e_mail = input.next();
            
            while (true) {
                System.out.print("Password: ");
                String password = input.next();
                
                boolean passwordIsValid = isPasswordComplex(password);
                if (!passwordIsValid) {
                    System.out.println("\nPassword is too simple.\nMinimum password length is 8 include with uppercase, lowercase, number and special character\n");
                    continue;
                }
                
                System.out.print("Confirmation password: ");
                String confirmationPassword = input.next();
            
                boolean passwordMatch = confirmPassword(password, confirmationPassword);
                
                if (!passwordMatch) {
                    System.out.println("\nPassword does not match! Please enter again.\n");
                    continue;
                }
                
                List<String[]> allUser = file.get_user_csv();
                allUser.add(new String[]{"", user_name, e_mail, password});
                file.set_user_csv(allUser);
                System.out.println("\nRegister successful!");
                break;
            }
        }else{
            System.out.println("\n== Please enter your email and password ==");
            
            System.out.print("Email: ");
            String e_mail = input.nextLine();
            input.next();
            System.out.print("Password: ");
            String password = input.nextLine();
            
            boolean hasAcc = compareUser(new String[]{e_mail, password});
            
            
        }

        
    }
    
    private static boolean confirmPassword(String password, String confirmationPassword) {
        return password.equals(confirmationPassword);
    }
    
    private static boolean isPasswordComplex(String password) {
        char pwdCheck;
        int upperCaseCount = 0, lowerCaseCount = 0, numCount = 0,specialCharCount = 0;
        int minLength = 8;
                    
        for (int i = 0; i< password.length(); ++i){
                
            pwdCheck = password.charAt(i);
            
            if ( pwdCheck >= 'A' && pwdCheck <= 'Z') {
                // Check for uppercase
                upperCaseCount++;
            } else if ( pwdCheck >= 'a' && pwdCheck <= 'z') {
                // Check for lowercase
                lowerCaseCount++;
            } else if ( pwdCheck - '0' >= 0 && pwdCheck - '0' <= 9) {
                // Check for numbers
                numCount++;
            } else if ( (pwdCheck - '!' >= 0 && pwdCheck - '!' <= 93)) {
                // Check for special characters
                specialCharCount++;
            }
            
        }

        return (password.length() >= minLength && upperCaseCount >= 1 && lowerCaseCount >= 1 && numCount >= 1 && specialCharCount >= 1);  
    }
    
    private static boolean validateEmail(String e_mail) {   
        return true;
    }
    
    private static boolean compareUser(String[] userLogin){
        return true;
    }
}
