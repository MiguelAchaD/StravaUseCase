package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.TrainingSessionDTO;

public interface IRemoteFacade extends Remote {	
	public long login(String email, String password) throws RemoteException;
	public long register(String email, String birthDate, double weight, double height, int restingHeartRate, int maxHeartRate, String name, String password) throws RemoteException;
	public void logout(long token) throws RemoteException; 
	public boolean createTrainingSession(long token, TrainingSessionDTO trainingSessionDetails) throws RemoteException;
	public boolean acceptChallenge(long token, String challengeTitle) throws RemoteException;
	public List<ChallengeDTO> getActiveChallenges(long token) throws RemoteException;
	public List<ChallengeDTO> getAllChallenges(long token) throws RemoteException;
	public boolean createChallenge(long token, ChallengeDTO challengeDetails) throws RemoteException;
}