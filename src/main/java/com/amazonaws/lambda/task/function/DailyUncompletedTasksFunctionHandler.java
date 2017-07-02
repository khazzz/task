package com.amazonaws.lambda.task.function;

import java.util.List;

import org.apache.log4j.Logger;

import com.amazonaws.lambda.task.dao.EmailService;
import com.amazonaws.lambda.task.dao.EmailServiceImpl;
import com.amazonaws.lambda.task.dao.TaskDao;
import com.amazonaws.lambda.task.dao.TaskDaoImpl;
import com.amazonaws.lambda.task.domain.Task;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * This lambda function is called from a gateway with a get request.
 * It is fetching a list of all uncompleted tasks and sends an email to the user of each task.
 * 
 * @author khazanov
 *
 */
public class DailyUncompletedTasksFunctionHandler implements RequestHandler<Void, Void> {

	private static final Logger log = Logger.getLogger(DailyUncompletedTasksFunctionHandler.class);

    private static final TaskDao taskDao = TaskDaoImpl.instance();
    
    private static final EmailService emailService = EmailServiceImpl.instance();
	
    @Override
    public Void handleRequest(Void input, Context context) {
    	
    		log.info("GetAllUncompletedTasks invoked to scan table for ALL Tasks");
        List<Task> tasks = taskDao.findAllUncompletedTasks();
        log.info("Found " + tasks.size() + " total tasks.");
        
        try {
        		emailService.sendEmails(tasks);
        } catch(Exception e) {
        		log.error(e.getMessage());
        }
        
        return null;
    }
}
