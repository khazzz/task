package com.amazonaws.lambda.task.dao;


import java.util.List;

import com.amazonaws.lambda.task.domain.Task;


public interface EmailService {

    void sendEmails(List<Task> tasks) throws Exception;
    
}
