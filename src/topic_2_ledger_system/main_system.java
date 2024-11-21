/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Write your name here
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun
 *
 */
public class main_system {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        
        List<String[]> transactions = new ArrayList<>();
        
        transactions = file.get_transactions_csv(2);
        
        String[] d1 = {"", "2", "debit", "1500", "Salary", "10/10/2024"};
        String[] d2 = {"", "4", "credit", "3000", "Salary", "11/10/2024"};
        String[] d3 = {"", "2", "debit", "2000", "Salary", "11/11/2024"};
        
//        transactions.add(d1);
//        transactions.add(d2);
//        transactions.add(d3);
        
        file.set_transactions_csv(transactions);
    }
}
