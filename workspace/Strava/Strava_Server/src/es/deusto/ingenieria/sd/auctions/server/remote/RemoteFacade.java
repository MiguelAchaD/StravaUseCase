package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.sd.auctions.server.data.domain.User;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionDTO;
import es.deusto.ingenieria.sd.auctions.server.services.ChallengeAppService;
import es.deusto.ingenieria.sd.auctions.server.services.TrainingSessionAppService;
import es.deusto.ingenieria.sd.auctions.server.services.UserAppService;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {	
	private static final long serialVersionUID = 1L;

	private Map<Long, User> serverState = new HashMap<>();
	
	public RemoteFacade() throws RemoteException {
		super();		
	}
	
	@Override
	public synchronized long login(String email, String password) throws RemoteException {
		System.out.println(" * RemoteFacade login(): " + email + " / " + password);
				
		User user = UserAppService.getInstance().login(email, password);
			
		if (user != null) {
			if (!this.serverState.values().contains(user)) {
				Long token = Calendar.getInstance().getTimeInMillis();		
				this.serverState.put(token, user);		
				return(token);
			} else {
				throw new RemoteException("User is already logged in!");
			}
		} else {
			throw new RemoteException("Login fails!");
		}
	}
	
	@Override
	public synchronized long register(String email, String birthDate, double weight, double height, int restingHeartRate, int maxHeartRate, String name, String password) throws RemoteException {
		System.out.println(" * RemoteFacade register(): " + email);
		User user = UserAppService.getInstance().register(email, birthDate, weight, height, restingHeartRate, maxHeartRate, name, password);
		if (user == null) {
			throw new RemoteException("User is already registered in!");
		}
		return login(email, password);
	}
	
	@Override
	public synchronized void logout(long token) throws RemoteException {
		System.out.println(" * RemoteFacade logout(): " + token);
		
		if (this.serverState.containsKey(token)) {
			this.serverState.remove(token);
		} else {
			throw new RemoteException("User is not logged in!");
		}
	}
	
	@Override
	public boolean createTrainingSession(long token, TrainingSessionDTO trainingSessionDetails) throws RemoteException {
		System.out.println(" * RemoteFacade create training session: " + trainingSessionDetails.getTitle());
		
		if (this.serverState.containsKey(token)) {						
			return TrainingSessionAppService.getInstance().createTrainingSession(TrainingSessionAssembler.getInstance().DTOToTrainingSession(trainingSessionDetails));
		} else {
			throw new RemoteException("To create a training session you must first log in");
		}
	}
	
	
	@Override
	public boolean createChallenge(long token, ChallengeDTO challengeDetails) throws RemoteException {
		System.out.println(" * RemoteFacade create challenge: " + challengeDetails.getName());
		
		if (this.serverState.containsKey(token)) {						
			return ChallengeAppService.getInstance().createChallenge(ChallengeAssembler.getInstance().DTOToChallenge(challengeDetails, serverState.get(token)));
		} else {
			throw new RemoteException("To create a training session you must first log in");
		}
	}
	
	@Override
	public List<ChallengeDTO> getActiveChallenges(long token) throws RemoteException {
		System.out.println(" * RemoteFacade getActiveChallenges(" + token + ")");
		if (this.serverState.containsKey(token)) {						
			return ChallengeAssembler.getInstance().challengeToDTO(serverState.get(token).getActiveChallenges());
		} else {
			throw new RemoteException("You must be logged in to see your active challenges");
		}		
	}
	
	@Override
	public List<ChallengeDTO> getAllChallenges(long token) throws RemoteException {
		System.out.println(" * RemoteFacade getAllChallenges(" + token + ")");
		if (this.serverState.containsKey(token)) {						
			return ChallengeAssembler.getInstance().challengeToDTO(ChallengeAppService.getInstance().getAllChallenges());
		} else {
			throw new RemoteException("You must be logged in to see all the challenges");
		}	
	}

	@Override
	public boolean acceptChallenge(long token, String challengeTitle) throws RemoteException{
		System.out.println(" * RemoteFacade acceptChallenge(" + token + ", " + challengeTitle + ")");
		if (this.serverState.containsKey(token)) {						
			return UserAppService.getInstance().acceptChallenge(serverState.get(token), ChallengeAppService.getInstance().getChallenge(challengeTitle));
		} else {
			throw new RemoteException("You must be logged in to accept a challenge");
		}		
	}
}