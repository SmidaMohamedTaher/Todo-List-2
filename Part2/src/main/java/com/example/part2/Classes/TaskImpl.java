package com.example.part2.Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TaskImpl implements Task{
    private int id_T ;
    private String name ;
    private String description ;
    private Date dueDate ;
    private Complete status;
    private Priorities prioritie ;
    private ArrayList<Category> categories ;

    /**
     * Create a new Task not exist
     * @param name
     * @param description
     * @param dueDate
     */
    public TaskImpl(String name, String description, Date dueDate) {

        this.name = name;
        this.description = description;
        this.dueDate = dueDate ;
        this.status = Complete.not_complated ;

    }

    public TaskImpl(int id_T,String name, String description, Date dueDate, Complete status) {
        this.id_T = id_T ;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate ;
        this.status = status;
    }

    /**
     * Create new object of task exit in DateBase
     * @param id_T
     * @param name
     * @param description
     * @param dueDate
     * @param status
     * @param categories
     */
    public TaskImpl(int id_T, String name, String description, Date dueDate,Complete status ,Priorities prioritie, ArrayList<Category> categories) {
        this.id_T = id_T ;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate ;
        this.status = status ;
        this.prioritie = prioritie ;
        this.categories = categories ;
    }

    /**
     * create nwe object of task exit in DataBase
     * @param id_T
     * @param name
     * @param description
     * @param dueDate
     * @param status
     * @param prioritie
     */

    public TaskImpl(int id_T, String name, String description, Date dueDate,Complete status,Priorities prioritie) {
        this.id_T = id_T ;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate ;
        this.status = status ;
        this.prioritie = prioritie ;
    }

    /**
     * this method if we need edit a task ( name or discription or due date )
     * @param task
     */
    public void edit(Task task) {
        this.name = task.getName();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();

    }


    /**
     * this is impl of method mark As Completed
     */
    public void markAsCompleted(){
        this.status = Complete.complated ;
    }

    /**
     * this is impl of method mark As Completed
     */
    public void markAsAbandoned(){
        this.status = Complete.abandoned ;
    }

    /**
     * this methode if the user want chenge the prioritie
     * @param prioritie
     */
    public void editPrioritie(Priorities prioritie) {
        this.prioritie = prioritie ;
    }

    /**
     * this is impl of method mark As not Completed
     */
    public void markAsNotComplated(){
        this.status = Complete.not_complated ;
    }

    /**
     * this function to chick if the task Belongs to Catigory
     * @param category
     * @return
     */
    public boolean isMyCategory(Category category){

        for (Category c : categories) {
            if (c == category) {
                return true;
            }
        }

        return false;
    }

    public int getId_T() {
        return this.id_T;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Complete getStatus() {
        return this.status;
    }

    public Priorities getPrioritie() {
        return this.prioritie;
    }
}
