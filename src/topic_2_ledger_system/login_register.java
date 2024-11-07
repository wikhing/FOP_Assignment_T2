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
        
        if(typeOfUser != 1 || typeOfUser != 2){
            System.out.println("Error! Please login or register again!");
            initialize();
        }
        
        
    }
    
    private int promptUser(int typeOfUser){
        System.out.print("== Khing's Ledger System ==\nLogin or Register:\n1. Login\n2. Register\n\n>");
        
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
        
        System.out.println("Confirmation password: ");
        String confirmationPassword = input.nextLine();
        
        boolean passwordMatch = comfirmPassword(password, confirmationPassword);
        if (passwordMatch == false)
            password = incorrectPassword(password, confirmationPassword);
    }
    
    private static boolean comfirmPassword(String password, String confirmationPassword) {
        if (password.equals(confirmationPassword)) {
            return true;
        } else {
            return false;
        }
    }
    
    private static String incorrectPassword(String password, String confirmationPassword) {
        Scanner input = new Scanner(System.in);
        while (!password.equals(confirmationPassword)) {
            System.out.println("Password didn't match");
            System.out.println("Password: ");
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
