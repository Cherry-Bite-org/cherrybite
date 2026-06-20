package com.cherrybite.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.User;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getCurrentUser() {

	    UUID userId = (UUID) SecurityContextHolder
	            .getContext()
	            .getAuthentication()
	            .getPrincipal();

	    return userRepository.findById(userId)
	            .orElseThrow(() ->
	                    new UserException("User not found"));
	}

	@Override
	public Optional<User> getUserByEmail(String email) {

		Optional<User> user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("User not found");
		}
		return user;
	}

	@Override
	public User getUserById(UUID userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
