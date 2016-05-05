package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.BusTravel;

public interface BusTravelRepository extends MongoRepository<BusTravel, String>{

}
