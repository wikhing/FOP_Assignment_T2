package topic_2_ledger_system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.ArrayList;

/**
 * Write your name here
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun, Cheng Kai Huang
 *
 */
public class file {
    // Constant file path here
    private static final Map<String, String> filesPath = new HashMap<>(Map.ofEntries(
            entry("user",           "src/topic_2_ledger_system/saved/user.csv"),
            entry("transactions",   "src/topic_2_ledger_system/saved/transactions.csv"),
            entry("savings",        "src/topic_2_ledger_system/saved/savings.csv"),
            entry("savHis",        "src/topic_2_ledger_system/saved/savHis.csv"),
            entry("loans",          "src/topic_2_ledger_system/saved/loans.csv"),
            entry("bank",           "src/topic_2_ledger_system/saved/bank.csv"),
            entry("accbalance",     "src/topic_2_ledger_system/saved/accbalance.csv")));
    
    // Convert data in arraylist to CSV format to be stored
    private static String convertToCSV(String[] data) {
        return Stream.of(data)
          .map(data1 -> escapeSpecialCharacters(data1))
          .collect(Collectors.joining(","));
    }
    
    //remove special character , \ and '
    private static String escapeSpecialCharacters(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
    
    // File writing
    private static void write(List<String[]> list, String filename){
        String path = filesPath.get(filename);
        File csvOutputFile = new File(path);
        
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            list.stream()
              .map(data -> convertToCSV(data))
              .forEach(pw::println);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
            try {
                csvOutputFile.createNewFile();
                PrintWriter pw = new PrintWriter(csvOutputFile);
                list.stream()
                  .map(data -> convertToCSV(data))
                  .forEach(pw::println);
                pw.flush();
                pw.close();
            } catch (IOException ex1) {
                System.out.println("Error 404!");
            }
        }
    }
    
    // File reading
    private static ArrayList read(String filename){
        String path = filesPath.get(filename);
        ArrayList<String[]> list = new ArrayList<>();
        File file = new File(path);
        
        Scanner fsc = null;
        try {
            fsc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            try {
                file.createNewFile();
                fsc = new Scanner(file);
            } catch (IOException ex1) {
                System.out.println("Error 404!");
            }
        }
        
        while(fsc.hasNextLine()){
            list.add(fsc.nextLine().split(","));
        }

        return list;
    }
    
    //Method to setup user account
    public static void new_user_acc_setup(int user_id){
        //Setup acc balance
        file.get_accbalance_csv(user_id);
        file.set_accbalance_csv(user_id, 0);
        //Setup savings
        file.get_savings_csv(user_id);
        file.set_savings_csv(new String[]{"", String.valueOf(user_id), "inactive", "0", "0"});
        //Setup loan?? One user can only have one loan or multiple loan???
        file.get_loans_csv(user_id);
        file.set_loans_csv(new String[]{"", String.valueOf(user_id), "0.0", "0.0", "0", "0.0", "inactive", "00/00/0000"});
    }
    
    /*
     * ----------------------------------------------------------------------------------
     * Methods below are individual method to get the data from csv file and
     * write new data to csv file
     * To write new data, get data first, add to the List and then set
     *
     */
    
    // Need get first then set
    // Array format: {"", name, email, password}
    public static ArrayList<String[]> get_user_csv(){
        List<String[]> user_csv = new ArrayList<>();
        user_csv = file.read("user");
        
        if(user_csv.isEmpty()) user_csv.add(new String[0]);
        
        return (ArrayList<String[]>) user_csv;
    }
    public static void set_user_csv(List<String[]> user_csv){
        //For loop to auto-increment the user_id
        for(int i = 1; i < user_csv.size(); i++){
            user_csv.get(i)[0] = String.valueOf(i);
        }
        write(user_csv, "user");
    }
    
    
    // Need get first then set
    // Array format: {"", user_id, transaction_type, amount, description, date}
    private static List<String[]> tempTransactions = new ArrayList<>();
    public static List<String[]> get_transactions_csv(int user_id){//put in user_id to directly get transaction data of the user
        List<String[]> transactions_csv = new ArrayList<>();
        transactions_csv = file.read("transactions");
        
        if(transactions_csv.isEmpty()){
            transactions_csv.add(new String[0]);
            return transactions_csv;
        }
        
        //return full transaction list if user_id is -1, but not recommended to use this
        if(user_id == -1){
            return transactions_csv;
        }
        
        //look for transaction according to user_id and remove the transaction from transactions_csv
        List<String[]> specificTransaction = new ArrayList<>();
        Iterator<String[]> it = transactions_csv.iterator();
        while(it.hasNext()){
            String[] data = it.next();
            
            if(data.length == 1) continue;
            if(Integer.parseInt(data[1]) == user_id){
                specificTransaction.add(data);
                it.remove();
            }
        }
        tempTransactions = transactions_csv;
        
        return specificTransaction;
    }
    public static void set_transactions_csv(List<String[]> specificTransaction){
        List<String[]> transactions_csv = new ArrayList<>();
        transactions_csv = tempTransactions;
        
        //add specificTransaction back to whole transaction_csv
        for(int i = 0; i < specificTransaction.size(); i++){
            transactions_csv.add(specificTransaction.get(i));
        }
        
        //For loop to auto-increment the transactions_id
        for(int i = 1; i < transactions_csv.size(); i++){
            transactions_csv.get(i)[0] = String.valueOf(i);
        }
        write(transactions_csv, "transactions");
    }
    
    
    // Need get first then set
    // Array format: {"", user_id, status, percentage, saving_balance}
    private static List<String[]> tempSavings = new ArrayList<>();
    public static String[] get_savings_csv(int user_id){
        List<String[]> savings_csv = new ArrayList<>();
        savings_csv = file.read("savings");
        
        if(savings_csv.isEmpty()){
            savings_csv.add(new String[0]);
            write(savings_csv, "savings");
        }
        
        //look for savings according to user_id and remove the savings from savings_csv
        String[] specificSaving = new String[5];
        Iterator<String[]> it = savings_csv.iterator();
        while(it.hasNext()){
            String[] data = it.next();
            
            if(data.length == 1 || data.length == 0) continue;
            if(Integer.parseInt(data[1]) == user_id){
                specificSaving = data;
                it.remove();
            }
        }
        tempSavings = savings_csv;
        
        return specificSaving;
    }
    public static void set_savings_csv(String[] specificSaving){
        List<String[]> savings_csv = new ArrayList<>();
        savings_csv = tempSavings;
        
        //add specificSaving back to whole savings_csv
        savings_csv.add(specificSaving);
        
        //For loop to auto-increment the savings_id
        for(int i = 1; i < savings_csv.size(); i++){
            savings_csv.get(i)[0] = String.valueOf(i);
        }
        write(savings_csv, "savings");
    }
    
    // Need get first then set
    // Array format: {"", user_id, savedBalance, date}
    private static List<String[]> tempSavHistory = new ArrayList<>();
    public static List<String[]> get_savHistory_csv(int user_id){
        List<String[]> savings_csv = new ArrayList<>();
        savings_csv = file.read("savHis");
        
        if(savings_csv.isEmpty()){
            savings_csv.add(new String[0]);
            write(savings_csv, "savHis");
        }
        
        //look for savings according to user_id and remove the savings from savings_csv
        List<String[]> specificSaving = new ArrayList<>();
        Iterator<String[]> it = savings_csv.iterator();
        while(it.hasNext()){
            String[] data = it.next();
            
            if(data.length == 1 || data.length == 0) continue;
            if(Integer.parseInt(data[1]) == user_id){
                specificSaving.add(data);
                it.remove();
            }
        }
        tempSavHistory = savings_csv;
        
        return specificSaving;
    }
    public static void set_savHistory_csv(List<String[]> specificSaving){
        List<String[]> savings_csv = new ArrayList<>();
        savings_csv = tempSavHistory;
        
        //add specificSaving back to whole savings_csv
        for(int i = 0; i < specificSaving.size(); i++){
            savings_csv.add(specificSaving.get(i));
        }
        
        //For loop to auto-increment the savings_id
        for(int i = 1; i < savings_csv.size(); i++){
            savings_csv.get(i)[0] = String.valueOf(i);
        }
        write(savings_csv, "savHis");
    }
    
    
    // Need get first then set
    // Array format: {"", user_id, principal_amount, interest_rate, repayment_period, outstanding_balance, status, created_at}
    private static List<String[]> tempLoans = new ArrayList<>();
    public static String[] get_loans_csv(int user_id){
        List<String[]> loans_csv = new ArrayList<>();
        loans_csv = file.read("loans");
        
        if(loans_csv.isEmpty()){
            loans_csv.add(new String[0]);
            write(loans_csv, "loans");
        }
        
        //look for loans according to loans_id and remove the loans from loans_csv
        String[] specificLoan = new String[8];
        Iterator<String[]> it = loans_csv.iterator();
        while(it.hasNext()){
            String[] data = it.next();
            
            if(data.length == 1 || data.length == 0) continue;
            if(Integer.parseInt(data[1]) == user_id){
                specificLoan = data;
                it.remove();
            }
        }
        tempLoans = loans_csv;
        
        return specificLoan;
    }
    public static void set_loans_csv(String[] specificLoan){
        List<String[]> loans_csv = new ArrayList<>();
        loans_csv = tempLoans;
        
        //add specificLoan back to whole loans_csv
        loans_csv.add(specificLoan);
        
        
        //For loop to auto-increment the loans_id
        for(int i = 1; i < loans_csv.size(); i++){
            loans_csv.get(i)[0] = String.valueOf(i);
        }
        write(loans_csv, "loans");
    }
    
    
    
    // Bank is read-only, no need to change anything
    // Array format: {bank_id, bank_name, interest_rate}
    public static List<String[]> get_bank_csv(){
        return file.read("bank");
    }

    
    // Need get first then set
    // No need to use List to get or set accbalance
    // Array format: {user_id, balance}
    private static List<String[]> tempAccBalance = new ArrayList<>();
    public static double get_accbalance_csv(int user_id){
        List<String[]> accbalance_csv = new ArrayList<>();
        accbalance_csv = file.read("accbalance");
        
        if(accbalance_csv.isEmpty()){
            accbalance_csv.add(new String[0]);
            write(accbalance_csv, "accbalance");
        }
        
        //look for loans according to loans_id and remove the loans from loans_csv
        double acc_balance = 0;
        Iterator<String[]> it = accbalance_csv.iterator();
        while(it.hasNext()){
            String[] data = it.next();
            
            if(data.length == 1 || data.length == 0) continue;
            if(Integer.parseInt(data[0]) == user_id){
                acc_balance = Double.parseDouble(data[1]);
                it.remove();
            }
        }
        tempAccBalance = accbalance_csv;
        
        return acc_balance;
    }
    public static void set_accbalance_csv(int user_id, double acc_balance){
        List<String[]> accbalance_csv = new ArrayList<>();
        accbalance_csv = tempAccBalance;
        accbalance_csv.add(new String[]{String.valueOf(user_id), String.valueOf(acc_balance)});
        
        write(accbalance_csv, "accbalance");
    }
}
