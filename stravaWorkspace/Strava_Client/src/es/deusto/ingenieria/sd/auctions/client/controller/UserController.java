package es.deusto.ingenieria.sd.auctions.client.controller;

import java.rmi.RemoteException;

import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UserDTO;

public class UserController {	
	
	private ServiceLocator serviceLocator;

	public UserController(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
	
	public boolean login(String email, String password) {
		try {
			TokenManager.getInstance().setToken(serviceLocator.getService().login(email, org.apache.commons.codec.digest.DigestUtils.sha1Hex(password)));
			return true;
		} catch (RemoteException e) {
			System.out.println("# Error during login: " + e);
			return false;
		}
	}
	
	public void logout() {
		try {
			serviceLocator.getService().logout(TokenManager.getInstance().getToken());
			TokenManager.getInstance().resetToken();
		} catch (RemoteException e) {
			System.out.println("# Error during logout: " + e);
		}
	}
	
	public boolean register(String email, String birthDate, double weight, double height, int restingHeartRate, int maxHeartRate, String name, String password) {
		try {
			UserDTO userDetails = new UserDTO();
			userDetails.setEmail(email);
			userDetails.setName(name);
			userDetails.setBirthdate(birthDate);
			userDetails.setWeight(weight);
			userDetails.setHeight(height);
			userDetails.setRestingHeartRate(restingHeartRate);
			userDetails.setMaxHeartRate(maxHeartRate);
			userDetails.setPassword(password);
			TokenManager.getInstance().setToken(serviceLocator.getService().register(userDetails));
			return true;
		} catch (RemoteException e) {
			System.out.println("# Error during registration: " + e);
			return false;
		}
	}
}