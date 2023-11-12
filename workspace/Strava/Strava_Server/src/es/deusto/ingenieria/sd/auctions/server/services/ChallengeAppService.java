package es.deusto.ingenieria.sd.auctions.server.services;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Challenge;
import es.deusto.ingenieria.sd.auctions.server.data.domain.SportType;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class ChallengeAppService {
	
	private static ChallengeAppService instance = new ChallengeAppService(); 
	
	private Map<String, Challenge> challenges = new HashMap<String, Challenge>();
	
	private ChallengeAppService() {
		this.initilizeData();
	}
	
	public static ChallengeAppService getInstance() {
		return instance;
	}
	
	private void initilizeData() {
		
		//Needed to implement singletonepattern in order to keep the references of the hardcoded users
		User user0 = UserAppService.getInstance().getUser("diego.merino@opendeusto.es".hashCode());
		User user1 = UserAppService.getInstance().getUser("user1@gmail.com".hashCode());
								
		Challenge challenge1 = new Challenge(
			    "Mountain Challenge",
			    Date.valueOf("2023-06-01"),
			    Date.valueOf("2023-06-30"),
			    100.0f,
			    Duration.ofHours(5),
			    user0,
			    SportType.CYCLING
			);

			Challenge challenge2 = new Challenge(
			    "City Marathon",
			    Date.valueOf("2023-07-15"),
			    Date.valueOf("2023-08-15"),
			    42.0f,
			    Duration.ofHours(4),
			    user1,
			    SportType.RUNNING
			);
			challenges.put(challenge1.getName(), challenge1);
			challenges.put(challenge2.getName(), challenge2);
	}
	
	
	public boolean createChallenge(Challenge challenge) {
		return challenges.putIfAbsent(challenge.getName(), challenge) != challenge;
	}
	
	public List<Challenge> getAllChallenges() {		
		return new ArrayList<Challenge>(challenges.values());
	}
	
	public Challenge getChallenge(String title) {
		return challenges.get(title);
	}
}