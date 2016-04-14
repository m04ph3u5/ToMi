package it.polito.applied.ToMi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import it.polito.applied.ToMi.pojo.DetectedPosition;

public class DetectedPositionRepositoryImpl implements CustomDetectedPositionRepository{

	@Autowired
	MongoOperations mongoOp;
	
	@Override
	public List<DetectedPosition> getMyPositions(String email, long start, long end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("timestamp").gte(start).
						andOperator(Criteria.where("timestamp").lte(end)).
						andOperator(Criteria.where("userEmail").is(email)));
		return mongoOp.find(q, DetectedPosition.class);
	}

}
