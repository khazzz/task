package com.amazonaws.lambda.task;

import org.junit.Test;

import com.amazonaws.lambda.task.function.DailyUncompletedTasksFunctionHandler;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DailyUncompletedTasksTest {

	private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }
    
    @Test
    public void testDeleteTaskFunction() throws Exception {
    		DailyUncompletedTasksFunctionHandler handler = new DailyUncompletedTasksFunctionHandler();
    		Context ctx = createContext();

    		handler.handleRequest(null, ctx);
    		
    }
}
