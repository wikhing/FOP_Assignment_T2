/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
public class menu {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static int user_id = 2;
    public static void set_user(int user_id){
        menu.user_id = user_id;
    }
    
    @FXML
    public void initialize(){
        update_account_info();
        
        sav_percentage.valueProperty().addListener((obs, oldVal, newVal) -> saving_setting());
        loan_choice.valueProperty().addListener((obs, oldVal, newVal) -> creditLoan(user_id));
    }
    
    
    
    
    
    
    
    
    
    
    @FXML Label username = new Label();
    @FXML Label balancefx = new Label();
    @FXML Label savingfx = new Label();
    @FXML Label loanfx = new Label();
    
    @FXML
    public void update_account_info(){
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
    
    
    
    
    
    
    
    
    
    
    @FXML TextField record_amount = new TextField();
    @FXML ChoiceBox record_choice = new ChoiceBox();
    @FXML TextArea record_descp = new TextArea();
    @FXML Label trans_info = new Label();
    
    @FXML Button record_submit = new Button();
    
    public void toTransaction(){
        transaction_record();
    }
    
    @FXML
    private void transaction_record() {
        double balance = file.get_accbalance_csv(user_id);
        String[] savings = file.get_savings_csv(user_id);
        
        double amount = 0, toSave = 0;
        String description = "", dateToday = "";
        if(record_amount.getText().length() == 0){
            trans_info.setText("Please fill in all the section of the form.");
            return;
        }
        amount = Double.parseDouble(record_amount.getText());
        
        String type = "";
        switch (record_choice.getValue().toString()) {
            case "Debit" -> {
                type = "debit";
                if(savings[2].equals("active")){
                    toSave = amount * Integer.parseInt(savings[3]) / 100.0;
                }
                
                if (amount > 0 && amount < (Math.pow(10, 9))) {
                    balance += amount - toSave;
                } else {
                    trans_info.setText("Invalid input, please retry");
                    return;
                }
            }
            case "Credit" -> {
                type = "credit";
                if(amount > 0 && amount < balance){
                    balance -= amount;
                }else{
                    trans_info.setText("Please enter amount less than your balance.");
                    return;
                }
                
                
            }
            default -> trans_info.setText("Error!");
        }
        

        // Automatically get date
        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateToday = date.format(pattern);


        description = record_descp.getText();
        if (description.length() > 100){
            trans_info.setText("Error: Description exceeds 100 characters, please retry.");
        } else if(description.length() == 0){
            trans_info.setText("Please fill in all the section of the form.");
        }else{
            List<String[]> transaction = file.get_transactions_csv(user_id);
            transaction.add(new String[]{"", String.valueOf(user_id), type, String.valueOf(amount), description, dateToday});
            file.set_transactions_csv(transaction);

            file.set_accbalance_csv(user_id, balance);

            savings[4] = String.valueOf(Double.parseDouble(savings[4]) + toSave);
            file.set_savings_csv(savings);

            update_account_info();
            
            if(type.equals("debit")){
                trans_info.setText("Debit Successfully Recorded!!!");
            }else if(type.equals("credit")){
                trans_info.setText("Credit Successfully Recorded!!!");
            }
        }   
    }
    
    
    
    
    
    
    
    
    
    
    @FXML CheckBox user_choice = new CheckBox();
    @FXML Spinner sav_percentage = new Spinner();
    @FXML Label sav_info = new Label();
            
    @FXML
    public void saving_setting() {
        char choice;
        boolean isActive = false;
        int percentage = 0;
        
        choice = user_choice.isSelected() ? 'y' : 'n';
            
        if(choice == 'y'){
            isActive = true;
            
            percentage = Integer.parseInt(sav_percentage.getValue().toString());

            if (percentage >= 0 && percentage <= 100){
                sav_info.setText("Savings Setting added successfully!");
            }else{
                sav_info.setText("Please enter valid percentage!");
            }
        }else if(choice == 'n'){
            isActive = false;
            sav_percentage.getValueFactory().setValue(0);
            sav_info.setText("Savings Setting is removed!");
        }
        
        String[] savings = file.get_savings_csv(user_id);
        if(isActive){
            savings[2] = "active";
            savings[3] = String.valueOf(percentage);
        }else if(!isActive){
            savings[2] = "inactive";
            savings[3] = "0";
            if(savings[4] != "0.0"){
                double balance = file.get_accbalance_csv(user_id);
                balance += Double.parseDouble(savings[4]);
                
                file.set_accbalance_csv(user_id, balance);
            }
            savings[4] = "0.0";
        }
        file.set_savings_csv(savings);
        
        update_account_info();
    }
    
    
    
    
    
    
    
    
    
    
    @FXML ChoiceBox loan_choice = new ChoiceBox();
    
    public void creditLoan(int user_id) {
        
        // Automatically get date
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToday = dateNow.format(pattern);

        String strChoice = loan_choice.getValue().toString();
        switch(strChoice){
            case "Apply Loan" -> loanApplySet(dateToday);
            case "Repay Loan" -> loanRepaySet(dateToday, dateNow);
            default -> System.out.println("Invalid input, please retry.");
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
                        String.valueOf(totalAmount), 
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
                        String.valueOf(balance), 
                        (activeLoan ? "active" : "inactive"), 
                        loan_csv[7]};
        
        
        file.set_loans_csv(loan);
        
    }
    
    public static int getPeriod(int user_id) {
        String[] loan_csv = file.get_loans_csv(user_id);
        return Integer.parseInt(loan_csv[4]);
    }
    
    @FXML StackPane loan_form;
    
    TextField loanPrincipal = new TextField();
    TextField loanInterest = new TextField();
    TextField loanPeriod = new TextField();
    Button applyBtn = new Button("Apply Loan");
    
    TextField payAmount = new TextField();
    Button payBtn = new Button("Pay Loan");
    
    VBox vertBox = new VBox();
    
    @FXML
    public void formMaker(int typeForm){
        vertBox.setSpacing(10);
        vertBox.setAlignment(Pos.CENTER);
        if(loan_form.getChildren().contains(vertBox)){
            loan_form.getChildren().remove(vertBox);
        }
        
        if(typeForm == 1){
            loanPrincipal.setPromptText("Loan Amount(RM)");
            loanPrincipal.setMaxWidth(120);
            loanInterest.setPromptText("Interest Rate(%)");
            loanInterest.setMaxWidth(120);
            loanPeriod.setPromptText("Period(months)");
            loanPeriod.setMaxWidth(120);
            
            applyBtn.setOnAction(e -> applyLoan());
            applyBtn.prefWidth(90);
            
            vertBox.getChildren().setAll(loanPrincipal, loanInterest, loanPeriod, applyBtn);
            loan_form.getChildren().add(vertBox);
        }else if(typeForm == 2){
            payAmount.setPromptText("Payment Amount(RM)");
            payAmount.setMaxWidth(130);
            
            payBtn.setOnAction(e -> repayLoan());
            payBtn.prefWidth(90);
            
            vertBox.getChildren().setAll(payAmount, payBtn);
            loan_form.getChildren().add(vertBox);
        }
    }
    
    @FXML VBox loan_infos;
    Label lT1 = new Label("");
    Label lT2 = new Label("");
    Label lT3 = new Label("");
    Label lT4 = new Label("");
    
    String date;
    
    @FXML
    public void loanApplySet(String dateToday) {
        formMaker(1);
        date = dateToday;
        
        loan_infos.getChildren().removeAll(lT1, lT2, lT3, lT4);
                
        boolean activeLoan = getActiveLoan(user_id);
        
        if (activeLoan) {
            lT1.setWrapText(true);
            lT1.setText("You already have an active loan.\nPlease repay it before applying for another.");
            loan_infos.getChildren().add(lT1);
        }
    }
    
    @FXML
    public void applyLoan(){
        boolean activeLoan = getActiveLoan(user_id);
        
        loan_infos.getChildren().removeAll(lT1, lT2, lT3, lT4);
        
        if (activeLoan) {
            lT1.setWrapText(true);
            lT1.setText("You already have an active loan.\nPlease repay it before applying for another.");
            loan_infos.getChildren().add(lT1);
        }
        
        if(loanPrincipal.getText().isEmpty() || loanInterest.getText().isEmpty() || loanPeriod.getText().isEmpty()){
            lT1.setText("Please fill in all section!");
            loan_infos.getChildren().add(lT1);
            return;
        }
        
        if(loanPrincipal.getText().matches("[a-z]") || loanInterest.getText().matches("[a-z]") || loanPeriod.getText().matches("[a-z]")) {
            lT1.setText("Invalid input, please retry.");
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
        
        update_account_info();
    }
    
    @FXML
    public void loanRepaySet(String dateToday, LocalDate dateNow) {
        formMaker(2);
        date = dateToday;
        
        loan_infos.getChildren().removeAll(lT1, lT2, lT3, lT4);
        
        boolean activeLoan = getActiveLoan(user_id);
        
        int period = getPeriod(user_id);
                
        if (!activeLoan) {
            lT1.setText("No active loan to repay.");
            loan_infos.getChildren().add(lT1);
            return;
        }   

        if (dateNow.isAfter(dateNow.plusMonths(period))) {
            lT1.setText("Loan repayment period has ended. Further debits and credits are not allowed.");
            loan_infos.getChildren().add(lT1);
        }
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
            loanHis.add(new String[]{"", String.valueOf(user_id), String.valueOf(paymentAmount), String.valueOf(Double.parseDouble(loanHis.get(loanHis.size()-1)[3]) + paymentAmount), date});
        }else{
            loanHis.add(new String[]{"", String.valueOf(user_id), String.valueOf(paymentAmount), String.valueOf(paymentAmount), date});
        }
        lT1.setText("Payment successful! ----" + date);
        
        if (balance > 0) {
            lT2.setText("Remaining balance: " + balance);
        } else if (balance < 0) {
            lT2.setText("Remaining balance is 0");
            lT3.setText("Your change: RM" + df.format(-balance));
            loanHis.get(loanHis.size()-1)[2] = String.valueOf(Double.parseDouble(loanHis.get(loanHis.size()-1)[2]) + balance);
            loanHis.get(loanHis.size()-1)[3] = String.valueOf(Double.parseDouble(loanHis.get(loanHis.size()-1)[3]) + balance);
        }
        
        if (balance <= 0) {
            lT4.setText("Loan fully repaid. Thank you!");
            activeLoan = false; // Mark loan as fully repaid
            balance = 0;
        }
        loan_infos.getChildren().addAll(lT1, lT2, lT3, lT4);
        
        updateLoan(user_id, balance, period, activeLoan);
        file.set_loanHistory_csv(loanHis);
        
        update_account_info();
    }
    
    
    
    
    
    
    
    
    
    
    @FXML ChoiceBox bank_list = new ChoiceBox();
    @FXML ChoiceBox period_list = new ChoiceBox();
    @FXML Label interest = new Label();
    @FXML Label interest_info = new Label();
    
    public void depositInterestPredictor() {
        // Retrieve all bank data from CSV
        List<String[]> banks = file.get_bank_csv();
    
        //Prompt user to select a bank with bank ID provided
        //Loop to ensure valid bank ID input
        int bank_id = -1;
        double selectedBankIntRate = 0.0;
        String selectedBank = "";
        
        switch(bank_list.getValue().toString()){
            case "RHB                          - 2.6%" -> bank_id = 1;
            case "Maybank                  - 2.5%" -> bank_id = 2;
            case "Hong Leong             - 2.3%" -> bank_id = 3;
            case "Alliance                    - 2.85%" -> bank_id = 4;
            case "AmBank                   - 2.55%" -> bank_id = 5;
            case "Standard Chartered - 2.65%" -> bank_id = 6;
            default -> bank_id = -1;
        }

        boolean validBankID = false;
        for (String[] bank : banks){
            if (Integer.parseInt(bank[0]) == bank_id){ 
                selectedBank = bank[1];
                selectedBankIntRate = Double.parseDouble(bank[2]);
                validBankID = true;
            }
        }

        if (!validBankID){
            interest_info.setText("Invalid bank selected!");
            return;
        }
         
        double deposit = file.get_accbalance_csv(user_id);  // Retrieves user's deposit balance
    
        int period = -1;
        switch(period_list.getValue().toString()){
            case "Daily" -> period = 1;
            case "Monthly" -> period = 2;
            case "Annually" -> period = 3;
            default -> period = -1;
        }
        // Check if the period is valid
        if (period < 1 || period > 3) {
            interest_info.setText("Invalid period selected!");
            return;
        }
            
        // Calculate interest earned for valid input
        double interestEarned = 0.0;
        interest.setWrapText(true);
        switch (period){
            case 1:
                interestEarned = (deposit*(selectedBankIntRate/100))/365;
                interest.setText("Daily Interest \nearned RM " + df.format(interestEarned));
                interest_info.setText("");
                break;
            case 2:
                interestEarned = (deposit*(selectedBankIntRate/100))/12;
                interest.setText("Monthly Interest \nearned RM" + df.format(interestEarned));
                interest_info.setText("");
                break;
            case 3: 
                interestEarned = deposit*(selectedBankIntRate/100);
                interest.setText("Annually Interest \nearned RM" + df.format(interestEarned));
                interest_info.setText("");
                break;
        }
    }
    
    
    
    
    
    
    
    
    
    
    @FXML TableView<Transaction> histories;
    @FXML TextField monthFilter;
    @FXML TextField yearFilter;
    
    public void history() {
        String monthYear = monthFilter.getText() + "/" + yearFilter.getText();                      //StringBuilder?
        
        if(monthYear.equals("/")){
            viewFullTransactionHistory();
            return;
        }
        
        // In case of invalid month/year input
        if (!monthYear.matches("\\d{2}/\\d{4}")) {
            System.out.println("Invalid format. Please use MM/YYYY and fill in all section");
            return;
        }
        
        viewAndExportTransactions(user_id, monthYear);
    }
    
    public void viewAndExportTransactions(int user_id, String monthYear) {
        List<String[]> transactions = view_export_csv.getTransactionsByMonth(user_id, monthYear);

        // In case no transaction history for specific user at that certain session
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for the given month and year.");
            return;
        }
        // Get transaction history
        System.out.println("Transaction History");
        System.out.println("===================");
        System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n", "Transaction ID", "User ID", "Type", "Amount", "Description", "Date");
        for (String[] row : transactions) {
            if (row.length>=6){
                System.out.printf("%-20s %-15s %-10s %-10s %-20s %-20s\n",
                row[0], // Transaction ID
                row[1], // User ID
                row[2], // Type
                row[3], // Amount
                row[4], // Description
                row[5]); // Date
            }
        }

        // Enhanced part:Let user to determine whether want to export scv file
        System.out.print("Export to CSV? (yes/no): ");
        Scanner sc = new Scanner(System.in);
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            view_export_csv.exportTransactionsToCSV(transactions, "TransactionHistory_" + monthYear.replace("-", "_"));
            System.out.println("Transactions exported successfully!");
        }
    }
    
    // View overall transaction history
    public static void viewFullTransactionHistory() {
        // Fetch all transactions
        List<String[]> allTransactions = file.get_transactions_csv(-1); // Pass -1 for all users

        if (allTransactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        // Display header
        System.out.println("Transaction History:");
        System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n", "Transaction ID", "User ID", "Type", "Amount", "Description", "Date");

        // Display each transaction
        for (String[] transaction : allTransactions) {
            if (transaction.length>=6){
                System.out.printf("%-20s %-15s %-10s %-10s %-20s %-20s\n",
                transaction[0], // Transaction ID
                transaction[1], // User ID
                transaction[2], // Type
                transaction[3], // Amount
                transaction[4], // Description
                transaction[5]); // Date
            }
        }  
    }
    
    private static void filterhistory(List<String[]> transactions){
        
        System.out.println();
        
        // Filter options: Amount Range, Date Range, Transaction Type
        int filterOption = 0; // Initialize variable to store user's choice
        while (true) {
            System.out.println();
            System.out.println("Filter Options:");
            System.out.println("1. Filter by Amount Range");
            System.out.println("2. Filter by Date Range");
            System.out.println("3. Filter by Transaction Type (debit/credit)");
            System.out.print("Enter filter option (1-3): ");

            if (sc.hasNextInt()) { // Check if the input is an integer
                filterOption = sc.nextInt();
                sc.nextLine(); // Consume newline

                if (filterOption >= 1 && filterOption <= 3) {
                    // Valid option, exit the loop
                    break;
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and 3.");
                }
            } else {
                // Clear invalid input
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Consume the invalid input
            }
        }
        
        // Initialize filter variables
        Double minAmount = null;
        Double maxAmount = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String transactionType = "";

        // Amount Range Filter
        if (filterOption == 1 ) {
            System.out.print("Enter minimum amount: ");
            minAmount = sc.nextDouble();
            System.out.print("Enter maximum amount: ");
            maxAmount = sc.nextDouble();
            sc.nextLine(); // Consume newline
        }

        // Date Range Filter
        if (filterOption == 2 ) {
            System.out.print("Enter start date (dd/MM/yyyy): ");
            String startDateInput = sc.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            while (startDate == null) {
                try {
                    // Try to parse the date
                    startDate = LocalDate.parse(startDateInput,formatter);
                } catch (Exception e) {
                    // If the date format is invalid, inform the user and ask again
                    System.out.println("Invalid start date. Please use the correct format (dd/MM/yyyy).");
                    System.out.print("Enter start date (dd/MM/yyyy): ");
                    startDateInput = sc.nextLine();  // Prompt the user again for the date
                }
            }

            System.out.print("Enter end date (dd/MM/yyyy): ");
            String endDateInput = sc.nextLine();

            while (endDate == null) {
                try {
                    // Try to parse the date
                    endDate = LocalDate.parse(endDateInput, formatter);
                } catch (Exception e) {
                    // If the date format is invalid, inform the user and ask again
                    System.out.println("Invalid end date. Please use the correct format (dd/MM/yyyy).");
                    System.out.print("Enter end date (dd/MM/yyyy): ");
                    endDateInput = sc.nextLine();  // Prompt the user again for the date
                }
            }
        }

        // Transaction Type Filter
        if (filterOption == 3 ) {
            String transactionTypeInput = "";
            while (true) {
                System.out.print("Enter transaction type (debit/credit): ");
                transactionTypeInput = sc.nextLine().toLowerCase(); // Accept lowercase
                if (transactionTypeInput.equals("debit") || transactionTypeInput.equals("credit")) {
                    transactionType = transactionTypeInput;
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'debit' or 'credit'.");
                }
            }
        }

        // Filtering transactions
        List<String[]> filteredTransactions = new ArrayList<>();
        for (String[] transaction : transactions) {
            try {
                // Ensure the transaction has at least 6 elements (e.g., date, amount, type)
                if (transaction.length < 6) {
                    System.out.println("Skipping malformed transaction: " + String.join(", ", transaction));
                    continue;
                }

                // Parsing date and amount for comparison
                LocalDate transactionDate = LocalDate.parse(transaction[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                double amount = Double.parseDouble(transaction[3]);  // Converts string to double
                String type = transaction[2].toLowerCase();

                // Filter by date range (if provided)
                if (startDate != null && transactionDate.isBefore(startDate)) {
                    continue;  // Skip transaction if before the start date
                }
                if (endDate != null && transactionDate.isAfter(endDate)) {
                    continue;  // Skip transaction if after the end date
                }

                // Filter by amount range (if provided)
                if (minAmount != null && amount < minAmount) {
                    continue;  // Skip transaction if less than minAmount
                }
                if (maxAmount != null && amount > maxAmount) {
                    continue;  // Skip transaction if greater than maxAmount
                }

                // Filter by transaction type (if provided)
                if (!transactionType.isEmpty() && !type.equals(transactionType)) {
                    continue;  // Skip transaction if type doesn't match
                }

                // If the transaction passes all filters, add it to the list
                filteredTransactions.add(transaction);
            } catch (Exception e) {
                System.out.println("Error processing transaction: " + String.join(", ", transaction));
            }
        }

        // Display filtered transactions
        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found matching the criteria.");
        } else {
            System.out.println("\nFiltered Transaction History");
            System.out.println("===========================================================================");
            System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n", 
                "Transaction ID", "User ID", "Type", "Amount", "Description", "Date");

            for (String[] transaction : filteredTransactions) {
                if (transaction.length >= 6) {
                    System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n",
                        transaction[0], // Transaction ID
                        transaction[1], // User ID
                        transaction[2], // Type
                        transaction[3], // Amount
                        transaction[4], // Description
                        transaction[5]); // Date
                } else {
                    System.out.println("Skipping malformed transaction: " + String.join(", ", transaction));
                }
            }
        }

    }
//    
//    private static void sortinghistory(){
//        
//        System.out.println();
//        
//        //sorting options
//        int sortOption = 0;
//        while (true) {
//            System.out.println("Select sorting option: ");
//            System.out.println("1. Sort by Date (Newest First)");
//            System.out.println("2. Sort by Date (Oldest First)");
//            System.out.println("3. Sort by Amount (Highest First)");
//            System.out.println("4. Sort by Amount (Lowest First)");
//            System.out.print("Enter your choice (1-4): ");
//
//            if (sc.hasNextInt()) { // Check if the input is an integer
//                sortOption = sc.nextInt();
//                sc.nextLine(); // Consume newline
//
//                if (sortOption >= 1 && sortOption <= 4) {
//                    // Valid option, exit the loop
//                    break;
//                } else {
//                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
//                }
//            } else {
//                // Clear invalid input
//                System.out.println("Invalid input. Please enter a valid number.");
//                sc.nextLine(); // Consume the invalid input
//            }
//        }
//        
//        // Initialize sorting variables
//        Double minAmount = null;
//        Double maxAmount = null;
//        LocalDate startDate = null;
//        LocalDate endDate = null;
//        String transactionType = "";
//
//        // Date Range Filter
//        if (sortOption == 1 || sortOption == 2) {
//            System.out.print("Enter start date (dd/MM/yyyy): ");
//            String startDateInput = sc.nextLine();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//            while (startDate == null) {
//                try {
//                    // Try to parse the date
//                    startDate = LocalDate.parse(startDateInput,formatter);
//                } catch (Exception e) {
//                    // If the date format is invalid, inform the user and ask again
//                    System.out.println("Invalid start date. Please use the correct format (dd/MM/yyyy).");
//                    System.out.print("Enter start date (dd/MM/yyyy): ");
//                    startDateInput = sc.nextLine();  // Prompt the user again for the date
//                }
//            }
//
//            System.out.print("Enter end date (dd/MM/yyyy): ");
//            String endDateInput = sc.nextLine();
//
//            while (endDate == null) {
//                try {
//                    // Try to parse the date
//                    endDate = LocalDate.parse(endDateInput, formatter);
//                } catch (Exception e) {
//                    // If the date format is invalid, inform the user and ask again
//                    System.out.println("Invalid end date. Please use the correct format (dd/MM/yyyy).");
//                    System.out.print("Enter end date (dd/MM/yyyy): ");
//                    endDateInput = sc.nextLine();  // Prompt the user again for the date
//                }
//            }
//        }
//        
//        // Amount Range Filter
//        if (sortOption == 3 || sortOption == 4) {
//            System.out.print("Enter minimum amount: ");
//            minAmount = sc.nextDouble();
//            System.out.print("Enter maximum amount: ");
//            maxAmount = sc.nextDouble();
//            sc.nextLine(); // Consume newline
//        }
//
//        //Fetch transaction 
//        List<String[]> transactions =file.get_transactions_csv(user_id);  
//        List<String[]> filteredTransactions = new ArrayList<>();
//
//        for (String[] transaction : transactions) {      //loop thru transactions
//            try {
//                if (transaction.length < 6) continue;   
//                
//                //converts string at index 5(date) into a LocalDate object
//                LocalDate transactionDate = LocalDate.parse(transaction[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                
//                //converts string at index 3(amount) into double 
//                double amount = Double.parseDouble(transaction[3]);
//
//                // Apply date range filtering
//                if ((startDate == null || !transactionDate.isBefore(startDate)) &&
//                    (endDate == null || !transactionDate.isAfter(endDate))) {
//                    // Apply amount range filtering
//                    if ((minAmount == null || amount >= minAmount) &&
//                        (maxAmount == null || amount <= maxAmount)) {
//                        //Adds the current transaction to the filteredTransactions list
//                        filteredTransactions.add(transaction);
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Skipping malformed transaction: " + Arrays.toString(transaction));
//            }
//        }
//            
//        // sorting option
//        switch (sortOption){
//            case 1:          //Sort by Date (Newest First)
//                filteredTransactions.sort((a, b) -> {
//                    // Compare by Date (Newest First)
//                    int dateComparison = LocalDate.parse(b[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//                                                 .compareTo(LocalDate.parse(a[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//
//                    // If dates are the same, compare by Transaction ID (Newest First)
//                    if (dateComparison == 0) {
//                        return b[0].compareTo(a[0]);  // Transaction ID in descending order
//                    }
//
//                    return dateComparison;  // Otherwise, order by date
//                });
//                break; 
//                
//            case 2:          // Sort by Date (Oldest First)
//                filteredTransactions.sort((a, b) -> {
//                    // Compare by Date (Oldest First)
//                    int dateComparison = LocalDate.parse(a[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//                                                 .compareTo(LocalDate.parse(b[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//
//                    // If dates are the same, compare by Transaction ID (Oldest First)
//                    if (dateComparison == 0) {
//                        return a[0].compareTo(b[0]);  // Transaction ID in ascending order
//                    }
//
//                    return dateComparison;  // Otherwise, order by date
//                });
//                break;
//                
//            case 3:           //Sort by Amount(Highest to Lowest)
//                filteredTransactions.sort((a, b) -> Double.compare(Double.parseDouble(b[3]), Double.parseDouble(a[3])));
//                break;
//                
//            case 4:           //Sort by Amount (Lowest to Highest)
//                filteredTransactions.sort((a, b) -> Double.compare(Double.parseDouble(a[3]), Double.parseDouble(b[3])));
//                break;
//                
//            default:
//                System.out.println("Invalid sort option.");
//                return;
//        }
//        
//        // Display filtered and sorted transactions
//        System.out.println("\nSorted Transaction History");
//        System.out.println("===========================================================================");
//        System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n", 
//            "Transaction ID", "User ID", "Type", "Amount", "Description", "Date");
//
//        // Display each transaction
//        for (String[] transaction : filteredTransactions) {
//            if (transaction.length >= 6) {
//                System.out.printf("%-20s %-15s %-10s %-10s %-20s %-15s\n",
//                    transaction[0], // Transaction ID
//                    transaction[1], // User ID
//                    transaction[2], // Type
//                    transaction[3], // Amount
//                    transaction[4], // Description
//                    transaction[5]); // Date
//            } else {
//                System.out.println("Skipping malformed transaction: " + String.join(", ", transaction));
//            }
//        }
//
//        // Check if no transactions were found
//        if (filteredTransactions.isEmpty()) {
//            System.out.println("No transactions found matching the criteria.");
//        }
//
//    }
//        
//    
//    
//    
//    
//    
//    private static int logOut() {
//        System.out.println("\nUser successfully logged out.");
//        return -1;
//    }
}
