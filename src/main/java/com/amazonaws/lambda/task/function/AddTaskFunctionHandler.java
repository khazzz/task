package com.amazonaws.lambda.task.function;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.amazonaws.lambda.task.dao.TaskDao;
import com.amazonaws.lambda.task.dao.TaskDaoImpl;
import com.amazonaws.lambda.task.pojo.TaskDto;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * This lambda function is called from a gateway with a post request to add a new task
 * 
 * @author khazanov
 *
 */
public class AddTaskFunctionHandler implements RequestHandler<TaskDto, Void> {

	private static final Logger log = Logger.getLogger(AddTaskFunctionHandler.class);

    private static final TaskDao taskDao = TaskDaoImpl.instance();
	
    @Override
    public Void handleRequest(TaskDto task, Context context) {
    	
    		if (null == task) {
            log.error("SaveTask received null input");
            throw new IllegalArgumentException("Cannot save null object");
        }
        
    		// validate an input dto
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TaskDto>> violations = validator.validate(task);
        for (ConstraintViolation<TaskDto> violation : violations) {
            log.error(violation.getMessage()); 
            throw new IllegalArgumentException(violation.getMessage());
        }

        log.info("Saving a task for user = " + task.getUser() + " , date = " + task.getCompleted());
        try {
        		taskDao.saveTask(task.toDomain());
        } catch(Exception e) {
        		log.error(e.getMessage());
        }
        
        return null;
    }
}
