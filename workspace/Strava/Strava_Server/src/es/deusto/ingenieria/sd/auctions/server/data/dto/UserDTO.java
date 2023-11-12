package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {	
	private static final long serialVersionUID = 1L;	
	
	private String email;
	private String name;
	private String birthdate;
	private double weight;
	private double height;
	private int maxHeartRate;
	private int restingHeartRate;
	
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public void setMaxHeartRate(int maxHeartRate) {
		this.maxHeartRate = maxHeartRate;
	}
	public void setRestingHeartRate(int restingHeartRate) {
		this.restingHeartRate = restingHeartRate;
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

	
}