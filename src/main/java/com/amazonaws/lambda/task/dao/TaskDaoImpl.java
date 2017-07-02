package com.amazonaws.lambda.task.dao;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.amazonaws.lambda.task.domain.Task;
import com.amazonaws.lambda.task.manager.DynamoDBManager;
import com.amazonaws.lambda.task.util.TaskComparator;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;


public class TaskDaoImpl implements TaskDao {

    private static final Logger log = Logger.getLogger(TaskDaoImpl.class);

    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    private static volatile TaskDaoImpl instance;


    private TaskDaoImpl() { }

    public static TaskDaoImpl instance() {

        if (instance == null) {
            synchronized(TaskDaoImpl.class) {
                if (instance == null)
                    instance = new TaskDaoImpl();
            }
        }
        return instance;
    }

    @Override
    public List<Task> findAllTasks() {
    		List<Task> tasks = mapper.scan(Task.class, new DynamoDBScanExpression());
        
    		// need to create a new list because PaginatedList from query is immutable
        List<Task> allTasks = new LinkedList<>();
        allTasks.addAll(tasks);
    		
    		// sort tasks first by the completed date (descending) and secondly by the priority (ascending)
        Collections.sort(allTasks, new TaskComparator());
        
        return tasks;
    }
    
    @Override
    public List<Task> findAllUncompletedTasks() {
    
    		// completed tasks are stored with a value of 0, so everything that is > 0 is uncompleted
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("completedDate", 
                new Condition()
                   .withComparisonOperator(ComparisonOperator.GT)
                   .withAttributeValueList(new AttributeValue().withN("0")));
        
        return mapper.scan(Task.class, scanExpression);
    }

    @Override
    public List<Task> findTasksByUser(String user) {

    		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("user", 
                new Condition()
                   .withComparisonOperator(ComparisonOperator.EQ)
                   .withAttributeValueList(new AttributeValue().withS(user)));
        
        return mapper.scan(Task.class, scanExpression);
    }

    @Override
    public void saveTask(Task task) {

        mapper.save(task);
    }
    
    @Override
    public void updateTask(Task task) {

    		List<Task> tasks = findTasksByUser(task.getUser());
		
		// update a matching uncompleted task
		if(tasks!=null && !tasks.isEmpty()) {
	    		for(Task currentTask: tasks) {
	    			if(currentTask.equalsWithoutCompletedDate(task) 
	    					&& currentTask.getCompletedDate().longValue() > 0
	    					&& task.getCompletedDate().longValue() == 0) {
	    				
	    				currentTask.setCompletedDate(task.getCompletedDate());
	    				mapper.save(currentTask);
	    				return;
	    			}
	    		}
		}
	    log.error("Could not update task");
	    throw new IllegalArgumentException("Update failed for nonexistent task");
    }

    @Override
    public void deleteTask(Task task) {

    		List<Task> tasks = findTasksByUser(task.getUser());
    		
    		// delete a matching task
    		if(tasks!=null && !tasks.isEmpty()) {
	    		for(Task currentTask: tasks) {
	    			if(currentTask.equals(task)) {
	    				mapper.delete(currentTask);
	    				return;
	    			}
	    		}
    		}
        log.error("Could not delete task");
        throw new IllegalArgumentException("Delete failed for nonexistent task");
    }
}
