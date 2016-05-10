package it.polito.applied.ToMi.pojo;

public class StopInfo {

	private String busStopId;
	private String busStopName;
	private long time;
	private int numPassengerGetIn;
	private int numPassengerGetOut;
	
	public StopInfo(){
		
	}
	
	public StopInfo(BusStop s){
		this();
		busStopId = s.getIdStop();
		busStopName = s.getName();
		time = s.getTime();
	}
	
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
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
