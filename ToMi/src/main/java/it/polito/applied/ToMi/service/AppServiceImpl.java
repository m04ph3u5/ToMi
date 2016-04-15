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
import it.polito.applied.ToMi.pojo.Travel;
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
	
	public final int IN_VEHICLE=0;
	public final int ON_BICYCLE=1;
	public final int ON_FOOT=2;
	public final int STILL=3;
	public final int UNKNOWN=4;
	public final int TILTING=5;
	public final int WALKING=7;
	public final int RUNNING=8;
	public final int ENTER=9;
	public final int EXIT=10;
	public final int ONCREATE=11;
	public final int ONDESTROY=12;

	private final int COMPLETE=0;
	private final int PARTIAL_START=1;
	private final int PARTIAL_END=2;
	private final int INVALID=3;
	
	@Override
	public void saveDetectedPosition(List<DetectedPosition> position, Passenger passenger) {
		
		TemporaryTravel tempTravel = tempTravelRepo.findByPassengerIdAndDeviceId(passenger.getId(), position.get(0).getDeviceId());
		if(tempTravel==null){
			tempTravel = new TemporaryTravel();
			tempTravel.setPassengerId(passenger.getId());
			tempTravel.setDeviceId(position.get(0).getDeviceId());
		}		
		
		for(DetectedPosition p : position){
			p.setUserEmail(passenger.getEmail());
			//Lunghezza della lista delle detectedPosition già presenti dentro temporaryTravel
			int numActualPos = tempTravel.getSizeOfDetectedPosition();
			int mode = p.getMode();
			switch(mode){
				case ENTER:{	//ENTER
					if(numActualPos>0){
						tempTravel.addDetectedPos(p);
						saveTravel(tempTravel);
						tempTravel.clear();
					}
					break;
				}
				case EXIT:{ 	//EXIT
					if(numActualPos>0){
						saveTravel(tempTravel);
						tempTravel.clear();
					}
					tempTravel.addDetectedPos(p);
					break;
				}
				case ONCREATE:{	//ONCREATE
					if(numActualPos>0){
						saveTravel(tempTravel);
						tempTravel.clear();
					}
					tempTravel.addDetectedPos(p);
					break;
				}
				case ONDESTROY:{	//ONDESTROY
					if(numActualPos>0){
						tempTravel.addDetectedPos(p);
						saveTravel(tempTravel);
						tempTravel.clear();
					}
					break;	
				}
				default : {
					tempTravel.addDetectedPos(p);
				}
			}
		}
		
		if(tempTravel.getSizeOfDetectedPosition()>0)
			tempTravelRepo.save(tempTravel);
		posRepo.insert(position);
	}

	private void saveTravel(TemporaryTravel tempTravel) {
		// TODO Auto-generated method stub
		if(tempTravel.getId()!=null && !tempTravel.getId().isEmpty())
			tempTravelRepo.delete(tempTravel.getId());
		
		/*
		 * 0 - total travel (known exit and enter)
		 * 1 - partial start travel (unknown exit, know enter)
		 * 2 - partial end travel (known exit, unknown enter)
		 * 3 - invalid travel (absence of movement mode or unknown enter and exit or too short travel).
		 * */
		int travelType = recognizeTravel(tempTravel.getDetectedPosList()); 
		
		if(travelType!=INVALID){
			Travel travel = new Travel();
			travel.setPassengerId(tempTravel.getPassengerId());
			travel.setStart(tempTravel.getDetectedPosList().get(0).getTimestamp());
			travel.setEnd(tempTravel.getDetectedPosList().get(tempTravel.getDetectedPosList().size()-1).getTimestamp());
			switch(travelType){
				case COMPLETE : {
					travel.setStartIsKnown(true);
					travel.setEndIsKnown(true);
					break;
				}
				case PARTIAL_START : {
					travel.setStartIsKnown(false);
					travel.setEndIsKnown(true);
					break;
				}
				case PARTIAL_END : {
					travel.setStartIsKnown(true);
					travel.setEndIsKnown(false);
					break;
				}
			}
			extractPartialTravel(travel, tempTravel.getDetectedPosList());
		}
		
	}


	private void extractPartialTravel(Travel travel, List<DetectedPosition> positions) {
		DetectedPosition firstPosition = positions.get(0);
		if(firstPosition.getMode()==EXIT || firstPosition.getMode()==ONCREATE){
			positions.remove(0);
		}
		for(int i=0; i<positions.size(); i++){
			if(positions.get(i).getMode()==ENTER || positions.get(i).getMode()== ONDESTROY){
				positions.remove(i);
			}else{
				
			}
		}
	}

	private int recognizeTravel(List<DetectedPosition> positions) {
		if(positions.size()<=2)
			return INVALID;
		
		if(positions.get(0).getMode()!=EXIT && positions.get(0).getMode()!=ONCREATE
				&& positions.get(positions.size()-1).getMode()!=ENTER
				&& positions.get(positions.size()-1).getMode()!=ONDESTROY)
			return INVALID;
		
		if(!containsMovementPosition(positions))
			return INVALID;
		
		if(positions.get(0).getMode()==EXIT && positions.get(positions.size()-1).getMode()==ENTER)
			return COMPLETE;
		else if(positions.get(0).getMode()!=EXIT && positions.get(positions.size()-1).getMode()==ENTER)
			return PARTIAL_START;
		else 
			return PARTIAL_END;
	}

	private boolean containsMovementPosition(List<DetectedPosition> positions) {
		for(DetectedPosition p : positions){
			int mode = p.getMode();
			if(mode==IN_VEHICLE || mode==ON_BICYCLE || mode==ON_FOOT || mode==WALKING || mode==RUNNING)
				return true;
		}
		return false;
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
