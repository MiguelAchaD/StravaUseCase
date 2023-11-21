package es.deusto.ingenieria.sd.auctions.server.services;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Challenge;
import es.deusto.ingenieria.sd.auctions.server.data.domain.SportType;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class ChallengeAppService {
    private static final ChallengeAppService instance = new ChallengeAppService();
    private Map<String, Challenge> challenges = new HashMap<>();

    private ChallengeAppService() {
        this.initilizeData();
    }

    public static ChallengeAppService getInstance() {
        return instance;
    }

    private void initilizeData() {
        generateRandomChallenges(15);
    }

    private void generateRandomChallenges(int numberOfChallenges) {
        Random random = new Random();
        List<User> userList = new ArrayList<>(UserAppService.getInstance().getUsers().values());
        SportType[] sportTypes = SportType.values();

        for (int i = 0; i < numberOfChallenges; i++) {
            User user = userList.get(random.nextInt(userList.size()));
            String name = "Challenge " + (i + 1);
            double targetDistance = 10 + random.nextInt(90);
            Duration targetTime = Duration.ofHours(2 + random.nextInt(4));
            SportType sportType = sportTypes[random.nextInt(sportTypes.length)];
            LocalDate startDate = getRandomDateIn2023();
            LocalDate endDate = startDate.plusDays(random.nextInt(30) + 1);

            Challenge challenge = new Challenge(name, Date.valueOf(startDate), Date.valueOf(endDate), targetDistance, targetTime, user, sportType);
            challenges.put(name, challenge);
        }
    }

    private LocalDate getRandomDateIn2023() {
        Random random = new Random();
        int dayOfYear = 1 + random.nextInt(365);
        return LocalDate.ofYearDay(2023, dayOfYear);
    }
	
	
	public boolean createChallenge(Challenge challenge) {
		return challenges.putIfAbsent(challenge.getName(), challenge) != challenge;
	}
	
	public List<Challenge> getAllChallenges() {		
		return new ArrayList<Challenge>(challenges.values());
	}
	
	public Challenge getChallenge(String name) {
		return challenges.get(name);
	}
}