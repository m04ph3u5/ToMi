package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.PathWithTime;

public interface PathWithTimeRepository extends MongoRepository<PathWithTime, String>{

}
