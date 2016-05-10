package it.polito.applied.ToMi.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="run_index", unique=true, def ="{'idRun' : 1, 'idLine':1, 'day':-1}")
})
public class Run {

	@Id
	private String id;
	private String idRun;
	private String idLine;
	private String day;
	private int totPassenger;
	private List<StopInfo> stops;
	//TRUE TOMI, FALSE MITO. E' ridondante come info ma serve per reperire più velocemente la direzione
	private boolean direction;

	public Run(){
		stops = new ArrayList<StopInfo>();
		totPassenger=1;
	}
	public void addStopInfo(StopInfo s){
		stops.add(s);
	}
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
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
