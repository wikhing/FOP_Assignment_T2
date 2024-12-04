/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
/**
 * Write your name here
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun
 *
 */
public class login_register {
    private static Scanner input = new Scanner(System.in);
    
    public static int initialize() throws IOException{

        int typeOfUser = promptUser();
        
        while(typeOfUser != 1 && typeOfUser != 2){
            System.out.println("\nError! Please login or register again!\n");
            typeOfUser = promptUser();
        }
        
        int user_id = -1;
        while(user_id == -1){
            user_id = loginORregister(typeOfUser);
            
            if(user_id == -1) typeOfUser = promptUser();
        }
        
        return user_id;
    }
    
    private static int promptUser(){
        System.out.print("\n== The One Ledger System ==\nLogin or Register:\n1. Login\n2. Register\n\n> ");
        
        int typeOfUser = input.nextInt();
        
        return typeOfUser;
    }
    
    private static int loginORregister(int typeOfUser) throws IOException{
        
        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToday = date.format(pattern);
        
        if(typeOfUser == 2){
            System.out.println("\n== Please fill in the form ==");

            System.out.print("Name: ");
            input.nextLine();
            String user_name = input.nextLine();

            String e_mail = "";
            while(true){
                System.out.print("Email: ");
                e_mail = input.next();
                
                boolean validEmailFormat = validateEmail(e_mail);
                
                if(!validEmailFormat){
                    System.out.println("\nInvalid e-mail format!");
                    System.out.println("Please enter your e-mail again.\n");
                    System.out.println("Name: " + user_name);
                    continue;
                }
                
                break;
            }
            
            while (true) {
                System.out.print("Password: ");
                String password = input.next();

                boolean passwordIsValid = isPasswordComplex(password);
                if (!passwordIsValid) {
                    System.out.println("\nPassword is too simple.\nMinimum password length is 8 include with uppercase, lowercase, number and special character\n");
                    System.out.println("Name: " + user_name);
                    System.out.println("Email: " + e_mail);
                    continue;
                }

                System.out.print("Confirmation password: ");
                String confirmationPassword = input.next();
                boolean passwordMatch = confirmPassword(password, confirmationPassword);
                if (!passwordMatch) {
                    System.out.println("\nPassword does not match! Please enter again.\n");
                    System.out.println("Name: " + user_name);
                    System.out.println("Email: " + e_mail);
                    continue;
                }

                List<String[]> allUser = file.get_user_csv();
                String hashedPassword = bcrypt.hashpw(password, bcrypt.gensalt());
                allUser.add(new String[]{"", user_name, e_mail, hashedPassword, dateToday});
                file.set_user_csv(allUser);
                file.new_user_acc_setup(allUser.size() - 1);
                System.out.println("\nRegister successful!");
                break;
            }
        }else{
            System.out.println("\n== Please enter your email and password ==");

            int handleEnter = 0;
            int isUser = -1;
            do{
                System.out.print("Email: ");
                if(handleEnter == 0){
                    input.nextLine();
                    handleEnter++;
                }
                String e_mail = input.nextLine();
                if(e_mail.equals("-1")){
                    promptUser();
                    break;
                }

                System.out.print("Password: ");
                String password = input.nextLine();

                isUser = compareUser(new String[]{e_mail, password});

                if(isUser == -1){
                    System.out.println("\nE-mail or Password is incorrect!");
                    System.out.println("To exit to main menu, please enter -1\n");
                    System.out.println("Please re-enter your email and password again.");
                }
            }while(isUser == -1);
            
            System.out.println("\nLogin Successful!\n");
            
            return isUser;
        }
        return -1;
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
        return !(!e_mail.contains("@") || !e_mail.contains(".com") || e_mail.indexOf("@") + 1 == e_mail.indexOf(".com"));
    }
    
    private static int compareUser(String[] loginInfo) throws IOException{
        List<String[]> users = file.get_user_csv();

        for(int i = 1; i < users.size(); i++){
            if(users.get(i)[2].equals(loginInfo[0]) && bcrypt.checkpw(loginInfo[1], users.get(i)[3])){
                return Integer.parseInt(users.get(i)[0]);
            }
        }
        
        return -1;
    }
}
