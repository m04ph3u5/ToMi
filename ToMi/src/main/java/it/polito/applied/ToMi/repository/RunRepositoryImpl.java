package it.polito.applied.ToMi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.DailyInfo;
import it.polito.applied.ToMi.pojo.Run;

public class RunRepositoryImpl implements CustomRunRepository{
	
	@Autowired
	private MongoOperations mongoOp;

	@Override
	public void updateRun(String idRun, String idLine, String day, BusStop getIn, BusStop getOut) {
		Query q = new Query();
		q.addCriteria(Criteria.where("idRun").is(idRun)
				.andOperator(Criteria.where("idLine").is(idLine)
						.andOperator(Criteria.where("day").is(day)
								.andOperator(Criteria.where("stops.busStopId").is(getIn.getIdStop())))));
		
		Update u = new Update();
		u.inc("totPassenger", 1);
		u.inc("stops.$.numPassengerGetIn", 1);
		
		mongoOp.updateFirst(q, u, Run.class);
		
		Query q2 = new Query();
		q2.addCriteria(Criteria.where("idRun").is(idRun)
				.andOperator(Criteria.where("idLine").is(idLine)
						.andOperator(Criteria.where("day").is(day)
								.andOperator(Criteria.where("stops.busStopId").is(getOut.getIdStop())))));
		Update u2 = new Update();
		u2.inc("stops.$.numPassengerGetOut", 1);

		mongoOp.updateFirst(q2, u2, Run.class);
		
	}

	@Override
	public List<DailyInfo> getDailyInfoTomi() {
		Criteria c = new Criteria();
		c = Criteria.where("direction").is(true);
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.group("day").count().as("runs").sum("totPassenger").as("passengers"),
				Aggregation.project("runs", "passengers").and("day").previousOperation());
		AggregationResults result = mongoOp.aggregate(agg, Run.class, DailyInfo.class);
		
		List<DailyInfo> i = result.getMappedResults();
		return i;
	}

	@Override
	public List<DailyInfo> getDailyInfoMito() {
		Criteria c = new Criteria();
		c = Criteria.where("direction").is(false);
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.group("day").count().as("runs").sum("totPassenger").as("passengers"),
				Aggregation.project("runs", "passengers").and("day").previousOperation());

		AggregationResults result = mongoOp.aggregate(agg, Run.class, DailyInfo.class);
		
		List<DailyInfo> i = result.getMappedResults();
		return i;
	}

}
