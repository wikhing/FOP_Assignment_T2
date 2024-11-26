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
    public static void main(String[] args) throws IOException{
        login_register.initialize();
        
        //Call the method for Transaction History
        try {
            // Initialize login or registration process
            int user_id = login_register.initialize();

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