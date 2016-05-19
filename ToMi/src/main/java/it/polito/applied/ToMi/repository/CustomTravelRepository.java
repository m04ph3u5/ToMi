package it.polito.applied.ToMi.repository;

import java.util.List;

import it.polito.applied.ToMi.pojo.DayPassengerBusRun;

public interface CustomTravelRepository {
	
	List<DayPassengerBusRun> countToMiDailyBusTravel(String passengerId);
	List<DayPassengerBusRun> countMiToDailyBusTravel(String passengerId);


}
