package com.java.cqrs.service;

import org.springframework.stereotype.Service;
import com.java.cqrs.entity.UserEntity;
import com.java.cqrs.model.UpdateFirstName;
import com.java.cqrs.model.UserRecord;
import com.java.cqrs.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEntityCommandImpl implements UserEntityCommand {
	
	private final UserEntityRepository userEntityRepository;

	@Override
	public void handle(UpdateFirstName updateFirstName) {
		UserEntity user = userEntityRepository.findById(updateFirstName.id())
				.orElseThrow(() -> new RuntimeException("UserName Not Found!"));

		user.setFirstName(updateFirstName.firstName());
		userEntityRepository.save(user);
	}
	
	@Override
	public void addUserRecord(UserRecord userRecord) {
		UserEntity user = UserEntity.builder()
				.firstName(userRecord.firstName())
				.lastName(userRecord.lastName())
				.age(userRecord.age())
				.mobileNo(userRecord.mobileNo())
				.address(userRecord.address())
				.build();

		userEntityRepository.save(user);

	}
}
