package it.polito.applied.ToMi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.geo.GeoResults;

import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.InfoPosition;
import it.polito.applied.ToMi.pojo.RunDTO;

public interface CustomBusStopRepository {
	GeoResults<BusStop> findNear(InfoPosition infoPosition, String idLine);
	Collection<? extends BusStop> findBetweenFirstAndLast(BusStop busStop, BusStop busStop2);
	public List<BusStop> findAllSortByIdRunAndIdLineAndIdProg();

}
