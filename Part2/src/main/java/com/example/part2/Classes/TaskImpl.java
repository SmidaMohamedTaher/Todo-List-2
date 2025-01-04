package com.example.part2.Classes;


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


    public TaskImpl( String name, String description, Date dueDate,Priorities prioritie) {

        this.name = name;
        this.description = description;
        this.dueDate = dueDate ;
        this.prioritie = prioritie ;
        this.status = Complete.not_completed ;
        this.categories = new ArrayList<>();
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
        this.categories = new ArrayList<>() ;
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
        this.status = Complete.completed ;
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
        this.status = Complete.not_completed ;
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

    public boolean equal(Task task){
        return (this.name.equals(task.getName()) && this.description.equals(task.getDescription()) && (this.dueDate.getDay() == task.getDueDate().getDay() && this.dueDate.getMonth() == task.getDueDate().getMonth() && this.dueDate.getYear() == task.getDueDate().getYear()) && this.status.equals(task.getStatus()) && this.prioritie == task.getPriority());
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

    public Priorities getPriority() {
        return this.prioritie;
    }

    public void setCategories(ArrayList<Category> categories){
        this.categories = categories ;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addCategorie(Category category){
        this.categories.add(category);
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }
    public void setPriorities(Priorities priorities){
        this.prioritie = priorities ;
    }
}
