package it.polito.applied.ToMi.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BusStop {
	@Id
	private String id;
	private String idStop;
	private String name;
	private double lat;
	private double lng;
	
	public String getIdStop() {
		return idStop;
	}
	public void setIdStop(String idStop) {
		this.idStop = idStop;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getId() {
		return id;
	}
	
	
	
	
}
