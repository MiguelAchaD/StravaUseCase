package es.deusto.ingenieria.sd.auctions.server.services;

import java.util.HashMap;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Challenge;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class UserAppService {
	
	private static UserAppService instance = new UserAppService(); 
	
	private Map<Integer, User> users = new HashMap<Integer, User>(
		    Map.of(
		        "diego.merino@opendeusto.es".hashCode(),
		        new User(
		            "diego.merino@opendeusto.es", 
		            org.apache.commons.codec.digest.DigestUtils.sha1Hex("123"),
		            "Diego", 
		            "2003-09-06", 
		            80.0f, 
		            1.80f, 
		            200, 
		            80 
		        ),
		        "user1@gmail.com".hashCode(),
		        new User(
		            "user1@gmail.com",
		            org.apache.commons.codec.digest.DigestUtils.sha1Hex("123"),
		            "User 1",
		            "2005-12-13",
		            70.0f,
		            1.75f,
		            190,
		            60
		        ),
		        "user2@gmail.com".hashCode(),
		        new User(
		            "user2@gmail.com",
		            org.apache.commons.codec.digest.DigestUtils.sha1Hex("123"),
		            "User 2",
		            "2004-02-01",
		            65.0f,
		            1.65f,
		            180,
		            70
		        )
		    )
		);

	private UserAppService() { }
	
	public static UserAppService getInstance() {
		return instance;
	}
		
	public User login(String email, String password) {
		
		if(users.containsKey(email.hashCode())) {
			User user = users.get(email.hashCode());
			if (user.getEmail().equals(email) && user.checkPassword(password)) {		
				return user;
			}
		}
		return null;
	}
	
	public User register(String email, String birthDate, double weight, double height, int restingHeartRate, int maxHeartRate, String name, String password) {
		User user = new User(email, password, name, birthDate, weight, height, maxHeartRate, restingHeartRate);
		return users.putIfAbsent(user.getEmail().hashCode(), user) != user? user: null;
	}
	
	public User getUser(int key) {
		return users.get(key);
	}
	
	public boolean acceptChallenge(User user, Challenge challenge) {
		if(challenge == null) return false;
		users.get(user.getEmail().hashCode()).addChallenge(challenge);
		return true;
	}
	
	public List<Challenge> getActiveChallenges(User user) {
	    List<Challenge> challenges = users.get(user.getEmail().hashCode()).getActiveChallenges();

	    return challenges.stream()
	                     .filter(challenge -> 
	                         challenge.getEndDate().toInstant()
	                         .atZone(ZoneId.systemDefault())
	                         .toLocalDate()
	                         .isAfter(LocalDate.now()))
	                     .collect(Collectors.toList());
	}
}