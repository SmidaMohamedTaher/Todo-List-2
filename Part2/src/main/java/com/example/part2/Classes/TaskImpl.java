package com.example.part2.Classes;

import java.util.Date;

public class TaskImpl implements Task{
    private int id_T ;
    private String name ;
    private String description ;
    private Date dueDate ;
    private Complete status;

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

    /**
     * Create new object of task exit in DateBase
     * @param id_T
     * @param name
     * @param description
     * @param dueDate
     * @param status
     */
    public TaskImpl(int id_T, String name, String description, Date dueDate,Complete status) {
        this.id_T = id_T ;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate ;
        this.status = status ;
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
     * this is impl of method mark As not Completed
     */
    public void markAsNotComplated(){
        this.status = Complete.not_complated ;
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
}
