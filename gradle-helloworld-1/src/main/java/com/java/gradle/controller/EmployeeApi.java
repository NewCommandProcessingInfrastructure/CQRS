package com.java.gradle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeApi {

	@GetMapping(path = "/{empId}")
	public String getEmployee(@PathVariable String empId) {
		return "Employee Found";
	}
}
