package com.amazonaws.lambda.task.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.amazonaws.lambda.task.dao.TaskDao;
import com.amazonaws.lambda.task.dao.TaskDaoImpl;
import com.amazonaws.lambda.task.domain.Task;
import com.amazonaws.lambda.task.pojo.TaskDto;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * This lambda function is called from a gateway with a get request.
 * It is fetching a list of all tasks.
 * 
 * @author khazanov
 *
 */
public class ListTasksFunctionHandler implements RequestHandler<Void, List<TaskDto>> {

	private static final Logger log = Logger.getLogger(ListTasksFunctionHandler.class);

    private static final TaskDao taskDao = TaskDaoImpl.instance();
	
    @Override
    public List<TaskDto> handleRequest(Void input, Context context) {
    	
    		log.info("GetAllTasks invoked to scan table for ALL Tasks");
        List<Task> tasks = taskDao.findAllTasks();
        log.info("Found " + tasks.size() + " total tasks.");
        
        List<TaskDto> result = new ArrayList<TaskDto>();
        for(Task task: tasks) {
        		result.add(task.toDto());
        }
        return result;
    }
}
