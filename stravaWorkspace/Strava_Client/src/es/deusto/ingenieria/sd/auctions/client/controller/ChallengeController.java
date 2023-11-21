package es.deusto.ingenieria.sd.auctions.client.controller;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeDTO;

public class ChallengeController {
	
	private ServiceLocator serviceLocator;
	
	public ChallengeController(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator; 
	}
	
	public boolean acceptChallenge(String challengeTitle) {
		try {
			return serviceLocator.getService().acceptChallenge(TokenManager.getInstance().getToken(), challengeTitle);
		} catch (RemoteException e) {
			System.out.println("# Error accepting challenge: " + e);
			return false;
		}
	}
	
	public List<ChallengeDTO> getActiveChallenges(){
		try {
			return serviceLocator.getService().getActiveChallenges(TokenManager.getInstance().getToken());
		} catch (RemoteException e) {
			System.out.println("# Error getting active challenges: " + e);
			return null;
		}
	}
	
	public List<ChallengeDTO> getAllChallenges(){
		try {
			return serviceLocator.getService().getAllChallenges(TokenManager.getInstance().getToken());
		} catch (RemoteException e) {
			System.out.println("# Error getting all challenges: " + e);
			return null;
		}
	}
	
	public boolean createChallenge(String name, String startDate, String endDate, Duration duration, String sportType, double distance) {
		try {
			ChallengeDTO challengeDetails = new ChallengeDTO();
			challengeDetails.setName(name);
			challengeDetails.setStartDate(startDate);
			challengeDetails.setEndDate(endDate);
			challengeDetails.setTargetTime(duration);
			challengeDetails.setSportType(sportType);
			challengeDetails.setTargetDistance(distance);
			return serviceLocator.getService().createChallenge(TokenManager.getInstance().getToken(), challengeDetails);
		} catch (RemoteException e) {
			System.out.println("# Error creating the challenge: " + e);
			return false;
		}
	}
	
	
}