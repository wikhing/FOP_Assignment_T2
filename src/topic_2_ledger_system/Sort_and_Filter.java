/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author enxi
 */
public class Sort_and_Filter {
    
    private static int user_id;
    
        private static void filterhistory(List<String[]> transactions){
            Scanner sc = new Scanner (System.in);
            
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
            if (filterOption == 1 || filterOption == 0) {
                System.out.print("Enter minimum amount: ");
                minAmount = sc.nextDouble();
                System.out.print("Enter maximum amount: ");
                maxAmount = sc.nextDouble();
                sc.nextLine(); // Consume newline
            }

            // Date Range Filter
            if (filterOption == 2 || filterOption == 0) {
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
            if (filterOption == 3 || filterOption == 0) {
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
                System.out.println("=======================================");
                System.out.printf("%-15s %-10s %-15s %-15s\n", "Type", "Amount", "Description", "Date");

                for (String[] transaction : filteredTransactions) {
                    System.out.printf("%-15s %-10s %-15s %-15s\n",
                        transaction[2], transaction[3], transaction[4], transaction[5]);
                }
            }
        }

    private static void sortinghistory(){
        Scanner sc = new Scanner(System.in);
        
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
        System.out.println("=======================================");
        System.out.printf("%-15s %-10s %-15s %-15s\n", "Type", "Amount", "Description", "Date");

        for (String[] transaction : filteredTransactions) {
            System.out.printf("%-15s %-10s %-15s %-15s\n",
                    transaction[2], transaction[3], transaction[4], transaction[5]);
        }

        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found matching the criteria.");
        }
    }
}