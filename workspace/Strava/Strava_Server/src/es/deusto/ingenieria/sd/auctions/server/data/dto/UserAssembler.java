package es.deusto.ingenieria.sd.auctions.server.data.dto;

import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class UserAssembler {
	private static UserAssembler instance = new UserAssembler();

	private UserAssembler() { }
	
	public static UserAssembler getInstance() {
		return instance;
	}

	public UserDTO userToDTO(User user) {
		UserDTO dto = new UserDTO();
		
		dto.setEmail(user.getEmail());
		dto.setBirthdate(user.getBirthdate());
		dto.setHeight(user.getHeight());
		dto.setMaxHeartRate(user.getMaxHeartRate());
		dto.setName(user.getName());
		dto.setRestingHeartRate(user.getRestingHeartRate());
		dto.setWeight(user.getWeight());
		
		return dto;
	}
}