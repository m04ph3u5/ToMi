package it.polito.applied.ToMi.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bus {
	
	@Id
	private String id;
	private String idBus;
	private String idLinea;
	
	public String getIdBus() {
		return idBus;
	}
	public void setIdBus(String idBus) {
		this.idBus = idBus;
	}
	public String getIdLinea() {
		return idLinea;
	}
	public void setIdLinea(String idLinea) {
		this.idLinea = idLinea;
	}
	public String getId() {
		return id;
	}
	
	
}
