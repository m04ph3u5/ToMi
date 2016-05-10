package it.polito.applied.ToMi.pojo;

public class BusRunDetector {
	
	private BusStop first;
	private BusStop last;
	//km
	private double distanceF, distanceL;
	//minuti
	private long timeF, timeL;
	//costante che rappresenta il quadrato della velocità media espressa in km/minuto (70 km/h = 1.166.. km/minuto =>^2 = 1.361..
	private final static double SQUARE_AVERAGE_SPEED=1.361;
	
	public BusRunDetector(BusStop f, BusStop l, double distanceF, double distanceL, long logTimeF, long logTimeL){
		this.first=f;
		this.last = l;
		this.distanceF = distanceF;
		this.distanceL = distanceL;
		this.timeF = Math.abs(first.getTime()-logTimeF)/60000;
		this.timeL = Math.abs(last.getTime()-logTimeL)/60000;
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
	
	public double evaluateGoodIndex(){
		double firstIndex, lastIndex;
		
		firstIndex = distanceF*distanceF + SQUARE_AVERAGE_SPEED*timeF*timeF;
		lastIndex = distanceL*distanceL + SQUARE_AVERAGE_SPEED*timeL*timeL;
		goodIndex = (firstIndex+lastIndex)/2;
		return goodIndex;
	}
	public double getGoodIndex() {
		return goodIndex;
	}
	
	
	
}
