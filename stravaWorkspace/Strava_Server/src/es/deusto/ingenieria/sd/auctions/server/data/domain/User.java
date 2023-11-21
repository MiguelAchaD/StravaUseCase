package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {	
	private String email;
	private String password;
	private String name;
	private Date birthdate;
	private double weight;
	private double height;
	private int maxHeartRate;
	private int restingHeartRate;
	private List<Challenge> activeChallenges = new ArrayList<>();
	private List<TrainingSession> personalTrainingSessions = new ArrayList<TrainingSession>();
	private Map<String, Double> progresses = new HashMap<String, Double>();
	
	public User(String email, String password, String name, Date birthdate, double weight, double height, int maxHeartRate, int restingHeartRate) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.birthdate = birthdate;
		this.weight = weight;
		this.height = height;
		this.maxHeartRate = maxHeartRate;
		this.restingHeartRate = restingHeartRate;
	}
	
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public boolean addChallenge(Challenge challenge) {
		if(!activeChallenges.contains(challenge)) {
			return activeChallenges.add(challenge);
		}
		return false;
	}
	
	public boolean addPersonalTrainingSession(TrainingSession trainingSession) {
		if(!personalTrainingSessions.contains(trainingSession)) {
			return personalTrainingSessions.add(trainingSession);
		}
		return false;
	}
	
    public void addProgress(Challenge challenge, TrainingSession trainingSession) {
        progresses.merge(challenge.getName(), trainingSession.getDistance() / challenge.getTargetDistance(), Double::sum);
    }
	
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public double getWeight() {
		return weight;
	}
	public double getHeight() {
		return height;
	}
	public int getMaxHeartRate() {
		return maxHeartRate;
	}
	public int getRestingHeartRate() {
		return restingHeartRate;
	}
	public List<Challenge> getActiveChallenges(){
		return activeChallenges;
	}
	public List<TrainingSession> getPersonalTrainingSessions(){
		return personalTrainingSessions;
	}
	public Map<String, Double> getProgresses(){
		return progresses;
	}
}