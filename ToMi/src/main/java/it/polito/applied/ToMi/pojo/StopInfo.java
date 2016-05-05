package it.polito.applied.ToMi.pojo;

import java.util.Date;

public class StopInfo {

	private String busStopId;
	private String busStopName;
	private Date date;
	private int numPassengerGetIn;
	private int numPassengerGetOut;
	
	public String getBusStopId() {
		return busStopId;
	}
	public void setBusStopId(String busStopId) {
		this.busStopId = busStopId;
	}
	public String getBusStopName() {
		return busStopName;
	}
	public void setBusStopName(String busStopName) {
		this.busStopName = busStopName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getNumPassengerGetIn() {
		return numPassengerGetIn;
	}
	public void setNumPassengerGetIn(int numPassengerGetIn) {
		this.numPassengerGetIn = numPassengerGetIn;
	}
	public int getNumPassengerGetOut() {
		return numPassengerGetOut;
	}
	public void setNumPassengerGetOut(int numPassengerGetOut) {
		this.numPassengerGetOut = numPassengerGetOut;
	}
	
	
}
