package com.amazonaws.lambda.task.domain;


import java.io.Serializable;

import com.amazonaws.lambda.task.pojo.TaskDto;
import com.amazonaws.lambda.task.util.DateUtil;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName = "Task")
public class Task implements Serializable {

    private static final long serialVersionUID = -8243145429438016232L;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;
    
    @DynamoDBRangeKey
    private String user;
    
    @DynamoDBAttribute
    private Long completedDate;

    @DynamoDBAttribute
    private String description;
    
    @DynamoDBAttribute
    private Long priority;

    public Task() { }

	public Task(String id, Long completedDate, String user, String description, Long priority) {
		super();
		this.id = id;
		this.completedDate = completedDate;
		this.user = user;
		this.description = description;
		this.priority = priority;
	}
	
	public Task(Long completedDate, String user, String description, Long priority) {
		super();
		this.id = null;
		this.completedDate = completedDate;
		this.user = user;
		this.description = description;
		this.priority = priority;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (completedDate == null) {
			if (other.completedDate != null)
				return false;
		} else if (!completedDate.equals(other.completedDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	public boolean equalsWithoutCompletedDate(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public Long getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Long completedDate) {
		this.completedDate = completedDate;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TaskDto toDto() {
		TaskDto taskDto = new TaskDto(DateUtil.dateToString(this.completedDate), this.user, this.description, this.priority);  
		return taskDto;
	}
}