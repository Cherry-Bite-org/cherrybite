package com.cherrybite.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.cherrybite.entity.User;

public interface UserService {

	User getCurrentUser();

	Optional<User> getUserByEmail(String email);

	User getUserById(UUID userId);

	List<User> getAllUsers();

}
