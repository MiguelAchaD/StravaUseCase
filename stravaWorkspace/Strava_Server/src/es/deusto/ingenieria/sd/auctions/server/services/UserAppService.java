package es.deusto.ingenieria.sd.auctions.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.sql.Date;
import java.time.LocalDate;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Challenge;
import es.deusto.ingenieria.sd.auctions.server.data.domain.TrainingSession;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class UserAppService {

    private static final UserAppService instance = new UserAppService();
    private Map<String, User> users;

    private UserAppService() {
        users = initializeUsers();
    }

    private Map<String, User> initializeUsers() {
        users = new HashMap<String, User>(
        		Map.of("diego.merino@opendeusto.es",
        				new User(
        						"diego.merino@opendeusto.es", 
        						org.apache.commons.codec.digest.DigestUtils.sha1Hex("123"),
        						"Diego", 
        						Date.valueOf("2003-09-06"), 
        						80.0, 
        						1.80, 
        						200, 
        						80 
        						)
        				)
        		);
        generateRandomUsers(10);
        return users;
    }

    private void generateRandomUsers(int numberOfUsers) {
        Random random = new Random();
        for (int i = 1; i < numberOfUsers + 1; i++) {
            String email = "user" + i + "@gmail.com";
            String name = "User " + i;
            String password = org.apache.commons.codec.digest.DigestUtils.sha1Hex("123");
            LocalDate birthdate = getRandomDateIn2023();
            double weight = 50 + random.nextInt(50);
            double height = 1.5 + random.nextDouble() * 0.5;
            int maxHeartRate = 160 + random.nextInt(40);
            int restingHeartRate = 60 + random.nextInt(20);

            users.put(email, new User(email, password, name, Date.valueOf(birthdate), weight, height, maxHeartRate, restingHeartRate));
        }
    }

    private LocalDate getRandomDateIn2023() {
        Random random = new Random();
        int dayOfYear = 1 + random.nextInt(365);
        return LocalDate.ofYearDay(2023, dayOfYear);
    }
    
    public static UserAppService getInstance() {
        return instance;
    }
        
    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.checkPassword(password)) {        
            return user;
        }
        return null;
    }
    
    public User register(User user) {
        return users.putIfAbsent(user.getEmail(), user) != user ? user : null;
    }
    
    public User getUser(String email) {
        return users.get(email);
    }
    
    public boolean acceptChallenge(User user, Challenge challenge) {
        return users.get(user.getEmail()).addChallenge(challenge);
    }
    
    public List<TrainingSession> getPersonalTrainingSessions(User user){
        return user.getPersonalTrainingSessions();
    }
    
    public List<Challenge> getActiveChallenges(User user) {
        List<Challenge> challenges = user.getActiveChallenges();
        List<Challenge> toRemoveChallenges = new ArrayList<Challenge>();
        for(Challenge challenge: challenges) {
        	if(challenge.getStartDate().before(new Date(System.currentTimeMillis()))) {
        		toRemoveChallenges.add(challenge);
        	}else {
                for (TrainingSession trainingSession : user.getPersonalTrainingSessions()) {
                    if (isTrainingSessionDuringChallenge(trainingSession, challenge)) {
                        user.addProgress(challenge, trainingSession);
                    }
                }
        	}
        }
        challenges.removeAll(toRemoveChallenges);
        return challenges;
    }
    
    private boolean isTrainingSessionDuringChallenge(TrainingSession session, Challenge challenge) {
        long sessionEndTime = session.getStartDate().getTime() + session.getDuration().getSeconds() * 1000 + session.getDuration().getNano();
        return (challenge.getStartDate().before(session.getStartDate()) || challenge.getStartDate().equals(session.getStartDate())) && challenge.getEndDate().after(new Date(sessionEndTime));
    }
    
    protected Map<String, User> getUsers(){
    	return users;
    }

}
