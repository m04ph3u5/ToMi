package it.polito.applied.ToMi.service;

import java.util.List;

import it.polito.applied.ToMi.pojo.DetectedPosition;

public interface AppService {

	public void saveDetectedPosition(List<DetectedPosition> position);

	public List<DetectedPosition> getMyPositions(String userEmail, long start, long end);

	public List<DetectedPosition> getAllBusStop();

	public List<DetectedPosition> getAllPaths();

	public List<DetectedPosition> getAllPathsWithTime();

	public List<DetectedPosition> getAllBus();
}
