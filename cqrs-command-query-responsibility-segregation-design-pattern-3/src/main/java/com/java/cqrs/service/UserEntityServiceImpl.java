package com.java.cqrs.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.java.cqrs.entity.UserEntity;
import com.java.cqrs.model.UserRecord;
import com.java.cqrs.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

	private final UserEntityRepository userEntityRepository;

	@Override
	public List<UserRecord> getUserRecordList() {
		List<UserEntity> userEntityList = userEntityRepository.findAll();
		List<UserRecord> userRecordList = new ArrayList<>();

		for (UserEntity element : userEntityList) {
			UserRecord userRecord = new UserRecord(element.getFirstName(), element.getLastName(),
					element.getAge(), element.getMobileNo(), element.getAddress());
			userRecordList.add(userRecord);
		}

		return userRecordList;
	}

}
