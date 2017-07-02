package com.amazonaws.lambda.task;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.task.function.ListTasksFunctionHandler;
import com.amazonaws.lambda.task.pojo.TaskDto;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListTasksTest {

	private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }
    
    @Test
    public void testDeleteTaskFunction() throws Exception {
    		ListTasksFunctionHandler handler = new ListTasksFunctionHandler();
    		Context ctx = createContext();

    		List<TaskDto> list = handler.handleRequest(null, ctx);
    		
    		Assert.assertNotNull(list);
    }
}
