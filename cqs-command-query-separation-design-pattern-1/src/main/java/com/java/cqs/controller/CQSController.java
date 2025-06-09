package com.java.cqs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java.cqs.model.GreetingRequest;
import com.java.cqs.service.CQSCommandService;
import com.java.cqs.service.CQSService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class CQSController {

	private final CQSService cqsService;
	private final CQSCommandService cqsCommandService;

	@GetMapping(path = "/get-greeting")
  public String getGreeting() {
      return cqsService.getGREETING();
  }

  @PostMapping(path = "/set-greeting")
  public void updateGreeting(@RequestBody GreetingRequest request) {
  	cqsCommandService.updateGreeting(request.greeting());
  }
}
