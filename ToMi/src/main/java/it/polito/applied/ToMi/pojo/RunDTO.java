package it.polito.applied.ToMi.pojo;

public class RunDTO implements Comparable<RunDTO>{

	private String idRun;
	private String origin;
	private String destination;
	private String hOrigin;
	private String hDestionation;
	
	public RunDTO(){}
	
	public RunDTO(String idRun){
		this();
		this.idRun=idRun;
	}
	
	public String getIdRun() {
		return idRun;
	}
	public void setIdRun(String idRun) {
		this.idRun = idRun;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String gethOrigin() {
		return hOrigin;
	}
	public void sethOrigin(String hOrigin) {
		this.hOrigin = hOrigin;
	}
	public String gethDestionation() {
		return hDestionation;
	}
	public void sethDestionation(String hDestionation) {
		this.hDestionation = hDestionation;
	}

	@Override
	public int compareTo(RunDTO o) {
		int i=Integer.parseInt(idRun);
		int j=Integer.parseInt(o.getIdRun());
		if(i<j)
			return -1;
		else if(i>j)
			return 1;
		else
			return 0;
	}
}
