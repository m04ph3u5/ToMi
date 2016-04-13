package it.polito.applied.ToMi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.DetectedPosition;

public interface DetectedPositionRepository extends MongoRepository<DetectedPosition, String>{

	public List<DetectedPosition> findByUserEmail(String userEmail);
}
