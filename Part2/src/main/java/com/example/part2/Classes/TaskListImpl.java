package com.example.part2.Classes;



import com.example.part2.DataBases.DataBaseControler;

import java.util.ArrayList;
import java.util.Comparator;

public class TaskListImpl extends TaskList{

    /**
     * List of the Tasks
     */
    private ArrayList<Task> tasks;

    /**
     * Create a new List of Tasks
     */
    public TaskListImpl() {
        this.tasks = new ArrayList<Task>();
        update();
    }

    public TaskListImpl(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * add a task { task } in the List and the dateBase
     * @param task
     */
    public  void addTask(Task task){
        DataBaseControler.create(task) ;
        update();
    }

    /**
     * delete a task { task } in the List and the dateBase
     * @param task
     */
    public void deleteTask(Task task){
       DataBaseControler.deleteById(task.getId_T());
       update();
    }

    /**
     * Impl of the method search the task we need edit it
     * @param idOfTask
     * @param task
     */
    public void editTask(int idOfTask ,Task task){
        for (Task task1 : this.tasks) {
            if (task1.getId_T() == idOfTask) {
                task1.edit(task);
                DataBaseControler.update(task1);
                update();
                break;
            }
        }
    }

    /**
     * this method to return the all tasks as ArrayList
     * @return ArrayList<Task>
     */
    public ArrayList<Task> displayTasks(){
        update();
        return this.tasks ;
    }

    /**
     * this method to sort the List by Due date
     */
    public void sortByDate(){
        this.tasks.sort(Comparator.comparing(item -> item.getDueDate()));
    }

    /**
     * Impl the method search ( method to search the tasks hase a word in name or description and return List )
     * @param word
     * @return ArrayList<Task>
     */
    public ArrayList<Task> searchByKyword(String word){
        ArrayList<Task> Findtasks = new ArrayList<>();
        for (Task task : this.tasks) {
            if (task.getName().contains(word) || task.getDescription().contains(word)) {
                Findtasks.add(task);
            }
        }
        
        return Findtasks;
    }

    /**
     * this function to find the tasks belomgs to the sime catigory
     * @param category
     * @return
     */
    
    public ArrayList<Task> searchByCategory(Category category){
        ArrayList<Task> findtasks = new ArrayList<Task>();
        
        for (Task task : this.tasks) {
            if (task.isMyCategory(category)) {
                findtasks.add(task);
            }
        }

        return findtasks;
    }

    /**
     * the method update to take all the tasks from dateBase and sort it in the List
     */
    public void update(){
        this.tasks = DataBaseControler.findAll() ;
        sortByDate();

    }
}
