package it.polito.applied.ToMi.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class Path {
	
	@Id
	private String id;
	private String idPath;
	private List <BusStop> stop;
	
	public String getId() {
		return id;
	}
	public String getIdPath() {
		return idPath;
	}
	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}
	public List<BusStop> getStop() {
		return stop;
	}
	public void setStop(List<BusStop> stop) {
		this.stop = stop;
	}
	
	
	
}
