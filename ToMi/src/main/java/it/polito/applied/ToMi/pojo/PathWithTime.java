package it.polito.applied.ToMi.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class PathWithTime {

	@Id
	private String id;
	private String idPathWithTime;
	
	private Path path;
	
	public String getId() {
		return id;
	}
	public String getIdPathWithTime() {
		return idPathWithTime;
	}
	public void setIdPathWithTime(String idPathWithTime) {
		this.idPathWithTime = idPathWithTime;
	}
	
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	
	
	
}
