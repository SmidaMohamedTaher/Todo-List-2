package com.example.part2.notifications;

import com.example.part2.Classes.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

import java.time.LocalDate;

public class SystemNotifecation {

    public static void dueDateNotifacation(Task task){

        LocalDate today = LocalDate.now();
        LocalDate dueDate = LocalDate.of(task.getDueDate().getYear(), task.getDueDate().getMonth(), task.getDueDate().getDay());

        if(dueDate.isEqual(today.minusDays(1))) {
            Notifications.create()
                    .title("The time is approaching ")
                    .text("There is a day left on " + task.getName())
                    .showConfirm();
        }
        else if(dueDate.isEqual(today)) {

            Notifications.create()
                    .title("Date of task ")
                    .text("Today you have the task " + task.getName())
                    .showConfirm();
        }
    }

    public static void AddNotifacition(){

        Image image = new Image("file:src/main/resources/com/example/part2/notifacitionPhoto/addPhoto.jpeg") ;

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30); // ضبط عرض الصورة
        imageView.setFitHeight(30); // ضبط ارتفاع الصورة
        imageView.setPreserveRatio(true);

        Notifications.create()
                .title("Add successful")
                .text("you add a new Task")
                .graphic(imageView)
                .show();
    }
}
