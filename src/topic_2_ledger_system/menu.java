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
    
    private static int user_id = 2;
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
    
    @FXML
    public void update_account_info(){
        update_by_end_month(user_id);
        file.update_login_date(user_id);
        
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
    
    
    
    
    
    
    
    
    
    
    public void toTransaction() throws IOException{
        main_system.setScene("transaction");
        FXtransaction.set_user(user_id);
    }
    
    public void toSaving() throws IOException{
        main_system.setScene("saving");
        FXsaving.set_user(user_id);
    }
    
    public void toCreditLoan() throws IOException{
        main_system.setScene("creditloan");
        FXcreditLoan.set_user(user_id);
    }
    
    public void toDepositInterest() throws IOException{
        main_system.setScene("depositinterest");
        FXdepositInterest.set_user(user_id);
    }
    
    public void toHistory() throws IOException{
        main_system.setScene("history");
        FXhistory.set_user(user_id);
    }
    
//    private static int logOut() {
//        System.out.println("\nUser successfully logged out.");
//        return -1;
//    }
}
