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
public class FXsaving {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private static int user_id = 2;
    public static void set_user(int user_id){
        FXsaving.user_id = user_id;
    }
    
    @FXML
    public void initialize(){
        String[] savings = file.get_savings_csv(user_id);
        if(savings[2].equals("active")) user_choice.setSelected(true);
        
        sav_percentage.valueProperty().addListener((obs, oldVal, newVal) -> saving_setting());
        sav_percentage.getValueFactory().setValue(Integer.parseInt(savings[3]));
        
        saving_setting();
    }
    
    @FXML
    public void back() throws IOException{
        main_system.setScene("menu");
        
        menu m = new menu();
        m.update_account_info();
    }
    
    
    
    
    
    
    
    
    
    
    @FXML Label sav_status = new Label();
    @FXML Label sav_perc = new Label();
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
            sav_status.setText("Current Status - " + savings[2]);
            sav_perc.setText("Current Percentage - " + savings[3]);
        }else if(!isActive){
            savings[2] = "inactive";
            savings[3] = "0";
            if(!"0.0".equals(savings[4])){
                double balance = file.get_accbalance_csv(user_id);
                balance += Double.parseDouble(savings[4]);
                
                file.set_accbalance_csv(user_id, balance);
            }
            savings[4] = "0.0";
            sav_status.setText("Current Status - " + savings[2]);
            sav_perc.setText("Current Percentage - " + savings[3]);
        }
        file.set_savings_csv(savings);
    }
}
