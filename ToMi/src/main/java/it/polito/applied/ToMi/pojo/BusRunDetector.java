package it.polito.applied.ToMi.pojo;

public class BusRunDetector {
	
	private BusStop first;
	private BusStop last;
	private double distanceF, distanceL;
	private long timeF, timeL;
	
	public BusRunDetector(BusStop f, BusStop l, double distanceF, double distanceL, long logTimeF, long logTimeL){
		this.first=f;
		this.last = l;
		this.distanceF = distanceF;
		this.distanceL = distanceL;
		this.timeF = Math.abs(first.getTime()-logTimeF);
		this.timeL = Math.abs(last.getTime()-logTimeL);
	}
	
	private double goodIndex=0;
	
	public BusStop getFirst() {
		return first;
	}
	public void setFirst(BusStop first) {
		this.first = first;
	}
	public BusStop getLast() {
		return last;
	}
	public void setLast(BusStop last) {
		this.last = last;
	}
	
	public Integer getIdProgFirst(){
		if(first!=null)
			return first.getIdProg();
		else
			return null;
	}
	
	public Integer getIdProgLast(){
		if(last!=null)
			return last.getIdProg();
		else
			return null;
	}
	
	public boolean isValid(){
		if(first!=null && last!=null)
			return true;
		else
			return false;
	}
	
	public double evaluateGoodIndex(double firstDistance, double lastDistance, long firstTime, long lastTime){
		
		return goodIndex;
	}
	public double getGoodIndex() {
		return goodIndex;
	}
	
	
	
}
