package it.polito.applied.ToMi.pojo;

import java.util.Comparator;

public class PartialTravelComparatorOnRunId implements Comparator<PartialTravel>{

	@Override
	public int compare(PartialTravel o1, PartialTravel o2) {
		if(o1.getIdRun()==null || o1.getIdRun().isEmpty())
			return -1;
		else if((o1.getIdRun()!=null || !o1.getIdRun().isEmpty()) && (o2.getIdRun()==null || o2.getIdRun().isEmpty()))
			return 1;
		else{
			return o1.getIdRun().compareTo(o2.getIdRun());
		}
	}

}
