package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.Path;

public interface PathRepository extends MongoRepository<Path, String>{

}
