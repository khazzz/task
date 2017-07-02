package com.amazonaws.lambda.task;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.task.function.UpdateTaskFunctionHandler;
import com.amazonaws.lambda.task.pojo.TaskDto;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UpdateTaskFunctionTest {

    private static TaskDto input;

    @BeforeClass
    public static void createInput() throws IOException {
    		input = new TaskDto("", "testy.mctester@example.com", "Do something awesome", 1L);    
    	}
    
    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }
    
    @Test
    public void testUpdateTaskFunction() throws Exception {
        UpdateTaskFunctionHandler handler = new UpdateTaskFunctionHandler();
    		Context ctx = createContext();

        handler.handleRequest(input, ctx);
    }
}
