package it.polito.applied.ToMi.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.polito.applied.ToMi.pojo.DetectedPosition;
import it.polito.applied.ToMi.repository.DetectedPositionRepository;

public class AppServiceImpl implements AppService{
	
	@Autowired
	private DetectedPositionRepository posRepo;

	@Override
	public void saveDetectedPosition(DetectedPosition position) {
		posRepo.save(position);
		//TODO aggiungere logica aggregazione a seconda di come saranno i dati forniti dalla società di bus
	}

}
