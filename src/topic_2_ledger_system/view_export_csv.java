/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Harry
 */
public class view_export_csv {
    public static List<String[]> getTransactionsByMonth(int user_id, String monthYear) {
        //Retrieve all transactions from the CSV
        List<String[]> allTransactions = file.get_transactions_csv(user_id);

        //Filter transactions by month-year
        List<String[]> filteredTransactions = new ArrayList<>();
        for (String[] transaction : allTransactions) {
            if (transaction.length < 6) continue; // Skip invalid rows
            String transactionDate = transaction[5]; 
            if (transactionDate.endsWith(monthYear)) { 
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }
    
    public static List<String[]> getTransactions(int user_id) {
        //Retrieve all transactions from the CSV
        List<String[]> allTransactions = file.get_transactions_csv(user_id);

        return allTransactions;
    }

    
    
    /*public static void main(String[] args) {
        file.viewFullTransactionHistory();
        System.out.println("");
        viewAndExportTransactions(2, "11/2024");
    }*/
    //this main only for testing not sure how to call specific user's user_id
}
