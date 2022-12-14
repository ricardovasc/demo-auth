package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtUtils;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.AuthenticationRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserDao userDao;
	
	private final JwtUtils jwtUtils;
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> auth(@RequestBody AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		final UserDetails user = userDao.findUserByEmail(request.getEmail());
		
		if (user != null) {
			return ResponseEntity.ok(jwtUtils.generateToken(user));
		}
		
		return ResponseEntity.status(400).body("Authotization error");
	}

}
