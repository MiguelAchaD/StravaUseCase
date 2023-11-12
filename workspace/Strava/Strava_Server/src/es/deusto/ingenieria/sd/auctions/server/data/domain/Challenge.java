package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class Challenge {	
	private String name;
	private Date startDate;
	private Date endDate;
	private double targetDistance;
	private Duration targetTime;
	private User creator;
	private List<User> acceptedUsers = new ArrayList<>();
	private SportType sportType;
	
	
	
	public Challenge(String name, Date startDate, Date endDate, double targetDistance, Duration targetTime, User creator, SportType sportType) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.targetDistance = targetDistance;
		this.targetTime = targetTime;
		this.creator = creator;
		this.sportType = sportType;
	}
	
	
	public String getName() {
		return name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public double getTargetDistance() {
		return targetDistance;
	}
	public Duration getTargetTime() {
		return targetTime;
	}
	public User getCreator() {
		return creator;
	}
	public List<User> getAcceptedUsers() {
		return acceptedUsers;
	}
	public SportType getSportType() {
		return sportType;
	}
	
}