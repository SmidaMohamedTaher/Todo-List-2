package com.example.part2;

import com.example.part2.Classes.*;
import com.example.part2.DataBases.Task_Data_Base_Method;

import java.sql.Date;
import java.util.ArrayList;


public class main {

    public static void main(String[] args) {
        TaskListImpl task = new TaskListImpl(Task_Data_Base_Method.findTasksByUserId(1),1) ;

        System.out.println(task.displayTasks().size());

        for (Task t : task.displayTasks()){
            System.out.println(t.getName());
        }
    }
}
