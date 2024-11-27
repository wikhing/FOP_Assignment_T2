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

    private static void debit(double balance) {
           
        while (true) {
            System.out.println("== Debit ==");
            System.out.print("Enter amount: ");

            double amount = sc.nextDouble();

            if (amount > 0 && amount < (Math.pow(10, 9))) {
                balance += amount;
            } else {
                System.out.println("Invalid input, please retry");
                continue;
            }

            // Automatically get date
            LocalDate date = LocalDate.now();
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String dateToday = date.format(pattern);

            System.out.print("Enter description: ");
            String description = sc.next();
            if (description.length() > 100){
                System.out.println("Error: Description exceeds 100 characters, please retry. ");
            } else {
                System.out.print("\nDebit Successfully Recorded!!!");
                break;
            }
        }
        
    }
    
    private static void credit(int user_id) {
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
                System.out.println("\n\nCredit Successfully Recorded!!!\n\n");
                break;
            }
            
            System.out.println("\nError: Description exceeds 100 characters. ");
        }
        
        // Automatically get date
        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateToday = date.format(pattern);
        
        List<String[]> transaction = file.get_transactions_csv(user_id);
        transaction.add(new String[]{"", String.valueOf(user_id), "credit", String.valueOf(credit_amount), credit_description, dateToday});
        file.set_transactions_csv(transaction);
        
        file.set_accbalance_csv(user_id, balance);
    }
    
    private static void history() {
        Scanner sc = new Scanner (System.in);
        System.out.print ("Enter the month and year (eg. MM-YYYY): ");
        String monthYear = sc.nextLine();
        
        // In case of invalid month/year input
        if (!monthYear.matches("\\d{2}/\\d{4}")) {
            System.out.println("Invalid format. Please use MM/YYYY.");
            return;
        }
        view_export_csv.viewAndExportTransactions(user_id, monthYear);
    }
    
    private static void saving() {
        System.out.print("\n== Savings ==");
        System.out.print("Are you sure you want to activare it? (Y/N) : ");
        char choice = sc.next().charAt(0);

        if(choice == 'Y' || choice == 'y'){
            System.out.print("Please enter the percentage you wish to deduct from the next debit: ");
            int percentage = sc.nextInt();

            if (percentage<0 || percentage>100){
                System.out.println("Please enter again");}
            else
                System.out.println("\n\nSavings Settings added successfully!!!");
        }
        else if(choice == 'N'|| choice == 'n')
            System.out.println("\n\nSavings Settings added successfully!!!");
        else
            System.out.println("Invalid! Please enter Y or N");
    }
    
    private static void creditLoan() {
        
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
            
            double totalamount = 0;
            int period = 0;
            double balanced;
            boolean activeloan = false;
            
            if (choices == 1) {
                
                if (activeloan) {
                    System.out.println("You already have an active loan. Please repay it before applying for another.");
                    continue;
                }   

                System.out.print("Enter principal amount: ");
                double principal = sc.nextDouble();
                System.out.print("Enter annual interest rate (in %): ");
                double rate = sc.nextDouble();
                System.out.print("Enter repayment period (in months): ");
                period = sc.nextInt();

                if (!(principal > 0 && rate > 0 && period > 0)) {
                    System.out.println("Invalid input, please retry");
                    continue;
                }

                totalamount = principal + (principal * (rate / 100) * (period / 12.0));
                double monthlypayment = totalamount / period;
                balanced = totalamount;

                activeloan = true;

                System.out.println("Loan approved!");
                System.out.println("Total repayment amount: " + totalamount);
                System.out.println("Monthly repayment amount: " + monthlypayment);
                System.out.println("Repayment period: " + period + " months");
                System.out.println("Loan start date: " + dateToday);
                break;

            } else if (choices == 2) {

                if (!activeloan) {
                    System.out.println("No active loan to repay.");
                    continue;
                }   


                if (date.isAfter(date.plusMonths(period))) {
                    System.out.println("Loan repayment period has ended. Further debits and credits are not allowed.");
                    activeloan = false; // Mark the loan as inactive to prevent further actions
                    continue;
                }   


                System.out.println("Enter loan ID: ");
                int loan_id = sc.nextInt();
                System.out.println("Enter payment amount: ");
                double paymentAmount = sc.nextDouble();

                if (paymentAmount <= 0) {
                    System.out.println("Invalid input, please retry.");
                    continue;
                }

                balanced = totalamount - paymentAmount;
                System.out.println("Payment successful! ----" + dateToday);
                System.out.println("Remaining balance: " + balanced);

                if (balanced <= 0) {
                    System.out.println("Loan fully repaid. Thank you!");
                    activeloan = false; // Mark loan as fully repaid
                }   

            } else if (choices == 3) {
                System.out.println("Exiting the system. Thank you!");
                break;
            } else {
                System.out.println("Invalid input, please retry.");
                continue;
            }
        }
        
    }
    
    private static void depositInterestPredictor() {
        
    }
    
    private static int logOut() {
        System.out.println("User successfully logged out.");
        return -1;
    }
    
    
    
    
    private static int optionHandler(int option) {
        
        int info = 0;
        
        switch(option) {  
            case 1:
                debit(0);
                break;
            case 2:
                credit(0);
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
                
                if (optionHandler(option) == -1) {
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
