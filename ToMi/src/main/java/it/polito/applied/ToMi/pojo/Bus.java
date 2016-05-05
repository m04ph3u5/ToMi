package it.polito.applied.ToMi.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bus {
	
	@Id
	private String id;
	private String idBus;
	private String idLine;
	private String nameLine;
	private String beaconId;
	private int capacity;
	
	public String getIdBus() {
		return idBus;
	}
	public void setIdBus(String idBus) {
		this.idBus = idBus;
	}
	public String getIdLinea() {
		return idLine;
	}
	public void setIdLinea(String idLine) {
		this.idLine = idLine;
	}
	public String getId() {
		return id;
	}
	public String getIdLine() {
		return idLine;
	}
	public void setIdLine(String idLine) {
		this.idLine = idLine;
	}
	public String getNameLine() {
		return nameLine;
	}
	public void setNameLine(String nameLine) {
		this.nameLine = nameLine;
	}
	public String getBeaconId() {
		return beaconId;
	}
	public void setBeaconId(String beaconId) {
		this.beaconId = beaconId;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	
}
