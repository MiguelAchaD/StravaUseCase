package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Challenge;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UserAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UserDTO;
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
	public synchronized long register(UserDTO userDetails) throws RemoteException {
		System.out.println(" * RemoteFacade register(): " + userDetails.getName());
		User user = UserAppService.getInstance().register(UserAssembler.getInstance().DTOToUser(userDetails));
		if (user == null) {
			throw new RemoteException("User is already registered in!");
		}
		return localLogin(user);
	}
	
	private long localLogin(User user) throws RemoteException {
		System.out.println(" * RemoteFacade localLogin(): " + user.getEmail());
		if (!serverState.values().contains(user)) {
			Long token = Calendar.getInstance().getTimeInMillis();
			serverState.put(token, user);
			return token;
		} else {
			throw new RemoteException("User is already logged in!");
		}
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
			return TrainingSessionAppService.getInstance().createTrainingSession(TrainingSessionAssembler.getInstance().DTOToTrainingSession(trainingSessionDetails, serverState.get(token)));
		} else {
			throw new RemoteException("To create a training session you must first log in");
		}
	}
	
	@Override
	public List<TrainingSessionDTO> getAllTrainingSessions(long token) throws RemoteException {
		System.out.println(" * RemoteFacade getAllChallenges(" + token + ")");
		if (this.serverState.containsKey(token)) {						
			return TrainingSessionAssembler.getInstance().trainingSessionToDTO(TrainingSessionAppService.getInstance().getAllTrainingSessions());
		} else {
			throw new RemoteException("You must be logged in to see all the challenges");
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
			List<Challenge> activeChallenges = UserAppService.getInstance().getActiveChallenges(serverState.get(token));
			Map<String, Double> progresses = serverState.get(token).getProgresses();
			return ChallengeAssembler.getInstance().challengeToDTO(activeChallenges, progresses);
		} else {
			throw new RemoteException("You must be logged in to see your active challenges");
		}		
	}
	
	@Override
	public List<TrainingSessionDTO> getPersonalTrainingSessions(long token) throws RemoteException{
		System.out.println(" * RemoteFacade getActiveChallenges(" + token + ")");
		if (this.serverState.containsKey(token)) {						
			return TrainingSessionAssembler.getInstance().trainingSessionToDTO(UserAppService.getInstance().getPersonalTrainingSessions(serverState.get(token)));
		} else {
			throw new RemoteException("You must be logged in to see your personal training Sessions");
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
	public boolean acceptChallenge(long token, String challengeName) throws RemoteException{
		System.out.println(" * RemoteFacade acceptChallenge(" + token + ", " + challengeName + ")");
		if (this.serverState.containsKey(token)) {						
			return UserAppService.getInstance().acceptChallenge(serverState.get(token), ChallengeAppService.getInstance().getChallenge(challengeName));
		} else {
			throw new RemoteException("You must be logged in to accept a challenge");
		}
	}
}