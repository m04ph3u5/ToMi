package it.polito.applied.ToMi.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Run {

	@Id
	private String id;
	private String idRun;
	private String idLine;
	private Date date;
	private int totPassenger;
	private List<StopInfo> stops;
	//TRUE TOMI, FALSE MITO. E' ridondante come info ma serve per reperire più velocemente la direzione
	private boolean direction;

	public String getIdRun() {
		return idRun;
	}

	
	public boolean isDirection() {
		return direction;
	}


	public void setDirection(boolean direction) {
		this.direction = direction;
	}


	public void setIdRun(String idRun) {
		this.idRun = idRun;
	}

	public String getIdLine() {
		return idLine;
	}

	public void setIdLine(String idLine) {
		this.idLine = idLine;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTotPassenger() {
		return totPassenger;
	}

	public void setTotPassenger(int totPassenger) {
		this.totPassenger = totPassenger;
	}

	public List<StopInfo> getStops() {
		return stops;
	}

	public void setStops(List<StopInfo> stops) {
		this.stops = stops;
	}

	public String getId() {
		return id;
	}
	
	
	
}
