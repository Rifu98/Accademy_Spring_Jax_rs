package com.federicorifugiato.dao;

import org.springframework.data.repository.CrudRepository;

import com.federicorifugiato.model.User;

public interface UserDao extends CrudRepository<User, Integer> {

	boolean existsByMail(String mail);
}
