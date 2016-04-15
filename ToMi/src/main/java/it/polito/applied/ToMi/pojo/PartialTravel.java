package it.polito.applied.ToMi.pojo;

import java.util.Date;
import java.util.List;

public class PartialTravel {

	private String id;
	private int mode;
	private int beaconId;
	private Date start;
	private Date end;
	private List<DetectedPosition> allPositions;
	private double lengthTravel;
	private double lengthAccuracy;
	
	public PartialTravel(){
		super();
	}
	

	public PartialTravel(String id, int mode, int beaconId, Date start, Date end, List<DetectedPosition> allPositions) {
		super();
		this.id = id;
		this.mode = mode;
		this.beaconId = beaconId;
		
		this.start = start;
		this.end = end;
		this.allPositions = allPositions;
	}

	public List<DetectedPosition> getAllPositions() {
		return allPositions;
	}

	public void setAllPositions(List<DetectedPosition> allPositions) {
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


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getBeaconId() {
		return beaconId;
	}
	public void setBeaconId(int beaconId) {
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
	
	
	
}
