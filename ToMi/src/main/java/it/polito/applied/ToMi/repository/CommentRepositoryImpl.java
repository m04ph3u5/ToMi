package it.polito.applied.ToMi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import it.polito.applied.ToMi.pojo.Comment;

public class CommentRepositoryImpl implements CustomCommentRepository{

	
	@Autowired
	private MongoOperations mongoOp;
	
	private final int numCommentPerRequest=20;

	@Override
	public List<Comment> getLastComments() {
		Query q = new Query();
		Sort sort = new Sort(Sort.Direction.DESC,"_id");
		q.with(sort);
		q.limit(numCommentPerRequest);
		return mongoOp.find(q, Comment.class);
	}

	@Override
	public List<Comment> getCommentsAfterId(String lastId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("_id").gt(lastId));
		Sort sort = new Sort(Sort.Direction.DESC,"_id");
		q.with(sort);
		q.limit(numCommentPerRequest);
		return mongoOp.find(q, Comment.class);
	}
	
	
}
