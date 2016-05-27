package it.polito.applied.ToMi.sademData;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "EstimatedJourneyVersionFrame")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimatedJourneyVersionFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2812194587855230578L;
	
	@XmlElement(name="ResponseTimestamp")
	private Date timestamp;

	@XmlElement(name="EstimatedVehicleJourney")
	private EstimatedVehicleJourney estimatedVehicleJourney;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public EstimatedVehicleJourney getEstimatedVehicleJourney() {
		return estimatedVehicleJourney;
	}

	public void setEstimatedVehicleJourney(EstimatedVehicleJourney estimatedVehicleJourney) {
		this.estimatedVehicleJourney = estimatedVehicleJourney;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
