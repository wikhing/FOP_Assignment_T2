/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author USER
 */
public class credit_loan {
    
    private static Scanner sc = new Scanner(System.in);
    
    public static boolean getActiveLoan(int user_id) {
        
        boolean isActive = false;
        
        try {
            List<String[]> loan_csv = file.get_loans_csv(user_id);
            isActive = loan_csv.get(0)[6].equals("active");
        } catch (Exception e) {
            return false;
        }
                
        return isActive;
    }
    
    public static double getBalance(int user_id) {
        return file.get_accbalance_csv(user_id);
    }
    
    public static void updateLoan(int user_id, double totalAmount, double monthlyPayment, int period, boolean activeLoan, String dateToday, double interestRate) {
        
        String[] loan = {"", 
                        String.valueOf(user_id), 
                        String.valueOf(totalAmount), 
                        String.valueOf(interestRate), 
                        String.valueOf(period), 
                        String.valueOf(totalAmount), 
                        (activeLoan ? "active" : "inactive"), 
                        dateToday};
        
        
        file.set_loans_csv(loan);
    }
    
    public static void updateLoan(int user_id, double totalAmount, double period, boolean activeLoan) {
        
    }
    
    public static int getPeriod(int user_id) {
        List<String[]> loan_csv = file.get_loans_csv(user_id);
        return Integer.parseInt(loan_csv.get(0)[4]);
    }
    
    public static int applyLoan(int user_id, String dateToday) {
        
                
        boolean activeLoan = getActiveLoan(user_id);
        
        if (activeLoan) {
            System.out.println("You already have an active loan. Please repay it before applying for another.");
            return -1;
        }   

        System.out.print("Enter principal amount: ");
        double principal = sc.nextDouble();
        System.out.print("Enter annual interest rate (in %): ");
        double rate = sc.nextDouble();
        System.out.print("Enter repayment period (in months): ");
        int period = sc.nextInt();

        if (!(principal > 0 && rate > 0 && period > 0)) {
            System.out.println("Invalid input, please retry");
            return -1;
        }

        double totalAmount = principal + (principal * (rate / 100) * (period / 12.0));
        double monthlyPayment = totalAmount / period;

        activeLoan = true;

        System.out.println("Loan approved!");
        System.out.println("Total repayment amount: " + totalAmount);
        System.out.println("Monthly repayment amount: " + monthlyPayment);
        System.out.println("Repayment period: " + period + " months");
        System.out.println("Loan start date: " + dateToday);

        updateLoan(user_id, totalAmount, monthlyPayment, period, activeLoan, dateToday, rate);
       
        return 0;
    }
    
    public static int repayLoan(int user_id, String dateToday, LocalDate date) {
        
        boolean activeLoan = getActiveLoan(user_id);
        int period = getPeriod(user_id);
        double balance = getBalance(user_id);
        
        if (!activeLoan) {
            System.out.println("No active loan to repay.");
            return -1;
        }   

        if (date.isAfter(date.plusMonths(period))) {
            System.out.println("Loan repayment period has ended. Further debits and credits are not allowed.");
            return -1;
        }   

        System.out.println("Enter payment amount: ");
        double paymentAmount = sc.nextDouble();

        if (paymentAmount <= 0) {
            System.out.println("Invalid input, please retry.");
            return -1;
        }

        balance -= paymentAmount;
        System.out.println("Payment successful! ----" + dateToday);
        
        if (balance > 0) {
            System.out.println("Remaining balance: " + balance);
        } else if (balance < 0) {
            System.out.println("Remaining balance is 0");
            System.out.printf("Your change : RM%.2f\n", -balance);
        }
        
        if (balance <= 0) {
            System.out.println("Loan fully repaid. Thank you!");
            activeLoan = false; // Mark loan as fully repaid
            balance = 0;
        }
        
        updateLoan(user_id, balance, period, activeLoan);
        
        return 0;
    }
    
}
