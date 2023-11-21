package es.deusto.ingenieria.sd.auctions.server.services;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import es.deusto.ingenieria.sd.auctions.server.data.domain.TrainingSession;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class TrainingSessionAppService {
    private static final TrainingSessionAppService instance = new TrainingSessionAppService();
    private Map<String, TrainingSession> trainingSessions = new HashMap<>();

    private TrainingSessionAppService() {
        this.initilizeData();
    }

    public static TrainingSessionAppService getInstance() {
        return instance;
    }

    private void initilizeData() {
        generateRandomTrainingSessions(20);
    }

    private void generateRandomTrainingSessions(int numberOfSessions) {
        Random random = new Random();
        List<User> userList = new ArrayList<>(UserAppService.getInstance().getUsers().values());

        for (int i = 0; i < numberOfSessions; i++) {
            User user = userList.get(random.nextInt(userList.size()));
            String title = "Session " + (i + 1);
            double distance = 5 + random.nextInt(15);
            LocalDate startDate = getRandomDateIn2023();
            Time startTime = Time.valueOf(String.format("%02d:00:00", random.nextInt(24)));
            Duration duration = Duration.ofHours(1 + random.nextInt(2));

            TrainingSession session = new TrainingSession(title, distance, Date.valueOf(startDate), startTime, duration, user);
            user.addPersonalTrainingSession(session);
            trainingSessions.put(title, session);
        }
    }

    private LocalDate getRandomDateIn2023() {
        Random random = new Random();
        int dayOfYear = 1 + random.nextInt(365); // 2023 no es bisiesto
        return LocalDate.ofYearDay(2023, dayOfYear);
    }
	
	public boolean createTrainingSession(TrainingSession trainingSession) {
		if(trainingSession == null) return false;
		return trainingSessions.putIfAbsent(trainingSession.getTitle(), trainingSession) != trainingSession;
	}
	
	public List<TrainingSession> getAllTrainingSessions() {		
		return new ArrayList<TrainingSession>(trainingSessions.values());
	}
	
	public TrainingSession getTrainingSession(String title) {
		return trainingSessions.get(title);
	}
}