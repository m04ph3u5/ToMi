package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.User;

public interface UserRepository extends MongoRepository<User, String>{

	public User findById(String id);
	public User findByUsername(String username);
	
	
}
