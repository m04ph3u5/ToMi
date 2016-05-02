package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.TemporaryTravel;

public interface TemporaryTravelRepository extends MongoRepository<TemporaryTravel, String>{

	public TemporaryTravel findByPassengerIdAndDeviceId(String passengerId, String deviceId);
}
