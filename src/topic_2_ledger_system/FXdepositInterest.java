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
public class FXdepositInterest {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static int user_id = 2;
    public static void set_user(int user_id){
        FXdepositInterest.user_id = user_id;
    }
    
    @FXML
    public void back() throws IOException{
        main_system.setScene("menu");
        
        menu m = new menu();
        m.update_account_info();
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
}
