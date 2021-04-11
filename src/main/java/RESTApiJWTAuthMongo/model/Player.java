package RESTApiJWTAuthMongo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection ="player")
public class Player implements Serializable {

	@Id	
	private String playerId;
	
	@Field (name = "playerName")
	@Indexed (unique = true)
	private String playerName;
	
	@Field (name = "password")
	private String password;
	
	@Field (name = "winRate")  //, precision = 5, scale =2
	private double winRate;
		
	
	//	@Temporal(value = TemporalField.TIMESTAMP)	
	@Field (name = "registration_date")      //, updatable = false
	private LocalDateTime registrationDate;
	
	@DBRef 	
	private List<DiceRoll> diceRolls = new ArrayList<>();
	
	public Player() {
		
	}  
	
	public Player(String playerId, String playerName, String password, double winRate, LocalDateTime registrationDate) {
		this.playerId = playerId;
		this.playerName = playerName;
		this.password = password;
		this.winRate = winRate;
		this.registrationDate = LocalDateTime.now();
	}
	
	public Player(String playerName, String password, LocalDateTime registrationDate) {
		this.playerName = playerName;
		this.password = password;
		this.registrationDate = LocalDateTime.now();
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	} 

	public String getPlayerName() {
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

	public double getWinRate() {
		return winRate;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public List<DiceRoll> getDiceRolls() {
		return diceRolls;
	}

	public void setDiceRolls(List<DiceRoll> diceRolls) {
		this.diceRolls = diceRolls;
	}	

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Player))
			return false;
		Player player = (Player) o;
		return Objects.equals(this.playerId, player.playerId) && Objects.equals(this.playerName, player.playerName) 
    		&& Objects.equals(this.password, player.password) && Objects.equals(this.winRate, player.winRate)
    		&& Objects.equals(this.registrationDate, player.registrationDate)
    		&& Objects.equals(this.diceRolls, player.diceRolls);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.playerId, this.playerName, this.password, this.registrationDate);
	}

	@Override
	public String toString() {
	   	return "Player{" + "id=" + this.playerId + ", name='" + this.playerName + '\'' + ", date of registration='" + this.registrationDate + '\'' + '}';
	}

	public double calculateWinRate(Player playerThrowing) {
		
		double dicerolls = playerThrowing.getDiceRolls().size();
		double wins = (double) playerThrowing.getDiceRolls().stream()
				.filter(w -> w.getResult().equals("WIN")).count();
		
		double winRate =  (wins/dicerolls)*100;
		
		return winRate;
	} 
}
