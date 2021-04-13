package RESTApiJWTAuthMongo.security;

public class AuthenticationRequest {

	private String playerName;
	
	private String password;

	public AuthenticationRequest() {
		
	}

	public String gePlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
