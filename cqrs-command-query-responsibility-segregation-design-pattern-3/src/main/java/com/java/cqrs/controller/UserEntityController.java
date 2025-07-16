package com.java.cqrs.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java.cqrs.model.UpdateFirstName;
import com.java.cqrs.model.UserRecord;
import com.java.cqrs.service.UserEntityCommand;
import com.java.cqrs.service.UserEntityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UserEntityController {

	private final UserEntityService userEntityService;
	private final UserEntityCommand userEntityCommandService;
	
	@GetMapping(path = "/get-user-list")
	public ResponseEntity<List<UserRecord>> getUserList() {
		List<UserRecord> userRecordList = userEntityService.getUserRecordList();
		return ResponseEntity.status(HttpStatus.OK).body(userRecordList);
	}
	
	@PostMapping(path = "/create-new-user")
	public ResponseEntity<String> createNewUserRecord(@RequestBody UserRecord userRecord) {
		userEntityCommandService.addUserRecord(userRecord);
		return ResponseEntity.status(HttpStatus.CREATED).body("User Record Created successfully");
	}
	
	@PatchMapping(path = "/{id}/firstName")
	public ResponseEntity<String> updateUserFirstName(@PathVariable Long id, @RequestBody Map<String, String> payload) {
		String newFirstName = payload.get("firstName");
		userEntityCommandService.handle(new UpdateFirstName(id, newFirstName));
		return ResponseEntity.status(HttpStatus.OK).body("User FirstName Updated!");
	}

}
