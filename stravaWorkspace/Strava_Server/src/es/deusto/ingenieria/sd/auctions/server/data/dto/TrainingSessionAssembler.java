package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.domain.TrainingSession;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class TrainingSessionAssembler {

    private static final TrainingSessionAssembler instance = new TrainingSessionAssembler();

    private TrainingSessionAssembler() {}

    public static TrainingSessionAssembler getInstance() {
        return instance;
    }

    public TrainingSessionDTO trainingSessionToDTO(TrainingSession trainingSession) {
        TrainingSessionDTO dto = new TrainingSessionDTO();

        dto.setTitle(trainingSession.getTitle());
        dto.setDistance(trainingSession.getDistance());
        dto.setStartDate(trainingSession.getStartDate().toString());
        dto.setStartTime(trainingSession.getStartTime());
        dto.setDuration(trainingSession.getDuration());

        return dto;
    }
    
    public List<TrainingSessionDTO> trainingSessionToDTO(List<TrainingSession> trainingSessions){
    	if(trainingSessions == null) return null;
    	List<TrainingSessionDTO> DTOs = new ArrayList<TrainingSessionDTO>();
    	for (TrainingSession trainingSession : trainingSessions) {
    		DTOs.add(trainingSessionToDTO(trainingSession));
    	}
    	return DTOs;
    }
    
    public TrainingSession DTOToTrainingSession(TrainingSessionDTO trainingSessionDetails, User user) {
    	TrainingSession trainingSession = new TrainingSession(trainingSessionDetails.getTitle(), trainingSessionDetails.getDistance(), Date.valueOf(trainingSessionDetails.getStartDate()), trainingSessionDetails.getStartTime(), trainingSessionDetails.getDuration(), user);
    	return user.addPersonalTrainingSession(trainingSession)? trainingSession: null;
    }
}
