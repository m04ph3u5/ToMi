package it.polito.applied.ToMi.sademData;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "ServiceDelivery")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceDelivery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 44570827846535775L;
	
	@XmlElement(name="ResponseTimestamp")
	private Date timestamp;
	
	@XmlElement(name="ProducerRef")
	private String producerRef;
	
	@XmlElement(name="EstimatedTimetableDelivery")
	private EstimatedTimeTableDelivery estimatedTimeTableDelivery;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getProducerRef() {
		return producerRef;
	}

	public void setProducerRef(String producerRef) {
		this.producerRef = producerRef;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public EstimatedTimeTableDelivery getEstimatedTimeTableDelivery() {
		return estimatedTimeTableDelivery;
	}

	public void setEstimatedTimeTableDelivery(EstimatedTimeTableDelivery estimatedTimeTableDelivery) {
		this.estimatedTimeTableDelivery = estimatedTimeTableDelivery;
	}
	
}
