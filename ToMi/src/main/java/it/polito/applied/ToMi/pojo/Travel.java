package it.polito.applied.ToMi.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Travel {
	@Id
	private String id;
	private boolean startIsKnown;
	private boolean endIsKnown;
	private String passengerId;
	private List<DetectedPosition> allPositions;
	private Date start;
	private List<PartialTravel> partialTravels;
	
	
	
	public List<PartialTravel> getPartialTravels() {
		return partialTravels;
	}

	public void setPartialTravels(List<PartialTravel> partialTravels) {
		this.partialTravels = partialTravels;
	}
	
	public void addPartialTravel(PartialTravel p){
		this.partialTravels.add(p);
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public boolean isStartIsKnown() {
		return startIsKnown;
	}
	public void setStartIsKnown(boolean startIsKnown) {
		this.startIsKnown = startIsKnown;
	}
	public boolean isEndIsKnown() {
		return endIsKnown;
	}
	public void setEndIsKnown(boolean endIsKnown) {
		this.endIsKnown = endIsKnown;
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
	public List<DetectedPosition> getAllPositions() {
		return allPositions;
	}
	public void setAllPositions(List<DetectedPosition> allPositions) {
		this.allPositions = allPositions;
	}
		
	
}
