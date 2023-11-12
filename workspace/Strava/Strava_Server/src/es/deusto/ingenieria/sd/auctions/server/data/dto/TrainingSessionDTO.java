package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;

public class TrainingSessionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private double distance;
    private String startDate;
    private Time startTime;
    private Duration duration;
    
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public String getTitle() {
		return title;
	}
	public double getDistance() {
		return distance;
	}
	public String getStartDate() {
		return startDate;
	}
	public Time getStartTime() {
		return startTime;
	}
	public Duration getDuration() {
		return duration;
	}
	@Override
	public String toString() {
		return title.toUpperCase();
	}
	
	


}
