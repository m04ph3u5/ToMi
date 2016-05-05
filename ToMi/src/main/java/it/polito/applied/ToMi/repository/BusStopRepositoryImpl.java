package it.polito.applied.ToMi.repository;

import java.util.Collection;

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
	
	private final float SEARCH_RADIUS=0.5f;
	private final int MAX_NUM_RESULTS=2;
	
	@Override
	public GeoResults<BusStop> findNear(InfoPosition infoPosition) {
		NearQuery q = NearQuery.near(infoPosition.getPosition().getLat(), infoPosition.getPosition().getLng())
				.maxDistance(new Distance(SEARCH_RADIUS, Metrics.KILOMETERS));	
		q.num(MAX_NUM_RESULTS);
		return mongoOp.geoNear(q, BusStop.class);
	}

	@Override
	public Collection<? extends BusStop> findBetweenFirstAndLast(BusStop first, BusStop last) {
		Query q = new Query();
		q.addCriteria(Criteria.where("idProg").gt(first.getIdProg())
				.andOperator(Criteria.where("idProg").lt(last.getIdProg())));
		q.with(new Sort(Direction.ASC,"idProg"));
		
		return mongoOp.find(q, BusStop.class);
	}

}
