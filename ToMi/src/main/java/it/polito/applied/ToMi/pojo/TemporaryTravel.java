package it.polito.applied.ToMi.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TemporaryTravel {

	@Id
	private String id;
	private String passengerId;
	private List<DetectedPosition> detectedPosList;
	
	public TemporaryTravel(){
		super();
		detectedPosList = new ArrayList<DetectedPosition>();
	}
	
	public TemporaryTravel(List<DetectedPosition> detectedPosList) {
		super();	
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

	public List<DetectedPosition> getDetectedPosList() {
		return detectedPosList;
	}
	public void setDetectedPosList(List<DetectedPosition> detectedPosList) {
		this.detectedPosList = detectedPosList;
	}
	
	public void addDetectedPos (DetectedPosition p){
		this.detectedPosList.add(p);
	}
	
	public int getSizeOfDetectedPosition(){
		return detectedPosList.size();
	}
	
	
}
