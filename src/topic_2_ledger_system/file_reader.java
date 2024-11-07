package topic_2_ledger_system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Wong Ing Khing
 */
public class file_reader {
    private final String[] filesPath = {/*all file path here*/};
  //sim test//   
    private String convertToCSV(String[] data) {
        return Stream.of(data)
          .map(this::escapeSpecialCharacters)
          .collect(Collectors.joining(","));
    }
    
    private String escapeSpecialCharacters(String data) {
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
    
    public void file_write(ArrayList<String[]> list, int fileidx) throws IOException {
        String path = filesPath[fileidx];
        File csvOutputFile = new File(path);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            list.stream()
              .map(this::convertToCSV)
              .forEach(pw::println);
        }
    }
    
    public void file_read(ArrayList<String[]> list, int fileidx) {
        String path = filesPath[fileidx];
        File file = new File(path);
        Scanner fsc = null;
        try {
            fsc = new Scanner(file);
            if(!file.isFile()) file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(file_reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(fsc.hasNextLine()){
            list.add(fsc.nextLine().split(","));
        }
    }
    
    
    
    private List<String[]> user_csv = new ArrayList<>();
    public List<String[]> get_user_csv(){
        return user_csv;
    }
    public void set_user_csv(ArrayList<String[]> user_csv) throws IOException{
        this.user_csv = user_csv;
        file_write(user_csv, 0);
    }
    
    
    private List<String[]> transactions_csv = new ArrayList<>();
    public List<String[]> get_transactions_csv(){
        return transactions_csv;
    }
    public void set_transactions_csv(ArrayList<String[]> transactions_csv) throws IOException{
        this.transactions_csv = transactions_csv;
        file_write(transactions_csv, 1);
    }
    
    
    private List<String[]> savings_csv = new ArrayList<>();
    public List<String[]> get_savings_csv(){
        return savings_csv;
    }
    public void set_savings_csv(ArrayList<String[]> savings_csv) throws IOException{
        this.savings_csv = savings_csv;
        file_write(savings_csv, 2);
    }
    
    
    private List<String[]> loans_csv = new ArrayList<>();
    public List<String[]> get_loans_csv(){
        return loans_csv;
    }
    public void set_loans_csv(ArrayList<String[]> loans_csv) throws IOException{
        this.loans_csv = loans_csv;
        file_write(loans_csv, 3);
    }
    
    
    private List<String[]> bank_csv = new ArrayList<>();
    public List<String[]> get_bank_csv(){
        return bank_csv;
    }
    public void set_bank_csv(ArrayList<String[]> bank_csv) throws IOException{
        this.bank_csv = bank_csv;
        file_write(bank_csv, 4);
    }
    
    
    private List<String[]> accbalance_csv = new ArrayList<>();
    public List<String[]> get_accbalance_csv(){
        return accbalance_csv;
    }
    public void set_accbalance_csv(ArrayList<String[]> accbalance_csv) throws IOException{
        this.accbalance_csv = accbalance_csv;
        file_write(accbalance_csv, 5);
    }
}
