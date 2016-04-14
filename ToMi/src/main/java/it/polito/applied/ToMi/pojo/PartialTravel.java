package it.polito.applied.ToMi.pojo;

public class PartialTravel {

	private String id;
	private int mode;
	private int beaconId;
	private double initialLat;
	private double endLat;
	private double initialLng;
	private double endLng;
	
	public PartialTravel(){
		super();
	}
	
	public PartialTravel(String id, int mode, int beaconId, double initialLat, double endLat, double initialLng,
			double endLng) {
		super();
		this.id = id;
		this.mode = mode;
		this.beaconId = beaconId;
		this.initialLat = initialLat;
		this.endLat = endLat;
		this.initialLng = initialLng;
		this.endLng = endLng;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getBeaconId() {
		return beaconId;
	}
	public void setBeaconId(int beaconId) {
		this.beaconId = beaconId;
	}
	public double getInitialLat() {
		return initialLat;
	}
	public void setInitialLat(double initialLat) {
		this.initialLat = initialLat;
	}
	public double getEndLat() {
		return endLat;
	}
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}
	public double getInitialLng() {
		return initialLng;
	}
	public void setInitialLng(double initialLng) {
		this.initialLng = initialLng;
	}
	public double getEndLng() {
		return endLng;
	}
	public void setEndLng(double endLng) {
		this.endLng = endLng;
	}
	
	
}
