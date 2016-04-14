package it.polito.applied.ToMi.repository;

import java.util.List;

import it.polito.applied.ToMi.pojo.DetectedPosition;

public interface CustomDetectedPositionRepository {

	public List<DetectedPosition> getMyPositions(String email, long start, long end);
}
