/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author I Khing
 */
public class FXhistory {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static int user_id = 2;
    public static void set_user(int user_id){
        FXhistory.user_id = user_id;
    }
    
    @FXML
    public void initialize(){
        balanceColumn.setComparator(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Double.compare(Double.parseDouble(a), Double.parseDouble(b));
            }
        });
        dateColumn.setComparator(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {

                //compare year
                if(!a.substring(6).equals(b.substring(6))){
                    return Integer.compare(Integer.parseInt(a.substring(6)), Integer.parseInt(b.substring(6)));
                }
                
                //compare month
                if(!a.substring(3, 5).equals(b.substring(3, 5))){
                    return Integer.compare(Integer.parseInt(a.substring(3, 5)), Integer.parseInt(b.substring(3, 5)));
                }
                
                //compare date
                if(!a.substring(0, 2).equals(b.substring(0, 2))){
                    return Integer.compare(Integer.parseInt(a.substring(0, 2)), Integer.parseInt(b.substring(0, 2)));
                }
                
                return 0; //all equal
            }
        });
        
        history();
    }
    
    @FXML
    public void back() throws IOException{
        main_system.setScene("menu");
        
        menu m = new menu();
        m.update_account_info();
    }
    
    
    
    
    
    
    
    
    @FXML TableColumn balanceColumn = new TableColumn();
    @FXML TableColumn typeColumn = new TableColumn();
    @FXML TableColumn descriptionColumn = new TableColumn();
    @FXML TableColumn dateColumn = new TableColumn();
    
    @FXML TableView<Transaction> histories = new TableView<Transaction>();
    @FXML TextField monthFilter;
    @FXML TextField yearFilter;
    
    @FXML Label his_info = new Label();
    
    @FXML
    public void history() {
        ObservableList<Transaction> data = histories.getItems();
        data.clear();
        his_info.setText("");
        
        String monthYear = monthFilter.getText() + "/" + yearFilter.getText();                      //StringBuilder?
        
        if(monthYear.equals("/")){
            viewFullTransactionHistory();
            return;
        }
        
        // In case of invalid month/year input
        if (!monthYear.matches("\\d{2}+/\\d{4}+")) {
            his_info.setText("Invalid format. Please use MM/YYYY and fill in all section");
            return;
        }
        
        viewAndExportTransactions(user_id, monthYear);
    }
    
    @FXML
    public void viewAndExportTransactions(int user_id, String monthYear) {
        List<String[]> transactions = view_export_csv.getTransactionsByMonth(user_id, monthYear);

        // In case no transaction history for specific user at that certain session
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for the given month and year.");
            return;
        }
        
        ObservableList<Transaction> data = histories.getItems();
        
        
        for (String[] row : transactions) {
            if(row.length == 1) continue;
            data.add(new Transaction(row[3], row[2], row[4], row[5]));
        }
        

//        // Enhanced part:Let user to determine whether want to export scv file
//        System.out.print("Export to CSV? (yes/no): ");
//        Scanner sc = new Scanner(System.in);
//        if (sc.nextLine().equalsIgnoreCase("yes")) {
//            view_export_csv.exportTransactionsToCSV(transactions, "TransactionHistory_" + monthYear.replace("-", "_"));
//            System.out.println("Transactions exported successfully!");
//        }
    }
    
    @FXML
    // View overall transaction history
    public void viewFullTransactionHistory() {
        // Fetch all transactions
        List<String[]> allTransactions = view_export_csv.getTransactions(user_id);

        if (allTransactions.isEmpty()) {
            his_info.setText("No transactions found.");
            return;
        }
        
        ObservableList<Transaction> data = histories.getItems();
        
        
        // Display each transaction
        for (String[] transaction : allTransactions) {
            if(transaction.length != 6) continue;
            data.add(new Transaction(df.format(Double.parseDouble(transaction[3])), transaction[2], transaction[4], transaction[5]));
        }  
    }
    
//    private static void filterhistory(List<String[]> transactions){
//        
//        System.out.println();
//        
//        // Filter options: Amount Range, Date Range, Transaction Type
//        int filterOption = 0; // Initialize variable to store user's choice
//        while (true) {
//            System.out.println();
//            System.out.println("Filter Options:");
//            System.out.println("1. Filter by Amount Range");
//            System.out.println("2. Filter by Date Range");
//            System.out.println("3. Filter by Transaction Type (debit/credit)");
//            System.out.print("Enter filter option (1-3): ");
//
//            if (sc.hasNextInt()) { // Check if the input is an integer
//                filterOption = sc.nextInt();
//                sc.nextLine(); // Consume newline
//
//                if (filterOption >= 1 && filterOption <= 3) {
//                    // Valid option, exit the loop
//                    break;
//                } else {
//                    System.out.println("Invalid option. Please enter a number between 1 and 3.");
//                }
//            } else {
//                // Clear invalid input
//                System.out.println("Invalid input. Please enter a valid number.");
//                sc.nextLine(); // Consume the invalid input
//            }
//        }
//        
//        // Initialize filter variables
//        Double minAmount = null;
//        Double maxAmount = null;
//        LocalDate startDate = null;
//        LocalDate endDate = null;
//        String transactionType = "";
//
//        // Amount Range Filter
//        if (filterOption == 1 ) {
//            System.out.print("Enter minimum amount: ");
//            minAmount = sc.nextDouble();
//            System.out.print("Enter maximum amount: ");
//            maxAmount = sc.nextDouble();
//            sc.nextLine(); // Consume newline
//        }
//
//        // Date Range Filter
//        if (filterOption == 2 ) {
//            System.out.print("Enter start date (dd/MM/yyyy): ");
//            String startDateInput = sc.nextLine();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//            while (startDate == null) {
//                try {
//                    // Try to parse the date
//                    startDate = LocalDate.parse(startDateInput,formatter);
//                } catch (Exception e) {
//                    // If the date format is invalid, inform the user and ask again
//                    System.out.println("Invalid start date. Please use the correct format (dd/MM/yyyy).");
//                    System.out.print("Enter start date (dd/MM/yyyy): ");
//                    startDateInput = sc.nextLine();  // Prompt the user again for the date
//                }
//            }
//
//            System.out.print("Enter end date (dd/MM/yyyy): ");
//            String endDateInput = sc.nextLine();
//
//            while (endDate == null) {
//                try {
//                    // Try to parse the date
//                    endDate = LocalDate.parse(endDateInput, formatter);
//                } catch (Exception e) {
//                    // If the date format is invalid, inform the user and ask again
//                    System.out.println("Invalid end date. Please use the correct format (dd/MM/yyyy).");
//                    System.out.print("Enter end date (dd/MM/yyyy): ");
//                    endDateInput = sc.nextLine();  // Prompt the user again for the date
//                }
//            }
//        }
//
//        // Transaction Type Filter
//        if (filterOption == 3 ) {
//            String transactionTypeInput = "";
//            while (true) {
//                System.out.print("Enter transaction type (debit/credit): ");
//                transactionTypeInput = sc.nextLine().toLowerCase(); // Accept lowercase
//                if (transactionTypeInput.equals("debit") || transactionTypeInput.equals("credit")) {
//                    transactionType = transactionTypeInput;
//                    break;
//                } else {
//                    System.out.println("Invalid input. Please enter 'debit' or 'credit'.");
//                }
//            }
//        }
//
//        // Filtering transactions
//        List<String[]> filteredTransactions = new ArrayList<>();
//        for (String[] transaction : transactions) {
//            try {
//                // Ensure the transaction has at least 6 elements (e.g., date, amount, type)
//                if (transaction.length < 6) {
//                    System.out.println("Skipping malformed transaction: " + String.join(", ", transaction));
//                    continue;
//                }
//
//                // Parsing date and amount for comparison
//                LocalDate transactionDate = LocalDate.parse(transaction[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                double amount = Double.parseDouble(transaction[3]);  // Converts string to double
//                String type = transaction[2].toLowerCase();
//
//                // Filter by date range (if provided)
//                if (startDate != null && transactionDate.isBefore(startDate)) {
//                    continue;  // Skip transaction if before the start date
//                }
//                if (endDate != null && transactionDate.isAfter(endDate)) {
//                    continue;  // Skip transaction if after the end date
//                }
//
//                // Filter by amount range (if provided)
//                if (minAmount != null && amount < minAmount) {
//                    continue;  // Skip transaction if less than minAmount
//                }
//                if (maxAmount != null && amount > maxAmount) {
//                    continue;  // Skip transaction if greater than maxAmount
//                }
//
//                // Filter by transaction type (if provided)
//                if (!transactionType.isEmpty() && !type.equals(transactionType)) {
//                    continue;  // Skip transaction if type doesn't match
//                }
//
//                // If the transaction passes all filters, add it to the list
//                filteredTransactions.add(transaction);
//            } catch (Exception e) {
//                System.out.println("Error processing transaction: " + String.join(", ", transaction));
//            }
//        }
//
//        // Display filtered transactions
//        if (filteredTransactions.isEmpty()) {
//            System.out.println("No transactions found matching the criteria.");
//        } else {
//            System.out.println("\nFiltered Transaction History");
//            System.out.println("===========================================================================");
//            System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n", 
//                "Transaction ID", "User ID", "Type", "Amount", "Description", "Date");
//
//            for (String[] transaction : filteredTransactions) {
//                if (transaction.length >= 6) {
//                    System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n",
//                        transaction[0], // Transaction ID
//                        transaction[1], // User ID
//                        transaction[2], // Type
//                        transaction[3], // Amount
//                        transaction[4], // Description
//                        transaction[5]); // Date
//                } else {
//                    System.out.println("Skipping malformed transaction: " + String.join(", ", transaction));
//                }
//            }
//        }
//
//    }
//    
    private void sortingHistory(String column,String sortType){
        //sorting options
        int sortOption = 0;
        switch(column + sortType){
            case "date" + "DESCENDING" -> sortOption = 1;
            case "date" + "ASCENDING" -> sortOption = 2;
            case "balance" + "DESCENDING" -> sortOption = 3;
            case "balance" + "ASCENDING" -> sortOption = 4;
        }
        
        
        // Initialize sorting variables
        Double minAmount = null;
        Double maxAmount = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String transactionType = "";

//        // Date Range Filter
//        if (sortOption == 1 || sortOption == 2) {
//            System.out.print("Enter start date (dd/MM/yyyy): ");
//            String startDateInput = sc.nextLine();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//            while (startDate == null) {
//                try {
//                    // Try to parse the date
//                    startDate = LocalDate.parse(startDateInput,formatter);
//                } catch (Exception e) {
//                    // If the date format is invalid, inform the user and ask again
//                    System.out.println("Invalid start date. Please use the correct format (dd/MM/yyyy).");
//                    System.out.print("Enter start date (dd/MM/yyyy): ");
//                    startDateInput = sc.nextLine();  // Prompt the user again for the date
//                }
//            }
//
//            System.out.print("Enter end date (dd/MM/yyyy): ");
//            String endDateInput = sc.nextLine();
//
//            while (endDate == null) {
//                try {
//                    // Try to parse the date
//                    endDate = LocalDate.parse(endDateInput, formatter);
//                } catch (Exception e) {
//                    // If the date format is invalid, inform the user and ask again
//                    System.out.println("Invalid end date. Please use the correct format (dd/MM/yyyy).");
//                    System.out.print("Enter end date (dd/MM/yyyy): ");
//                    endDateInput = sc.nextLine();  // Prompt the user again for the date
//                }
//            }
//        }
        
//        // Amount Range Filter
//        if (sortOption == 3 || sortOption == 4) {
//            System.out.print("Enter minimum amount: ");
//            minAmount = sc.nextDouble();
//            System.out.print("Enter maximum amount: ");
//            maxAmount = sc.nextDouble();
//            sc.nextLine(); // Consume newline
//        }

        //Fetch transaction 
        List<String[]> transactions =file.get_transactions_csv(user_id);  
        List<String[]> filteredTransactions = new ArrayList<>();

//        for (String[] transaction : transactions) {      //loop thru transactions
//            try {
//                if (transaction.length < 6) continue;   
//                
//                //converts string at index 5(date) into a LocalDate object
//                LocalDate transactionDate = LocalDate.parse(transaction[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                
//                //converts string at index 3(amount) into double 
//                double amount = Double.parseDouble(transaction[3]);
//
//                // Apply date range filtering
//                if ((startDate == null || !transactionDate.isBefore(startDate)) &&
//                    (endDate == null || !transactionDate.isAfter(endDate))) {
//                    // Apply amount range filtering
//                    if ((minAmount == null || amount >= minAmount) &&
//                        (maxAmount == null || amount <= maxAmount)) {
//                        //Adds the current transaction to the filteredTransactions list
//                        filteredTransactions.add(transaction);
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Skipping malformed transaction: " + Arrays.toString(transaction));
//            }
//        }
            

        ObservableList<Transaction> data = histories.getItems();
        
        // sorting option
        switch (sortOption){
            case 1:          //Sort by Date (Newest First)
                transactions.sort((a, b) -> {
                    // Compare by Date (Newest First)
                    int dateComparison = LocalDate.parse(b[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                                 .compareTo(LocalDate.parse(a[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                    // If dates are the same, compare by Transaction ID (Newest First)
                    if (dateComparison == 0) {
                        return b[0].compareTo(a[0]);  // Transaction ID in descending order
                    }

                    return dateComparison;  // Otherwise, order by date
                });
                break; 
                
            case 2:          // Sort by Date (Oldest First)
                transactions.sort((a, b) -> {
                    // Compare by Date (Oldest First)
                    int dateComparison = LocalDate.parse(a[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                                 .compareTo(LocalDate.parse(b[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                    // If dates are the same, compare by Transaction ID (Oldest First)
                    if (dateComparison == 0) {
                        return a[0].compareTo(b[0]);  // Transaction ID in ascending order
                    }

                    return dateComparison;  // Otherwise, order by date
                });
                break;
                
            case 3:           //Sort by Amount(Highest to Lowest)
//                data.sort((b, a) -> Double.compare(Double.parseDouble(a.getBalance()), Double.parseDouble(b.getBalance())));
                break;
                
            case 4:           //Sort by Amount (Lowest to Highest)
                data.sort((a, b) -> Double.compare(Double.parseDouble(a.getBalance()), Double.parseDouble(b.getBalance())));
                break;
                
            default:
                System.out.println("Invalid sort option.");
                return;
        }
        
        // Check if no transactions were found
        if (transactions.isEmpty()) {
            his_info.setText("No transactions found matching the criteria.");
            return;
        }
        
        data.sort((a, b) -> Double.compare(Double.parseDouble(a.getBalance()), Double.parseDouble(b.getBalance())));
        
        
        // Display each transaction
//        for (String[] transaction : transactions) {
//            if(transaction.length != 6) continue;
//            data.add(new Transaction(df.format(Double.parseDouble(transaction[3])), transaction[2], transaction[4], transaction[5]));
//        }
    }
}
