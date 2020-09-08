package com.example.todolist.sqlitepackage;

// Class Task referring as a task in our todolist

public class Task {

    //variables
    private int id;
    private String name;
    private String description;

    // empty constructor
    public Task(){

    }

    // constructor overloading
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // constructor overloading
    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Initializing getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
