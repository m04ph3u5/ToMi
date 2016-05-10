package it.polito.applied.ToMi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.Run;

public interface RunRepository extends MongoRepository<Run, String>, CustomRunRepository{

	public Run findByIdRunAndIdLineAndDay(String idRun, String idLine, String day);

}
