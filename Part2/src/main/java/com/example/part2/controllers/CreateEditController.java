package com.example.part2.controllers;

import com.example.part2.Classes.*;
import com.example.part2.DataBases.Category_Data_Base_methode;
import com.example.part2.DataBases.Task_Data_Base_Method;
import com.example.part2.DataBases.Utils;
import com.example.part2.HelloApplication;
import com.example.part2.notifications.SystemNotifecation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;





public class CreateEditController  implements Initializable {

    User user ;

    Task editerTask = null ;

    CategoryList list ;

    public void setEditerTask(Task editerTask) {
        this.editerTask = editerTask;

        if (editerTask != null) {
            taskname.setText(editerTask.getName());
            desc.setText(editerTask.getDescription());
            duedate.setValue(LocalDate.of(editerTask.getDueDate().getYear(),editerTask.getDueDate().getMonth(),editerTask.getDueDate().getDay()));
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private TextField taskname;
    @FXML
    private ChoiceBox categorues;
    @FXML
    private ChoiceBox priorities;
    @FXML
    private DatePicker duedate;
    @FXML
    private TextArea desc ;
    @FXML
    private TextField addcategorie;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        list = new CategoryList(Category_Data_Base_methode.findAll()) ;

        for (Category item : list.getCategories()){
            categorues.getItems().add(item.getCategoryName());
        }

        priorities.getItems().addAll("low","medium","high");



    }



    @FXML
    protected void createCategory() {
        Category_Data_Base_methode.create(new Category(addcategorie.getText()));
    }


     @FXML
    protected void addTask() throws IOException  {
         Task task = new TaskImpl(taskname.getText(),desc.getText(), (Date) Date.from(duedate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),Priorities.valueOf(priorities.getSelectionModel().getSelectedItem().toString())) ;

         System.out.println(Utils.getSqlDate(task.getDueDate()));
         Task_Data_Base_Method.create(task, user.getIdUser(),list.findTheCatigory(categorues.getSelectionModel().getSelectedItem().toString()));
         SystemNotifecation.AddNotifacition();

         desc.getScene().getWindow().hide();

         Stage stage = new Stage();
         FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TodoList.fxml"));
         HelloController controller = new HelloController(user) ;
         fxmlLoader.setController(controller);
         Parent root = (Parent) fxmlLoader.load();
         Scene scene = new Scene(root);
         stage.setTitle("Todo List ");
         stage.setScene(scene);
         stage.show();
    }

    @FXML
    protected void editTask(){

         editerTask.setName( taskname.getText());
         editerTask.setDescription( desc.getText());
         editerTask.setDueDate((Date) Date.from(duedate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
         if(categorues.getSelectionModel().getSelectedItem() != null)
         editerTask.addCategorie(list.findTheCatigory(categorues.getSelectionModel().getSelectedItem().toString()));

         Task_Data_Base_Method.update(editerTask);
    }


}
