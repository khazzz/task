package com.amazonaws.lambda.task.dao;


import java.util.List;

import com.amazonaws.lambda.task.domain.Task;


public interface TaskDao {

    List<Task> findAllTasks();

    List<Task> findTasksByUser(String user);

    void saveTask(Task task);
    
    void updateTask(Task task);

    void deleteTask(Task task);

}
