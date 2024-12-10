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

    public static void exportTransactionsToCSV(List<String[]> transactions, String fileName) {
        // Define the file path 
        String filePath = "src/topic_2_ledger_system/exported/" + fileName + ".csv";
        File file = new File(filePath);
        // Ensure the directory exists
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();  // Create the directory if it doesn't exist
        }

        // Write to the CSV file
        try (PrintWriter writer = new PrintWriter(file)) {
            // Write header
            writer.println("Transaction ID\", \"User ID\", \"Type\", \"Amount\", \"Description\", \"Date\"");

            // Write each transaction row
            for (String[] transaction : transactions) {
                if (transaction.length>=6){
                writer.printf("%s, %s, %s, %s, %s, %s%n",
                            transaction[0], // Transaction ID
                            transaction[1], // User ID
                            transaction[2], // Type
                            transaction[3], // Amount
                            transaction[4], // Description
                            transaction[5]  // Date
                    ); 
                }
            }

            System.out.println("File saved at: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error while exporting transactions: " + e.getMessage());
        }
    }

    
    
    /*public static void main(String[] args) {
        file.viewFullTransactionHistory();
        System.out.println("");
        viewAndExportTransactions(2, "11/2024");
    }*/
    //this main only for testing not sure how to call specific user's user_id
}
