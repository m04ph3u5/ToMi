package it.polito.applied.ToMi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.Run;

public interface RunRepository extends MongoRepository<Run, String>, CustomRunRepository{

	public Run findByIdRunAndIdLineAndDay(String idRun, String idLine, String day);

	public List<Run> findByTimestamp(long time);

}
