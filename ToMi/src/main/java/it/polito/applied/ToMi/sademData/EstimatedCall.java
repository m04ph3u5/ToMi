package it.polito.applied.ToMi.sademData;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "EstimatedCall")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimatedCall implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1976772072284888775L;
	
	@XmlElement(name="StopPointRef")
	private String stopPointRef;
	
	@XmlElement(name="VisitNumber")
	private Integer visitNumber;
	
	@XmlElement(name="StopPointName")
	private String stopPointName;
	
	@XmlElement(name="Cancellation")
	private Boolean cancellation;
	
	@XmlElement(name="AimedDepartureTime")
	private Date aimedDepartureTime;
	
	@XmlElement(name="ExpectedDepartureTime")
	private Date expectedDepartureTime;

	public String getStopPointRef() {
		return stopPointRef;
	}

	public void setStopPointRef(String stopPointRef) {
		this.stopPointRef = stopPointRef;
	}

	public Integer getVisitNumber() {
		return visitNumber;
	}

	public void setVisitNumber(Integer visitNumber) {
		this.visitNumber = visitNumber;
	}

	public String getStopPointName() {
		return stopPointName;
	}

	public void setStopPointName(String stopPointName) {
		this.stopPointName = stopPointName;
	}

	public Boolean getCancellation() {
		return cancellation;
	}

	public void setCancellation(Boolean cancellation) {
		this.cancellation = cancellation;
	}

	public Date getAimedDepartureTime() {
		return aimedDepartureTime;
	}

	public void setAimedDepartureTime(Date aimedDepartureTime) {
		this.aimedDepartureTime = aimedDepartureTime;
	}

	public Date getExpectedDepartureTime() {
		return expectedDepartureTime;
	}

	public void setExpectedDepartureTime(Date expectedDepartureTime) {
		this.expectedDepartureTime = expectedDepartureTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString(){
		return "- "+stopPointRef+" "+visitNumber+" "+stopPointName+" "
				+cancellation+" "+aimedDepartureTime+" "+expectedDepartureTime+"\n";
	}
}
