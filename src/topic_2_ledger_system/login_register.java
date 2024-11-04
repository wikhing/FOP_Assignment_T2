/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.util.Scanner;
/**
 *
 * @author Wong Ing Khing
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
        
        
    }
}
