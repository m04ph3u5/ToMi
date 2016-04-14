package it.polito.applied.ToMi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.polito.applied.ToMi.pojo.Bus;
import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.DetectedPosition;
import it.polito.applied.ToMi.pojo.Passenger;
import it.polito.applied.ToMi.pojo.Path;
import it.polito.applied.ToMi.pojo.PathWithTime;
import it.polito.applied.ToMi.pojo.TemporaryTravel;
import it.polito.applied.ToMi.repository.BusRepository;
import it.polito.applied.ToMi.repository.BusStopRepository;
import it.polito.applied.ToMi.repository.DetectedPositionRepository;
import it.polito.applied.ToMi.repository.PathRepository;
import it.polito.applied.ToMi.repository.PathWithTimeRepository;
import it.polito.applied.ToMi.repository.TemporaryTravelRepository;

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
	
	@Autowired
	private TemporaryTravelRepository tempTravelRepo;
	

	@Override
	public void saveDetectedPosition(List<DetectedPosition> position, Passenger passenger) {
		
		TemporaryTravel tempTravel = tempTravelRepo.findByPassengerId(passenger.getId());
		if(tempTravel==null){
			tempTravel = new TemporaryTravel();
			tempTravel.setPassengerId(passenger.getId());
		}		
		
		for(DetectedPosition p : position){
			p.setUserEmail(passenger.getEmail());
			//Lunghezza della lista delle detectedPosition già presenti dentro temporaryTravel
			int numActualPos = tempTravel.getSizeOfDetectedPosition();
			int mode = p.getMode();
			switch(mode){
				case 9:{	//ENTER
					if(numActualPos>0){
						tempTravel.addDetectedPos(p);
						saveTravel(tempTravel);
					}
					break;
				}
				case 10:{ 	//EXIT
					break;
				}
				case 11:{	//ONCREATE
					break;
				}
				case 12:{	//ONDESTROY
					break;	
				}
				default : {
					
				}
			}
		}
		
		posRepo.insert(position);
	}

	private void saveTravel(TemporaryTravel tempTravel) {
		// TODO Auto-generated method stub
		//TODO eliminare tempTravel dal database se tempTravel ha un id
	}

	@Override
	public List<DetectedPosition> getMyPositions(String userEmail, long start, long end) {
		return posRepo.getMyPositions(userEmail, start, end);
	}

	@Override
	public List<BusStop> getAllBusStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Path> getAllPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PathWithTime> getAllPathsWithTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bus> getAllBus() {
		// TODO Auto-generated method stub
		return null;
	}

}
