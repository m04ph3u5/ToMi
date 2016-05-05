package it.polito.applied.ToMi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import it.polito.applied.ToMi.pojo.DailyData;

public class TravelRepositoryImpl implements CustomTravelRepository{
	
	@Autowired
	private MongoOperations mongoOp;

	@Override
	public DailyData getDailyData(String passengerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
