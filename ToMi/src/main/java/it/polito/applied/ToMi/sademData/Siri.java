package it.polito.applied.ToMi.sademData;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="Siri")
@XmlAccessorType(XmlAccessType.FIELD)
public class Siri implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4980887660271822005L;
	
	@XmlElement(name="ServiceDelivery")
	private ServiceDelivery serviceDelivery;

	public ServiceDelivery getServiceDelivery() {
		return serviceDelivery;
	}

	public void setServiceDelivery(ServiceDelivery serviceDelivery) {
		this.serviceDelivery = serviceDelivery;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString(){
		String s = serviceDelivery.getProducerRef()+" "+serviceDelivery.getTimestamp();
				
		EstimatedTimeTableDelivery ettd = serviceDelivery.getEstimatedTimeTableDelivery();
		if(ettd==null)
			System.out.print("EstimatedTimeTableDelivery null");
		
		EstimatedJourneyVersionFrame ejvf = ettd.getEstimatedJourneyVersionFrame();
		if(ejvf==null)
			System.out.print("EstimatedJourneyVersionFrame null");
		
		EstimatedVehicleJourney evj = ejvf.getEstimatedVehicleJourney();
		if(evj==null)
			System.out.print("EstimatedVehicleJourney null");
		else
			s+=evj.toString();

		return s;
	}

}
