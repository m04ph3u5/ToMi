package it.polito.applied.ToMi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.mongodb.core.MongoDataIntegrityViolationException;
import org.springframework.stereotype.Service;

import it.polito.applied.ToMi.exception.BadRequestException;
import it.polito.applied.ToMi.pojo.Bus;
import it.polito.applied.ToMi.pojo.BusRunDetector;
import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.Comment;
import it.polito.applied.ToMi.pojo.DailyData;
import it.polito.applied.ToMi.pojo.DailyInfo;
import it.polito.applied.ToMi.pojo.DayPassengerBusRun;
import it.polito.applied.ToMi.pojo.DetectedPosition;
import it.polito.applied.ToMi.pojo.InfoPosition;
import it.polito.applied.ToMi.pojo.PartialTravel;
import it.polito.applied.ToMi.pojo.Passenger;
import it.polito.applied.ToMi.pojo.Run;
import it.polito.applied.ToMi.pojo.StopInfo;
import it.polito.applied.ToMi.pojo.TemporaryTravel;
import it.polito.applied.ToMi.pojo.Travel;
import it.polito.applied.ToMi.repository.BusRepository;
import it.polito.applied.ToMi.repository.BusStopRepository;
import it.polito.applied.ToMi.repository.CommentRepository;
import it.polito.applied.ToMi.repository.DetectedPositionRepository;
import it.polito.applied.ToMi.repository.RunRepository;
import it.polito.applied.ToMi.repository.TemporaryTravelRepository;
import it.polito.applied.ToMi.repository.TravelRepository;

@Service
public class AppServiceImpl implements AppService{

	@Autowired
	private DetectedPositionRepository posRepo;

	@Autowired
	private BusStopRepository busStopRepo;

	@Autowired 
	private RunRepository runRepo;

	@Autowired
	private TemporaryTravelRepository tempTravelRepo;

	@Autowired
	private TravelRepository travelRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private BusRepository busRepo;

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
	private final int ON_BUS=15;

	private final int NOPOSITION=1000;
	private final int STILL_THREESHOLD = 100;  

	//20minutes in millis
	private final long TWENTY_MINUTES=1200000;
	//1minute in millis
	private final long ONE_MINUTE=60000;
	//3minutes in millis
	private final long THREE_MINUTES=180000;
	//5minutes in millis
	private final long FIVE_MINUTES=300000;
	
	private final long ACCURACY_TIME_THREESHOLD=1000;
	

	private SimpleDateFormat date;
	private SimpleDateFormat time;
	
	@PostConstruct
	public void initialize(){
		date = new SimpleDateFormat("yyyy-MM-dd");
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
			p.setUserEmail(passenger.getEmail());
			DetectedPosition last = tempTravel.getLastPosition();
			
			if(p.getPosition().getLat()==NOPOSITION)
				tempTravel.addMissingPoint();

			if(last==null && p.getMode()!=ENTER && p.getMode()!=ONDESTROY && p.getPosition().getLat()!=NOPOSITION)
				tempTravel.addDetectedPos(p);
			else{
				if(last!=null && (p.getMode()==ENTER || p.getMode()==ONDESTROY || (p.getTimestamp().getTime()-last.getTimestamp().getTime()>TWENTY_MINUTES))){
					saveTravel(tempTravel);
					tempTravel = new TemporaryTravel();
					tempTravel.setPassengerId(passenger.getId());
					tempTravel.setDeviceId(position.get(0).getDeviceId());
					if(p.getMode()!=ENTER && p.getMode()!=ONDESTROY && p.getPosition().getLat()!=NOPOSITION)
						tempTravel.addDetectedPos(p);
				}else{
					if(p.getPosition().getLat()!=NOPOSITION)
						tempTravel.addDetectedPos(p);
				}
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
		return busStopRepo.findAll();
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
	public void saveAnswerToComments(String id, List<Comment> answers, String userEmail) throws BadRequestException {
		// TODO Auto-generated method stub
		Comment father = commentRepo.findById(id);
		if(father==null)
			throw new BadRequestException("Commento non trovato!");
		father.addAnswers(answers);
		commentRepo.save(father);
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
		
		List<PartialTravel> partials = new LinkedList<PartialTravel>();
		List<DetectedPosition> positions = tempTravel.getDetectedPosList();
		boolean atLeastOneBusTravel = false;
		if(positions.size()>2){
			
			PartialTravel currentPartial = null;
			
			for(DetectedPosition p : positions){
				if(currentPartial==null){
					currentPartial = new PartialTravel();
					currentPartial.setStart(p.getTimestamp());
					if(p.getBeaconId()!=null && !p.getBeaconId().isEmpty()){
						p.setMode(ON_BUS);
						atLeastOneBusTravel=true;
						currentPartial.setBeaconId(p.getBeaconId());
					}else{
						currentPartial.setMode(p.getMode());
						currentPartial.setBeaconId("");
					}
					InfoPosition i = new InfoPosition(p);
					currentPartial.addInfoPosition(i);
				}else{
					if(p.getBeaconId()!=null && !p.getBeaconId().isEmpty()){
						if(currentPartial.getBeaconId().equals(p.getBeaconId())){
							InfoPosition i = new InfoPosition(p);
							currentPartial.addInfoPosition(i);
						}else{
							currentPartial.setEnd(currentPartial.getAllPositions().get(currentPartial.getAllPositions().size()-1).getTimestamp());
							lengthOfPartial(currentPartial);
							if(currentPartial.getBeaconId()!=null && !currentPartial.getBeaconId().isEmpty())
								currentPartial.setMode(ON_BUS);
							partials.add(currentPartial);
							currentPartial = new PartialTravel();
							currentPartial.setStart(p.getTimestamp());
							p.setMode(ON_BUS);
							atLeastOneBusTravel=true;
							currentPartial.setBeaconId(p.getBeaconId());
							
							InfoPosition i = new InfoPosition(p);
							currentPartial.addInfoPosition(i);
						}
					}else if((p.getBeaconId()==null || p.getBeaconId().isEmpty()) 
							&& currentPartial.getBeaconId()!=null && !currentPartial.getBeaconId().isEmpty()){
						currentPartial.setEnd(currentPartial.getAllPositions().get(currentPartial.getAllPositions().size()-1).getTimestamp());
						lengthOfPartial(currentPartial);
						currentPartial.setMode(ON_BUS);
						partials.add(currentPartial);
						currentPartial = new PartialTravel();
						currentPartial.setStart(p.getTimestamp());
						currentPartial.setMode(p.getMode());
						currentPartial.setBeaconId("");
						
						InfoPosition i = new InfoPosition(p);
						currentPartial.addInfoPosition(i);
					}else if(isMovement(p.getMode())){
						if(!isMovement(currentPartial.getMode())){
							currentPartial.setMode(p.getMode());
							InfoPosition i = new InfoPosition(p);
							currentPartial.addInfoPosition(i);
						}else{
							if(p.getMode()==currentPartial.getMode()){
								InfoPosition i = new InfoPosition(p);
								currentPartial.addInfoPosition(i);
							}else{
								currentPartial.setEnd(currentPartial.getAllPositions().get(currentPartial.getAllPositions().size()-1).getTimestamp());
								lengthOfPartial(currentPartial);
								if(currentPartial.getBeaconId()!=null && !currentPartial.getBeaconId().isEmpty())
									currentPartial.setMode(ON_BUS);
								partials.add(currentPartial);
								currentPartial = new PartialTravel();
								currentPartial.setStart(p.getTimestamp());
								if(p.getBeaconId()!=null && !p.getBeaconId().isEmpty()){
									p.setMode(ON_BUS);
									currentPartial.setBeaconId(p.getBeaconId());
								}else{
									currentPartial.setMode(p.getMode());
									currentPartial.setBeaconId("");
								}
								InfoPosition i = new InfoPosition(p);
								currentPartial.addInfoPosition(i);
							}
						}
					}else{
						InfoPosition i = new InfoPosition(p);
						currentPartial.addInfoPosition(i);
						if(currentPartial.getMode()==EXIT && p.getMode()==STILL)
							currentPartial.setMode(STILL);
					}
				}
				
			}
			if(currentPartial.getAllPositions().size()>0){
				currentPartial.setEnd(currentPartial.getAllPositions().get(currentPartial.getAllPositions().size()-1).getTimestamp());
				lengthOfPartial(currentPartial);
				if(currentPartial.getBeaconId()!=null && !currentPartial.getBeaconId().isEmpty())
					currentPartial.setMode(ON_BUS);
				partials.add(currentPartial);
			}
		}
		
		for(int i=0;i<partials.size();i++){
			PartialTravel p = partials.get(i);
			
			if(!isValidStep(p)){
				aggregateStep(i, partials);
			}
		}
		
		if(partials.size()>0){
			Travel travel = new Travel();
			travel.setPassengerId(tempTravel.getPassengerId());
			travel.setStart(partials.get(0).getStart());
			travel.setEnd(partials.get(partials.size()-1).getEnd());
			travel.setOnBus(atLeastOneBusTravel);
			travel.setPartials(partials);
			Calendar cal = Calendar.getInstance();
			cal.setTime(travel.getStart());
			cal.set(Calendar.HOUR_OF_DAY, 12);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND, 0);
			travel.setDayTimestamp(cal.getTime().getTime());
			lengthOfTravel(travel, tempTravel.getMissingPoint());
			
			if(travel.isOnBus()){
				for(PartialTravel p: travel.getPartials()){
					if(p.getBeaconId()!=null && !p.getBeaconId().isEmpty()){
						saveRun(p, travel.getPassengerId(), travel.getDayTimestamp(), date.format(travel.getStart()));
						
					}
				}
			}
			travelRepo.save(travel);
		}
		
		if(tempTravel.getId()!=null && !tempTravel.getId().isEmpty())
			tempTravelRepo.delete(tempTravel.getId());
	}
	
	/*questo metodo cerca se c'è già una corsa con quell'idRun, idLine, day salvata. Se c'è incrementa il numero di persone su quella corsa di 1 e
	/aggiorna i valori di salite/discese delle specifiche fermate sui cui il passeggero è salito/sceso. */
	
	private void saveRun(PartialTravel p, String passengerId, long timestamp, String day){
		System.out.println(p.getAllPositions().size());
		List<BusStop> stops = new ArrayList<BusStop>();
		Bus bus = busRepo.findByBeaconId(p.getBeaconId());
		String idLine = bus.getIdLine();
		stops = getBusStops(p, idLine);
		if(stops!=null){
			p.setIdRun(stops.get(0).getIdRun());
			
			Run run = runRepo.findByIdRunAndIdLineAndDay(p.getIdRun(), idLine, day);
			if(run==null){
				run = new Run();
				run.setDay(day);
				run.setTimestamp(timestamp);
				run.setIdRun(p.getIdRun());
				run.setIdLine(idLine);
				if(stops.get(0).getIdProg()>=0){
					run.setDirection(false);
					p.setDirection(false);
				}
				else{
					run.setDirection(true);
					p.setDirection(true);
				}
				List<BusStop> allRunStops = busStopRepo.findByIdRun(run.getIdRun(), new Sort(Direction.ASC,"idProg"));
				for(BusStop s : allRunStops){
					StopInfo si = new StopInfo(s);
					
					if(s.getIdStop().equals(stops.get(0).getIdStop()))
						si.setNumPassengerGetIn(1);
					else if(s.getIdStop().equals(stops.get(stops.size()-1).getIdStop()))
						si.setNumPassengerGetOut(1);
					run.addStopInfo(si);
				}
				try{
					runRepo.insert(run);
				}catch(MongoDataIntegrityViolationException e){
					runRepo.updateRun(p.getIdRun(), idLine, day, stops.get(0), stops.get(stops.size()-1));
				}
			}else{
				runRepo.updateRun(p.getIdRun(), idLine, day, stops.get(0), stops.get(stops.size()-1));
			}
		}
	}

//	private void saveBusTravel(PartialTravel p, String passengerId, String day) {
//		BusTravel b = new BusTravel();
//		b.setBeaconId(p.getBeaconId());
//		b.setPassengerId(passengerId);
//		b.setDay(day);
//		List<BusStop> stops = getBusStops(p);
//		
//		
//		if(stops==null){
//			System.out.println("NO ONE STOP");
//			return;
//		}
//		p.setIdRun(stops.get(0).getIdRun());
//		b.setStops(stops);
//		if(stops.get(0).getIdProg()>=0){
//			b.setDirection(false);//MITO
//		}else{
//			b.setDirection(true);//TOMI
//		}
//		b.setTimestamp(p.getStart());
//		busTravelRepo.save(b);
//	}

	private List<BusStop> getBusStops(PartialTravel p, String idLine) {
		List<BusStop> stops = new ArrayList<BusStop>();
		
		InfoPosition firstPosition=null, lastPosition=null;
	
		int i=0;
		while(i<p.getAllPositions().size()){
			firstPosition = p.getAllPositions().get(i);
			if(firstPosition.getPosition().getLat()==NOPOSITION){
				firstPosition = null;
				i++;
			}
			else
				break;
		}
		i=p.getAllPositions().size()-1;
		while(i>=0){
			lastPosition = p.getAllPositions().get(i);
			if(lastPosition.getPosition().getLat()==NOPOSITION){
				lastPosition = null;
				i--;
			}
			else
				break;
		}
		// a questo punto fistPosition contiene l'InfoPosition relativa al primo log con una posizione
		// del partial travel (o null) e lastPosition l'InfoPosition relativa all'ultimo log con una posizione
		// del partial travel (o null)
		
		
		//first conterrà la lista dei BusStop candidati ad essere l'inizio del viaggio in bus
		//last conterrà la lista dei BusStop candidati ad essere la fine del viaggio in bus
		List<GeoResult<BusStop>> first=null, last=null;
		if(firstPosition!=null && lastPosition!=null && firstPosition!=lastPosition 
				&&(firstPosition.getPosition().getLat()!=lastPosition.getPosition().getLat()
				|| firstPosition.getPosition().getLng()!=lastPosition.getPosition().getLng())){
			
			first = busStopRepo.findNear(firstPosition, idLine).getContent();
			last = busStopRepo.findNear(lastPosition, idLine).getContent();
			
			System.out.println(first+" "+last);
			
			//con questo metodo vado a scegliere il più probabile inizio e la più probabile fine del viaggio in bus
			List<BusStop> firstAndLast = getFirstLastStop(first, last, firstPosition, lastPosition);
			
			if(firstAndLast.size()==2){
				BusStop firstStop = firstAndLast.get(0);
				BusStop lastStop = firstAndLast.get(1);
				stops.add(firstStop);
				stops.addAll(busStopRepo.findBetweenFirstAndLast(firstStop, lastStop));
				stops.add(lastStop);
				return stops;
			}else
				return null;

		}else
			return null;
		
		
//		if(first!=null && first.size()>0 && last!=null && last.size()>0){
//			if(first.get(0).getContent().getIdProg()<last.get(0).getContent().getIdProg() && first.get(0).getContent().getIdProg()*last.get(0).getContent().getIdProg()>0){
//				stops.add(0, first.get(0).getContent());
//				stops.addAll(busStopRepo.findBetweenFirstAndLast(first.get(0).getContent(), last.get(0).getContent()));
//				stops.add(last.get(0).getContent());
//			}else if(last.size()>1 && 
//					first.get(0).getContent().getIdProg()<last.get(1).getContent().getIdProg() && 
//					first.get(0).getContent().getIdProg()*last.get(1).getContent().getIdProg()>0){
//				stops.add(0, first.get(0).getContent());
//				stops.addAll(busStopRepo.findBetweenFirstAndLast(first.get(0).getContent(), last.get(1).getContent()));
//				stops.add(last.get(1).getContent());
//			}else if(first.size()>1 && 
//					first.get(1).getContent().getIdProg()<last.get(0).getContent().getIdProg() && 
//					first.get(1).getContent().getIdProg()*last.get(0).getContent().getIdProg()>0){
//				stops.add(0, first.get(1).getContent());
//				stops.addAll(busStopRepo.findBetweenFirstAndLast(first.get(1).getContent(), last.get(0).getContent()));
//				stops.add(last.get(0).getContent());
//			}else if(first.size()>1 && last.size()>1 && 
//					first.get(1).getContent().getIdProg()<last.get(1).getContent().getIdProg() && 
//					first.get(1).getContent().getIdProg()*last.get(1).getContent().getIdProg()>0){
//				stops.add(0, first.get(1).getContent());
//				stops.addAll(busStopRepo.findBetweenFirstAndLast(first.get(1).getContent(), last.get(1).getContent()));
//				stops.add(last.get(1).getContent());
//			}
//			
//			return stops;
//		}
//		else
//			return null;
	}


	private List<BusStop> getFirstLastStop(List<GeoResult<BusStop>> first, List<GeoResult<BusStop>> last, InfoPosition firstPosition, InfoPosition lastPosition) {
		
		List<BusStop> listToReturn = new ArrayList<BusStop>();
//		List <BusRunDetector> elegibleCouples = new ArrayList<BusRunDetector>();
		BusRunDetector run = null;
		for(int i=0; i< first.size();i++){
			for(int j=0; j<last.size(); j++){
				if(     first.get(i).getContent().getIdRun().equals(last.get(j).getContent().getIdRun())
						&& first.get(i).getContent().getIdProg() < last.get(j).getContent().getIdProg()){
					
					//BusStop f, BusStop l, double distanceF, double distanceL, long logTimeF, long logTimeL
					BusRunDetector possible = new BusRunDetector( first.get(i).getContent(), last.get(j).getContent(),
							first.get(i).getDistance().getValue(), last.get(j).getDistance().getValue(), firstPosition.getHour(), lastPosition.getHour());
					System.out.println("getValue= "+ last.get(j).getDistance().getValue());
					System.out.println("getNormalizedValue= "+ last.get(j).getDistance().getNormalizedValue());
					possible.setFirst(first.get(i).getContent());
					possible.setLast(last.get(j).getContent());
					possible.evaluateGoodIndex();
					if(run==null || run.getGoodIndex()>possible.getGoodIndex())
						run = possible;
//					elegibleCouples.add(possible);
					
				}
			}
		}
		
		
		if(run!=null){
			listToReturn.add(run.getFirst());
			listToReturn.add(run.getLast());
		}
		
		return listToReturn;

	}

//	private List<BusStop> bestCouplePossible(List<BusRunDetector> elegibleCouples){
//		return null;
//	}
//	
	
	private void aggregateStep(int i, List<PartialTravel> partials) {
		PartialTravel prev = null, next=null, toAggregate=null;
		System.out.println("AGGREGATION");

		toAggregate = partials.get(i);
		if(i>0)
			prev = partials.get(i-1);
		if(i<partials.size()-1)
			next = partials.get(i+1);
		
		if(prev!=null && next!=null && prev.getMode()==next.getMode()){
				PartialTravel newPartial = new PartialTravel();
				newPartial.setMode(prev.getMode());
				newPartial.setStart(prev.getStart());
				newPartial.setEnd(next.getEnd());
				newPartial.setBeaconId(prev.getBeaconId());
				List<InfoPosition> positions = new ArrayList<InfoPosition>();
				positions.addAll(prev.getAllPositions());
				positions.addAll(toAggregate.getAllPositions());
				positions.addAll(next.getAllPositions());
				newPartial.setAllPositions(positions);
				lengthOfPartial(newPartial);
				partials.add(i-1, newPartial);
				partials.remove(prev);
				partials.remove(toAggregate);
				partials.remove(next);
		}else{
			if(prev!=null && next!=null){
				long prevTimeDistance = toAggregate.getStart().getTime()-prev.getEnd().getTime();
				long nextTimeDistance = next.getStart().getTime()-toAggregate.getEnd().getTime();
				if(prevTimeDistance<nextTimeDistance){
					if(prev.getBeaconId()==null || prev.getBeaconId().isEmpty()){
						PartialTravel newPartial = new PartialTravel();
						newPartial.setMode(prev.getMode());
						newPartial.setStart(prev.getStart());
						newPartial.setEnd(toAggregate.getEnd());
						newPartial.setBeaconId(prev.getBeaconId());
						List<InfoPosition> positions = new ArrayList<InfoPosition>();
						positions.addAll(prev.getAllPositions());
						positions.addAll(toAggregate.getAllPositions());
						newPartial.setAllPositions(positions);
						
						lengthOfPartial(newPartial);
						partials.add(i-1, newPartial);
						partials.remove(prev);
						partials.remove(toAggregate);
					}
				}else{
					if(next.getBeaconId()==null || next.getBeaconId().isEmpty()){
						PartialTravel newPartial = new PartialTravel();
						newPartial.setMode(next.getMode());
						newPartial.setStart(toAggregate.getStart());
						newPartial.setEnd(next.getEnd());
						newPartial.setBeaconId(next.getBeaconId());
						List<InfoPosition> positions = new ArrayList<InfoPosition>();
						positions.addAll(toAggregate.getAllPositions());
						positions.addAll(next.getAllPositions());
						newPartial.setAllPositions(positions);
						lengthOfPartial(newPartial);
						partials.add(i, newPartial);
						partials.remove(toAggregate);
						partials.remove(next);
					}
				}
			}else if(prev!=null){
				if(prev.getBeaconId()==null || prev.getBeaconId().isEmpty()){
					PartialTravel newPartial = new PartialTravel();
					newPartial.setMode(prev.getMode());
					newPartial.setStart(prev.getStart());
					newPartial.setEnd(toAggregate.getEnd());
					newPartial.setBeaconId(prev.getBeaconId());
					List<InfoPosition> positions = new ArrayList<InfoPosition>();
					positions.addAll(prev.getAllPositions());
					positions.addAll(toAggregate.getAllPositions());
					newPartial.setAllPositions(positions);
					
					lengthOfPartial(newPartial);
					partials.add(i-1, newPartial);
					partials.remove(prev);
					partials.remove(toAggregate);
				}
			}else if(next!=null){
				if(next.getBeaconId()==null || next.getBeaconId().isEmpty()){
					PartialTravel newPartial = new PartialTravel();
					newPartial.setMode(next.getMode());
					newPartial.setStart(toAggregate.getStart());
					newPartial.setEnd(next.getEnd());
					newPartial.setBeaconId(next.getBeaconId());
					List<InfoPosition> positions = new ArrayList<InfoPosition>();
					positions.addAll(toAggregate.getAllPositions());
					positions.addAll(next.getAllPositions());
					newPartial.setAllPositions(positions);
				
					lengthOfPartial(newPartial);
					partials.add(i, newPartial);
					partials.remove(toAggregate);
					partials.remove(next);
				}
			}else{
				partials.remove(i);
			}
		}
	}

	private boolean isValidStep(PartialTravel p) {
		int mode = p.getMode();
		long duration = getDuration(p);
		
		switch(mode){
		case IN_VEHICLE : {
			if(duration>FIVE_MINUTES)
				return true;
			else 
				return false;
		}
		case ON_BICYCLE : {
			if(duration>THREE_MINUTES)
				return true;
			else 
				return false;
		}
		case ON_FOOT : {
			if(duration>ONE_MINUTE)
				return true;
			else 
				return false;
		}
		case RUNNING : {
			if(duration>ONE_MINUTE)
				return true;
			else 
				return false;
		}
		case WALKING : {
			if(duration>ONE_MINUTE)
				return true;
			else 
				return false;
		}
		case ON_BUS : {
			if(duration>ONE_MINUTE)
				return true;
			else
				return false;
		}
		case STILL : {
			if(p.getLengthTravel()>STILL_THREESHOLD && duration>ONE_MINUTE){
				p.setMode(UNKNOWN);
				return true;
			}
			else 
				return false;
		}
		}
		return false;
	}

	private long getDuration(PartialTravel p) {
		if(p!=null && p.getStart()!=null && p.getEnd()!=null){
			return p.getEnd().getTime()-p.getStart().getTime();
		}
		return 0;
	}

	private void lengthOfPartial(PartialTravel partial){
		
		double distance=0d;
		int sameDistancePoints=0;
		if(partial!=null){
			List<InfoPosition> points = partial.getAllPositions();
			if(points.size()>1){
				InfoPosition before = points.get(0);
				InfoPosition actual = null;
				for(int i=1; i<points.size(); i++){
					actual = points.get(i);
					long intervalTime = actual.getTimestamp().getTime()-before.getTimestamp().getTime();
					if(before.getPosition().getLat()==actual.getPosition().getLat() 
							&& before.getPosition().getLng()==actual.getPosition().getLng() && intervalTime>ACCURACY_TIME_THREESHOLD){
						sameDistancePoints++;
					}else{
						distance+=distFrom(before.getPosition().getLat(), before.getPosition().getLng(), actual.getPosition().getLat(), actual.getPosition().getLng());
					}
					before = points.get(i);
				}
				partial.setLengthTravel(distance);
				if(distance>0)
					partial.setLengthAccuracy(100-((sameDistancePoints*100)/points.size()));
				else
					partial.setLengthAccuracy(0);
			}
		}
	}

	private void lengthOfTravel(Travel travel, int missingPoints) {
		double length=0;
		double accuracy=0;
		int points=0;
		long totalDuration=0;
		long haveAccuracyDuration=0;
		for(PartialTravel p : travel.getPartials()){
			length+=p.getLengthTravel();
			accuracy+=(p.getLengthAccuracy()*p.getLengthTravel());
			points+=p.getAllPositions().size();
			long actualDuration = getDuration(p);
			totalDuration+=actualDuration;
			if(p.getLengthTravel()>0)
				haveAccuracyDuration+=actualDuration;
				
		}
		if(length!=0){
			accuracy=(accuracy/length)*(points/(points+missingPoints))*(haveAccuracyDuration/totalDuration);
		}
		else
			accuracy=0;
		travel.setLengthTravel(length);
		travel.setLengthAccuracy(accuracy);
	}

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

	private boolean isMovement(int mode) {
		if(mode==IN_VEHICLE || mode==ON_BICYCLE || mode==ON_FOOT || mode==WALKING || mode==RUNNING)
			return true;
		
		return false;
	}
	
	private void printMode(DetectedPosition p){		
		switch(p.getMode()){
		case IN_VEHICLE : System.out.println("IN_VEHICLE");
						  break;
		case ON_BICYCLE : System.out.println("ON_BICYCLE");
		                  break;
		case ON_FOOT : System.out.println("ON_FOOT");
					   break;
		case STILL : System.out.println("STILL");
					 break;
		case UNKNOWN : System.out.println("UNKNOWN");
		   			   break;
		case TILTING : System.out.println("TILTING");
		   			   break;
		case WALKING : System.out.println("WALKING");
		   			   break;
		case RUNNING : System.out.println("RUNNING");
		   			   break;
		case ENTER : System.out.println("ENTER");
		   			 break;
		case EXIT : System.out.println("EXIT");
		   			break;
		case ONCREATE : System.out.println("ONCREATE");
						break;
		case ONDESTROY : System.out.println("ONDESTROY");
		   				 break;
		case ON_BUS : System.out.println("ON_BUS");
		   			  break;
		}
	}

	@Override
	public DailyData getDailyData(String passengerId) {
		DailyData dd = new DailyData();
		List<DailyInfo> tomi = runRepo.getDailyInfoTomi();
		List<DailyInfo> mito = runRepo.getDailyInfoMito();

		List<DayPassengerBusRun> lTomi = travelRepo.countToMiDailyBusTravel(passengerId);
		List<DayPassengerBusRun> lMito = travelRepo.countMiToDailyBusTravel(passengerId);

		dd.setMito(mito);
		for(DayPassengerBusRun d : lTomi){
			if(d.getCount()>0){
				DailyInfo di = searchDailyInfo(tomi, d.getTimestamp());
				if(di!=null)
					di.setMyRoute(true);
			}
		}
		dd.setTomi(tomi);
		for(DayPassengerBusRun d : lMito){
			if(d.getCount()>0){
				DailyInfo di = searchDailyInfo(mito, d.getTimestamp());
				if(di!=null)
					di.setMyRoute(true);
			}
		}
		return dd;
	}

	private DailyInfo searchDailyInfo(List<DailyInfo> list, long day){
		int max = list.size();
		if(max==0)
			return null;
		if(max==1){
			DailyInfo d = list.get(0);
			if(d.getTimestamp()==day)
				return d;
			else 
				return null;
		}else{
			DailyInfo d = list.get(max/2);
			if(d.getTimestamp()-day<0){
				return searchDailyInfo(list.subList(max/2, max), day);
			}else if(d.getTimestamp()-day>0){
				return searchDailyInfo(list.subList(0, max/2), day);
			}else
				return d;
		}
	}
	

}
