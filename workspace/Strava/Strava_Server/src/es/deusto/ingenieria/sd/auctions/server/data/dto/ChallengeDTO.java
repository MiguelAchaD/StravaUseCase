package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.io.Serializable;
import java.time.Duration;

public class ChallengeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String startDate;
    private String endDate;
    private double targetDistance;
    private Duration targetTime;
    private String sportType;
    
	public void setName(String name) {
		this.name = name;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setTargetDistance(double targetDistance) {
		this.targetDistance = targetDistance;
	}
	public void setTargetTime(Duration targetTime) {
		this.targetTime = targetTime;
	}
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	public String getName() {
		return name;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public double getTargetDistance() {
		return targetDistance;
	}
	public Duration getTargetTime() {
		return targetTime;
	}
	public String getSportType() {
		return sportType;
	}
	@Override
	public String toString() {
		return name.toUpperCase() + "(" + sportType + ") " + targetDistance + "kms in " + targetTime.toMinutes() + " minutes (" + startDate + " to " + endDate + ")";
	}
	
	
}
