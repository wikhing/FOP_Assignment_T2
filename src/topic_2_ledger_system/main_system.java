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
            System.out.print("\n\n>");
    }
    
    
    private static void debit(double balance) {        
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
        
        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateToday = date.format(pattern);
        
        List<String[]> transaction = file.get_transactions_csv(user_id);
        transaction.add(new String[]{"", String.valueOf(user_id), "credit", String.valueOf(credit_amount), credit_description, dateToday});
        file.set_transactions_csv(transaction);
        
        file.set_accbalance_csv(user_id, balance);
    }
    
    private static void history() {
        
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
            System.out.println("\n---- Credit Loan System ----");
            System.out.println("1. Apply Loan");
            System.out.println("2. Repay Loan");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choices = sc.nextInt();

        double totalamount = 0;
        int period = 0;
        double balanced;
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

            totalamount = principal + (principal * (rate / 100) * (period / 12.0));
            double monthlypayment = totalamount / period;
            balanced = totalamount;
            loanStartDate = LocalDate.now();
            activeloan = true;

            System.out.println("Loan approved!");
            System.out.println("Total repayment amount: " + totalamount);
            System.out.println("Monthly repayment amount: " + monthlypayment);
            System.out.println("Repayment period: " + period + " months");
            System.out.println("Loan start date: " + loanStartDate);

        }else if (choices == 2) {

            if (!activeloan) {
                System.out.println("No active loan to repay.");
                continue;
            }

            LocalDate currentDate = LocalDate.now();
            LocalDate repaymentEndDate = loanStartDate.plusMonths(period);
            if (currentDate.isAfter(repaymentEndDate)) {
                System.out.println("Loan repayment period has ended. Further debits and credits are not allowed.");
                activeloan = false; // Mark the loan as inactive to prevent further actions
                continue;
            }

            System.out.println("Enter loan ID: ");
            int loanid = sc.nextInt();
            System.out.println("Enter payment amount: ");
            double paymentamount = sc.nextDouble();
            balanced = totalamount - paymentamount;
            System.out.println("Payment successful! ----"+date);
            System.out.println("Remaining balance: "+balanced);

            if (balance <= 0) {
                System.out.println("Loan fully repaid. Thank you!");
                activeloan = false; // Mark loan as fully repaid
            }
            break;

        }else if (choices == 3){
            System.out.println("Exiting the system. Thank you!");
            break;
        } 
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