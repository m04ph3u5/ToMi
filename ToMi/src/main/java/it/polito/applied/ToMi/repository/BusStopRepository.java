package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.BusStop;

public interface BusStopRepository extends MongoRepository<BusStop, String>{

}


