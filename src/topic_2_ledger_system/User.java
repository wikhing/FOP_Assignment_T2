/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author I Khing
 */
public class User {
    
    int user_id;
    String username;
    String email;
    String password;
    
    int saving_id;
    String saving_status;
    int saving_percentage;
    double saving_amount;
    
    int loan_id;
    double loan_principal_amount;
    double loan_interest_rate;
    int loan_repay_period;
    double loan_outstanding_bal;
    String loan_status;
    String loan_created_at;      // Date
    
    double balance;
    
    
    
    int transaction_id;
    String transaction_type;
    double amount;
    String description;
    String date;
    
    
    
    int bank_id;
    String bank_name;
    double bank_interest_rate;
    
    
    User(int user_id){
        for(String[] user : fullList){
            if(user_id == Integer.parseInt(user[0])){
                this.user_id = user_id;
                this.username = user[1];
                this.email = user[2];
                this.password = user[3];
                this.saving_id = Integer.parseInt(user[4]);
                this.saving_status = user[5];
                this.saving_percentage = Integer.parseInt(user[6]);
                this.saving_amount = Double.parseDouble(user[7]);
                this.loan_id = Integer.parseInt(user[8]);
                this.loan_principal_amount = Double.parseDouble(user[9]);
                this.loan_interest_rate = Double.parseDouble(user[10]);
                this.loan_repay_period = Integer.parseInt(user[11]);
                this.loan_outstanding_bal = Double.parseDouble(user[12]);
                this.loan_status = user[13];
                this.loan_created_at = user[14];
                this.balance = Double.parseDouble(user[15]);
            }
        }
    }
    
    static List<String[]> fullList = new ArrayList<>();
    public static void load(){
        List<String[]> user_csv = file.get_user_csv();
        List<String[]> saving_csv = file.get_savings_csv();
        List<String[]> loan_csv = file.get_loans_csv();
        List<String[]> balance_csv = file.get_accbalance_csv();
        
        for(int i = 1; i < user_csv.size(); i++){
            String[] temp = new String[16];
            
            for(int j = 0; j < 4; j++){
                temp[j] = user_csv.get(i)[j];
            }
            
            fullList.add(temp);
        }
        
        for(int i = 0; i < fullList.size(); i++){
            String user_id = fullList.get(i)[0];
            
            for(int j = 1; j < saving_csv.size(); j++){
                if(user_id.equals(saving_csv.get(j)[1])){
                    fullList.get(i)[4] = saving_csv.get(j)[0];
                    fullList.get(i)[5] = saving_csv.get(j)[2];
                    fullList.get(i)[6] = saving_csv.get(j)[3];
                    fullList.get(i)[7] = saving_csv.get(j)[4];
                    break;
                }
            }
            
            for(int j = 1; j < loan_csv.size(); j++){
                if(user_id.equals(loan_csv.get(j)[1])){
                    fullList.get(i)[8] = loan_csv.get(j)[0];
                    fullList.get(i)[9] = loan_csv.get(j)[2];
                    fullList.get(i)[10] = loan_csv.get(j)[3];
                    fullList.get(i)[11] = loan_csv.get(j)[4];
                    fullList.get(i)[12] = loan_csv.get(j)[5];
                    fullList.get(i)[13] = loan_csv.get(j)[6];
                    fullList.get(i)[14] = loan_csv.get(j)[7];
                    break;
                }
            }
            
            for(int j = 1; j < balance_csv.size(); j++){
                if(user_id.equals(balance_csv.get(j)[0])){
                    fullList.get(i)[15] = balance_csv.get(j)[1];
                    break;
                }
            }
        }
    }
}
