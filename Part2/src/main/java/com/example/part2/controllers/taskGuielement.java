package com.example.part2.controllers;


import com.example.part2.Classes.Priorities;
import com.example.part2.Classes.Task;
import com.example.part2.Classes.TaskImpl;
import com.example.part2.Classes.TaskListImpl;
import com.example.part2.DataBases.Task_Data_Base_Method;
import com.example.part2.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class taskGuielement {


    @FXML
    HBox task_instance;
    @FXML
    private Label taskLabel;
    @FXML
    Label descriptionLable ;
    @FXML
    Label DateLable ;
    @FXML
    Label priortie ;
    @FXML
    Button status ;
    @FXML
    Button deleteBu ;

    ArrayList<Task> list = Task_Data_Base_Method.findAll();

    // this is wrong
    // tike the methode to HalloController  ******************
    //**********************************************



    @FXML
    protected void editStatus() throws IOException {

        String name = taskLabel.getText();
        Task task = null;

        for (Task t : list){
            if (t.getName().equals(name)) {
                task = t;
                break;
            }
        }

        if (status.getText().equals("not_complated")) {
            status.setText("complated");
            status.getStyleClass().clear();
            status.getStyleClass().add("complated-button");
            task.markAsCompleted();
        }
        else{
            if (status.getText().equals("complated")) {
                status.setText("abandoned");
                status.getStyleClass().clear();
                status.getStyleClass().add("Abandoned-button");
                task.markAsAbandoned();

            }
            else {
                status.setText("not_complated");
                status.getStyleClass().clear();
                status.getStyleClass().add("not-complited-button");
                task.markAsNotComplated();
            }


        }
     //   list.editTask(task.getId_T(), task);
        Task_Data_Base_Method.update(task);
    }

    @FXML
    protected void goToEdit() throws IOException, ParseException {

        task_instance.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create_Edit.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        CreateEditController cont = fxmlLoader.getController() ;

        // تحديد التنسيق
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;


try{
            date = (Date) formatter.parse(DateLable.getText());
    } catch (ParseException e) {

        System.out.println("Failed to parse date: " + e.getMessage());
    }



        Task task = new TaskImpl(taskLabel.getText(),descriptionLable.getText(),date, Priorities.valueOf(priortie.getText())) ;

        for (Task t : list){
            if(t.equal(task)){
                task = t ;
                break;
            }
        }

        cont.setEditerTask(task);
        Stage stage = new Stage();
        stage.setTitle("Todo List ");
        stage.setScene(scene);
        stage.show();

    }

}
