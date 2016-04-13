package it.polito.applied.ToMi.pojo;

public class Stop {

	private String idStop;
	private int passengers;
	private boolean myRoute;
	
	public Stop(){
		
	}
	
	public Stop(String idStop, int passengers, boolean myRoute) {
		super();
		this.idStop = idStop;
		this.passengers = passengers;
		this.myRoute = myRoute;
	}
	
	public String getIdStop() {
		return idStop;
	}
	public void setIdStop(String idStop) {
		this.idStop = idStop;
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
