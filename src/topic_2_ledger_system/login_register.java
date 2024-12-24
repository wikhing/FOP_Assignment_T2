/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package topic_2_ledger_system;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Write your name here
 * @author Teo Yik Kiat, Wong Ing Khing, Sim Pei Jun
 *
 */
public class login_register extends main_system{
    
    @FXML Button back_btn = new Button();
    @FXML VBox main_btn = new VBox();
    @FXML GridPane login = new GridPane();
    @FXML GridPane register = new GridPane();
    
    @FXML
    private void back(){                            //back button
        login.setVisible(false);
        register.setVisible(false);
        main_btn.setVisible(true);
        back_btn.setVisible(false);
    }
    
    @FXML 
    private void toLogin() throws IOException{      //go to login form
        main_btn.setVisible(false);
        login.setVisible(true);
        back_btn.setVisible(true);
        loginFX();
    }
    
    @FXML 
    private void toRegister() throws IOException {  //go to registerform
        main_btn.setVisible(false);
        register.setVisible(true);
        back_btn.setVisible(true);
        registerFX();
    }
    
    @FXML TextField email_login = new TextField();
    @FXML PasswordField pass_login = new PasswordField();
    @FXML Button submit_login = new Button();
    
    @FXML
    private void loginFX() throws IOException {             //login for fxml form
        email_login.setPromptText("eg. johndoe@gmail.com"); //prompt text
        email_login.getParent().requestFocus();             //prompt text disappear when clicked on textfield
                
        submit_login.setOnMouseClicked((MouseEvent e) -> {  //event to do when button clicked
            String email = email_login.getText();
            String password = pass_login.getText();
            
            try {
                int user_id = login(email, password);
                
                if(user_id != -1){
//                    System.out.println("Login Succesful " + user_id);

                    menu.set_user(user_id);
                    main_system.setScene("menu");
                    
                    menu m = new menu();
                    m.ReminderSystem(user_id);
                }
                
            } catch (IOException ex) {
                error("login");
            }
        });
    }
    
    @FXML TextField name_regis = new TextField();
    @FXML TextField email_regis = new TextField();
    @FXML PasswordField pass_regis = new PasswordField();
    @FXML PasswordField conf_pass_regis = new PasswordField();
    @FXML Button submit_register = new Button();
    
    @FXML
    private void registerFX() throws IOException {            //register for fxml form
        name_regis.setPromptText("eg. John Doe");
        name_regis.getParent().requestFocus();
        
        email_regis.setPromptText("eg. johndoe@gmail.com");
        email_regis.getParent().requestFocus();
        
        submit_register.setOnMouseClicked((MouseEvent e) -> {
            String name = name_regis.getText();
            String email = email_regis.getText();
            String password = pass_regis.getText();
            String conf_pass = conf_pass_regis.getText();
            
            try {
                int user_id = register(name, email, password, conf_pass);
                
                if(user_id != -1){
//                    System.out.println("Register and login successful" + user_id);

                    menu.set_user(user_id);
                    main_system.setScene("menu");
                }
                
            } catch (IOException ex) {
                error("register");
            }
        });
    }
    
    @FXML
    private void error(String login_register){          //handle error
        Label error = new Label();
        
        error.setText("Error!");
        
        GridPane.setHalignment(error, HPos.CENTER);
        GridPane.setColumnSpan(error, 2);
        
        if(login_register.equals("login")){
            login.getChildren().remove(error);
            login.add(error, 0, 6);
        }else if(login_register.equals("register")){
            register.getChildren().remove(error);
            register.add(error, 0, 9);
        }
    }
    
    /*
    private static Scanner input = new Scanner(System.in);
    
    public static int initialize() throws IOException{

        int typeOfUser = promptUser();
        
        while(typeOfUser != 1 && typeOfUser != 2){
            System.out.println("\nError! Please login or register again!\n");
            typeOfUser = promptUser();
        }
        
        int user_id = -1;
        while(user_id == -1){
            user_id = loginORregister(typeOfUser);
            
            if(user_id == -1) typeOfUser = promptUser();
        }
        return user_id;
    }
    
    private static int promptUser(){
        System.out.print("\n== The One Ledger System ==\nLogin or Register:\n1. Login\n2. Register\n\n> ");
        
        int typeOfUser = input.nextInt();
        
        return typeOfUser;
    }
    */
    @FXML Label input_invalid = new Label();
    @FXML Label passesNotSame = new Label();
    
    //register to check value
    private int register(String name, String e_mail, String password, String confirmationPassword) throws IOException{
        boolean validEmailFormat = validateEmail(e_mail);
        boolean passwordIsValid = isPasswordComplex(password);
        
        if(!validEmailFormat || !passwordIsValid){
            passesNotSame.setVisible(false);
            input_invalid.setVisible(true);
            
            String text = "Incorrect E-mail Format or Password Format";
            input_invalid.setText(text);

            GridPane.setHalignment(input_invalid, HPos.CENTER);
            GridPane.setColumnSpan(input_invalid, 2);

            register.getChildren().remove(input_invalid);
            register.add(input_invalid, 0, 7);
            
            return -1;
        }

        boolean passwordMatch = confirmPassword(password, confirmationPassword);
        if (!passwordMatch && validEmailFormat && passwordIsValid) {
            input_invalid.setVisible(false);
            passesNotSame.setVisible(true);

            String text = "Password and Confirmation Password are not same";
            passesNotSame.setText(text);

            GridPane.setHalignment(passesNotSame, HPos.CENTER);
            GridPane.setColumnSpan(passesNotSame, 2);

            register.add(passesNotSame, 0, 7);
        }

        int user_id = -1;
        if(passwordMatch && validEmailFormat && passwordIsValid){
            List<String[]> allUser = file.get_user_csv();
            String hashedPassword = bcrypt.hashpw(password, bcrypt.gensalt());
            allUser.add(new String[]{"", name, e_mail, hashedPassword, "00/00/0000"});
            file.set_user_csv(allUser);
            file.new_user_acc_setup(allUser.size() - 1);
            
            user_id = allUser.size() - 1;
        }
        
        return user_id;
    }
    
    private int login(String e_mail, String password) throws IOException{
        int user_id = -1;
        user_id = compareUser(new String[]{e_mail, password});

        if(user_id == -1){
            String text = "Incorrect E-mail or Password";
            input_invalid.setText(text);

            GridPane.setHalignment(input_invalid, HPos.CENTER);
            GridPane.setColumnSpan(input_invalid, 2);

            login.getChildren().remove(input_invalid);
            login.add(input_invalid, 0, 4);
        }
        
        return user_id;
    }
    
    /*
    private static int loginORregister(int typeOfUser) throws IOException{
        
        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToday = date.format(pattern);
        
        if(typeOfUser == 2){
            System.out.println("\n== Please fill in the form ==");

            System.out.print("Name: ");
            input.nextLine();
            String user_name = input.nextLine();

            String e_mail = "";
            while(true){
                System.out.print("Email: ");
                e_mail = input.next();
                
                boolean validEmailFormat = validateEmail(e_mail);
                
                if(!validEmailFormat){
                    System.out.println("\nInvalid e-mail format!");
                    System.out.println("Please enter your e-mail again.\n");
                    System.out.println("Name: " + user_name);
                    continue;
                }
                
                break;
            }
            
            while (true) {
                System.out.print("Password: ");
                String password = input.next();

                boolean passwordIsValid = isPasswordComplex(password);
                if (!passwordIsValid) {
                    System.out.println("\nPassword is too simple.\nMinimum password length is 8 include with uppercase, lowercase, number and special character\n");
                    System.out.println("Name: " + user_name);
                    System.out.println("Email: " + e_mail);
                    continue;
                }

                System.out.print("Confirmation password: ");
                String confirmationPassword = input.next();
                boolean passwordMatch = confirmPassword(password, confirmationPassword);
                if (!passwordMatch) {
                    System.out.println("\nPassword does not match! Please enter again.\n");
                    System.out.println("Name: " + user_name);
                    System.out.println("Email: " + e_mail);
                    continue;
                }

                List<String[]> allUser = file.get_user_csv();
                String hashedPassword = bcrypt.hashpw(password, bcrypt.gensalt());
                allUser.add(new String[]{"", user_name, e_mail, hashedPassword, dateToday});
                file.set_user_csv(allUser);
                file.new_user_acc_setup(allUser.size() - 1);
                System.out.println("\nRegister successful!");
                break;
            }
        }else{
            System.out.println("\n== Please enter your email and password ==");

            int handleEnter = 0;
            int isUser = -1;
            do{
                System.out.print("Email: ");
                if(handleEnter == 0){
                    input.nextLine();
                    handleEnter++;
                }
                String e_mail = input.nextLine();
                if(e_mail.equals("-1")){
                    promptUser();
                    break;
                }

                System.out.print("Password: ");
                String password = input.nextLine();

                isUser = compareUser(new String[]{e_mail, password});

                if(isUser == -1){
                    System.out.println("\nE-mail or Password is incorrect!");
                    System.out.println("To exit to main menu, please enter -1\n");
                    System.out.println("Please re-enter your email and password again.");
                }
            }while(isUser == -1);
            
            System.out.println("\nLogin Successful!\n");
            
            return isUser;
        }
        return -1;
    }
    */
    
    private static boolean confirmPassword(String password, String confirmationPassword) {
        return password.equals(confirmationPassword);
    }
    
    private static boolean isPasswordComplex(String password) {
        char pwdCheck;
        int upperCaseCount = 0, lowerCaseCount = 0, numCount = 0,specialCharCount = 0;
        int minLength = 8;
                    
        for (int i = 0; i< password.length(); ++i){
                
            pwdCheck = password.charAt(i);
            
            if ( pwdCheck >= 'A' && pwdCheck <= 'Z') {
                // Check for uppercase
                upperCaseCount++;
            } else if ( pwdCheck >= 'a' && pwdCheck <= 'z') {
                // Check for lowercase
                lowerCaseCount++;
            } else if ( pwdCheck - '0' >= 0 && pwdCheck - '0' <= 9) {
                // Check for numbers
                numCount++;
            } else if ( (pwdCheck - '!' >= 0 && pwdCheck - '!' <= 93)) {
                // Check for special characters
                specialCharCount++;
            }
            
        }

        return (password.length() >= minLength && upperCaseCount >= 1 && lowerCaseCount >= 1 && numCount >= 1 && specialCharCount >= 1);  
    }
    
    private static boolean validateEmail(String e_mail) {
        return !(!e_mail.contains("@") || !e_mail.contains(".com") || e_mail.indexOf("@") + 1 == e_mail.indexOf(".com"));
    }
    
    private static int compareUser(String[] loginInfo) throws IOException{
        List<String[]> users = file.get_user_csv();

        for(int i = 1; i < users.size(); i++){
            if (users.get(i).length < 5){
                continue;
            }
            if(users.get(i)[2].equals(loginInfo[0]) && bcrypt.checkpw(loginInfo[1], users.get(i)[3])){
                return Integer.parseInt(users.get(i)[0]);
            }
        }
        
        return -1;
    }
}
