package es.deusto.ingenieria.sd.auctions.server.services;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.domain.TrainingSession;

public class TrainingSessionAppService {
	
	private static TrainingSessionAppService instance = new TrainingSessionAppService(); 
	
	private List<TrainingSession> trainingSessions = new ArrayList<TrainingSession>();
	
	private TrainingSessionAppService() {
		this.initilizeData();
	}
	
	public static TrainingSessionAppService getInstance() {
		return instance;
	}
	
	private void initilizeData() {
		TrainingSession trainingSession = new TrainingSession("Morning Run", 5.0f, Date.valueOf("2023-06-01"), Time.valueOf("06:00:00"), Duration.ofHours(1));
		trainingSessions.add(trainingSession);
	}
	
	public boolean createTrainingSession(TrainingSession trainingSession) {
		return trainingSessions.add(trainingSession);
	}
}