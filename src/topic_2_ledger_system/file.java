package topic_2_ledger_system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Write your name here
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun
 *
 */
public class file {
    // Constant file path here
    private static final Map<String, String> filesPath = new HashMap<>(Map.ofEntries(
            entry("user",           "src/topic_2_ledger_system/saved/user.csv"),
            entry("transactions",   "src/topic_2_ledger_system/saved/transactions.csv"),
            entry("savings",        "src/topic_2_ledger_system/saved/savings.csv"),
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
    private static void write(List<String[]> list, String filename) throws IOException {
        String path = filesPath.get(filename);
        File csvOutputFile = new File(path);
        
        if(!csvOutputFile.isFile()){
            csvOutputFile.createNewFile();
        }
        
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            list.stream()
              .map(data -> convertToCSV(data))
              .forEach(pw::println);
            pw.flush();
            pw.close();
        }
    }
    
    // File reading
    private static ArrayList read(String filename) throws FileNotFoundException, IOException {
        String path = filesPath.get(filename);
        ArrayList<String[]> list = new ArrayList<>();
        File file = new File(path);
        Scanner fsc = null;
                
        fsc = new Scanner(file);
        if(!file.isFile()) file.createNewFile();
        
        while(fsc.hasNextLine()){
            list.add(fsc.nextLine().split(","));
        }

        return list;
    }
    
    
    /*
     * ----------------------------------------------------------------------------------
     * Methods below are individual method to get the data from csv file and
     * write new data to csv file
     *
     */
    
    public static ArrayList<String[]> get_user_csv() throws IOException{
        List<String[]> user_csv = new ArrayList<>();
        user_csv = file.read("user");
        
        if(user_csv.isEmpty()) user_csv.add(new String[0]);
        
        return (ArrayList<String[]>) user_csv;
    }
    public static void set_user_csv(List<String[]> user_csv) throws IOException{
        //For loop to auto-increment the user_id
        for(int i = 1; i < user_csv.size(); i++){
            user_csv.get(i)[0] = String.valueOf(i);
        }
        write(user_csv, "user");
    }
    
    
    private static List<String[]> transactions_csv = new ArrayList<>();
    public static List<String[]> get_transactions_csv() throws IOException{
        transactions_csv = file.read("transactions");
        return transactions_csv;
    }
    public void set_transactions_csv(ArrayList<String[]> transactions_csv) throws IOException{
        file.transactions_csv = transactions_csv;
        write(transactions_csv, "transactions");
    }
    
    
    private static List<String[]> savings_csv = new ArrayList<>();
    public static List<String[]> get_savings_csv() throws IOException{
        savings_csv = file.read("savings");
        return savings_csv;
    }
    public void set_savings_csv(ArrayList<String[]> savings_csv) throws IOException{
        file.savings_csv = savings_csv;
        write(savings_csv, "savings");
    }
    
    
    private static List<String[]> loans_csv = new ArrayList<>();
    public static List<String[]> get_loans_csv() throws IOException{
        loans_csv = file.read("loans");
        return loans_csv;
    }
    public void set_loans_csv(ArrayList<String[]> loans_csv) throws IOException{
        file.loans_csv = loans_csv;
        write(loans_csv, "loans");
    }
    
    
    private static List<String[]> bank_csv = new ArrayList<>();
    public static List<String[]> get_bank_csv() throws IOException{
        bank_csv = file.read("bank");
        return bank_csv;
    }
    public void set_bank_csv(ArrayList<String[]> bank_csv) throws IOException{
        file.bank_csv = bank_csv;
        write(bank_csv, "bank");
    }
    
    
    private static List<String[]> accbalance_csv = new ArrayList<>();
    public static List<String[]> get_accbalance_csv() throws IOException{
        accbalance_csv = file.read("accbalance");
        return accbalance_csv;
    }
    public void set_accbalance_csv(ArrayList<String[]> accbalance_csv) throws IOException{
        file.accbalance_csv = accbalance_csv;
        write(accbalance_csv, "accbalance");
    }
}
