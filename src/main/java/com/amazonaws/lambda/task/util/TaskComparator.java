package com.amazonaws.lambda.task.util;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.amazonaws.lambda.task.domain.Task;

/**
 * This custom comparator would help to sort a list of tasks first
 * by the completed date (descending) and secondly by the priority (ascending).
 * 
 * @author khazanov
 */
public class TaskComparator implements Comparator<Task> {
 
    @Override
    public int compare(Task o1, Task o2) {
        return new CompareToBuilder()
                .append(o2.getCompletedDate(), o1.getCompletedDate())
                .append(o1.getPriority(), o2.getPriority()).toComparison();
    }
}
