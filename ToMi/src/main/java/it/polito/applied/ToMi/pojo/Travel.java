package it.polito.applied.ToMi.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Travel {
	@Id
	private String id;
	@Indexed
	private boolean isOnBus;
	private String passengerId;
	private double lengthTravel;
	private double lengthAccuracy;
	private Date start;
	private Date end;
	private List<PartialTravel> partials;
	private long dayTimestamp;
	
	
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
	public boolean isOnBus() {
		return isOnBus;
	}
	public void setOnBus(boolean isOnBus) {
		this.isOnBus = isOnBus;
	}
	public List<PartialTravel> getPartials() {
		return partials;
	}
	public void setPartials(List<PartialTravel> partials) {
		this.partials = partials;
	}
	public long getDayTimestamp() {
		return dayTimestamp;
	}
	public void setDayTimestamp(long dayTimestamp) {
		this.dayTimestamp = dayTimestamp;
	}
	
}
