package it.polito.applied.ToMi.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class BusTravel {
	
	@Id
	private String id; 
	private String beaconId;
	//TRUE TOMI, FALSE MITO
	private boolean direction;
	private String idRun;
	private Date timestamp;
	private String passengerId;
	private String day;
	private List<BusStop> stops;
	
	public String getId(){
		return id;
	}
	public String getBeaconId() {
		return beaconId;
	}
	public void setBeaconId(String beaconId) {
		this.beaconId = beaconId;
	}
	public boolean isDirection() {
		return direction;
	}
	public void setDirection(boolean direction) {
		this.direction = direction;
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
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<BusStop> getStops() {
		return stops;
	}
	public void setStops(List<BusStop> stops) {
		this.stops = stops;
	}
	
	

}
