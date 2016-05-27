package it.polito.applied.ToMi.sademData;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "EstimatedVehicleJourney")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimatedVehicleJourney implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8462647046812739654L;
	
	@XmlElement(name="LineRef")
	private String lineRef;
	
	@XmlElement(name="DirectionRef")
	private String direction;
	
	@XmlElement(name="DatedVehicleJourneyRef")
	private String datedVehicleJourneyRef;
	
	@XmlElement(name="Cancellation")
	private Boolean cancellation;

	@XmlElementWrapper(name="EstimatedCalls")
    @XmlElement(name="EstimatedCall")
	private List<EstimatedCall> estimatedCalls;

	public String getLineRef() {
		return lineRef;
	}

	public void setLineRef(String lineRef) {
		this.lineRef = lineRef;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDatedVehicleJourneyRef() {
		return datedVehicleJourneyRef;
	}

	public void setDatedVehicleJourneyRef(String datedVehicleJourneyRef) {
		this.datedVehicleJourneyRef = datedVehicleJourneyRef;
	}

	public Boolean getCancellation() {
		return cancellation;
	}

	public void setCancellation(Boolean cancellation) {
		this.cancellation = cancellation;
	}

	public List<EstimatedCall> getEstimatedCalls() {
		return estimatedCalls;
	}

	public void setEstimatedCalls(List<EstimatedCall> estimatedCalls) {
		this.estimatedCalls = estimatedCalls;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override 
	public String toString(){
		
		String s = "\nDATI VIAGGIO: "+lineRef+" "+direction+" "+datedVehicleJourneyRef+" "+cancellation+". "+"Elenco fermate";
		if(estimatedCalls!=null && !estimatedCalls.isEmpty()){
			s+=" ("+estimatedCalls.size()+"):\n";
			for(EstimatedCall e : estimatedCalls)
				s+=e.toString();
		}else
			s+="Nessuna fermata dispnibile";
		
		return s;
	}
}
