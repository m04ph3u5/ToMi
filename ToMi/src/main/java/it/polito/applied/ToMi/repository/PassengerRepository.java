package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.Passenger;

public interface PassengerRepository extends MongoRepository<Passenger, String>, CustomPassengerRepository{

	public Passenger findByEmail(String email);
}
