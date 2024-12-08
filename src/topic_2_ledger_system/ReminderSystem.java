/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author xian
 */
public class ReminderSystem {
    private static void ReminderSystem(int user_id){
   
        boolean activeLoan = menu.getActiveLoan(user_id);
        if (!activeLoan) {
            System.out.println("There are no active loans.");
            return;
        }

        //Obtain loan details
        String[] loan_csv = file.get_loans_csv(user_id);
        int period = Integer.parseInt(loan_csv[4]); // Repayment period
        LocalDate startDate = LocalDate.parse(loan_csv[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        double totalLoanAmount = Double.parseDouble(loan_csv[2]);
        double monthlyRepayment = totalLoanAmount / period;

        //Create a list that collect duedates and expectedbalances every month
        String[][] scheduledExpectation = new String[period][2];
        for (int i = 0; i < period; i++) {
            LocalDate dueDate = startDate.plusMonths(i + 1);
            scheduledExpectation[i][0] = dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            
            double expectedBalance = totalLoanAmount - (monthlyRepayment * (i + 1));
            scheduledExpectation[i][1] = String.format("%.2f", expectedBalance);
            //System.out.println(scheduledExpectation[i][0]);
        }
        
        LocalDate today = LocalDate.now();
        LocalDate lastDueDate = null;
        double lastExpectedBalance = 0;        
        
        //Find the duedate before today by comparing todaydate and duedates found above
        for (int i = 0; i < period; i++) {
            LocalDate dueDate = LocalDate.parse(scheduledExpectation[i][0], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            
            if (dueDate.isEqual(today)) {
                lastDueDate = dueDate;
                lastExpectedBalance = Double.parseDouble(scheduledExpectation[i][1]);
                break; //stop if todaydate equal to duedate
            }
            if (dueDate.isBefore(today)) {
                lastDueDate = dueDate;
                lastExpectedBalance = Double.parseDouble(scheduledExpectation[i][1]);               
            }            
        }
        
        LocalDate nextDueDate = null;
        double nextExpectedBalance =0;
        
       //find due date after today.
        for (int i = 0; i < period; i++) {
            LocalDate dueDate = LocalDate.parse(scheduledExpectation[i][0], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            
            if (dueDate.isAfter(today)) {
                nextDueDate = dueDate;
                nextExpectedBalance = Double.parseDouble(scheduledExpectation[i][1]);
                break; 
            }
        }
        
        // If no valid lastduedate is found (First month of loan repayment)
        if (lastDueDate == null) {
            System.out.println("You have an active loan. \nA message will be generated to remind you to pay the loan before due date.");
            double daysUntilDue = ChronoUnit.DAYS.between(today,nextDueDate);
            
            if(daysUntilDue<=5){
                System.out.println("Your loan will due on "+daysUntilDue+" days.");
                System.out.println("Due date: " + nextDueDate);
                
            }else{
                System.out.println("Due Date: "+nextDueDate);
                System.out.printf("Amount of monthly repayment: RM%.2f", monthlyRepayment);    
            }
            return;
        }
        
        //Compare the balances, then determine is user 
        //overdue//has upcoming loan repayment(<=5days)//has no upcoming loan repayment(<=5days)
        double currentBalance = menu.getBalance(user_id);
        //System.out.println("lastduedate: "+lastDueDate);
        //System.out.println("lastexpectedbalance: "+lastExpectedBalance);
        //System.out.println("nextdueddate: "+nextDueDate);
        //System.out.println("Nextexpectedbalance:"+nextExpectedBalance);
        
        if (currentBalance >lastExpectedBalance) {
            System.out.println("Reminder: Your loan is overdue!");
            System.out.println("Last due date: " + lastDueDate);
            System.out.printf("Expected balance by this date: RM%.2f\n", lastExpectedBalance);
            System.out.printf("Your current balance(loan): RM%.2f", currentBalance);
            
        } else { //currentbalance <= last expected balance (no overdue)
            long daysUntilDue = ChronoUnit.DAYS.between(today,nextDueDate);
            
            if(daysUntilDue<=5){
                System.out.println("Alert: Your loan will due on "+daysUntilDue+" day(s)!");
                System.out.println("Due date: "+nextDueDate);
                System.out.printf("Expected balance by this date: RM%.2f\n", lastExpectedBalance);
                System.out.printf("Your current balance(loan): RM%.2f", currentBalance);
                
            }else{ // daysUntilDue>5
                System.out.println("Your loan repayments are on track.");
                System.out.println("Due date: " + nextDueDate);
                System.out.printf("Expected balance by this date: RM%.2f\n", nextExpectedBalance);
                System.out.printf("Your current balance(loan): RM%.2f", currentBalance);
               
            }
        }
    }
    

}    

//System.out.println("lastDueDate\n"+lastDueDate);
//System.out.println("nextDueDate:\n"+nextDueDate);
//System.out.println("Today:\n"+today);
//System.out.println("Lastxpected Balance:\n"+lastExpectedBalance);
//System.out.println("Next expected Balance:\n"+nextExpectedBalance)

        
       