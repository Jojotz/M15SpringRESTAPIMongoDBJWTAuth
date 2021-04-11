package RESTApiJWTAuthMongo.exceptions;

public class DiceRollNotFoundException extends RuntimeException {

	public DiceRollNotFoundException(String playerId) {
		super("No diceRolls for player with id: " + playerId);
	}
}