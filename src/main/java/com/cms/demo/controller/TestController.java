package com.cms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cms.demo.dto.UserResponse;
import com.cms.demo.service.UserService;

@RestController
public class TestController {
	
	@Autowired
	private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Authenticated Hello!!!!!!!!!!!!!!!!!!!!!!!";
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserResponse response){
    	String token = userService.login(response);
    	if(token == null) {
    		return new ResponseEntity<>("invalid",HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity<>(token, HttpStatus.OK);
    	
    }
   
}
