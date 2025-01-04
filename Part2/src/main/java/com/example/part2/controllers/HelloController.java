package com.example.part2.controllers;

import com.example.part2.Classes.*;
import com.example.part2.DataBases.Category_Data_Base_methode;
import com.example.part2.DataBases.Task_Data_Base_Method;
import com.example.part2.HelloApplication;
import com.example.part2.notifications.SystemNotifecation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

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
    private TextField searchCatigory ;
    @FXML
    private DatePicker taskDate ;
    @FXML
    private Label user_name ;
    @FXML
    private ChoiceBox catigoriesChice ;

    Label i = new Label() ;

    User user ;

    public HelloController(User user) {
        this.user = user;
    }

    TaskList List;
    CategoryList catList ;

    /*
        find the list catigory from databases
        searchCatigory is woring , it must be not TextField
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        user_name.setText(user.getUsername());
        List = new TaskListImpl(Task_Data_Base_Method.findTasksByUserId(user.getIdUser()), user.getIdUser());
        catList = new CategoryList(Category_Data_Base_methode.findAll()) ;

        for(Category cate :catList.getCategories()){
            catigoriesChice.getItems().add(cate.getCategoryName()) ;
        }

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

//    @FXML
//    protected void addTask() throws IOException {
//        if (taskName != null && taskDescription != null)
//            List.addTask(new TaskImpl(taskName.getText(), taskDescription.getText(), (Date) Date.from(taskDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
//
//        ListOfTaskes.getChildren().clear();
//        displayAllTasks(List.displayTasks());
//        taskName.clear();
//        taskDescription.clear();
//        sherchWord.clear();
//        taskDate.setValue(null);
//
//        SystemNotifecation.AddNotifacition();
//
//    }

    /**
     * this methode well add HBox with task data to the List of Tasks in interface
     */
    private void addHBox(String nameOfTask, String discriptionOfTask, Date dateOfTask, Complete status,Priorities prioritie)  throws IOException {
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
        t.getChildren().add(PriortieLable) ;
        t.getChildren().add(statusButtom) ;
        t.getChildren().add(deleteButtom) ;

        ListOfTaskes.getChildren().add( t );
    }


    public void displayAllTasks(ArrayList<Task> tasks) throws IOException {

        ListOfTaskes.getChildren().clear();
        for (Task task : tasks){
            addHBox(task.getName(),task.getDescription(),task.getDueDate(),task.getStatus(),task.getPriority());
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

    @FXML
    public void goToAdd() throws IOException {

        ListOfTaskes.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create_Edit.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        CreateEditController cont = fxmlLoader.getController() ;
        cont.setUser(user);
        Stage stage = new Stage();
        stage.setTitle("Todo List ");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void gotToSearch() throws IOException {

        ListOfTaskes.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Todo List ");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void goToEdit() throws IOException {

        ListOfTaskes.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create_Edit.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        CreateEditController cont = fxmlLoader.getController() ;

        // set the edit task here
        cont.setUser(user);
        Stage stage = new Stage();
        stage.setTitle("Todo List ");
        stage.setScene(scene);
        stage.show();

    }



}