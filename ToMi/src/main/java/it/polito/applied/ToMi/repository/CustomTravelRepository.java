package it.polito.applied.ToMi.repository;

import it.polito.applied.ToMi.pojo.DailyData;

public interface CustomTravelRepository {
	
	public DailyData getDailyData(String passengerId);


}
