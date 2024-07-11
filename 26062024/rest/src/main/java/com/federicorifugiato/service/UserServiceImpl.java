package com.federicorifugiato.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.federicorifugiato.dao.UserDao;
import com.federicorifugiato.exception.InvalidPasswordException;
import com.federicorifugiato.exception.UserNotFoundException;
import com.federicorifugiato.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
    public void registerUtente(User user) throws InvalidPasswordException {
		
        if (!isValidPassword(user.getPassword())) {
            throw new InvalidPasswordException("Password non valida");
        }

        String hashedPass = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(hashedPass);
        userDao.save(user);
    }

    private boolean isValidPassword(String password) {
        return password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}");
    }

	@Override
	public void updateUtente(User user) {
		
		Optional<User> optionalUser = userDao.findById(user.getId());
		
		if(optionalUser.isPresent()) {
			
			User newUser = optionalUser.get();
			
			newUser.setLastname(user.getLastname());
			newUser.setName(user.getName());
			newUser.setMail(user.getMail());
			newUser.setPassword(user.getPassword());
			
			userDao.save(newUser);
			
		}
		
	}

	@Override
	public User getUserById(int id) throws UserNotFoundException {
		
		Optional<User> userOptiion = userDao.findById(id);
		
		if (userOptiion.isEmpty()) {
			
			throw new UserNotFoundException("User not found");
			
		}
		
		return userOptiion.get();
	}

	@Override
	public List<User> getUsers() {
		return (List<User>) userDao.findAll();
	}

	@Override
	public void deleteUser(int id) throws UserNotFoundException {
		
		Optional<User> userOptiion = userDao.findById(id);
		
		if (userOptiion.isEmpty()) {
			
			throw new UserNotFoundException("User not found");
			
		}
		
		userDao.delete(userOptiion.get());
		
	}

	@Override
	public boolean existsUserByMail(String mail) {
		
		return userDao.existsByMail(mail);
	}
	

}
