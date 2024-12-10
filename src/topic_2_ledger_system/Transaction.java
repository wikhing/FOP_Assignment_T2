/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author I Khing
 */
public class Transaction {
    
    private final SimpleStringProperty type = new SimpleStringProperty("");
    private final SimpleStringProperty amount = new SimpleStringProperty("");
    private final SimpleStringProperty description = new SimpleStringProperty("");
    private final SimpleStringProperty date = new SimpleStringProperty("");
    
    public Transaction(){
        this("", "", "", "");
    }
    
    public Transaction(String type, String amount, String description, String date){
        setType(type);
        setAmount(amount);
        setDescription(description);
        setDate(date);
    }
    
    public String getType(){
        return type.get();
    }
    public void setType(String typ){
        type.set(typ);
    }
    
    public String getAmount(){
        return amount.get();
    }
    public void setAmount(String amo){
        amount.set(amo);
    }
    
    public String getDescription(){
        return description.get();
    }
    public void setDescription(String des){
        description.set(des);
    }
    
    public String getDate(){
        return date.get();
    }
    public void setDate(String dat){
        date.set(dat);
    }
}
