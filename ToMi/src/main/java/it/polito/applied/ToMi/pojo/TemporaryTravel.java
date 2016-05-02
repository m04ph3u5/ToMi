package it.polito.applied.ToMi.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndex (name = "passenger_device", def = "{'passengerId': 1, 'deviceId': 1}", unique=true)
public class TemporaryTravel {

	@Id
	private String id;
	private String passengerId;
	private String deviceId;
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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void clear(){
		this.id = ""; 
		this.detectedPosList.clear();
	}
	public DetectedPosition getLastPosition(){
		if(detectedPosList!=null && detectedPosList.size()>0)
			return detectedPosList.get(detectedPosList.size()-1);
		else
			return null;
	}
}
