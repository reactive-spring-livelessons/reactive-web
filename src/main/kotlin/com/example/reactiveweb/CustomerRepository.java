package com.example.reactiveweb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public interface CustomerRepository
	extends ReactiveMongoRepository<Customer, String> {
}
