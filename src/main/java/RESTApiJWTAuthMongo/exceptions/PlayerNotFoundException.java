package RESTApiJWTAuthMongo.exceptions;

public class PlayerNotFoundException extends RuntimeException {

	public PlayerNotFoundException(String playerId) {
		super("Could not find player " + playerId);
	}
}