/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    
    // Need to set up user acc when user register, add savings and acc balance
    private static Scanner sc = new Scanner(System.in);
    private static int user_id;
    
    
    public static void printMenu(String username, double balance, double saving, double loan) {
        System.out.println("\n\n== Welcome, " + username + " ==");
        System.out.print("Balance: " );
        System.out.printf("%.2f",balance);
        System.out.print("\nSavings: ");
        System.out.printf("%.2f", saving);
        System.out.print("\nLoan: ");
        System.out.printf("%.2f",loan);

        System.out.println("\n\n== Transaction ==");
        System.out.println("1. Debit");
        System.out.println("2. Credit");
        System.out.println("3. History");
        System.out.println("4. Savings");
        System.out.println("5. Credit Loan");
        System.out.println("6. Deposit Interest Predictor");
        System.out.println("7. Logout\n\n");
        System.out.print(">");
    }

    private static void debit() {
        double balance = file.get_accbalance_csv(user_id);
        
        double amount = 0;
        String description = "", dateToday = "";
        while (true) {
            System.out.println("== Debit ==");
            System.out.print("Enter amount: ");

            amount = sc.nextDouble();

            if (amount > 0 && amount < (Math.pow(10, 9))) {
                balance += amount;
            } else {
                System.out.println("Invalid input, please retry");
                continue;
            }

            // Automatically get date
            LocalDate date = LocalDate.now();
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateToday = date.format(pattern);

            System.out.print("Enter description: ");
            description = sc.next();
            if (description.length() > 100){
                System.out.println("Error: Description exceeds 100 characters, please retry. ");
            } else {
                System.out.print("\nDebit Successfully Recorded!!!");
                break;
            }
        }
        
        List<String[]> transaction = file.get_transactions_csv(user_id);
        transaction.add(new String[]{"", String.valueOf(user_id), "debit", String.valueOf(amount), description, dateToday});
        file.set_transactions_csv(transaction);
        
        file.set_accbalance_csv(user_id, balance);
    }
    
    private static void credit() {
        double balance = file.get_accbalance_csv(user_id);
        
        System.out.println("== Credit ==");
        
        double credit_amount = 0;
        while(true){
            System.out.print("Enter amount: ");
            credit_amount = sc.nextDouble();

            if(credit_amount > 0 && credit_amount < balance){
                balance = balance - credit_amount;
                break;
            }
            
            System.out.println();
            System.out.println("Please enter amount greater than 0 and less than your balance.");
        }
        
        sc.nextLine();
        String credit_description = "";
        while(true){
            System.out.print("Enter description: ");
            credit_description = sc.nextLine();
            
            if(credit_description.length() <= 100){
                System.out.println("\n\nCredit Successfully Recorded!!!\n");
                break;
            }
            
            System.out.println("\nError: Description exceeds 100 characters. ");
        }
        
        // Automatically get date
        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToday = date.format(pattern);
        
        List<String[]> transaction = file.get_transactions_csv(user_id);
        transaction.add(new String[]{"", String.valueOf(user_id), "credit", String.valueOf(credit_amount), credit_description, dateToday});
        file.set_transactions_csv(transaction);
        
        file.set_accbalance_csv(user_id, balance);
    }
    
    private static void history() {
        System.out.print("Enter the month and year (eg. MM/YYYY): ");
        sc.nextLine();
        String monthYear = sc.nextLine();
        
        // In case of invalid month/year input
        if (!monthYear.matches("\\d{2}/\\d{4}")) {
            System.out.println("Invalid format. Please use MM/YYYY.");
            return;
        }
        view_export_csv.viewAndExportTransactions(user_id, monthYear);
    }
    
    private static void filterhistory(List<String[]> transactions){
        
        System.out.println();
        
        // Filter options: Amount Range, Date Range, Transaction Type
        int filterOption = 0; // Initialize variable to store user's choice
        while (true) {
            System.out.println();
            System.out.println("Filter Options:");
            System.out.println("1. Filter by Amount Range");
            System.out.println("2. Filter by Date Range");
            System.out.println("3. Filter by Transaction Type (debit/credit)");
            System.out.print("Enter filter option (1-3): ");

            if (sc.hasNextInt()) { // Check if the input is an integer
                filterOption = sc.nextInt();
                sc.nextLine(); // Consume newline

                if (filterOption >= 1 && filterOption <= 3) {
                    // Valid option, exit the loop
                    break;
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and 3.");
                }
            } else {
                // Clear invalid input
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Consume the invalid input
            }
        }
        
        // Initialize filter variables
        Double minAmount = null;
        Double maxAmount = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String transactionType = "";

        // Amount Range Filter
        if (filterOption == 1 ) {
            System.out.print("Enter minimum amount: ");
            minAmount = sc.nextDouble();
            System.out.print("Enter maximum amount: ");
            maxAmount = sc.nextDouble();
            sc.nextLine(); // Consume newline
        }

        // Date Range Filter
        if (filterOption == 2 ) {
            System.out.print("Enter start date (dd/MM/yyyy): ");
            String startDateInput = sc.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            while (startDate == null) {
                try {
                    // Try to parse the date
                    startDate = LocalDate.parse(startDateInput,formatter);
                } catch (Exception e) {
                    // If the date format is invalid, inform the user and ask again
                    System.out.println("Invalid start date. Please use the correct format (dd/MM/yyyy).");
                    System.out.print("Enter start date (dd/MM/yyyy): ");
                    startDateInput = sc.nextLine();  // Prompt the user again for the date
                }
            }

            System.out.print("Enter end date (dd/MM/yyyy): ");
            String endDateInput = sc.nextLine();

            while (endDate == null) {
                try {
                    // Try to parse the date
                    endDate = LocalDate.parse(endDateInput, formatter);
                } catch (Exception e) {
                    // If the date format is invalid, inform the user and ask again
                    System.out.println("Invalid end date. Please use the correct format (dd/MM/yyyy).");
                    System.out.print("Enter end date (dd/MM/yyyy): ");
                    endDateInput = sc.nextLine();  // Prompt the user again for the date
                }
            }
        }

        // Transaction Type Filter
        if (filterOption == 3 ) {
            String transactionTypeInput = "";
            while (true) {
                System.out.print("Enter transaction type (debit/credit): ");
                transactionTypeInput = sc.nextLine().toLowerCase(); // Accept lowercase
                if (transactionTypeInput.equals("debit") || transactionTypeInput.equals("credit")) {
                    transactionType = transactionTypeInput;
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'debit' or 'credit'.");
                }
            }
        }

        // Filtering transactions
        List<String[]> filteredTransactions = new ArrayList<>();
        for (String[] transaction : transactions) {
            try {
                // Ensure the transaction has at least 6 elements (e.g., date, amount, type)
                if (transaction.length < 6) {
                    System.out.println("Skipping malformed transaction: " + String.join(", ", transaction));
                    continue;
                }

                // Parsing date and amount for comparison
                LocalDate transactionDate = LocalDate.parse(transaction[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                double amount = Double.parseDouble(transaction[3]);  // Converts string to double
                String type = transaction[2].toLowerCase();

                // Filter by date range (if provided)
                if (startDate != null && transactionDate.isBefore(startDate)) {
                    continue;  // Skip transaction if before the start date
                }
                if (endDate != null && transactionDate.isAfter(endDate)) {
                    continue;  // Skip transaction if after the end date
                }

                // Filter by amount range (if provided)
                if (minAmount != null && amount < minAmount) {
                    continue;  // Skip transaction if less than minAmount
                }
                if (maxAmount != null && amount > maxAmount) {
                    continue;  // Skip transaction if greater than maxAmount
                }

                // Filter by transaction type (if provided)
                if (!transactionType.isEmpty() && !type.equals(transactionType)) {
                    continue;  // Skip transaction if type doesn't match
                }

                // If the transaction passes all filters, add it to the list
                filteredTransactions.add(transaction);
            } catch (Exception e) {
                System.out.println("Error processing transaction: " + String.join(", ", transaction));
            }
        }

        // Display filtered transactions
        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found matching the criteria.");
        } else {
            System.out.println("\nFiltered Transaction History");
            System.out.println("===========================================================================");
            System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n", 
                "Transaction ID", "User ID", "Type", "Amount", "Description", "Date");

            for (String[] transaction : filteredTransactions) {
                if (transaction.length >= 6) {
                    System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n",
                        transaction[0], // Transaction ID
                        transaction[1], // User ID
                        transaction[2], // Type
                        transaction[3], // Amount
                        transaction[4], // Description
                        transaction[5]); // Date
                } else {
                    System.out.println("Skipping malformed transaction: " + String.join(", ", transaction));
                }
            }
        }

    }
    
    private static void sortinghistory(){
        
        System.out.println();
        
        //sorting options
        int sortOption = 0;
        while (true) {
            System.out.println("Select sorting option: ");
            System.out.println("1. Sort by Date (Newest First)");
            System.out.println("2. Sort by Date (Oldest First)");
            System.out.println("3. Sort by Amount (Highest First)");
            System.out.println("4. Sort by Amount (Lowest First)");
            System.out.print("Enter your choice (1-4): ");

            if (sc.hasNextInt()) { // Check if the input is an integer
                sortOption = sc.nextInt();
                sc.nextLine(); // Consume newline

                if (sortOption >= 1 && sortOption <= 4) {
                    // Valid option, exit the loop
                    break;
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                }
            } else {
                // Clear invalid input
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Consume the invalid input
            }
        }
        
        // Initialize sorting variables
        Double minAmount = null;
        Double maxAmount = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String transactionType = "";

        // Date Range Filter
        if (sortOption == 1 || sortOption == 2) {
            System.out.print("Enter start date (dd/MM/yyyy): ");
            String startDateInput = sc.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            while (startDate == null) {
                try {
                    // Try to parse the date
                    startDate = LocalDate.parse(startDateInput,formatter);
                } catch (Exception e) {
                    // If the date format is invalid, inform the user and ask again
                    System.out.println("Invalid start date. Please use the correct format (dd/MM/yyyy).");
                    System.out.print("Enter start date (dd/MM/yyyy): ");
                    startDateInput = sc.nextLine();  // Prompt the user again for the date
                }
            }

            System.out.print("Enter end date (dd/MM/yyyy): ");
            String endDateInput = sc.nextLine();

            while (endDate == null) {
                try {
                    // Try to parse the date
                    endDate = LocalDate.parse(endDateInput, formatter);
                } catch (Exception e) {
                    // If the date format is invalid, inform the user and ask again
                    System.out.println("Invalid end date. Please use the correct format (dd/MM/yyyy).");
                    System.out.print("Enter end date (dd/MM/yyyy): ");
                    endDateInput = sc.nextLine();  // Prompt the user again for the date
                }
            }
        }
        
        // Amount Range Filter
        if (sortOption == 3 || sortOption == 4) {
            System.out.print("Enter minimum amount: ");
            minAmount = sc.nextDouble();
            System.out.print("Enter maximum amount: ");
            maxAmount = sc.nextDouble();
            sc.nextLine(); // Consume newline
        }

        //Fetch transaction 
        List<String[]> transactions =file.get_transactions_csv(user_id);  
        List<String[]> filteredTransactions = new ArrayList<>();

        for (String[] transaction : transactions) {      //loop thru transactions
            try {
                if (transaction.length < 6) continue;   
                
                //converts string at index 5(date) into a LocalDate object
                LocalDate transactionDate = LocalDate.parse(transaction[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                
                //converts string at index 3(amount) into double 
                double amount = Double.parseDouble(transaction[3]);

                // Apply date range filtering
                if ((startDate == null || !transactionDate.isBefore(startDate)) &&
                    (endDate == null || !transactionDate.isAfter(endDate))) {
                    // Apply amount range filtering
                    if ((minAmount == null || amount >= minAmount) &&
                        (maxAmount == null || amount <= maxAmount)) {
                        //Adds the current transaction to the filteredTransactions list
                        filteredTransactions.add(transaction);
                    }
                }
            } catch (Exception e) {
                System.out.println("Skipping malformed transaction: " + Arrays.toString(transaction));
            }
        }
            
        // sorting option
        switch (sortOption){
            case 1:          //Sort by Date (Newest First)
                filteredTransactions.sort((a, b) -> {
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
                filteredTransactions.sort((a, b) -> {
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
                filteredTransactions.sort((a, b) -> Double.compare(Double.parseDouble(b[3]), Double.parseDouble(a[3])));
                break;
                
            case 4:           //Sort by Amount (Lowest to Highest)
                filteredTransactions.sort((a, b) -> Double.compare(Double.parseDouble(a[3]), Double.parseDouble(b[3])));
                break;
                
            default:
                System.out.println("Invalid sort option.");
                return;
        }
        
        // Display filtered and sorted transactions
        System.out.println("\nSorted Transaction History");
        System.out.println("===========================================================================");
        System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n", 
            "Transaction ID", "User ID", "Type", "Amount", "Description", "Date");

        // Display each transaction
        for (String[] transaction : filteredTransactions) {
            if (transaction.length >= 6) {
                System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n",
                    transaction[0], // Transaction ID
                    transaction[1], // User ID
                    transaction[2], // Type
                    transaction[3], // Amount
                    transaction[4], // Description
                    transaction[5]); // Date
            } else {
                System.out.println("Skipping malformed transaction: " + String.join(", ", transaction));
            }
        }

        // Check if no transactions were found
        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found matching the criteria.");
        }

    }
        
    
    
    private static void saving() {
        System.out.println("\n== Savings ==");
        
        char choice;
        boolean isActive = false;
        int percentage = 0;
        while(true){
            System.out.print("Are you sure you want to activare it? (Y/N) : ");
            choice = sc.next().charAt(0);
            
            if(choice == 'Y' || choice == 'y'){
                isActive = true;
                while(true){
                    System.out.print("Please enter the percentage you wish to deduct from the next debit: ");
                    percentage = sc.nextInt();

                    if (percentage >= 0 && percentage <= 100){
                        System.out.println("\n\nSavings Setting added successfully!!!\n");
                        break;
                    }
                    
                    System.out.println("Please enter valid percentage!");
                }
                break;
            }else if(choice == 'N'|| choice == 'n'){
                isActive = false;
                System.out.println("\n\nSavings Setting is not added!!!");
                break;
            }
            
            System.out.println("Invalid! Please enter Y or N");
        }
        
        String[] savings = file.get_savings_csv(user_id);
        if(isActive){
            savings[2] = "active";
            savings[3] = String.valueOf(percentage);
        }else if(!isActive){
            savings[2] = "inactive";
            savings[3] = "0";
            if(savings[4] != "0"){
                double balance = file.get_accbalance_csv(user_id);
                balance += Double.parseDouble(savings[4]);
                
                file.set_accbalance_csv(user_id, balance);
            }
            savings[4] = "0";
        }
        file.set_savings_csv(savings);
        
        
    }
    
    private static void creditLoan(int user_id) {
        
        // Automatically get date
        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateToday = date.format(pattern);

        while (true) {
            System.out.println("\n---- Credit Loan System ----");
            System.out.println("1. Apply Loan");
            System.out.println("2. Repay Loan");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choices = sc.nextInt();
            
            
            if (choices == 1) {
                credit_loan.applyLoan(user_id, dateToday); 
            } else if (choices == 2) {
                credit_loan.repayLoan(user_id, dateToday, date);   
            } else if (choices == 3) {
                System.out.println("Exiting the system. Thank you!");
                break;
            } else {
                System.out.println("Invalid input, please retry.");
            }
        }
        
    }
    
    private static void depositInterestPredictor() {
        deposit_interest_predictor.depositInterestPredictor(user_id);
    }
    
    private static int logOut() {
        System.out.println("\nUser successfully logged out.");
        return -1;
    }
    
    
    
    
    private static int optionHandler(int option, int user_id, List<String[]>transactions) {
        
        int info = 0;
        
        switch(option) {  
            case 1:
                debit();
                break;
            case 2:
                credit();
                break; 
            case 3:
                history();
                System.out.println("Would you like to:");
                System.out.println("1. Filter transactions");
                System.out.println("2. Sort transactions");
                System.out.println("3. Return to the main menu ");
                System.out.print("Enter your choice (1-3): ");
                int choice = sc.nextInt();
                sc.nextLine();  // Consume newline

                if (choice == 1) {
                    // Filter transactions
                    filterhistory(transactions);
                } else if (choice == 2) {
                    // Sort transactions
                    sortinghistory();
                }else if (choice == 3){
                    break;
                } else {
                    System.out.println("Invalid option! Please choose either 1, 2 or 3.");
                }
                break;

            case 4:
                saving();
                break;
            case 5:
                creditLoan(user_id);
                break;
            case 6:
                depositInterestPredictor();
                break;
            case 7:
                info = logOut();
                break;
            default:
                break;
        }
        
        return info;
    }
    
    
    
    public static void loginPage(int user_id) {
        ArrayList<String[]> user_csv = file.get_user_csv();
        String username = user_csv.get(user_id)[1];
        String email = user_csv.get(user_id)[2];
        
        List<String[]> transactions = file.get_transactions_csv(user_id);
        
        while (true) {
            printMenu(username, file.get_accbalance_csv(user_id), 0, 0);
            String rawOption = sc.next();
            int option;
            
            try {
                option = Integer.parseInt(rawOption);
                
                if (!(option >= 1 && option <= 7)) {
                    System.out.println("Invalid option, please retry.");
                    continue;
                }
                
                if (optionHandler(option, user_id, transactions) == -1) {
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("Invalid option, please retry.");
            } 
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        user_id = login_register.initialize();
        
        loginPage(user_id);
        
        System.out.println("Thank you for using Ledger System.");
        
        
        
    }
}
