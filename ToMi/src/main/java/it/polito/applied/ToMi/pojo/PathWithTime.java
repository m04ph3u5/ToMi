package it.polito.applied.ToMi.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class PathWithTime {

	@Id
	private String id;
	
	private Path path;
	private String hour;
	private String minute;
	
	
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getId() {
		return id;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	
	
	
}
