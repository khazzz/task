package com.amazonaws.lambda.task;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.task.function.AddTaskFunctionHandler;
import com.amazonaws.lambda.task.pojo.TaskDto;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddTaskFunctionTest {

    private static TaskDto input;
    private static TaskDto invalidDateInput;
    private static TaskDto blankDateInput;
    private static TaskDto invalidUserInput;
    private static TaskDto invalidUserInput2;
    private static TaskDto invalidDescInput;
    private static TaskDto invalidPriorityInput;
    
    @BeforeClass
    public static void createInput() throws IOException {
        input = new TaskDto("2016-06-06T12:22:46-04:00", "testy.mctester@example.com", "Do something awesome", 0L);
        invalidDateInput = new TaskDto("2009-05-19T14:3924", "pk@test.com", "test task 3", 0L);
        blankDateInput = new TaskDto("", "pk@test.com", "test task 3", 0L);
        invalidUserInput = new TaskDto("2017-06-30T10:49:46-04:00", "r@t.", "test task 3", 0L);
        invalidUserInput2 = new TaskDto("2017-06-30T10:49:46-04:00", "", "test task 3", 0L);
        invalidDescInput = new TaskDto("2017-06-30T10:49:46-04:00", "rk@test.com", "", 0L);
        invalidPriorityInput = new TaskDto("2017-06-30T10:49:46-04:00", "rk@test.com", "test task 3", 10L);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }
    
    @Test
    public void testAddTaskFunction() throws Exception {
    		AddTaskFunctionHandler handler = new AddTaskFunctionHandler();
    		Context ctx = createContext();

        handler.handleRequest(input, ctx);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidDateInputTaskFunction() throws Exception {
    		AddTaskFunctionHandler handler = new AddTaskFunctionHandler();
		Context ctx = createContext();

		handler.handleRequest(invalidDateInput, ctx);
    }
    
//    @Test
//    public void testAddBlankDateInputTaskFunction() throws Exception {
//    		AddTaskFunctionHandler handler = new AddTaskFunctionHandler();
//		Context ctx = createContext();
//
//		handler.handleRequest(blankDateInput, ctx);
//    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidUserInputTaskFunction() throws Exception {
    		AddTaskFunctionHandler handler = new AddTaskFunctionHandler();
		Context ctx = createContext();

		handler.handleRequest(invalidUserInput, ctx);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidUserInput2TaskFunction() throws Exception {
    		AddTaskFunctionHandler handler = new AddTaskFunctionHandler();
		Context ctx = createContext();

		handler.handleRequest(invalidUserInput2, ctx);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidDescInputTaskFunction() throws Exception {
    		AddTaskFunctionHandler handler = new AddTaskFunctionHandler();
		Context ctx = createContext();

		handler.handleRequest(invalidDescInput, ctx);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidPriorityInputTaskFunction() throws Exception {
    		AddTaskFunctionHandler handler = new AddTaskFunctionHandler();
		Context ctx = createContext();

		handler.handleRequest(invalidPriorityInput, ctx);
    }
}
