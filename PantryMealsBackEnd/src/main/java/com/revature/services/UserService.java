package com.revature.services;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.IUserDAO;
import com.revature.templates.LoginTemplate;
import com.revature.templates.RegisterTemplate;

@Service
public class UserService {

	@Autowired
	private IUserDAO userDao;
	
	private Logger log = Logger.getLogger(UserService.class);
	
	
	public UserService() {
		super();
	}
	
	

	public UserService(IUserDAO userDao) {
		super();
		this.userDao = userDao;
	}



	//private PasswordHashingService pw;
	public User login(LoginTemplate lf) {
		System.out.println(userDao.findAll());
		User u = userDao.findByUsername(lf.getUsername());
		if(u == null) {
			log.info("couldn't find username");
			return null;
		}
		//if(pw.passwordHash(u.getPassword()).equals(pw.passwordHash(lf.getPassword()))
		if(u.getPassword().equals(lf.getPassword())) {
			log.info("Logged in a user");
			return u;
		}
		log.info("Loggin attemp failed");
		return null;
	}

	public boolean save(RegisterTemplate rt){
		User u = new User(0, rt.getUsername(), rt.getPassword(), rt.getFirstname(), rt.getLastname(), rt.getEmail());
		
		return userDao.save(u);
	}
	
	public boolean update(User u) {
		return userDao.update(u);
	}
	
	public boolean delete(User u) {
		return userDao.delete(u);
	}

	public Set<User> findAll() {
		return userDao.findAll();
	}

	public User findById(int id) {
		return userDao.findById(id);
	}
}
