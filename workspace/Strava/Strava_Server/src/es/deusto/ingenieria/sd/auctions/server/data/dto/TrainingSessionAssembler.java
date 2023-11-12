package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.sql.Date;

import es.deusto.ingenieria.sd.auctions.server.data.domain.TrainingSession;

public class TrainingSessionAssembler {

    private static TrainingSessionAssembler instance = new TrainingSessionAssembler();

    private TrainingSessionAssembler() {}

    public static TrainingSessionAssembler getInstance() {
        return instance;
    }

    public TrainingSessionDTO trainingSessionToDTO(TrainingSession session) {
        TrainingSessionDTO dto = new TrainingSessionDTO();

        dto.setTitle(session.getTitle());
        dto.setDistance(session.getDistance());
        dto.setStartDate(session.getStartDate().toString());
        dto.setStartTime(session.getStartTime());
        dto.setDuration(session.getDuration());

        return dto;
    }
    
    public TrainingSession DTOToTrainingSession(TrainingSessionDTO trainingSessionDetails) {
    	return new TrainingSession(trainingSessionDetails.getTitle(), trainingSessionDetails.getDistance(), Date.valueOf(trainingSessionDetails.getStartDate()), trainingSessionDetails.getStartTime(), trainingSessionDetails.getDuration());
    }
}
