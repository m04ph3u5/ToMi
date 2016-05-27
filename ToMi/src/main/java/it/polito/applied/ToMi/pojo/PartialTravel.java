package it.polito.applied.ToMi.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartialTravel {

	private int mode;
	private String beaconId;
	private Date start;
	private Date end;
	private List<InfoPosition> allPositions;
	private List<String> busStopsId;
	private double lengthTravel;
	private double lengthAccuracy;
	private String idRun;
	//TRUE TOMI, FALSE MITO. E' ridondante come info ma serve per reperire più velocemente la direzione
	private boolean direction;
	
	public PartialTravel(){
		super();
		allPositions = new ArrayList<InfoPosition>();
		busStopsId = new ArrayList<String>();
	}
	

	public PartialTravel(int mode, String beaconId, Date start, Date end, List<InfoPosition> allPositions) {
		this();
		this.mode = mode;
		this.beaconId = beaconId;
		this.start = start;
		this.end = end;
		this.allPositions = allPositions;
	}

	public String getIdRun() {
		return idRun;
	}

	public void setIdRun(String idRun) {
		this.idRun = idRun;
	}

	public List<InfoPosition> getAllPositions() {
		return allPositions;
	}

	public void setAllPositions(List<InfoPosition> allPositions) {
		this.allPositions = allPositions;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public String getBeaconId() {
		return beaconId;
	}
	public void setBeaconId(String beaconId) {
		this.beaconId = beaconId;
	}

	public double getLengthTravel() {
		return lengthTravel;
	}

	public void setLengthTravel(double lengthTravel) {
		this.lengthTravel = lengthTravel;
	}

	public double getLengthAccuracy() {
		return lengthAccuracy;
	}

	public void setLengthAccuracy(double lengthAccuracy) {
		this.lengthAccuracy = lengthAccuracy;
	}
	
	public void addInfoPosition(InfoPosition i){
		allPositions.add(i);
	}

	public boolean isDirection() {
		return direction;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	public List<String> getBusStopsId() {
		return busStopsId;
	}

	public void setBusStopsId(List<String> busStopsId) {
		this.busStopsId = busStopsId;
	}
	
	public void addBusStopId(String busStopId){
		busStopsId.add(busStopId);
	}
	
}
