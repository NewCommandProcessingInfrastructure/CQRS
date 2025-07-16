package com.java.cqrs.service;

import com.java.cqrs.model.UpdateFirstName;
import com.java.cqrs.model.UserRecord;

public interface UserEntityCommand {

	public void handle(UpdateFirstName updateFirstName) ;
	public void addUserRecord(UserRecord userRecord);
}
