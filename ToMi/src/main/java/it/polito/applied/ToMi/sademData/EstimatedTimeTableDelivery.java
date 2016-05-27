package it.polito.applied.ToMi.sademData;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "EstimatedTimeTableDelivery")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimatedTimeTableDelivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 301725512469128640L;
	
	@XmlElement(name="ResponseTimestamp")
	private Date timestamp;
	
	@XmlElement(name="EstimatedJourneyVersionFrame")
	private EstimatedJourneyVersionFrame estimatedJourneyVersionFrame;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public EstimatedJourneyVersionFrame getEstimatedJourneyVersionFrame() {
		return estimatedJourneyVersionFrame;
	}

	public void setEstimatedJourneyVersionFrame(EstimatedJourneyVersionFrame estimatedJourneyVersionFrame) {
		this.estimatedJourneyVersionFrame = estimatedJourneyVersionFrame;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
