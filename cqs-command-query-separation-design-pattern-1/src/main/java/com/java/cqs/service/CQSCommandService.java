package com.java.cqs.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CQSCommandService {

	private final CQSService cqsService;

	public void updateGreeting(String newGreeting) {
		cqsService.setGREETING(newGreeting);
	}
}
