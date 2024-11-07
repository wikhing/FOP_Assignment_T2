/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wong Ing Khing
 */
public class main_system {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //debugging methods in file
        List<String[]> input = new ArrayList<>();
        
        //adding data into user.csv
        String[] arrS1 = {"001", "Wong", "test123@gmail.com", "testtest123"};
        String[] arrS2 = {"002", "John", "testjohn@gmail.com", "testagain123"};
        String[] arrS3 = {"003", "Doe", "doe123@hotmail.com", "password"};
        input.add(arrS1);
        input.add(arrS2);
        input.add(arrS3);
        
        //reading data from csv
        List<String[]> userdata = file.get_user_csv();
        
        for(String[] arr : userdata){
            for(String str : arr){
                System.out.println(str);
            }
            System.out.println();
        }
        
        
        //writing new data
        
        
        /*
        try {
            writefile.write(input, "user");
        } catch (IOException ex) {
            Logger.getLogger(main_system.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        //removing data from center
//        for(int i = 0; i < userdata.size(); i++){
//            if(userdata.get(i)[0].equals("002")) userdata.remove(i);        //after removing need to change all user id as well
//        }                                                                   //and also need to remove all data related to the user id

        try {
            file.set_user_csv(input);
        } catch (IOException ex) {
            Logger.getLogger(main_system.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
