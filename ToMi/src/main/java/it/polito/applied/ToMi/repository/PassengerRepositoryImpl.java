package it.polito.applied.ToMi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import it.polito.applied.ToMi.pojo.Passenger;

public class PassengerRepositoryImpl implements CustomPassengerRepository{
	
	@Autowired
	private MongoOperations mongoOp;

	@Override
	public void updatePassenger(Passenger p) {
		Query q = new Query();
		q.addCriteria(Criteria.where("email").is(p.getEmail()));
		Update u = new Update();
		if(p.getUsername()!=null && !p.getUsername().isEmpty())
			u.set("username", p.getUsername());
		if(p.getAge()>0)
			u.set("age", p.getAge());
		if(p.getProfession()!=null && !p.getProfession().isEmpty())
			u.set("profession", p.getProfession());
		if(p.getPhoto()!=null && !p.getPhoto().isEmpty())
			u.set("photo", p.getPhoto());
		if(p.getGender()!=null && !p.getGender().isEmpty())
			u.set("gender", p.getGender());
		
		mongoOp.updateFirst(q, u, Passenger.class);
	}

}
