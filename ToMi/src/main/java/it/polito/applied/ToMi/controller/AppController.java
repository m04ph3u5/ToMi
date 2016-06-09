package it.polito.applied.ToMi.controller;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoDataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.polito.applied.ToMi.exception.BadRequestException;
import it.polito.applied.ToMi.exception.ForbiddenException;
import it.polito.applied.ToMi.exception.NotFoundException;
import it.polito.applied.ToMi.pojo.Bus;
import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.Comment;
import it.polito.applied.ToMi.pojo.DailyData;
import it.polito.applied.ToMi.pojo.DetectedPosition;
import it.polito.applied.ToMi.pojo.Passenger;
import it.polito.applied.ToMi.pojo.RunDTO;
import it.polito.applied.ToMi.pojo.RunDetail;
import it.polito.applied.ToMi.repository.PassengerRepository;
import it.polito.applied.ToMi.sademData.Siri;
import it.polito.applied.ToMi.service.AppService;


@RestController
public class AppController extends BaseController{
	
	@Autowired
	private PassengerRepository passRepo;
	
	@Autowired
	private AppService appService;
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/register", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void registerPassenger(@RequestBody Passenger passenger, @RequestHeader(required=true, value="user") @Email String userEmail) throws BadRequestException, ForbiddenException {
		if(!userEmail.equals(passenger.getEmail()))
			throw new ForbiddenException("Operation not allowed");
		try{
			passenger.setRegistrationDate(new Date());
			passRepo.save(passenger);
		}catch(MongoDataIntegrityViolationException e){
			throw new BadRequestException("User already registered");
		}
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/user", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Passenger getPassenger(@RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		else 
			return p;
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/user", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void updatePassenger(@RequestBody Passenger passenger, @RequestHeader(required=true, value="user") @Email String userEmail) throws ForbiddenException {
		if(!userEmail.equals(passenger.getEmail()))
			throw new ForbiddenException("Operation not allowed");
		
		passRepo.updatePassenger(passenger);
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/position", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void savePosition(@RequestBody List<DetectedPosition> positions, @RequestHeader(required=true, value="user")  String userEmail) throws BadRequestException, ForbiddenException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new ForbiddenException("Operation not allowed");
		if(positions.size()>0)
			appService.saveDetectedPosition(positions, p);
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/myPositions", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<DetectedPosition> getMyPositions(@RequestHeader(required=true, value="user") @Email String userEmail, @RequestParam(value = "start", required=true) long start, @RequestParam(value = "end", required=true) long end) throws NotFoundException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		return appService.getMyPositions(userEmail, start, end);
		
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/busStop", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<BusStop> getBusStops(@RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		return appService.getAllBusStop();
		
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/bus", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Bus> getBus(@RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		return appService.getAllBus();
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/comment", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void postComment(@RequestBody List<Comment> comments, @RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		
		appService.saveComments(comments, userEmail);
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/comment/{id}", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void postAnswerToComment(@PathVariable String id, @RequestBody List<Comment> answers, @RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException, BadRequestException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		
		appService.saveAnswerToComments(id, answers, userEmail);
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/comment", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Comment> getComment(@RequestParam(value = "last", required=false) String lastId, @RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		
		return appService.getComments(lastId);
		
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/dailyData", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public DailyData getDailyData(@RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		
		return appService.getDailyData(p.getId());
		
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/runs", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<RunDTO> getRuns(@RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		
		return appService.getRuns();
		
	}
	
	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/runDetails", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<RunDetail> getRunDetails(@RequestParam(required=true, value="date") long timestamp, @RequestHeader(required=true, value="user") @Email String userEmail) throws NotFoundException, BadRequestException {
		Passenger p = passRepo.findByEmail(userEmail);
		if(p==null)
			throw new NotFoundException("User not found");
		
		if(timestamp==0)
			throw new BadRequestException("Give valide date");
		
		return appService.getRunDetails(timestamp, p.getId());
		
	}
	
//	@RequestMapping(value="/v1/siri", method=RequestMethod.POST)
//	@ResponseStatus(value = HttpStatus.CREATED)
//	public void postSiri(@RequestBody Siri siri) throws NotFoundException {
//		System.out.println(siri);
//	}
}
