/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

/**
 *
 * @author Tan Kai Chun
 */
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class deposit_interest_predictor {
    public static void depositInterestPredictor(int user_id){
        Scanner sc = new Scanner(System.in);
        // Retrieve all bank data from CSV
        List<String[]> banks = file.get_bank_csv();
    
        // Display all banks available and respective interest rates
        System.out.println("Available Banks: ");
        System.out.println("===================");
        for (String[] bank : banks){
            System.out.printf("%s-%s:%.2f%%\n",bank[0],bank[1],Double.parseDouble(bank[2]));
            // bank[0] = bank_id
            // bank[1] = bank_name
            // bank[2] = interest_rate(as a String, needs to be parsed into Double)
        }
    
        //Prompt user to select a bank with bank ID provided
        //Loop to ensure valid bank ID input
        int bank_id = -1;
        double selectedBankIntRate = 0.0;
        String selectedBank = "";
        while (true){
            System.out.print("\nEnter a bank ID to select the bank: ");
            try {
                bank_id = sc.nextInt();
                boolean validBankID = false;
                for (String[] bank : banks){
                    if (Integer.parseInt(bank[0]) == bank_id){ 
                        selectedBank = bank[1];
                        selectedBankIntRate = Double.parseDouble(bank[2]);
                        validBankID = true;
                        break;
                    }
                }
            
                if (!validBankID){
                    System.out.println("Invalid bank ID selected. Please re-enter!");
                    continue;
                }
            
                System.out.println("You have selected "+selectedBank+" with interest rate of "+selectedBankIntRate+"% ");
                break;// Exit the loop once valid bank ID is entered
            } catch (InputMismatchException exc) {// Identifier exc holds the exception object
                System.out.println("Invalid input. Please enter a valid integer for bank ID!");
                sc.next();// Clear invalid input
            }
        }
    
        System.out.print("\nDeposit amount: ");
        double deposit = file.get_accbalance_csv(user_id);// Retrieves user's deposit balance
    
        System.out.println("");
    
        // Loop to ensure period input
        int period = -1;
        while (true){
            System.out.println("Interest earned over a period of: ");
            System.out.println("1. Daily");
            System.out.println("2. Monthly");
            System.out.println("3. Annually");
            System.out.print("\nPick a period: ");
            try{
                period = sc.nextInt();
                // Check if the period is valid
                if (period < 1 || period > 3) {
                    System.out.println("Invalid period selected. Please re-enter!");
                    continue; // Allow user to re-enter the selection
                }
            
                // Calculate interest earned for valid input
                double interestEarned = 0.0;
                switch (period){
                    case 1:
                        interestEarned = (deposit*(selectedBankIntRate/100))/365;
                        System.out.printf("Daily Interest: %.2f\n",interestEarned);
                        break;
                    case 2:
                        interestEarned = (deposit*(selectedBankIntRate/100))/12;
                        System.out.printf("Monthly Interest: %.2f\n",interestEarned);
                        break;
                    case 3: 
                        interestEarned = deposit*(selectedBankIntRate/100);
                        System.out.printf("Annually Interest: %.2f\n",interestEarned);
                        break;
                }
                break;// Exit the loop after valid period
            } catch(InputMismatchException exc){
                System.out.println("Invalid input. Please enter a valid integer for period!");
                sc.next();// Clear invalid input
            }
        }
    }
    
}
