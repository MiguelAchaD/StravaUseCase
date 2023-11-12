package es.deusto.ingenieria.sd.auctions.client.controller;

import java.rmi.RemoteException;

import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;

public class UserController {	
	
	private ServiceLocator serviceLocator;

	private long token = -1;

	public UserController(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
	
	public boolean login(String email, String password) {
		try {
			this.token = this.serviceLocator.getService().login(email, org.apache.commons.codec.digest.DigestUtils.sha1Hex(password));
			return true;
		} catch (RemoteException e) {
			System.out.println("# Error during login: " + e);
			this.token = -1;
			return false;
		}
	}
	
	public boolean register(String email, String birthDate, double weight, double height, int restingHeartRate, int maxHeartRate, String name, String password) {
		try {
			this.token = this.serviceLocator.getService().register(email, birthDate, weight, height, restingHeartRate, maxHeartRate, name, password) ;
			return true;
		} catch (RemoteException e) {
			System.out.println("# Error during registration: " + e);
			this.token = -1;
			return false;
		}
	}
	
	public void logout() {
		try {
			this.serviceLocator.getService().logout(this.token);
			this.token = -1;
		} catch (RemoteException e) {
			System.out.println("# Error during logout: " + e);
		}
	}

	public long getToken() {
		return token;
	}
}