package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UserDTO;

public interface IRemoteFacade extends Remote {	
	public long login(String email, String password) throws RemoteException;
	public void logout(long token) throws RemoteException;
	public long register(UserDTO userDetails) throws RemoteException;
	public boolean createTrainingSession(long token, TrainingSessionDTO trainingSessionDetails) throws RemoteException;
	public List<ChallengeDTO> getActiveChallenges(long token) throws RemoteException;
	public boolean acceptChallenge(long token, String challengeName) throws RemoteException;
	public boolean createChallenge(long token, ChallengeDTO challengeDetails) throws RemoteException;
	public List<ChallengeDTO> getAllChallenges(long token) throws RemoteException;
	public List<TrainingSessionDTO> getAllTrainingSessions(long token) throws RemoteException;
	public List<TrainingSessionDTO> getPersonalTrainingSessions(long token) throws RemoteException;
}