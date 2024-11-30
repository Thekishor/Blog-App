package com.BlogApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogApp.Entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
}
