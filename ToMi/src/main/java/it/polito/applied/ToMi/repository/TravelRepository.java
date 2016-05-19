package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.Travel;

public interface TravelRepository extends MongoRepository<Travel, String>, CustomTravelRepository{


}
