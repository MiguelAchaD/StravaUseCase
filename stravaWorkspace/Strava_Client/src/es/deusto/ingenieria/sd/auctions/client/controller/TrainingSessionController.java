package es.deusto.ingenieria.sd.auctions.client.controller;

import java.rmi.RemoteException;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionDTO;

public class TrainingSessionController {
	
	private ServiceLocator serviceLocator;
	
	public TrainingSessionController(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator; 
	}
	
	public boolean createTrainingSession(String title, String startDate, Time startTime, Duration duration, double distance) {
		try {
			TrainingSessionDTO trainingSessionDetails = new TrainingSessionDTO();
			trainingSessionDetails.setTitle(title);
			trainingSessionDetails.setStartDate(startDate);
			trainingSessionDetails.setStartTime(startTime);
			trainingSessionDetails.setDuration(duration);
			trainingSessionDetails.setDistance(distance);
			return serviceLocator.getService().createTrainingSession(TokenManager.getInstance().getToken(), trainingSessionDetails);
		} catch (RemoteException e) {
			System.out.println("# Error creating the training session: " + e);
			return false;
		}
	}
	
	public List<TrainingSessionDTO> getAllTrainingSessions(){
		try {
			return serviceLocator.getService().getAllTrainingSessions(TokenManager.getInstance().getToken());
		} catch (RemoteException e) {
			System.out.println("# Error getting all challenges: " + e);
			return null;
		}
	}
	
	public List<TrainingSessionDTO> getPersonalTrainingSessions(){
		try {
			return serviceLocator.getService().getPersonalTrainingSessions(TokenManager.getInstance().getToken());
		} catch (RemoteException e) {
			System.out.println("# Error getting all challenges: " + e);
			return null;
		}
	}
}