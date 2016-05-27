package it.polito.applied.ToMi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import it.polito.applied.ToMi.pojo.DayPassengerBusRun;
import it.polito.applied.ToMi.pojo.Travel;

public class TravelRepositoryImpl implements CustomTravelRepository{
	
	@Autowired
	private MongoOperations mongoOp;

	@Override
	public List<DayPassengerBusRun> countToMiDailyBusTravel(String passengerId) {

		Criteria c = Criteria.where("passengerId").is(passengerId)
				.andOperator(Criteria.where("isOnBus").is(true)
				.andOperator(Criteria.where("partials.direction").is(true)));
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.group("dayTimestamp").count().as("count"),
				Aggregation.project("count").and("day").previousOperation(),
				Aggregation.sort(new Sort(Direction.ASC, "day")));
		
		AggregationResults<DayPassengerBusRun> result = mongoOp.aggregate(agg, Travel.class, DayPassengerBusRun.class);
		return result.getMappedResults();
	}

	@Override
	public List<DayPassengerBusRun> countMiToDailyBusTravel(String passengerId) {
		Criteria c = Criteria.where("passengerId").is(passengerId)
				.andOperator(Criteria.where("isOnBus").is(true)
				.andOperator(Criteria.where("partials.direction").is(false)));
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.group("dayTimestamp").count().as("count"),
				Aggregation.project("count").and("day").previousOperation(),
				Aggregation.sort(new Sort(Direction.ASC, "day")));
		
		AggregationResults<DayPassengerBusRun> result = mongoOp.aggregate(agg, Travel.class, DayPassengerBusRun.class);
		return result.getMappedResults();
	}

	@Override
	public List<Travel> findMyBusTravelInDay(Date start, Date end, String passengerId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("isOnBus").is(true)
				.andOperator(Criteria.where("passengerId").is(passengerId)
				.andOperator(Criteria.where("start").gte(start)
				.andOperator(Criteria.where("end").lte(end)))));
		
		return mongoOp.find(q, Travel.class);
	}

	

}
