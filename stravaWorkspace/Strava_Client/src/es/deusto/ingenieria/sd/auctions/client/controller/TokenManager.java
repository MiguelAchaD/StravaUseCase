package es.deusto.ingenieria.sd.auctions.client.controller;

public class TokenManager {
	private long token = -1;
	private static TokenManager instance = new TokenManager();
	private TokenManager() {}
	
	public static TokenManager getInstance() {
		return instance;
	}

	public long getToken() {
		return token;
	}
	
	public void setToken(long token) {
		if (token > 0) this.token = token;
	}
	
	public void resetToken() {
		token = -1;
	}
}