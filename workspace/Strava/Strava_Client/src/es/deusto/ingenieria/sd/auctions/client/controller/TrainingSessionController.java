package es.deusto.ingenieria.sd.auctions.client.controller;

import java.rmi.RemoteException;
import java.sql.Time;
import java.time.Duration;
import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionDTO;

public class TrainingSessionController {
	
	private ServiceLocator serviceLocator;
	
	public TrainingSessionController(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator; 
	}
	
	public boolean createTrainingSession(long token, String title, String startDate, Time startTime, Duration duration, double distance) {
		try {
			TrainingSessionDTO trainingSessionDetails = new TrainingSessionDTO();
			trainingSessionDetails.setTitle(title);
			trainingSessionDetails.setStartDate(startDate);
			trainingSessionDetails.setStartTime(startTime);
			trainingSessionDetails.setDuration(duration);
			trainingSessionDetails.setDistance(distance);
			return serviceLocator.getService().createTrainingSession(token, trainingSessionDetails);
		} catch (RemoteException e) {
			System.out.println("# Error creating the training session: " + e);
			return false;
		}
	}
}