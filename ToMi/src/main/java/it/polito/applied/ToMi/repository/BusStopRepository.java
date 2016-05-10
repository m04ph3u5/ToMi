package it.polito.applied.ToMi.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.BusStop;

public interface BusStopRepository extends MongoRepository<BusStop, String>, CustomBusStopRepository{

	public List<BusStop> findByIdRun(String idRun, Sort sort);


}


