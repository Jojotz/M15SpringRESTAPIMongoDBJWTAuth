package RESTApiJWTAuthMongo.model;

import java.io.Serializable;

public class Ranking implements Serializable {

	private double averageWinRateAllPlayers;
	
	public Ranking() {
		
    }
	
	public Ranking (double averageWinRateAllPlayers) {
		this.averageWinRateAllPlayers = averageWinRateAllPlayers;
	}

	public Double getAverageWinRateAllPlayers() {
		return averageWinRateAllPlayers;
	}

	public void setAverageWinRateAllPlayers(Double averageWinRateAllPlayers) {
		this.averageWinRateAllPlayers = averageWinRateAllPlayers;
	}
	
	
}
