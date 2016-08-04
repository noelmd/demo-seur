package com.seur.test.security;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemUserRepository extends MongoRepository <SystemUser, String> 
{	  
	  public SystemUser findByUsername(String username);
}
