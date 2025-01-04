package com.example.part2.controllers;

import com.example.part2.Classes.*;
import com.example.part2.DataBases.User_Data_Base_Method;
import com.example.part2.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


public class LoginController {

    @FXML
    private TextField usernameLabel ;
    @FXML
    private TextField passwordLabel ;

    ArrayList<User> users = User_Data_Base_Method.findAll();

    @FXML
    protected void logIn() throws IOException {

        for(User user : users){
            if(user.getUsername().equals(usernameLabel.getText()) && user.getPassword().equals(passwordLabel.getText())){
                // log in to the home

                usernameLabel.getScene().getWindow().hide();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TodoList.fxml"));

                HelloController controller = new HelloController(user) ;
                fxmlLoader.setController(controller);
                Parent root = (Parent) fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setTitle("Todo List ");
                stage.setScene(scene);
                stage.show();

                return ;
            }

        }
        // errer
        // errer
    }

    @FXML
    protected  void SignUp() throws IOException {

        usernameLabel.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sigin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Todo List ");
        stage.setScene(scene);
        stage.show();
    }


}

