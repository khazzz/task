package com.amazonaws.lambda.task.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.amazonaws.lambda.task.domain.Task;
import com.amazonaws.lambda.task.util.DateUtil;

public class TaskDto {

	@Pattern(regexp = "^$|^(?:[1-9]\\d{3}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1\\d|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[1-9]\\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00)-02-29)T(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d(?:\\.\\d{1,9})?(?:Z|[+-][01]\\d:[0-5]\\d)$" 
			, message = "Task completed datetime should be formatted as an ISO8601 string")
    private String completed;

    @Size(min = 5, max = 254, message = "User's email must be between 5 and 254 characters")
    private String user;

    @NotNull(message = "Description of the task is a required field")
    @Size(min = 1, message = "Description of the task should not be less than 1 character")
    private String description;
    
    @NotNull(message = "Task priority is a required field")
    @Min(value = 0, message = "Task priority cannot be less than 0")
    @Max(value = 9, message = "Task priority cannot be greater than 9")
    private Long priority;

    public TaskDto() { }

	public TaskDto(String completed, String user, String description, Long priority) {
		super();
		this.completed = completed;
		this.user = user;
		this.description = description;
		this.priority = priority;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}
	
	public Task toDomain() throws Exception {
		Task task = new Task(DateUtil.stringToDate(this.completed), this.user, this.description, this.priority);
		return task;
	}
}
