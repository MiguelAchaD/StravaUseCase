package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.util.ArrayList;
import java.util.List;

public class User {	
	private String email;
	private String password;
	private String name;
	private String birthdate;
	private double weight;
	private double height;
	private int maxHeartRate;
	private int restingHeartRate;
	private List<Challenge> activeChallenges = new ArrayList<>();
	
	public User(String email, String password, String name, String birthdate, double weight, double height, int maxHeartRate, int restingHeartRate) {
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
	
	public void addChallenge(Challenge challenge) {
		activeChallenges.add(challenge);
	}
	
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getBirthdate() {
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
}