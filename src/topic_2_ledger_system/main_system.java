/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Write your name here
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun
 *
 */
public class main_system extends Application{

    private static Scene scene;
    public static Scene mainScene;

    private static Stage primStage;
    @Override
    public void start(Stage stage) throws IOException {
        primStage = stage;
        mainScene = new Scene(loadFXML("menu"), 1170, 420);
        scene = new Scene(loadFXML("login_register"), 640, 480);
        stage.setScene(mainScene);
        stage.show();
    }

    static void setScene(String fxml) throws IOException {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        
        if(fxml.equals("menu")){
            primStage.setScene(mainScene);
            primStage.setX((bounds.getMaxX() - mainScene.getWidth()) / 2);
            primStage.setY((bounds.getMaxY() - mainScene.getHeight()) / 2);
        }else if(fxml.equals("login_register")){
            primStage.setScene(scene);
            primStage.setX((bounds.getMaxX() - scene.getWidth()) / 2);
            primStage.setY((bounds.getMaxY() - scene.getHeight()) / 2);
        }
        
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_system.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    
    
//    private static Scanner sc = new Scanner(System.in);
//    private static int user_id;
//    
//    
//    public static void printMenu(String username, double balance, double saving, double loan) {
//        System.out.println("\n\n== Welcome, " + username + " ==");
//        System.out.print("Balance: " );
//        System.out.printf("%.2f",balance);
//        System.out.print("\nSavings: ");
//        System.out.printf("%.2f", saving);
//        System.out.print("\nLoan: ");
//        System.out.printf("%.2f",loan);
//
//        System.out.println("\n\n== Transaction ==");
//        System.out.println("1. Debit");
//        System.out.println("2. Credit");
//        System.out.println("3. History");
//        System.out.println("4. Savings");
//        System.out.println("5. Credit Loan");
//        System.out.println("6. Deposit Interest Predictor");
//        System.out.println("7. Logout\n");
//        System.out.print("> ");
//    }
//
//    private static void debit() {
//        double balance = file.get_accbalance_csv(user_id);
//        String[] savings = file.get_savings_csv(user_id);
//        
//        double amount = 0, toSave = 0;
//        String description = "", dateToday = "";
//        while (true) {
//            System.out.println("== Debit ==");
//            System.out.print("Enter amount: ");
//
//            amount = sc.nextDouble();
//
//            if(savings[2].equals("active")){
//                toSave = amount * Integer.parseInt(savings[3]) / 100.0;
//            }
//            
//            if (amount > 0 && amount < (Math.pow(10, 9))) {
//                balance += amount - toSave;
//            } else {
//                System.out.println("Invalid input, please retry");
//                continue;
//            }
//
//            // Automatically get date
//            LocalDate date = LocalDate.now();
//            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            dateToday = date.format(pattern);
//
//            System.out.print("Enter description: ");
//            description = sc.next();
//            if (description.length() > 100){
//                System.out.println("Error: Description exceeds 100 characters, please retry. ");
//            } else {
//                System.out.print("\nDebit Successfully Recorded!!!");
//                break;
//            }
//        }
//        
//        List<String[]> transaction = file.get_transactions_csv(user_id);
//        transaction.add(new String[]{"", String.valueOf(user_id), "debit", String.valueOf(amount), description, dateToday});
//        file.set_transactions_csv(transaction);
//        
//        file.set_accbalance_csv(user_id, balance);
//        
//        savings[4] = String.valueOf(Double.parseDouble(savings[4]) + toSave);
//        file.set_savings_csv(savings);
//    }
//    
//    private static void credit() {
//        double balance = file.get_accbalance_csv(user_id);
//        
//        System.out.println("== Credit ==");
//        
//        double credit_amount = 0;
//        while(true){
//            System.out.print("Enter amount: ");
//            credit_amount = sc.nextDouble();
//
//            if(credit_amount > 0 && credit_amount < balance){
//                balance = balance - credit_amount;
//                break;
//            }
//            
//            System.out.println();
//            System.out.println("Please enter amount greater than 0 and less than your balance.");
//        }
//        
//        sc.nextLine();
//        String credit_description = "";
//        while(true){
//            System.out.print("Enter description: ");
//            credit_description = sc.nextLine();
//            
//            if(credit_description.length() <= 100){
//                System.out.println("\n\nCredit Successfully Recorded!!!\n");
//                break;
//            }
//            
//            System.out.println("\nError: Description exceeds 100 characters. ");
//        }
//        
//        // Automatically get date
//        LocalDate date = LocalDate.now();
//        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String dateToday = date.format(pattern);
//        
//        List<String[]> transaction = file.get_transactions_csv(user_id);
//        transaction.add(new String[]{"", String.valueOf(user_id), "credit", String.valueOf(credit_amount), credit_description, dateToday});
//        file.set_transactions_csv(transaction);
//        
//        file.set_accbalance_csv(user_id, balance);
//    }
//    
//    private static void history() {
//        System.out.print("Enter the month and year (eg. MM/YYYY): ");
//        sc.nextLine();
//        String monthYear = sc.nextLine();
//        
//        // In case of invalid month/year input
//        if (!monthYear.matches("\\d{2}/\\d{4}")) {
//            System.out.println("Invalid format. Please use MM/YYYY.");
//            return;
//        }
//        view_export_csv.viewAndExportTransactions(user_id, monthYear);
//    }
//    
//    private static void saving() {
//        System.out.println("\n== Savings ==");
//        
//        char choice;
//        boolean isActive = false;
//        int percentage = 0;
//        while(true){
//            System.out.print("Are you sure you want to activare it? (Y/N) : ");
//            choice = sc.next().charAt(0);
//            
//            if(choice == 'Y' || choice == 'y'){
//                isActive = true;
//                while(true){
//                    System.out.print("Please enter the percentage you wish to deduct from the next debit: ");
//                    percentage = sc.nextInt();
//
//                    if (percentage >= 0 && percentage <= 100){
//                        System.out.println("\n\nSavings Setting added successfully!!!\n");
//                        break;
//                    }
//                    
//                    System.out.println("Please enter valid percentage!");
//                }
//                break;
//            }else if(choice == 'N'|| choice == 'n'){
//                isActive = false;
//                System.out.println("\n\nSavings Setting is not added!!!");
//                break;
//            }
//            
//            System.out.println("Invalid! Please enter Y or N");
//        }
//        
//        String[] savings = file.get_savings_csv(user_id);
//        if(isActive){
//            savings[2] = "active";
//            savings[3] = String.valueOf(percentage);
//        }else if(!isActive){
//            savings[2] = "inactive";
//            savings[3] = "0";
//            if(savings[4] != "0"){
//                double balance = file.get_accbalance_csv(user_id);
//                balance += Double.parseDouble(savings[4]);
//                
//                file.set_accbalance_csv(user_id, balance);
//            }
//            savings[4] = "0";
//        }
//        file.set_savings_csv(savings);
//        
//        
//    }
//    
//    private static void creditLoan(int user_id) {
//        
//        // Automatically get date
//        LocalDate date = LocalDate.now();
//        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String dateToday = date.format(pattern);
//
//        while (true) {
//            System.out.println("\n---- Credit Loan System ----");
//            System.out.println("1. Apply Loan");
//            System.out.println("2. Repay Loan");
//            System.out.println("3. Exit");
//            System.out.print("Enter your choice: ");
//            int choices = sc.nextInt();
//            
//            
//            if (choices == 1) {
//                credit_loan.applyLoan(user_id, dateToday); 
//            } else if (choices == 2) {
//                credit_loan.repayLoan(user_id, dateToday, date);   
//            } else if (choices == 3) {
//                System.out.println("Exiting the system. Thank you!");
//                break;
//            } else {
//                System.out.println("Invalid input, please retry.");
//            }
//        }
//        
//    }
//    
//    private static void depositInterestPredictor() {
//        deposit_interest_predictor.depositInterestPredictor(user_id);
//    }
//    
//    private static int logOut() {
//        System.out.println("\nUser successfully logged out.");
//        return -1;
//    }
//    
//    
//    
//    
//    private static int optionHandler(int option, int user_id) {
//        
//        int info = 0;
//        
//        if (option == 1 || option == 2) {
//            if (!credit_loan.getActiveLoan(user_id) && credit_loan.getBalance(user_id) != 0) {
//                System.out.println("Loan unpaid, please pay your loan.");
//                return -1;
//            }
//        }
//        
//        switch(option) {  
//            case 1 -> debit();
//            case 2 -> credit();
//            case 3 -> history();
//            case 4 -> saving();
//            case 5 -> creditLoan(user_id);
//            case 6 -> depositInterestPredictor();
//            case 7 -> info = logOut();
//            default -> {
//            }
//        }
//        
//        return info;
//    }
//    
//    
//    
//    private static void loginPage(int user_id) {
//        while (true) {
//            // Get basic info of user
//            ArrayList<String[]> user_csv = file.get_user_csv();
//            String username = user_csv.get(user_id)[1];
//            String email = user_csv.get(user_id)[2];
//
//            // Get other info of user
//            double balance = file.get_accbalance_csv(user_id);
//
//            String[] savings_data = file.get_savings_csv(user_id);
//            double user_saving = Double.parseDouble(savings_data[4]);
//
//            String[] loans_data = file.get_loans_csv(user_id);
//            double loans = 0, loansThisMonth = 0;
//            if(loans_data[5] != null){
//                loans = Double.parseDouble(loans_data[5]);
//                loansThisMonth = Double.parseDouble(loans_data[5]) / Double.parseDouble(loans_data[4]);//Not sure need this or not, for reminder?
//            }
//        
//            printMenu(username, balance, user_saving, loans);
//            String rawOption = sc.next();
//            int option;
//            
//            try {
//                option = Integer.parseInt(rawOption);
//                
//                if (!(option >= 1 && option <= 7)) {
//                    System.out.println("Invalid option, please retry.");
//                    continue;
//                }
//                
//                if (optionHandler(option, user_id) == -1) {
//                    break;
//                }
//                
//            } catch (Exception e) {
//                System.out.println("Invalid option, please retry.");
//            } 
//        }
//    }
    
    
    public static void main(String[] args) throws IOException{
//        login_register.initialize();
        launch(args);
    }
}
