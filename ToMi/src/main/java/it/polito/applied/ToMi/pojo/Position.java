package it.polito.applied.ToMi.pojo;

public class Position {
	
	private double lat;
	private double lng;
	private static final int FAKE_VALUE=1000;
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

	public boolean isValidPosition(){
		if(this.lat==FAKE_VALUE || this.lng==FAKE_VALUE)
			return false;
		else 
			return true;
	}
	
	@Override
	public boolean equals(Object o){
		if(!o.getClass().equals(Position.class))
			return false;
		
		Position p = (Position) o;
		if(p.getLat()==this.lat && p.getLng()==this.lng)
			return true;
		else
			return false;
	}
	
	public double getDistance(Position p){
		if(!p.isValidPosition() || !this.isValidPosition())
			return -1;
		
		return Math.sqrt((this.lat-p.getLat())*(this.lat-p.getLat()) + (this.lng-p.getLng())*(this.lng-p.getLng()));
	}
	
}
