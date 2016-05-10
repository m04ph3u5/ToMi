package it.polito.applied.ToMi.pojo;

public class DailyInfo {
	
	private String day;
	private int passengers;
	private int runs;
	private boolean myRoute;
	
	public DailyInfo(){
		
	}
	
	public DailyInfo(String day, int passengers, int runs, boolean myRoute){
		super();
		this.day = day;
		this.passengers = passengers;
		this.runs = runs;
		this.myRoute = myRoute;
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	

}
