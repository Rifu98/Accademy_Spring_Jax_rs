package com.federicorifugiato.service;

import java.util.List;

import com.federicorifugiato.exception.InvalidPasswordException;
import com.federicorifugiato.exception.UserNotFoundException;
import com.federicorifugiato.model.User;

public interface UserService {

	void registerUtente(User user) throws InvalidPasswordException;
	void updateUtente(User user);
	User getUserById(int id) throws UserNotFoundException;
	List<User> getUsers();
	void deleteUser(int id) throws UserNotFoundException;
	public boolean existsUserByMail (String mail);
}