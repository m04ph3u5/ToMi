package it.polito.applied.ToMi.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {

	@Id
	private String id;
	private String userEmail;
	private String author;
	private String title;
	private String body;
	private boolean isToMi;
	private Date timestamp;
	private String date;
	private String time;
	private String category;
	
	private List<Comment> answers;
	
	public void addAnswers(List<Comment> answers){
		if(this.answers!=null)
			this.answers.addAll(answers);
		else
			this.answers = answers;
	}
	
	public List<Comment> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Comment> answers) {
		this.answers = answers;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public boolean isToMi() {
		return isToMi;
	}
	public void setToMi(boolean isToMi) {
		this.isToMi = isToMi;
	}
}
