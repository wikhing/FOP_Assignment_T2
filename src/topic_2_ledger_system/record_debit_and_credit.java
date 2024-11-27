/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;
import java.time.LocalDate;
import java.util.Scanner;
/**
 *
 * @author Joey
 */
public class record_debit_and_credit {
    public static void main(String[] args){
        LocalDate date = LocalDate.now();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String user_name = sc.nextLine();
        System.out.print("Enter balance: ");
        double balance = sc.nextDouble();
        System.out.print("Enter Savings: ");
        double saving = sc.nextDouble();
        System.out.print("Enter Loan: ");
        double loan = sc.nextDouble();
        int num = 0;
        boolean activeloan = false;
        LocalDate loanStartDate = null;
        
        
        while(num !=7){
            System.out.println("\n\n== Welcome, " + user_name + " ==");
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
            num = sc.nextInt();
            
            switch(num){
                case 1: {
                    System.out.print("\n== Debit ==");
                    System.out.print("\nEnter amount: ");
                    double amount = sc.nextDouble();
                    if (amount >0 && amount < (Math.pow(10, 9)))
                        balance = balance + amount;
                    else{
                        System.out.print("Enter again the amount: ");
                        amount = sc.nextDouble();
                    }
                    
                    System.out.print("\nEnter transaction date(yyy-mm-dd): ");
                    sc.nextLine();
                    String dateInput = sc.nextLine();
                    LocalDate transactionDate;
                    
                    try{
                        transactionDate = LocalDate.parse(dateInput);
                    }catch (Exception e){
                        System.out.println("Error: Invalid date.");
                        return;
                    }
                    
                    LocalDate today = LocalDate.now();
                    if(transactionDate.isAfter(today)){
                        System.out.print("\nError: Transaction date cannot be in the future.");
                        return;
                    }
                    
                    System.out.print("Enter description: ");
                    sc.nextLine();
                    String description = sc.nextLine();
                    if (description.length() >100){
                        System.out.println("Error: Description exceeds 100 characters. ");
                        System.out.print("\nEnter description: ");
                        description = sc.nextLine();
                        System.out.print("\nDebit Successfully Recorded!!!");
                        break;}
                    
                    else{
                        System.out.print("\nDebit Successfully Recorded!!!");
                        break;}
                }
                case 2: {
                    System.out.print("== Credit ==");
                    System.out.print("\nEnter amount: ");
                    double credit_amount = sc.nextDouble();
                    
                    if(credit_amount >0 && credit_amount< balance){
                        balance = balance - credit_amount;
                    }
                    else{
                        System.out.print("Enter amount again: ");
                        credit_amount = sc.nextDouble();
                    }
                    
                    System.out.print("\nEnter transaction date (yyy-mm-dd) : ");
                    sc.nextLine();
                    String dateInput = sc.nextLine();
                    LocalDate transactionDate;
                    
                    try{
                        transactionDate = LocalDate.parse(dateInput);
                    }catch (Exception e){
                        System.out.print("Error: Invalid date.");
                        return;
                    }
                    LocalDate today = LocalDate.now();
                    if(transactionDate.isAfter(today)){
                        System.out.print("\nError: Transaction date cannot be in the future.");
                        return;
                    }
                    
                    System.out.print("Enter description: ");
                    sc.nextLine();
                    String credit_description = sc.nextLine();
                    if(credit_description.length()>100){
                        System.out.print("Error:Description exceeds 100 characters. ");
                        System.out.print("\nEnter description: ");
                        credit_description = sc.nextLine();
                        System.out.print("\nCredit Successfully Recorded!!!");
                        break;
                        
                    }
                    else{
                        System.out.print("\nCredit Successfully Recorded!!!");
                        break;}
                }
                case 3: {
                    break;
                }
                case 4:{
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
                case 5:
                    while (true){
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
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
            
        }

                
    }
    
}
