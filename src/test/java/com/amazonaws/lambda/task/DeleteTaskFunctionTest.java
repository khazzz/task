package com.amazonaws.lambda.task;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.task.function.DeleteTaskFunctionHandler;
import com.amazonaws.lambda.task.pojo.TaskDto;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteTaskFunctionTest {

    private static TaskDto input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = new TaskDto("2017-06-30T10:49:46-04:00", "pk@test.com", "test task 2", 1L);
    }

    
    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }
    
    @Test
    public void testDeleteTaskFunction() throws Exception {
        DeleteTaskFunctionHandler handler = new DeleteTaskFunctionHandler();
    		Context ctx = createContext();

        handler.handleRequest(input, ctx);
    }
}
