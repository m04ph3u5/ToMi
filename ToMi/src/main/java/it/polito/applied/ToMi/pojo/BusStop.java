package it.polito.applied.ToMi.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BusStop {
	@Id
	private String id;
	private String idStop;
	private String name;
	@GeoSpatialIndexed
	private double[] location;
	private int idProg;
	
	public BusStop(){
		location = new double[2];
	}
	
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
		if(location.length>0)
			return location[0];
		else
			return 1000;
	}
	public void setLat(double lat) {
		location[0]=lat;
	}
	public double getLng() {
		if(location.length>1)
			return location[1];
		else
			return 1000;
	}
	public void setLng(double lng) {
		location[1]=lng;
	}
	public String getId() {
		return id;
	}
	public int getIdProg() {
		return idProg;
	}
	public void setIdProg(int idProg) {
		this.idProg = idProg;
	}
	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}
	
	
	
	
}
