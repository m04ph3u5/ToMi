package it.polito.applied.ToMi.pojo;

import java.util.Date;

public class InfoPosition {

	private Date timestamp;
	private Position position;
	
	public InfoPosition(){
		super();
	}
	
	public InfoPosition(DetectedPosition p){
		this();
		this.timestamp = p.getTimestamp();
		this.position = p.getPosition();
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
