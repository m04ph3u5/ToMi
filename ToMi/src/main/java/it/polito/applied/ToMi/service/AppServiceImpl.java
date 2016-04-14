package it.polito.applied.ToMi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.polito.applied.ToMi.pojo.DetectedPosition;
import it.polito.applied.ToMi.repository.BusRepository;
import it.polito.applied.ToMi.repository.BusStopRepository;
import it.polito.applied.ToMi.repository.DetectedPositionRepository;
import it.polito.applied.ToMi.repository.PathRepository;
import it.polito.applied.ToMi.repository.PathWithTimeRepository;

public class AppServiceImpl implements AppService{
	
	@Autowired
	private DetectedPositionRepository posRepo;
	
	@Autowired
	private PathRepository pathRepo;
	
	@Autowired
	private PathWithTimeRepository pathWithTimeRepo;
	
	@Autowired
	private BusStopRepository busStopRepo;
	
	@Autowired 
	private BusRepository busRepo;

	@Override
	public void saveDetectedPosition(List<DetectedPosition> position) {
		posRepo.insert(position);
		//TODO aggiungere logica aggregazione a seconda di come saranno i dati forniti dalla società di bus
	}

	@Override
	public List<DetectedPosition> getMyPositions(String userEmail, long start, long end) {
		return posRepo.getMyPositions(userEmail, start, end);
	}

	@Override
	public List<DetectedPosition> getAllBusStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetectedPosition> getAllPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetectedPosition> getAllPathsWithTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetectedPosition> getAllBus() {
		// TODO Auto-generated method stub
		return null;
	}

}
