/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.lang.Integer;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import topic_2_ledger_system.FXcreditLoan;
import topic_2_ledger_system.FXcreditLoan;
import topic_2_ledger_system.FXdepositInterest;
import topic_2_ledger_system.FXdepositInterest;
import topic_2_ledger_system.FXhistory;
import topic_2_ledger_system.FXhistory;
import topic_2_ledger_system.FXsaving;
import topic_2_ledger_system.FXsaving;
import topic_2_ledger_system.FXtransaction;
import topic_2_ledger_system.FXtransaction;
import topic_2_ledger_system.file;
import topic_2_ledger_system.file;
import topic_2_ledger_system.main_system;
import topic_2_ledger_system.main_system;

/**
 *
 * @author I Khing
 */
public class menu {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static int user_id;
    public static void set_user(int user_id){
        menu.user_id = user_id;
    }
    
    @FXML
    public void initialize(){
        update_account_info();
    }
    
    
    
    
    
    
    
    
    @FXML Label username = new Label();
    @FXML Label balancefx = new Label();
    @FXML Label savingfx = new Label();
    @FXML Label loanfx = new Label();
    @FXML VBox addi_info = new VBox();
    
    @FXML
    public void update_account_info(){
        update_by_end_month(user_id);
        file.update_login_date(user_id);
        
        addi_info.getChildren().clear();
        
        List<String[]> users = file.get_user_csv();
        String name = null;
        for(int i = 1; i < users.size(); i++){
            if(user_id == Integer.parseInt(users.get(i)[0])){
                name = users.get(i)[1];
                break;
            }
        }
        username.setText(name);
        
        double bal = file.get_accbalance_csv(user_id);
        balancefx.setText("RM " + df.format(bal));
        
        String[] savings_data = file.get_savings_csv(user_id);
        String sav = savings_data[4];
        savingfx.setText("RM " + df.format(Double.parseDouble(sav)));
        
        String[] loans_data = file.get_loans_csv(user_id);
        String ln = loans_data[5];
        loanfx.setText("RM " + df.format(Double.parseDouble(ln)));
    }
    
    @FXML
    public void update_by_end_month(int user_id){
        ArrayList<String[]> user_csv = file.get_user_csv();
        String datePrevious = user_csv.get(user_id)[4];
        
 
        String[] datePreviousSplittedStr = datePrevious.split("/"); 
        int[] datePreviousSplitted = new int[datePreviousSplittedStr.length];
        for (int i = 0; i < datePreviousSplittedStr.length; i++){
            datePreviousSplitted[i] = Integer.parseInt(datePreviousSplittedStr[i]);
        }
        
        //int dayDatePrevious = datePreviousSplitted[0];
        int monthDatePrevious = datePreviousSplitted[1];
        int yearDatePrevious= datePreviousSplitted[2];
        
        LocalDate date = LocalDate.now();
        //int dayDate = date.getDayOfMonth();
        int monthDate = date.getMonthValue();
        int yearDate = date.getYear();
        
        // testrun
        //yearDate = 2025;
        //monthDate = 1;
        
        boolean isUpdate = true;
        
        if(yearDate > yearDatePrevious){
            isUpdate = false;
        }
        else if (yearDate == yearDatePrevious){
            if(monthDate > monthDatePrevious){
                isUpdate= false;
            }
        }
        
       
        String[] savings = file.get_savings_csv(user_id);
        double balance = file.get_accbalance_csv(user_id);
        double monthlySavings = Double.parseDouble(savings[4]);
        
        if(!isUpdate){
            //Declaring new window variable
            Stage popup = new Stage();
            VBox vb = new VBox();
            vb.setSpacing(10);
            
            Label lab1 = new Label();
            Label lab2 = new Label();
            Label lab3 = new Label();
            Label lab4 = new Label();
            
            Button close = new Button("Close");
            
            //Setting text
            lab1.setText("The savings amount has been successfully added to your balance from the previous login month.");
               
            balance -= monthlySavings;
            if (balance < 0.0){
                balance = 0.0;
            }
            lab2.setText("Final balance of the last login month: RM" + df.format(balance));
                
            balance = 0.0;
            file.set_accbalance_csv(user_id, balance);        
                
            monthlySavings -= balance;
            if (monthlySavings < 0.0){
                monthlySavings = 0.0;
            }
                
            savings[4] = String.valueOf(monthlySavings);
            file.set_savings_csv(savings);
                
            lab3.setText("Remaining balance to be carried forward to this month: RM" + df.format(monthlySavings));
            lab4.setText("The balance amount has been reset to zero for this month.");
            
            
            //Setting javafx
            close.setPrefWidth(80);
            close.setOnAction(e -> {
                popup.close();
            });
            
            vb.getChildren().addAll(lab1, lab2, lab3, lab4, close);
            vb.setAlignment(Pos.CENTER);
            vb.setPadding(new Insets(15, 15, 15, 15));
            Scene notify = new Scene(vb, 570, 180);
            
            popup.setScene(notify);
            popup.setAlwaysOnTop(true);
            popup.show();   
        }
    }
    
    private static boolean isOverdue = false;
    public static void set_overdue(boolean isOverdue){
        menu.isOverdue = isOverdue;
    }
    
    public void ReminderSystem(int user_id){
   
        boolean activeLoan = FXcreditLoan.getActiveLoan(user_id);
        if (!activeLoan) {
            Label info = new Label();
            info.setText("There are no active loans.");
            addi_info.getChildren().add(info);
            return;
        }

        //Obtain loan details
        String[] loan_csv = file.get_loans_csv(user_id);
        int period = Integer.parseInt(loan_csv[4]); // Repayment period
        LocalDate startDate = LocalDate.parse(loan_csv[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double totalLoanAmount = Double.parseDouble(loan_csv[2]);
        double monthlyRepayment = totalLoanAmount / period;
        if(totalLoanAmount != Double.parseDouble(loan_csv[5])){
            monthlyRepayment = Double.parseDouble(loan_csv[5]) - ((int)(Double.parseDouble(loan_csv[5]) / monthlyRepayment) * monthlyRepayment);
        }
        double currentBalance = FXcreditLoan.getBalance(user_id);

        //Create a list that collect duedates and expectedbalances every month
        String[][] scheduledExpectation = new String[period][2];
        for (int i = 0; i < period; i++) {
            LocalDate dueDate = startDate.plusMonths(i + 1);
            scheduledExpectation[i][0] = dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            double expectedBalance = totalLoanAmount - (monthlyRepayment * (i + 1));
            scheduledExpectation[i][1] = String.format("%.2f", expectedBalance);
            //System.out.println(scheduledExpectation[i][0]);
        }
        
        LocalDate today = LocalDate.now();
        LocalDate lastDueDate = null;
        double lastExpectedBalance = 0;        
        
        //Find the duedate before today by comparing todaydate and duedates found above
        for (int i = 0; i < period; i++) {
            LocalDate dueDate = LocalDate.parse(scheduledExpectation[i][0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
            LocalDate dueDate = LocalDate.parse(scheduledExpectation[i][0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (dueDate.isAfter(today)) {
                nextDueDate = dueDate;
                nextExpectedBalance = Double.parseDouble(scheduledExpectation[i][1]);
                break; 
            }
        }
        
        //Declaring new window variable
        Stage popup = new Stage();
        VBox vb = new VBox();
        vb.getChildren().clear();
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(15, 15, 15, 15));

        Label lab1 = new Label();
        Label lab2 = new Label();
        Label lab3 = new Label();
        Label lab4 = new Label();
        Label lab5 = new Label();
        
        HBox hb = new HBox();
        hb.getChildren().clear();
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(15, 15, 15, 15));

        Button close = new Button("Close");
        close.setPrefWidth(80);
        close.setOnAction(e -> {
            popup.close();
        });
        
        Button paynow = new Button("Pay now");
        paynow.setPrefWidth(80);
        paynow.setOnAction(e -> {
            FXcreditLoan.set_user(user_id);
            double mp = totalLoanAmount / period;
            mp = Double.parseDouble(loan_csv[5]) - ((int)(Double.parseDouble(loan_csv[5]) / mp) * mp);
            FXcreditLoan.set_payment(mp);
            try {
                main_system.setScene("creditloan");
            } catch (IOException ex) {
                System.out.println("Error setting up transaction scene.");
            }
            popup.close();
        });
        hb.getChildren().addAll(close, paynow);
        
        if(monthlyRepayment == 0){
            lab1.setText("You have paid the loan for this month.");
            lab2.setText("Due date: " + nextDueDate);
            lab3.setText("Your current total loan: RM" + df.format(currentBalance));
            
            vb.getChildren().addAll(lab1, lab2, lab3, lab4, close);
            Scene notify = new Scene(vb, 280, 180);

            popup.setScene(notify);
            popup.setAlwaysOnTop(true);
            popup.show(); 
            
            return;
        }
        
        // If no valid lastduedate is found (First month of loan repayment)
        if (lastDueDate == null) {
            lab1.setText("You have an active loan. \nA message will be generated to\nremind you to pay the loan before due date.");
            lab1.setWrapText(true);
            lab1.textAlignmentProperty().set(TextAlignment.CENTER);
            double daysUntilDue = ChronoUnit.DAYS.between(today,nextDueDate);
            if(daysUntilDue<=5){
                lab2.setText("Your loan will due on "+daysUntilDue+" days.");
                lab3.setText("Due date: " + nextDueDate);
            }else{
                lab2.setText("Due Date: "+nextDueDate);
                lab3.setText("Amount of monthly repayment: RM" + df.format(monthlyRepayment));    
            }
            
            vb.getChildren().addAll(lab1, lab2, lab3, hb);
            
            Scene notify = new Scene(vb, 340, 230);

            popup.setScene(notify);
            popup.setAlwaysOnTop(true);
            popup.show(); 
            
            return;
        }
        
        //Compare the balances, then determine is user 
        //overdue//has upcoming loan repayment(<=5days)//has no upcoming loan repayment(<=5days)
        //System.out.println("lastduedate: "+lastDueDate);
        //System.out.println("lastexpectedbalance: "+lastExpectedBalance);
        //System.out.println("nextdueddate: "+nextDueDate);
        //System.out.println("Nextexpectedbalance:"+nextExpectedBalance);
        
        if (currentBalance >lastExpectedBalance) {
            lab1.setText("Reminder: Your loan is overdue!");
            lab2.setText("Last due date: " + lastDueDate);
            lab3.setText("Expected payment by this date: RM" + df.format(monthlyRepayment));
            lab4.setText("Your current total loan: RM" + df.format(currentBalance));
            lab5.setText("Futher Debits and Credits \nTransaction are not allowed!");
            
            set_overdue(true);
            
            vb.getChildren().addAll(lab1, lab2, lab3, lab4, lab5, hb);
            Scene notify = new Scene(vb, 340, 260);

            popup.setScene(notify);
            popup.setAlwaysOnTop(true);
            popup.show();  
            
            return;
            
        } else { //currentbalance <= last expected balance (no overdue)
            long daysUntilDue = ChronoUnit.DAYS.between(today,nextDueDate);
            
            if(daysUntilDue<=5){
                lab1.setText("Alert: Your loan will due on "+daysUntilDue+" day(s)!");
                lab2.setText("Due date: "+nextDueDate);
                lab3.setText("Expected payment by this date: RM" + df.format(monthlyRepayment));
                lab4.setText("Your current balance(loan): RM" + df.format(currentBalance));
                
            }else{ // daysUntilDue>5
                lab1.setText("Your loan repayments are on track.");
                lab2.setText("Due date: " + nextDueDate);
                lab3.setText("Expected payment by this date: RM" + df.format(monthlyRepayment));
                lab4.setText("Your current balance(loan): RM" + df.format(currentBalance));
            }
        }
        
            
        vb.getChildren().addAll(lab1, lab2, lab3, lab4, hb);
        Scene notify = new Scene(vb, 340, 230);

        popup.setScene(notify);
        popup.setAlwaysOnTop(true);
        popup.show();   
    }
    
    
    
    
    
    
    
    
    
    
    public void toTransaction() throws IOException{
        if(isOverdue){
            ReminderSystem(user_id);
            return;
        }
        
        FXtransaction.set_user(user_id);
        main_system.setScene("transaction");
    }
    
    public void toSaving() throws IOException{
        FXsaving.set_user(user_id);
        main_system.setScene("saving");
    }
    
    public void toCreditLoan() throws IOException{
        FXcreditLoan.set_user(user_id);
        main_system.setScene("creditloan");
    }
    
    public void toDepositInterest() throws IOException{
        FXdepositInterest.set_user(user_id);
        main_system.setScene("depositinterest");
    }
    
    public void toHistory() throws IOException{
        FXhistory.set_user(user_id);
        main_system.setScene("history");
    }
    
    public void todataVisualization() throws IOException{
        FXdataVisual.set_user(user_id);
        main_system.setScene("dataVisual");
//        FXdataVisual.dataVisualization(user_id);
    }
    
    public void logOut() throws IOException {
        main_system.setScene("login_register");
    }
}
