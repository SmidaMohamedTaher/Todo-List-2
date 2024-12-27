package com.example.part2;

import com.example.part2.Classes.User;
import com.example.part2.DataBases.User_Data_Base_Method;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static jdk.xml.internal.SecuritySupport.getResource;

public class RegistrationUiController {

    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField password2;

    @FXML
    protected void registerUser(){

        if(usernameText.getText().equals("") || passwordText.getText().equals("") || password2.getText().equals("")){}
        if (passwordText.equals(password2)) {

            User user = new User(usernameText.getText(),passwordText.getText()) ;
            User_Data_Base_Method.create(user);

        }
    }

    @FXML
    protected void loginUser() throws IOException {
        usernameText.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Todo List ");
        stage.setScene(scene);
        stage.show();
    }
}
