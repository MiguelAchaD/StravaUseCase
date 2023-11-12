package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;

public class TrainingSession {	
	private String title;
	private double distance;
	private Date startDate;
	private Time startTime;
	private Duration duration;
	
	
	public TrainingSession(String title, double distance, Date startDate, Time startTime, Duration duration) {
		super();
		this.title = title;
		this.distance = distance;
		this.startDate = startDate;
		this.startTime = startTime;
		this.duration = duration;
	}
	
	public String getTitle() {
		return title;
	}
	public double getDistance() {
		return distance;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Time getStartTime() {
		return startTime;
	}
	public Duration getDuration() {
		return duration;
	}
		
}