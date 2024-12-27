package com.example.part2.Classes;

public class User {

    private int idUser ;
    private String username ;
    private String password ;
    private TaskList taskList ;

    public User(int idUser, String username, String password) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
