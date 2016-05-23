package it.polito.applied.ToMi.service;

import java.util.List;

import it.polito.applied.ToMi.exception.BadRequestException;
import it.polito.applied.ToMi.pojo.Bus;
import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.Comment;
import it.polito.applied.ToMi.pojo.DailyData;
import it.polito.applied.ToMi.pojo.DetectedPosition;
import it.polito.applied.ToMi.pojo.Passenger;
import it.polito.applied.ToMi.pojo.RunDTO;

public interface AppService {

	public void saveDetectedPosition(List<DetectedPosition> position, Passenger passenger);

	public List<DetectedPosition> getMyPositions(String userEmail, long start, long end);

	public List<BusStop> getAllBusStop();

	public List<Bus> getAllBus();

	public void saveComments(List<Comment> comments, String userEmail);

	public List<Comment> getComments(String lastId);

	public DailyData getDailyData(String passengerId);
	
	public void saveAnswerToComments(String id, List<Comment> answers, String userEmail) throws BadRequestException ;

	public List<RunDTO> getRuns();
}
