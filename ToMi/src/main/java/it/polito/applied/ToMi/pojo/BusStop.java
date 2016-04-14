package it.polito.applied.ToMi.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BusStop {
	@Id
	private String id;
	private String idStop;
	private double lat;
	private double lng;
	private String address;
	private String hour;  
	private String minute; //nel caso non ci siano questi due campi, l'oggetto identifica genericamente la fermata 
	
	
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getId() {
		return id;
	}
	public String getIdStop() {
		return idStop;
	}
	public void setIdStop(String idStop) {
		this.idStop = idStop;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
