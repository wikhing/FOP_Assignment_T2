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
public class FXtransaction {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static int user_id = 2;
    public static void set_user(int user_id){
        FXtransaction.user_id = user_id;
    }
    
    @FXML
    public void back() throws IOException{
        main_system.setScene("menu");
    }
    
    
    
    
    
    
    
    
    
    
    @FXML TextField record_amount = new TextField();
    @FXML ChoiceBox record_choice = new ChoiceBox();
    @FXML TextArea record_descp = new TextArea();
    @FXML Label trans_info = new Label();
    
    @FXML Button record_submit = new Button();
    
    @FXML
    public void transaction_record() {
        double balance = file.get_accbalance_csv(user_id);
        String[] savings = file.get_savings_csv(user_id);
        List<String[]> savHis = file.get_savHistory_csv(user_id);
        
        double amount = 0, toSave = 0;
        String description = "", dateToday = "";
        if(record_amount.getText().length() == 0){
            trans_info.setText("Please fill in all the section of the form.");
            return;
        }
        if(record_amount.getText().matches("[a-z]+")){
            trans_info.setText("Invalid Input. Please enter again.");
            return;
        }
        amount = Double.parseDouble(record_amount.getText());
        
        String type = "";
        switch (record_choice.getValue().toString()) {
            case "Debit" -> {
                type = "debit";
                if(savings[2].equals("active")){
                    toSave = amount * Integer.parseInt(savings[3]) / 100.0;
                    if(!savHis.isEmpty()){
                        savHis.add(new String[]{"", String.valueOf(user_id), String.valueOf(toSave), String.valueOf(Double.parseDouble(savHis.get(savHis.size()-1)[3]) + toSave), dateToday});
                    }else{
                        savHis.add(new String[]{"", String.valueOf(user_id), String.valueOf(toSave), String.valueOf(toSave), dateToday});
                    }
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
            trans_info.setText("Description exceeds 100 characters, please retry.");
        } else if(description.length() == 0){
            trans_info.setText("Please fill in all the section of the form.");
        }else{
            List<String[]> transaction = file.get_transactions_csv(user_id);
            transaction.add(new String[]{"", String.valueOf(user_id), type, String.valueOf(amount), description, dateToday});
            file.set_transactions_csv(transaction);

            file.set_accbalance_csv(user_id, balance);

            savings[4] = String.valueOf(Double.parseDouble(savings[4]) + toSave);
            file.set_savings_csv(savings);
            
            file.set_savHistory_csv(savHis);
            
            if(type.equals("debit")){
                trans_info.setText("Debit Successfully Recorded!!!");
            }else if(type.equals("credit")){
                trans_info.setText("Credit Successfully Recorded!!!");
            }
        }   
    }
}
