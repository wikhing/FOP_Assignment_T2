/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author I Khing
 */
public class FXcreditLoan {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static int user_id = 1;
    public static void set_user(int user_id){
        FXcreditLoan.user_id = user_id;
    }
    
    private static double toPay = -1;
    public static void set_payment(double toPay){
        FXcreditLoan.toPay = toPay;
    }
    
    @FXML
    public void initialize(){
        loanSet();
    }
    
    @FXML
    public void back() throws IOException{
        main_system.setScene("menu");
    }
    
    
    
    
    
    
    
    
    
    
    @FXML
    public void loanSet() {
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToday = dateNow.format(pattern);
        
        date = dateToday;
        
        loan_infos.getChildren().removeAll(lT1, lT2, lT3, lT4);
        
        boolean activeLoan = getActiveLoan(user_id);
        
        int period = getPeriod(user_id);
        
        if (!activeLoan) {
            lT1.setText("No active loan to repay. You may apply for new loan.");
            loan_infos.getChildren().add(lT1);
            return;
        }else{
            lT1.setWrapText(true);
            lT1.setText("You already have an active loan.\nPlease repay it before applying for another.");
            loan_infos.getChildren().add(lT1);
        }

//        if (dateNow.isAfter(dateNow.plusMonths(period))) {
//            lT2.setText("Loan repayment period has ended. Further debits and credits are not allowed.");
//            loan_infos.getChildren().add(lT2);
//        }
        
        if(toPay != -1){
            payAmount.setText(df.format(toPay));
            toPay = -1;
        }
    }
    
    private static final Scanner sc = new Scanner(System.in);
    
    public static boolean getActiveLoan(int user_id) {
        
        boolean isActive = false;
        
        try {
            String[] loan_csv = file.get_loans_csv(user_id);
            isActive = loan_csv[6].equals("active");
        } catch (Exception e) {
            return false;
        }
                
        return isActive;
    }
    
    public static double getBalance(int user_id) {
        try {
            String[] loan_csv = file.get_loans_csv(user_id);
            return Double.parseDouble(loan_csv[5]);
        } catch (Exception e) {
            return 0;
        }
        
    }
    
    public static void updateLoan(int user_id, double totalAmount, double monthlyPayment, int period, boolean activeLoan, String dateToday, double interestRate) {
        
        String[] loan = {"", 
                        String.valueOf(user_id), 
                        String.valueOf(totalAmount), 
                        String.valueOf(interestRate), 
                        String.valueOf(period), 
                        df.format(totalAmount), 
                        (activeLoan ? "active" : "inactive"), 
                        dateToday};
        
        
        file.set_loans_csv(loan);
    }
    
    public static void updateLoan(int user_id, double balance, int period, boolean activeLoan) {
        
        String[] loan_csv = file.get_loans_csv(user_id);
        
        String[] loan = {"", 
                        String.valueOf(user_id), 
                        loan_csv[2], 
                        loan_csv[3], 
                        String.valueOf(period), 
                        df.format(balance), 
                        (activeLoan ? "active" : "inactive"), 
                        loan_csv[7]};
        
        
        file.set_loans_csv(loan);
        
    }
    
    public static int getPeriod(int user_id) {
        String[] loan_csv = file.get_loans_csv(user_id);
        return Integer.parseInt(loan_csv[4]);
    }
    
    @FXML TextField loanPrincipal = new TextField();
    @FXML TextField loanInterest = new TextField();
    @FXML TextField loanPeriod = new TextField();
    
    @FXML TextField payAmount = new TextField();
    
    @FXML VBox loan_infos = new VBox();
    Label lT1 = new Label();
    Label lT2 = new Label();
    Label lT3 = new Label();
    Label lT4 = new Label();
    
    String date;
    
    @FXML
    public void applyLoan(){
        boolean activeLoan = getActiveLoan(user_id);
        List<String[]> loanHis = file.get_loanHistory_csv(user_id);
        
        loan_infos.getChildren().removeAll(lT1, lT2, lT3, lT4);
        
        if(loanPrincipal.getText().isEmpty() || loanInterest.getText().isEmpty() || loanPeriod.getText().isEmpty()){
            lT1.setText("Please fill in all section!");
            loan_infos.getChildren().add(lT1);
            return;
        }
        
        if(loanPrincipal.getText().matches("[a-z]+") || loanInterest.getText().matches("[a-z]+") || loanPeriod.getText().matches("[a-z]+")) {
            lT1.setText("Invalid input, please retry.");
            loan_infos.getChildren().add(lT1);
            return;
        }
        
        if (activeLoan) {
            lT1.setWrapText(true);
            lT1.setText("You already have an active loan.\nPlease repay it before applying for another.");
            loan_infos.getChildren().add(lT1);
            return;
        }
        
        double rate = Double.parseDouble(loanInterest.getText());
        int period = Integer.parseInt(loanPeriod.getText());
        
        double principal = Double.parseDouble(loanPrincipal.getText());

        if (!(principal > 0 && rate > 0 && period > 0)) {
            lT1.setText("Invalid input, please retry.");
            loan_infos.getChildren().add(lT1);
            return;
        }

        double totalAmount = principal + (principal * (rate / 100) * (period / 12.0));
        double monthlyPayment = totalAmount / period;

        activeLoan = true;
        
        lT1.setText("Loan approved!");
        lT2.setText("Total repayment amount: " + df.format(totalAmount));
        lT3.setText("Monthly repayment amount: " + df.format(monthlyPayment));
//        System.out.println("Repayment period: " + period + " months");
        lT4.setText("Loan start date: " + date);
        loan_infos.getChildren().addAll(lT1, lT2, lT3, lT4);

        updateLoan(user_id, Double.parseDouble(df.format(totalAmount)), Double.parseDouble(df.format(monthlyPayment)), period, activeLoan, date, rate);
        loanHis.add(new String[]{"", String.valueOf(user_id), "0", "0", String.valueOf(totalAmount), date});
        file.set_loanHistory_csv(loanHis);
    }
    
    @FXML
    public void repayLoan(){
        loan_infos.getChildren().removeAll(lT1, lT2, lT3, lT4);
        
        List<String[]> loanHis = file.get_loanHistory_csv(user_id);
        boolean activeLoan = getActiveLoan(user_id);
        double balance = getBalance(user_id);
        int period = getPeriod(user_id);
        
        if (!activeLoan) {
            lT1.setText("No active loan to repay.");
            loan_infos.getChildren().add(lT1);
            return;
        }   
        
        String pay_amount_str = payAmount.getText();
        if(pay_amount_str.isEmpty()){
            lT1.setText("Please fill in all section!");
            loan_infos.getChildren().add(lT1);
            return;
        }
        if(pay_amount_str.matches("[a-z]")) {
            lT1.setText("Invalid input, please retry.");
            loan_infos.getChildren().add(lT1);
            return;
        }
        
        double paymentAmount = Double.parseDouble(pay_amount_str);

        if (paymentAmount <= 0) {
            lT1.setText("Invalid input, please retry.");
            loan_infos.getChildren().add(lT1);
            return;
        }

        balance -= paymentAmount;
        if(!loanHis.isEmpty()){
            loanHis.add(new String[]{"", String.valueOf(user_id), String.valueOf(paymentAmount), String.valueOf(Double.parseDouble(loanHis.get(loanHis.size()-1)[3]) + paymentAmount), String.valueOf(balance), date});
        }else{
            loanHis.add(new String[]{"", String.valueOf(user_id), String.valueOf(paymentAmount), String.valueOf(paymentAmount), String.valueOf(balance), date});
        }
        lT1.setText("Payment successful! ----" + date);
        
        if (balance > 0) {
            lT2.setText("Remaining balance: " + balance);
        } else if (balance < 0) {
            lT2.setText("Remaining balance is 0");
            lT3.setText("Your change: RM" + df.format(-balance));
            loanHis.get(loanHis.size()-1)[2] = df.format(Double.parseDouble(loanHis.get(loanHis.size()-1)[2]) + balance);
            loanHis.get(loanHis.size()-1)[3] = df.format(Double.parseDouble(loanHis.get(loanHis.size()-1)[3]) + balance);
        }
        
        if (balance <= 0) {
            lT4.setText("Loan fully repaid. Thank you!");
            activeLoan = false; // Mark loan as fully repaid
            balance = 0;
        }
        loan_infos.getChildren().addAll(lT1, lT2, lT3, lT4);
        
        updateLoan(user_id, Double.parseDouble(df.format(balance)), period, activeLoan);
        file.set_loanHistory_csv(loanHis);
        
        menu m = new menu();
        m.ReminderSystem(user_id);
    }
}
