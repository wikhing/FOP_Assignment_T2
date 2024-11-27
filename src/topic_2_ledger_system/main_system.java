/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
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
    
    
    public static void loginPage(int user_id) {
        ArrayList<String[]> user_csv = file.get_user_csv();
        String username = user_csv.get(user_id)[1];
        String email = user_csv.get(user_id)[2];
        
        printMenu(username, file.get_accbalance_csv(user_id), get_saving, get_loan)
    }
    
    
    public static void main(String[] args) throws IOException{
        
        int user_id = login_register.initialize();
        
        loginPage(user_id);
        
        
        //Call the method for Transaction History
        try {
            // Initialize login or registration process
            user_id = login_register.initialize();

            // Get the month and year from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the month and year (MM-YYYY): ");
            String monthYear = scanner.nextLine();
            view_export_csv.viewAndExportTransactions(user_id, monthYear);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}