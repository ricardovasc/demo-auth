package com.example.demo.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	
	private final static List<UserDetails> APP_USERS = Arrays.asList(
			new User("ricardo@mail.com", "password", Collections.singleton(new SimpleGrantedAuthority("Admin"))));
	
	public UserDetails findUserByEmail(String email) {
		return APP_USERS.stream().filter(u -> u.getUsername().equals(email)).findFirst()
				.orElseThrow(() -> new UsernameNotFoundException(email));
	}

}
