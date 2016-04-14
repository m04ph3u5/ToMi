package it.polito.applied.ToMi.pojo;

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
	
	public Travel(boolean startIsKnown, boolean endIsKnown, String passengerId) {
		super();
		this.startIsKnown = startIsKnown;
		this.endIsKnown = endIsKnown;
		this.passengerId = passengerId;
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
