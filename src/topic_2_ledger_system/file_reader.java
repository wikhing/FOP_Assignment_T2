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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Wong Ing Khing
 */
public class file_reader {
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
        String[] files = {/*all file path here*/};
        File csvOutputFile = new File("User CSV");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            list.stream()
              .map(this::convertToCSV)
              .forEach(pw::println);
        }
    }
    
    private void file_read(ArrayList<String[]> list){
        
    }
    
    
    
    private List<String[]> user_csv = new ArrayList<>();
    public List<String[]> get_user_csv(){
        return user_csv;
    }
    public void set_user_csv(ArrayList<String[]> user_csv) throws IOException{
        this.user_csv = user_csv;
        file_write(user_csv, 0);
    }
}
