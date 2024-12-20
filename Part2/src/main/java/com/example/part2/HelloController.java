package com.example.part2;

import com.example.part2.Classes.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private VBox ListOfTaskes ;
    @FXML
    private TextField taskName ;
    @FXML
    private TextArea taskDescription ;
    @FXML
    private TextField sherchWord ;
    @FXML
    private DatePicker taskDate ;
    Label i = new Label() ;

    TaskList List = new TaskListImpl() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.seconds(1), e -> {
//                    try {
//                        displayAllTasks(List.displayTasks());
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                })
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
        ListOfTaskes.getChildren().clear();
        try {
            displayAllTasks(List.displayTasks());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @FXML
    protected void addTask() throws IOException {
        if (taskName != null && taskDescription != null)
            List.addTask(new TaskImpl(taskName.getText(), taskDescription.getText(), Date.from(taskDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));

        ListOfTaskes.getChildren().clear();
        displayAllTasks(List.displayTasks());
        taskName.clear();
        taskDescription.clear();
        sherchWord.clear();
        taskDate.setValue(null);

    }

    /**
     * this methode well add HBox with task data to the List of Tasks in interface
     */
    private void addHBox(String nameOfTask, String discriptionOfTask, Date dateOfTask, Complete status)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("taskCreation.fxml"));
        HBox t = fxmlLoader.load() ;
        Label taskNameLable =  (Label) t.getChildren().get(0) ;
        Label TaskDiscriptionLabel =  (Label) t.getChildren().get(1) ;
        Label TaskDateLable =  (Label) t.getChildren().get(2) ;
        Button statusButtom =  (Button) t.getChildren().get(3) ;
        Button deleteButtom = (Button) t.getChildren().get(4) ;


        taskNameLable.setText(nameOfTask) ;
        TaskDiscriptionLabel.setText(discriptionOfTask) ;
        TaskDateLable.setText(dateOfTask.toString()) ;
        statusButtom.setText(status.toString()) ;

        if (status == Complete.not_complated) {
            statusButtom.getStyleClass().add("not-complited-button") ;
        } else if (status == Complete.complated) {
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


    public void displayAllTasks(ArrayList<Task> tasks) throws IOException {

        ListOfTaskes.getChildren().clear();
        for (Task task : tasks){
            addHBox(task.getName(),task.getDescription(),task.getDueDate(),task.getStatus());
        }
    }


    @FXML
    protected void sherch() throws IOException {
        ArrayList<Task> sherchingTasks = List.search(sherchWord.getText());
        displayAllTasks(sherchingTasks);

    }

    @FXML
    private void refresh() throws IOException{
        displayAllTasks(List.displayTasks());
    }

}