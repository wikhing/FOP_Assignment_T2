/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;
import java.util.Scanner;
/**
 *
 * @author Joey
 */
public class record_debit_and_credit {
    public static void main(String[] args){
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
                        System.out.print("Enter agian the amount: ");
                        amount = sc.nextDouble();
                    }
                    System.out.print("Enter description: ");
                    sc.nextLine();
                    String description = sc.nextLine();
                    System.out.print("\nDebit Successfully Recorded!!!");
                    break;
                }
                case 2: {
                    System.out.print("== Credit ==");
                    System.out.print("\nEnter amount: ");
                    double credit_amount = sc.nextDouble();
                    balance = balance - credit_amount;
                    System.out.print("Enter description: ");
                    sc.nextLine();
                    String credit_description = sc.nextLine();
                    System.out.print("\nCredit Successfully Recorded!!!");
                    break;
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
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
            
        }

                
    }
    
}
