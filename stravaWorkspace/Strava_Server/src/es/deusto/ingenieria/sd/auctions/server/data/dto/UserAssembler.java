package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.sql.Date;

import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class UserAssembler {
	private static final UserAssembler instance = new UserAssembler();

	private UserAssembler() { }
	
	public static UserAssembler getInstance() {
		return instance;
	}

	public UserDTO userToDTO(User user) {
		UserDTO dto = new UserDTO();
		
		dto.setEmail(user.getEmail());
		dto.setBirthdate(user.getBirthdate().toString());
		dto.setHeight(user.getHeight());
		dto.setMaxHeartRate(user.getMaxHeartRate());
		dto.setName(user.getName());
		dto.setRestingHeartRate(user.getRestingHeartRate());
		dto.setWeight(user.getWeight());
		return dto;
	}
	
	public User DTOToUser(UserDTO userDetails) {
		return new User(userDetails.getEmail(), userDetails.getPassword(), userDetails.getName(), Date.valueOf(userDetails.getBirthdate()), userDetails.getWeight(), userDetails.getHeight(), userDetails.getMaxHeartRate(), userDetails.getRestingHeartRate());
	}
}