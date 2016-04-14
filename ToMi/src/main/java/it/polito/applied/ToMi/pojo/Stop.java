package it.polito.applied.ToMi.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Stop {

	@Id
	private String id;
	private int passengers;
	private boolean myRoute;
	
	public Stop(){
		
	}
	
	public Stop(String id, int passengers, boolean myRoute) {
		super();
		this.id = id;
		this.passengers = passengers;
		this.myRoute = myRoute;
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	public int getPassengers() {
		return passengers;
	}
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	public boolean isMyRoute() {
		return myRoute;
	}
	public void setMyRoute(boolean myRoute) {
		this.myRoute = myRoute;
	}
	
	
}
