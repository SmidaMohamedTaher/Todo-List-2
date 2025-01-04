package com.example.part2.controllers;

import com.example.part2.Classes.User;
import com.example.part2.DataBases.User_Data_Base_Method;
import com.example.part2.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;



public class RegistrationUiController {

    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField password2;

    @FXML
    protected void registerUser() throws IOException {

        if(!usernameText.getText().equals("") && !passwordText.getText().equals("") && !password2.getText().equals(""))
        if (passwordText.getText().equals(password2.getText())) {

            User user = new User(usernameText.getText(),passwordText.getText()) ;
            User_Data_Base_Method.create(user);
            System.out.println("User created");

            loginUser() ;
        }
    }

    @FXML
    protected void loginUser() throws IOException {
        usernameText.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Todo List ");
        stage.setScene(scene);
        stage.show();
    }
}
