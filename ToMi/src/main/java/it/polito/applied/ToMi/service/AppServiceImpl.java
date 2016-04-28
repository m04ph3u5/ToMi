package it.polito.applied.ToMi.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polito.applied.ToMi.pojo.Bus;
import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.Comment;
import it.polito.applied.ToMi.pojo.DetectedPosition;
import it.polito.applied.ToMi.pojo.Passenger;
import it.polito.applied.ToMi.pojo.Path;
import it.polito.applied.ToMi.pojo.PathWithTime;
import it.polito.applied.ToMi.pojo.TemporaryTravel;
import it.polito.applied.ToMi.pojo.Travel;
import it.polito.applied.ToMi.repository.BusRepository;
import it.polito.applied.ToMi.repository.BusStopRepository;
import it.polito.applied.ToMi.repository.CommentRepository;
import it.polito.applied.ToMi.repository.DetectedPositionRepository;
import it.polito.applied.ToMi.repository.PathRepository;
import it.polito.applied.ToMi.repository.PathWithTimeRepository;
import it.polito.applied.ToMi.repository.TemporaryTravelRepository;
import it.polito.applied.ToMi.repository.TravelRepository;

@Service
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

	@Autowired
	private TravelRepository travelRepo;
	
	@Autowired
	private CommentRepository commentRepo;

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

	private final int ON_BUS=0;
	private final int NOT_ON_BUS=1;
	private final int INVALID=2;

	private final int NOPOSITION=1000;

	//3h Time in millis 
	private final long THREE_HOURS=10800000;
	//20minutes in millis
	private final long TWENTY_MINUTES=1200000;

	private SimpleDateFormat date;
	private SimpleDateFormat time;
	
	@PostConstruct
	public void initialize(){
		date = new SimpleDateFormat("dd/MM/yyyy");
		time = new SimpleDateFormat("hh:mm");
	}

	@Override
	public void saveDetectedPosition(List<DetectedPosition> position, Passenger passenger) {

		//Mi arriva una lista di posizioni rilevate dall'app. Controllo se per quel passeggero ho già un viaggio pendente
		//(oggetti temporaryTravel). Nel caso non ne avessi per quel passeggero nel database, ne istanzio uno.
		TemporaryTravel tempTravel = tempTravelRepo.findByPassengerIdAndDeviceId(passenger.getId(), position.get(0).getDeviceId());
		if(tempTravel==null){
			tempTravel = new TemporaryTravel();
			tempTravel.setPassengerId(passenger.getId());
			tempTravel.setDeviceId(position.get(0).getDeviceId());
		}		

		for(DetectedPosition p : position){
			//Se il log che mi arriva dall'app non ha una posizione valida, lo salvo all'interno del repo delle detectedPosition (log) ma non lo considero
			//per la costruzione di viaggi
			if(p.getPosition().getLat()!=NOPOSITION){
				p.setUserEmail(passenger.getEmail());
				DetectedPosition last = tempTravel.getLastPosition();

				/*Se il log dell'app contiene un beaconId vuol dire che con relativa certezza l'utente si trovava su un pullman. Questo criterio ha priorità
				 * massima rispetto a tutti gli altri. È l'informazione più attendibile che possiamo ottenere.
				 * In questo caso considero uno stesso viaggio formato da log che hanno lo stesso beaconId e che hanno una distanza temporale tra un log 
				 * ed un altro, non superiore alle tre ore*/
				if(p.getBeaconId()!=null && !p.getBeaconId().isEmpty()){
					if(last==null){
						tempTravel.addDetectedPos(p);
					}else{
						if(last.getBeaconId().equals(p.getBeaconId()) && (p.getTimestamp().getTime()-last.getTimestamp().getTime() <= THREE_HOURS)){
							tempTravel.addDetectedPos(p);
						}else{
							saveTravel(tempTravel);
							tempTravel = new TemporaryTravel();
							tempTravel.setPassengerId(passenger.getId());
							tempTravel.setDeviceId(position.get(0).getDeviceId());
							tempTravel.addDetectedPos(p);
						}
					}
					/*Il log non contiene il beaconId. Ciò significa che non sono su un pullman*/	
				}else{
					if(last==null){
						if(p.getMode()!=ENTER && p.getMode()!=ONDESTROY)
							tempTravel.addDetectedPos(p);
					}else{
						//TODO valutare condizioni uscita e fine viaggio (oltre a EXIT
						if((last.getBeaconId()!=null && !last.getBeaconId().isEmpty()) || p.getMode()==EXIT
								|| (p.getTimestamp().getTime()-last.getTimestamp().getTime()>TWENTY_MINUTES)){
							saveTravel(tempTravel);
							tempTravel = new TemporaryTravel();
							tempTravel.setPassengerId(passenger.getId());
							tempTravel.setDeviceId(position.get(0).getDeviceId());
							if(p.getMode()!=ENTER && p.getMode()!=ONDESTROY)
								tempTravel.addDetectedPos(p);
						}else
							tempTravel.addDetectedPos(p);
					}
				}





				//				int mode = p.getMode();
				//				switch(mode){
				//					case ENTER:{	//ENTER
				//						if(numActualPos>0){
				//							tempTravel.addDetectedPos(p);
				//							saveTravel(tempTravel);
				//							tempTravel.clear();
				//						}
				//						break;
				//					}
				//					case EXIT:{ 	//EXIT
				//						if(numActualPos>0){
				//							saveTravel(tempTravel);
				//							tempTravel.clear();
				//						}
				//						tempTravel.addDetectedPos(p);
				//						break;
				//					}
				//					case ONCREATE:{	//ONCREATE
				//						if(numActualPos>0){
				//							saveTravel(tempTravel);
				//							tempTravel.clear();
				//						}
				//						tempTravel.addDetectedPos(p);
				//						break;
				//					}
				//					case ONDESTROY:{	//ONDESTROY
				//						if(numActualPos>0){
				//							tempTravel.addDetectedPos(p);
				//							saveTravel(tempTravel);
				//							tempTravel.clear();
				//						}
				//						break;	
				//					}
				//					default : {
				//						tempTravel.addDetectedPos(p);
				//					}
				//				}
			}
		}

		if(tempTravel.getSizeOfDetectedPosition()>0)
			tempTravelRepo.save(tempTravel);
		posRepo.insert(position);
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
	

	@Override
	public void saveComments(List<Comment> comments, String userEmail) {
		
		for(Comment c: comments){
			c.setUserEmail(userEmail);
			c.setDate(date.format(c.getTimestamp()));
			c.setTime(time.format(c.getTimestamp()));
		}
		commentRepo.save(comments);
	}
	
	@Override
	public List<Comment> getComments(String lastId) {
		if(lastId==null || lastId.isEmpty()){
			return commentRepo.getLastComments();
		}else{
			return commentRepo.getCommentsAfterId(lastId);
		}
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
			case ON_BUS:{
				travel.setOnBus(true);
				break;
			}
			case NOT_ON_BUS:{
				travel.setOnBus(false);
				break;
			}
			}
			travel.setPositions(tempTravel.getDetectedPosList());
			calculateDistance(travel);
			travelRepo.save(travel);
		}

	}


	private void calculateDistance(Travel travel) {
		double distance=0d;
		int sameDistancePoints=0;
		
		if(travel!=null){
			List<DetectedPosition> points = travel.getPositions();
			if(points.size()>1){
				DetectedPosition before = points.get(0);
				DetectedPosition actual = null;
				for(int i=1; i<points.size(); i++){
					actual = points.get(i);
					if(before.getPosition().getLat()==actual.getPosition().getLat() 
							&& before.getPosition().getLng()==actual.getPosition().getLng()){
						sameDistancePoints++;
					}else{
						distance+=distFrom(before.getPosition().getLat(), before.getPosition().getLng(), actual.getPosition().getLat(), actual.getPosition().getLng());
					}
					before = actual;
				}
				travel.setLengthTravel(distance);
				travel.setLengthAccuracy(100-((sameDistancePoints*100)/points.size()));
			}
		}

		
	}

	private int recognizeTravel(List<DetectedPosition> positions) {
		if(positions.size()<=2)
			return INVALID;

		if(positions.get(0).getBeaconId()!=null && !positions.get(0).getBeaconId().isEmpty())
			return ON_BUS;
		else
			return NOT_ON_BUS;
	}

//	private boolean containsMovementPosition(List<DetectedPosition> positions) {
//		for(DetectedPosition p : positions){
//			int mode = p.getMode();
//			if(mode==IN_VEHICLE || mode==ON_BICYCLE || mode==ON_FOOT || mode==WALKING || mode==RUNNING || mode==STILL)
//				return true;
//		}
//		return false;
//	}

	private double distFrom(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
		* Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = earthRadius * c;

		return dist;
	}

}
