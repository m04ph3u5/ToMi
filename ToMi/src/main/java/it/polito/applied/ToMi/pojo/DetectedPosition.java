package it.polito.applied.ToMi.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DetectedPosition {

	@Id
	private String id;
	private String deviceId;
	private Position position;
	private Date timestamp;
	private int mode;
	private String beaconId;
	private String userEmail;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
	
}
