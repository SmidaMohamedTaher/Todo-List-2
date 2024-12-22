package com.example.part2.Classes;

import java.util.Date;

public interface Task {

    /**
     * this fanction to edit the information of one tash
     *
     * @param task
     */
    public void edit(Task task);

    /**
     * this fanction to mack the task as Complated
     */
    public void markAsCompleted();

    /**
     * this fanction to mack the task as Abandoned
     */
    public void markAsAbandoned();

    /**
     * this fanction to mack the task as not complated
     */
    public void markAsNotComplated();


    /**
     * this function to edit the prioritie of the task
     *
     * @param prioritie
     */
    public void editPrioritie(Priorities prioritie);

    /**
     * this geter to return the id of the task
     *
     * @return int
     */
    public int getId_T();

    /**
     * this geter to return the Due Date of the task
     *
     * @return
     */
    public Date getDueDate();

    /**
     * this geter to return the name of the task
     *
     * @return
     */
    public String getName();

    /**
     * this geter to return the discription of the task
     *
     * @return
     */
    public String getDescription();

    /**
     * this geter to return the status of the task
     *
     * @return
     */
    public Complete getStatus();

    /**
     * this geter ot return the prioritie of the task
     *
     * @return
     */
    public Priorities getPrioritie();

    /**
     * this function to chick if the task Belongs to the Catigory
     *
     * @param category
     * @return
     */
    public boolean isMyCategory(Category category);
}


