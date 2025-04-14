package com.contact.receiver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.contact.receiver.entity.AppUser;
import com.contact.receiver.entity.Role;
import com.contact.receiver.permissions.RoleEnum;
import com.contact.receiver.service.UserService;

@SpringBootApplication
public class ReceiverApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ReceiverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Role> roles = new ArrayList<>();
		
		Role roleAdmin = new Role();
		roleAdmin.setName(RoleEnum.ADMIN);
		
		Role roleUser = new Role();
		roleUser.setName(RoleEnum.USER);
		
		roles.add(roleAdmin);
		roles.add(roleUser);
		
		AppUser user = new AppUser();
		user.setUsername("admin");
		user.setPassword("password");
		user.setRoles(roles);

		userService.saveUser(user);
		 

	}

	

}
