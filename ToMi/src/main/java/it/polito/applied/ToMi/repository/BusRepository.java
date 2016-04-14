package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.Bus;

public interface BusRepository extends MongoRepository<Bus, String> {

}
