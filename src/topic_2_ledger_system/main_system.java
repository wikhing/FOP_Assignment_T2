/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Write your name here
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun
 *
 */
public class main_system {

    /**
     * @param args the command line arguments
     */
    
    
    
    public static void printMenu(String username, double balance, double saving, double loan) {
            System.out.println("\n\n== Welcome, " + username + " ==");
            System.out.print("Balance: " );
            System.out.printf("%.2f",balance);
            System.out.print("\nSavings: ");
            System.out.printf("%.2f", saving);
            System.out.print("\nLoan: ");
            System.out.printf("%.2f",loan);

            System.out.print("\n\n== Transaction ==");
            System.out.print("\n1. Debit");
            System.out.print("\n2. Credit");
            System.out.print("\n3. History");
            System.out.print("\n4. Savings");
            System.out.print("\n5. Credit Loan");
            System.out.print("\n6. Deposit Interest Predictor");
            System.out.print("\n7. Logout");
            System.out.print("\n>");
    }
    
    
    private static void debit(double balance) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("== Debit ==");
            System.out.print("Enter amount: ");
            double amount = sc.nextDouble();
            if (amount > 0 && amount < (Math.pow(10, 9)))
                balance += amount;
            else{
                System.out.print("Enter again the amount: ");
                amount = sc.nextDouble();
            }

            System.out.print("\nEnter transaction date(yyy-mm-dd): ");
            sc.nextLine();
            String dateInput = sc.nextLine();
            LocalDate transactionDate;

            try {
                transactionDate = LocalDate.parse(dateInput);
            } catch (Exception e){
                System.out.println("Error: Invalid date.");
                continue;
            }

            LocalDate today = LocalDate.now();
            if(transactionDate.isAfter(today)){
                System.out.print("\nError: Transaction date cannot be in the future.");
                continue;
            }

            System.out.print("Enter description: ");
            sc.nextLine();
            String description = sc.nextLine();
            if (description.length() >100){
                System.out.println("Error: Description exceeds 100 characters. ");
                System.out.print("\nEnter description: ");
                description = sc.nextLine();
                System.out.print("\nDebit Successfully Recorded!!!");
            } else {
                System.out.print("\nDebit Successfully Recorded!!!");
            }
        }
        
    }
    
    private static void credit() {
        
    }
    
    private static void history() {
        
    }
    
    private static void saving() {
        
    }
    
    private static void creditLoan() {
        
    }
    
    private static void depositInterestPredictor() {
        
    }
    
    private static void logOut() {
        
    }
    
    
    
    
    private static void optionHandler(int option) {
        
        switch(option) {  
            case 1:
                debit();
                break;
            case 2:
                credit();
                break; 
            case 3:
                history();
                break;
            case 4:
                saving();
                break;
            case 5:
                creditLoan();
                break;
            case 6:
                depositInterestPredictor();
                break;
            case 7:
                logOut();
                break;
            default:
                break;
        }
    }
    
    
    
    public static void loginPage(int user_id) {
        ArrayList<String[]> user_csv = file.get_user_csv();
        String username = user_csv.get(user_id)[1];
        String email = user_csv.get(user_id)[2];
        
        printMenu(username, file.get_accbalance_csv(user_id), 0, 0);
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.print(">");
            String rawOption = sc.next();
            int option;
            
            try {
                option = Integer.parseInt(rawOption);
                
                if (!(option >= 1 && option <= 7)) {
                    System.out.println("Invalid option, please retry.");
                    continue;
                }
                
                optionHandler(option);
                
                break;
            } catch (Exception e) {
                System.out.println("Invalid option, please retry.");
            } 
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        
        int user_id = login_register.initialize();
        
        loginPage(user_id);
        
        
        
    }
}