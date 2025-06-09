package com.java.cqrs.service;

import java.util.List;
import com.java.cqrs.model.UserRecord;

public interface UserEntityService {
	public List<UserRecord> getUserRecordList();

}
