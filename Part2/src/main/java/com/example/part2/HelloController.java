package com.example.part2;

import com.almasb.fxgl.app.SystemActions;
import com.example.part2.Classes.*;
import com.example.part2.notifications.SystemNotifecation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
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
    private TextField searchCatigory ;
    @FXML
    private DatePicker taskDate ;
    Label i = new Label() ;

    TaskList List = new TaskListImpl() ;
    CategoryList catList = new CategoryList() ;


    /*
        find the list catigory from databases
        searchCatigory is woring , it must be not TextField
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListOfTaskes.getChildren().clear();
        try {
            displayAllTasks(List.displayTasks());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    protected void  deleteTask(String name) throws IOException {

        for (Task t : List.displayTasks()){
            if (t.getName().equals(name)) {
                List.deleteTask(t);
                break;
            }
        }

        refresh();



    }

    @FXML
    protected void addTask() throws IOException {
        if (taskName != null && taskDescription != null)
            List.addTask(new TaskImpl(taskName.getText(), taskDescription.getText(), (Date) Date.from(taskDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));

        ListOfTaskes.getChildren().clear();
        displayAllTasks(List.displayTasks());
        taskName.clear();
        taskDescription.clear();
        sherchWord.clear();
        taskDate.setValue(null);

        SystemNotifecation.AddNotifacition();

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

        deleteButtom.setOnAction(event -> {
            try {
                deleteTask(taskNameLable.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

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
        ArrayList<Task> sherchingTasks = List.searchByKyword(sherchWord.getText());
        displayAllTasks(sherchingTasks);

    }

    @FXML
    protected void searchByCatigory() throws IOException {
        ArrayList<Task> findTask = List.searchByCategory(catList.findTheCatigory(searchCatigory.getText())) ;
        displayAllTasks(findTask);

    }

    @FXML
    private void refresh() throws IOException{
        displayAllTasks(List.displayTasks());


    }

}