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
	private double lengthTravel;
	private double lengthAccuracy;
	
	public PartialTravel(){
		super();
		allPositions = new ArrayList<InfoPosition>();
	}
	

	public PartialTravel(int mode, String beaconId, Date start, Date end, List<InfoPosition> allPositions) {
		this();
		this.mode = mode;
		this.beaconId = beaconId;
		
		this.start = start;
		this.end = end;
		this.allPositions = allPositions;
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
	
}
