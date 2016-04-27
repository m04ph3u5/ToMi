package it.polito.applied.ToMi.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Travel {
	@Id
	private String id;
	private boolean isOnBus;
	private String passengerId;
	private double lengthTravel;
	private double lengthAccuracy;
	private Date start;
	private Date end;
	private List<DetectedPosition> positions;
	
	
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
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
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getId() {
		return id;
	}
	public List<DetectedPosition> getPositions() {
		return positions;
	}
	public void setPositions(List<DetectedPosition> positions) {
		this.positions = positions;
	}
	public boolean isOnBus() {
		return isOnBus;
	}
	public void setOnBus(boolean isOnBus) {
		this.isOnBus = isOnBus;
	}
	
		
	
}
