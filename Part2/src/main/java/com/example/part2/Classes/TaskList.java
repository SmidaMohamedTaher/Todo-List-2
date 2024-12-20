package com.example.part2.Classes;

import java.util.ArrayList;

public abstract class TaskList {

    /**
     * this method to add the object of class Task ( task ) to the List
     * @param task
     */
    public abstract void addTask(Task task);

    /**
     * this method if we need delete task from the List
     * @param task
     */
    public abstract void deleteTask(Task task);

    /**
     * this method search the task we need edit it
     * @param idOfTask
     * @param task
     */
    public abstract void editTask(int idOfTask,Task task);

    /**
     * the method display return ArrayList view contains all tasks
     * @return ArrayList<Task>
     */
    public abstract ArrayList<Task> displayTasks();

    /**
     * this method to sort the List by Due date
     */
    public abstract void sortByDate() ;

    /**
     * this method to search the tasks hase a word in name or description and return List
     * @param word
     * @return ArrayList<Task>
     */
    public abstract ArrayList<Task> search(String word) ;

}
