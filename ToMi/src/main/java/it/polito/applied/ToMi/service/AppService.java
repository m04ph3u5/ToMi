package it.polito.applied.ToMi.service;

import java.util.List;

import it.polito.applied.ToMi.pojo.Bus;
import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.DetectedPosition;
import it.polito.applied.ToMi.pojo.Passenger;
import it.polito.applied.ToMi.pojo.Path;
import it.polito.applied.ToMi.pojo.PathWithTime;

public interface AppService {

	public void saveDetectedPosition(List<DetectedPosition> position, Passenger passenger);

	public List<DetectedPosition> getMyPositions(String userEmail, long start, long end);

	public List<BusStop> getAllBusStop();

	public List<Path> getAllPaths();

	public List<PathWithTime> getAllPathsWithTime();

	public List<Bus> getAllBus();
}
