/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author
 */
public class FXhistory {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static int user_id;
    public static void set_user(int user_id){
        FXhistory.user_id = user_id;
    }
    
    @FXML
    public void initialize(){
        
        // Sorting
        balanceColumn.setComparator(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Double.compare(Double.parseDouble(a), Double.parseDouble(b));
            }
        });
        dateColumn.setComparator(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {

                //compare year
                if(!a.substring(6).equals(b.substring(6))){
                    return Integer.compare(Integer.parseInt(a.substring(6)), Integer.parseInt(b.substring(6)));
                }
                
                //compare month
                if(!a.substring(3, 5).equals(b.substring(3, 5))){
                    return Integer.compare(Integer.parseInt(a.substring(3, 5)), Integer.parseInt(b.substring(3, 5)));
                }
                
                //compare date
                if(!a.substring(0, 2).equals(b.substring(0, 2))){
                    return Integer.compare(Integer.parseInt(a.substring(0, 2)), Integer.parseInt(b.substring(0, 2)));
                }
                
                return 0; //all equal
            }
        });
        typeColumn.setSortable(false);          //Sorting of type not needed
        descriptionColumn.setSortable(false);   //Sorting of description not needed
        
        filterChoice.setOnAction(e -> {
            filterInitialize();
        });
        
        history();
    }
    
    @FXML
    public void back() throws IOException{
        main_system.setScene("menu");
    }
    
    
    
    
    
    
    
    
    @FXML TableColumn balanceColumn = new TableColumn();
    @FXML TableColumn typeColumn = new TableColumn();
    @FXML TableColumn descriptionColumn = new TableColumn();
    @FXML TableColumn dateColumn = new TableColumn();
    
    @FXML TableView<Transaction> histories = new TableView<Transaction>();
    @FXML TextField monthFilter;
    @FXML TextField yearFilter;
    
    @FXML Label his_info = new Label();
    
    List<String[]> transactionsToFilter;
    String monthYear;
    
    @FXML
    public void history() {
        exportLabel.setText("");
        
        ObservableList<Transaction> data = histories.getItems();
        data.clear();
        his_info.setText("");
        
        monthYear = monthFilter.getText() + "/" + yearFilter.getText();                      //StringBuilder?
        
        if(monthYear.equals("/")){
            viewFullTransactionHistory();
            return;
        }
        
        // In case of invalid month/year input
        if (!monthYear.matches("\\d{2}+/\\d{4}+")) {
            his_info.setText("Invalid format. Please use MM/YYYY and fill in all section");
            return;
        }
        
        viewAndExportTransactions(user_id, monthYear);
    }
    
    @FXML
    public void viewAndExportTransactions(int user_id, String monthYear) {
        List<String[]> transactions = view_export_csv.getTransactionsByMonth(user_id, monthYear);
        transactionsToFilter = transactions;

        // In case no transaction history for specific user at that certain session
        if (transactions.isEmpty()) {
            histories.setPlaceholder(new Label("No transactions found for the given month and year."));
            return;
        }
        
        ObservableList<Transaction> data = histories.getItems();
        
        
        for (String[] row : transactions) {
            if(row.length == 1) continue;
            data.add(new Transaction(df.format(Double.parseDouble(row[3])), row[2], row[4], row[5]));
        }
    }
    
    @FXML
    // View overall transaction history
    public void viewFullTransactionHistory() {
        // Fetch all transactions
        List<String[]> allTransactions = view_export_csv.getTransactions(user_id);
        transactionsToFilter = allTransactions;

        if (allTransactions.isEmpty()) {
            histories.setPlaceholder(new Label("No transactions found."));
            return;
        }
        
        ObservableList<Transaction> data = histories.getItems();
        
        
        // Display each transaction
        for (String[] transaction : allTransactions) {
            if(transaction.length != 7) continue;
            data.add(new Transaction(df.format(Double.parseDouble(transaction[3])), transaction[2], transaction[5], transaction[6]));
        }  
    }
    
    
    @FXML VBox filterHistory = new VBox();
    @FXML HBox filterBox = new HBox();
    
    @FXML ChoiceBox filterChoice = new ChoiceBox();
    
    VBox start = new VBox();
    Label laStart = new Label();
    TextField tfStart = new TextField();
    
    VBox end = new VBox();
    Label laEnd = new Label();
    TextField tfEnd = new TextField();
    
    DatePicker dateStart = new DatePicker();
    DatePicker dateEnd = new DatePicker();
    
    Button submitFilter = new Button("Filter");
    
    ChoiceBox<String> transactionType = new ChoiceBox<String>();
    
    private void filterInitialize(){
        his_info.setText("");
        
        filterBox.getChildren().clear();
        start.getChildren().clear();
        end.getChildren().clear();
        
        tfStart.prefWidth(100);
        tfStart.clear();
        start.getChildren().addAll(laStart, tfStart);
        
        tfEnd.prefWidth(100);
        tfEnd.clear();
        end.getChildren().addAll(laEnd, tfEnd);
        
        dateStart.setValue(LocalDate.now());
        dateStart.setMaxWidth(130);
        dateStart.setEditable(false);
        dateEnd.setValue(LocalDate.now());
        dateEnd.setMaxWidth(130);
        dateEnd.setEditable(false);
        
        submitFilter.prefWidth(100);
        submitFilter.setOnAction(e -> {
            filterHistory();
        });
        
        transactionType.prefWidth(100);
        transactionType.valueProperty().setValue("Choose Type");
        
        switch(filterChoice.getValue().toString()){
            case "Filter Amount Range" -> {
                laStart.setText("Minimum Amount(RM)");
                laEnd.setText("Maximum Amount(RM)");
                
                filterBox.getChildren().addAll(start, end, submitFilter);
            }
            case "Filter Date Range" -> {
                laStart.setText("Start Date");
                laEnd.setText("End Date");
                
                start.getChildren().remove(tfStart);
                start.getChildren().add(dateStart);
                end.getChildren().remove(tfEnd);
                end.getChildren().add(dateEnd);
                
                filterBox.getChildren().addAll(start, end, submitFilter);
            }
            case "Filter Transaction Type" -> {
                ObservableList<String> listss = transactionType.getItems();
                listss.clear();
                listss.addAll("Debit", "Credit");
                
                filterBox.getChildren().addAll(transactionType, submitFilter);
            }
        }
    }
          
    @FXML
    public void filterHistory(){
        
        int filterOption = 0;
        switch(filterChoice.getValue().toString()){
            case "Filter Amount Range" -> filterOption = 1;
            case "Filter Date Range" -> filterOption = 2;
            case "Filter Transaction Type" -> filterOption = 3;
        }
        
        if(filterOption == 1 && (tfStart.getText().isEmpty() || tfEnd.getText().isEmpty())){
            his_info.setText("Please fill in all section.");
            return;
        }
        
        // Initialize filter variables
        Double minAmount = null;
        Double maxAmount = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String transType = "";

        // Amount Range Filter
        if (filterOption == 1 ) {
            minAmount = Double.parseDouble(tfStart.getText());
            maxAmount = Double.parseDouble(tfEnd.getText());
        }

        // Date Range Filter
        if (filterOption == 2 ) {
            
            startDate = dateStart.getValue();
            endDate = dateEnd.getValue();
            
            if(monthYear.equals("//") && endDate.isBefore(LocalDate.parse("01/" + monthYear, DateTimeFormatter.ofPattern("dd/MM/yyyy")))){
                histories.getItems().clear();
                histories.setPlaceholder(new Label("Your filter is before the monthly transaction."));
                return;
            }else if(monthYear.equals("//") && startDate.isAfter(LocalDate.parse("31/" + monthYear, DateTimeFormatter.ofPattern("dd/MM/yyyy")))){
                histories.getItems().clear();
                histories.setPlaceholder(new Label("Your filter is after the monthly transaction."));
                return;
            }else if(startDate.isAfter(endDate)){
                histories.getItems().clear();
                histories.setPlaceholder(new Label("Your start date is after end date!"));
                return;
            }
        }

        // Transaction Type Filter
        if (filterOption == 3 ) {
            transType = transactionType.getValue().toLowerCase(); // Accept lowercase
        }

        // Filtering transactions
        List<String[]> filteredTransactions = new ArrayList<>();
        for (String[] transaction : transactionsToFilter) {
            try {
                // Ensure the transaction has at least 6 elements (e.g., date, amount, type)
                if (transaction.length < 7) {
                    continue;
                }

                // Parsing date and amount for comparison
                LocalDate transactionDate = LocalDate.parse(transaction[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
                if (!transType.isEmpty() && !type.equals(transType)) {
                    continue;  // Skip transaction if type doesn't match
                }

                // If the transaction passes all filters, add it to the list
                filteredTransactions.add(transaction);
            } catch (Exception e) {
                his_info.setText("Error processing transaction: " + String.join(", ", transaction));
            }
        }
        
        ObservableList<Transaction> data = histories.getItems();
        data.clear();

        // Display filtered transactions
        if (filteredTransactions.isEmpty()) {
            histories.setPlaceholder(new Label("No transactions found matching the criteria."));
        } else {
            for (String[] transaction : filteredTransactions) {
                if (transaction.length == 7) {
                    data.add(new Transaction(df.format(Double.parseDouble(transaction[3])), transaction[2], transaction[5], transaction[6]));
                }
            }
        }
    }
    
    @FXML Label exportLabel = new Label();
    
    public void export(){
        // Enhanced part:Let user to determine whether want to export scv file
        try{
            exportTransactionsToCSV(transactionsToFilter, "TransactionHistory_" + monthYear.substring(3) + "/month_" + monthYear.substring(0, 2));
        } catch (RuntimeException e){
            exportTransactionsToCSV(transactionsToFilter, "TransactionHistory_FullHistory");
        }
        
    }
    
    public void exportTransactionsToCSV(List<String[]> transactions, String fileName) {
        // Define the file path 
        String filePath = "src/topic_2_ledger_system/exported/" + fileName + ".csv";
        File file = new File(filePath);
        // Ensure the directory exists
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();  // Create the directory if it doesn't exist
        }

        // Write to the CSV file
        try (PrintWriter writer = new PrintWriter(file)) {
            // Write header
            writer.println("\"Amount\", \"Type\", \"Description\", \"Date\"");

            // Write each transaction row
            for (String[] transaction : transactions) {
                if (transaction.length>=7){
                writer.printf("%s, %s, %s, %s%n",
                            df.format(Double.parseDouble(transaction[3])), // Amount
                            transaction[2], // Type
                            transaction[5], // Description
                            transaction[6]  // Date
                    ); 
                }
            }
            exportLabel.setText("Transactions exported successfully!");
        } catch (IOException e) {
            exportLabel.setText("Error while exporting transactions: " + e.getMessage());
        }
    }
}
