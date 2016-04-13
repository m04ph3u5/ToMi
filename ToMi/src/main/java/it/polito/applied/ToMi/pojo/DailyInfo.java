package it.polito.applied.ToMi.pojo;

import java.util.Date;

public class DailyInfo {
	
	private Date time;
	private int passengers;
	private int runs;
	private boolean myRoute;
	
	public DailyInfo(){
		
	}
	
	public DailyInfo(Date time, int passengers, int runs, boolean myRoute){
		super();
		this.time = time;
		this.passengers = passengers;
		this.runs = runs;
		this.myRoute = myRoute;
	}
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getPassengers() {
		return passengers;
	}
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	public int getRuns() {
		return runs;
	}
	public void setRuns(int runs) {
		this.runs = runs;
	}
	public boolean isMyRoute() {
		return myRoute;
	}
	public void setMyRoute(boolean myRoute) {
		this.myRoute = myRoute;
	}
	
	

}
