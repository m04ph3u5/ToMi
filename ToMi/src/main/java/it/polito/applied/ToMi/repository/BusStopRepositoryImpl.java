package it.polito.applied.ToMi.repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.InfoPosition;

public class BusStopRepositoryImpl implements CustomBusStopRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	private final int SEARCH_RADIUS=1;
//	private final int MAX_NUM_RESULTS=2;
	private final int TWO_MINUTES = 120000;
	private final int ONE_HOUR = 3600000;
	
	@Override
	public GeoResults<BusStop> findNear(InfoPosition infoPosition, String idLine) {
		
		NearQuery nq = NearQuery.near(infoPosition.getPosition().getLat(), infoPosition.getPosition().getLng())
				.maxDistance(new Distance(SEARCH_RADIUS, Metrics.KILOMETERS));	
//		nq.num(MAX_NUM_RESULTS);
		
		long pTime = infoPosition.getHour();
		Query q = new Query();
		q.addCriteria(Criteria.where("idLine").is(idLine)
				.andOperator(Criteria.where("time").lt(pTime+TWO_MINUTES)
				.andOperator(Criteria.where("time").gt(pTime-ONE_HOUR))));
		
		nq.query(q);
		
		return mongoOp.geoNear(nq, BusStop.class);
	}

	@Override
	public Collection<? extends BusStop> findBetweenFirstAndLast(BusStop first, BusStop last) {
		Query q = new Query();
		q.addCriteria(Criteria.where("idRun").is(first.getIdRun())
				.andOperator(Criteria.where("idProg").gt(first.getIdProg())
				.andOperator(Criteria.where("idProg").lt(last.getIdProg()))));
		q.with(new Sort(Direction.ASC,"idProg"));
		
		return mongoOp.find(q, BusStop.class);
	}

	@Override
	public List<BusStop> findAllSortByIdLineAndIdRunAndIdProg() {
		Query q = new Query();
		q.with(new Sort(Direction.ASC, Arrays.asList("idLine","idRun","idProg")));
		
		return mongoOp.find(q, BusStop.class);
	}

}
