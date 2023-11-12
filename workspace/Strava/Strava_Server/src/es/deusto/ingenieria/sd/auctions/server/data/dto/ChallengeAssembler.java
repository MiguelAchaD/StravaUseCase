package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Challenge;
import es.deusto.ingenieria.sd.auctions.server.data.domain.SportType;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class ChallengeAssembler {

    private static ChallengeAssembler instance = new ChallengeAssembler();

    private ChallengeAssembler() {}

    public static ChallengeAssembler getInstance() {
        return instance;
    }

    public ChallengeDTO challengeToDTO(Challenge challenge) {
        ChallengeDTO dto = new ChallengeDTO();

        dto.setName(challenge.getName());
        dto.setStartDate(challenge.getStartDate().toString());
        dto.setEndDate(challenge.getEndDate().toString());
        dto.setTargetDistance(challenge.getTargetDistance());
        dto.setTargetTime(challenge.getTargetTime());
        dto.setSportType(challenge.getSportType().toString().toLowerCase());

        return dto;
    }
    
    public List<ChallengeDTO> challengeToDTO(List<Challenge> challenges){
    	if(challenges == null) return null;
    	List<ChallengeDTO> DTOs = new ArrayList<ChallengeDTO>();
    	for (Challenge challenge : challenges) {
    		DTOs.add(challengeToDTO(challenge));
    	}
    	return DTOs;
    }
    
    public Challenge DTOToChallenge(ChallengeDTO challengeDetails, User creator) {
    	return new Challenge(challengeDetails.getName(), Date.valueOf(challengeDetails.getStartDate()), Date.valueOf(challengeDetails.getEndDate()), challengeDetails.getTargetDistance(), challengeDetails.getTargetTime(), creator, SportType.valueOf(challengeDetails.getSportType()));
    }
}
