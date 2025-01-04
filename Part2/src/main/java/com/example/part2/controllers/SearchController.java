package com.example.part2.controllers;

import com.example.part2.Classes.*;
import com.example.part2.DataBases.Task_Data_Base_Method;
import com.example.part2.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    User user ;

    TaskList lisOfTaskes = new TaskListImpl(Task_Data_Base_Method.findTasksByUserId(user.getIdUser()), user.getIdUser()) ;

    @FXML
    private VBox ListOfTaskes ;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayAllTasks(ArrayList<Task> tasks) throws IOException {

        ListOfTaskes.getChildren().clear();
        for (Task task : tasks){
            addHBox(task.getName(),task.getDescription(),task.getDueDate(),task.getStatus(),task.getPriority());
        }
    }

    private void addHBox(String nameOfTask, String discriptionOfTask, Date dateOfTask, Complete status, Priorities prioritie)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("taskCreation.fxml"));
        HBox t = fxmlLoader.load() ;
        Label taskNameLable =  (Label) t.getChildren().get(0) ;
        Label TaskDiscriptionLabel =  (Label) t.getChildren().get(1) ;
        Label TaskDateLable =  (Label) t.getChildren().get(2) ;
        Label PriortieLable =  (Label) t.getChildren().get(3) ;
        Button statusButtom =  (Button) t.getChildren().get(4) ;
        Button deleteButtom = (Button) t.getChildren().get(5) ;


        taskNameLable.setText(nameOfTask) ;
        TaskDiscriptionLabel.setText(discriptionOfTask) ;
        TaskDateLable.setText(dateOfTask.toString()) ;
        statusButtom.setText(status.toString()) ;
        PriortieLable.setText(prioritie.name());

        deleteButtom.setOnAction(event -> {
            try {
                deleteTask(taskNameLable.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        if (status == Complete.not_completed) {
            statusButtom.getStyleClass().add("not-complited-button") ;
        } else if (status == Complete.completed) {
            statusButtom.getStyleClass().add("complated-button") ;
        }
        else {
            statusButtom.getStyleClass().add("Abandoned-button") ;
        }


        t.getChildren().clear();
        t.getChildren().add(taskNameLable) ;
        t.getChildren().add(TaskDiscriptionLabel) ;
        t.getChildren().add(TaskDateLable) ;
        t.getChildren().add(statusButtom) ;
        t.getChildren().add(deleteButtom) ;

        ListOfTaskes.getChildren().add( t );
    }

    @FXML
    protected void  deleteTask(String name) throws IOException {

        for (Task t : lisOfTaskes.displayTasks()){
            if (t.getName().equals(name)) {
                lisOfTaskes.deleteTask(t);
                break;
            }
        }

        refresh();
    }

    @FXML
    private void refresh() throws IOException{
        displayAllTasks(lisOfTaskes.displayTasks());
    }

}
