package it.polito.applied.ToMi.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TemporaryTravel {

	@Id
	private String id;
	private String passengerId;
	private Date start;
	private List<DetectedPosition> detectedPosList;
	
	public TemporaryTravel(){
		super();
	}
	
	public TemporaryTravel(Date start, List<DetectedPosition> detectedPosList) {
		super();
	
		this.start = start;
		this.detectedPosList = detectedPosList;
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
	public void setId(String id) {
		this.id = id;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public List<DetectedPosition> getDetectedPosList() {
		return detectedPosList;
	}
	public void setDetectedPosList(List<DetectedPosition> detectedPosList) {
		this.detectedPosList = detectedPosList;
	}
	
	public void addDetectedPos (DetectedPosition p){
		this.detectedPosList.add(p);
	}
	
	
}
