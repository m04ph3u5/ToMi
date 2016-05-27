package it.polito.applied.ToMi.pojo;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="run_index", unique=true, def ="{'idLine' : 1, 'idRun':1, 'idProg':1}")
})
public class BusStop {
	@Id
	private String id;
	private String idStop;
	private String idLine;
	private String idRun;
	private long time;
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

	public String getIdLine() {
		return idLine;
	}

	public void setIdLine(String idLine) {
		this.idLine = idLine;
	}

	public String getIdRun() {
		return idRun;
	}

	public void setIdRun(String idRun) {
		this.idRun = idRun;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public void setTime(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		time = h*60*60*1000 + m*60*1000;
	}
	
	
	
}
