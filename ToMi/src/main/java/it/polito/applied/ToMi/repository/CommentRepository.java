package it.polito.applied.ToMi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.ToMi.pojo.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>, CustomCommentRepository{

}
