package it.polito.applied.ToMi.pojo;

public class DailyInfo {
	
	private long timestamp;
	private int passengers;
	private int runs;
	private boolean myRoute;
	
	public DailyInfo(){
		
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
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
