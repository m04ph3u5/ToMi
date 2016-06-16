package it.polito.applied.ToMi.repository;

import java.util.Date;
import java.util.List;

import it.polito.applied.ToMi.pojo.DayPassengerBusRun;
import it.polito.applied.ToMi.pojo.Travel;

public interface CustomTravelRepository {
	
	List<DayPassengerBusRun> countToMiDailyBusTravel(String passengerId);
	List<DayPassengerBusRun> countMiToDailyBusTravel(String passengerId);
	List<Travel> findMyBusTravelInDay(Date start, Date end, String passengerId);
	List<Travel> findMyBusTravel(String id);


}
