package com.example.part2;

import com.example.part2.Classes.*;
import com.example.part2.DataBases.User_Data_Base_Method;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

import static jdk.xml.internal.SecuritySupport.getResource;


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

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TodoList.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Todo List ");
                stage.setScene(scene);
                stage.show();
            }

            return ;
        }
        // errer
        // errer
    }

    @FXML
    protected  void SignUp() throws IOException {

        usernameLabel.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(getResource("sigin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Todo List ");
        stage.setScene(scene);
        stage.show();
    }


}

