package it.polito.applied.ToMi.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RunDetail {

	private String idRun;
	private Date timestamp;
	private int passengers;
	private List<Stop> stops;
	
	public RunDetail(){
		stops = new ArrayList<Stop>();
	}
	
	public RunDetail(String idRun, Date timestamp, int passengers){
		this();
		this.idRun = idRun;
		this.timestamp = timestamp;
		this.passengers = passengers;
	}
	
	public void addStop(Stop s){
		stops.add(s);
	}
	
	public String getIdRun() {
		return idRun;
	}
	public void setIdRun(String idRun) {
		this.idRun = idRun;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getPassengers() {
		return passengers;
	}
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	public List<Stop> getStops() {
		return stops;
	}
	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}
	
	
	
}
