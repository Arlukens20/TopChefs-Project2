package com.revature.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.models.User;
import com.revature.services.IUserService;
import com.revature.services.PasswordHashingService;
import com.revature.services.UserService;
import com.revature.templates.LoginTemplate;
import com.revature.templates.RegisterTemplate;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/login")
public class UserController {

	@Autowired
	private UserService service;
	
//	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "login", produces = "application/json")
	@ResponseBody
	public ResponseEntity<User> loginController(@RequestBody LoginTemplate lf) {
		User u = service.login(lf);
		if(u != null) {
			return ResponseEntity.ok(u);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
				
	}
	
//	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(path = "newUser")
	public ResponseEntity<RegisterTemplate> newUser(@RequestBody RegisterTemplate u) {
		PasswordHashingService pw = new PasswordHashingService();
		u.setPassword(pw.passwordHash(u.getPassword()));
		if(service.save(u) == true) {
			System.out.println(u.toString());
			return ResponseEntity.ok(u);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
				
	}
	
//	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping(path = "deleteUser")
	public ResponseEntity delUser(@RequestBody User u) {
		System.out.println("deleting...");
		if(service.delete(u) == true) {
			return ResponseEntity.accepted().build();
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
				
	}

//	@CrossOrigin(origins = "http://localhost:4200")
	@PatchMapping(path = "updateUser")
	public ResponseEntity updateUser(@RequestBody User u) {
		if(service.update(u) == true) {
			return ResponseEntity.accepted().build();
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
				
	}
	
//	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path = "users")
	public ResponseEntity<Set<User>> users(){
		Set<User> list = service.findAll();
		return ResponseEntity.ok(list);
	}
	
}
