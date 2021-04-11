package RESTApiJWTAuthMongo;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import RESTApiJWTAuthMongo.model.DiceRoll;
import RESTApiJWTAuthMongo.model.Player;
import RESTApiJWTAuthMongo.repositories.DiceRollRepository;
import RESTApiJWTAuthMongo.repositories.PlayerRepository;

@SpringBootApplication  // (exclude = { SecurityAutoConfiguration.class })
public class M15RestApiJwtAuthMongoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(M15RestApiJwtAuthMongoApplication.class, args);
	}
	
	@Autowired
	private PlayerRepository playerRespository;
	
	@Autowired
	private DiceRollRepository diceRollRespository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Player player1 = new Player ("60705fb882c11d8264de83d2","Jacaranda", "despeinao", 33.33, LocalDateTime.now());
		Player player2 = new Player ("607065a58357de1130bd098b","Munchkin", "lagarto", 50, LocalDateTime.now());
		Player player3 = new Player ("607066788357de1130bd098c","Peter", "fregao", 100, LocalDateTime.now());
		Player player4 = new Player ("607066bf8357de1130bd098d","Petra", "carambota", 0, LocalDateTime.now());
		
		DiceRoll diceRoll1 = new DiceRoll ("6070626f82c11d8264de83d3", 4, 3, "WIN");
		DiceRoll diceRoll2 = new DiceRoll ("607063b08357de1130bd0985", 5, 2, "WIN");
		DiceRoll diceRoll3 = new DiceRoll ("607063bb8357de1130bd0986", 3, 1, "LOSS");
		DiceRoll diceRoll4 = new DiceRoll ("607063df8357de1130bd0987", 2, 6, "LOSS");
		DiceRoll diceRoll5 = new DiceRoll ("607064018357de1130bd0988", 4, 5, "LOSS");
		DiceRoll diceRoll6 = new DiceRoll ("607064178357de1130bd0989", 1, 6, "WIN");
		DiceRoll diceRoll7 = new DiceRoll ("6070642e8357de1130bd098a", 5, 1, "LOSS");
		
		player1.getDiceRolls().add(diceRoll1);
		player1.getDiceRolls().add(diceRoll3);
		player1.getDiceRolls().add(diceRoll5);
		player2.getDiceRolls().add(diceRoll2);
		player2.getDiceRolls().add(diceRoll4);
		player3.getDiceRolls().add(diceRoll6);
		player4.getDiceRolls().add(diceRoll7);
				
		this.playerRespository.save(player1);
		this.playerRespository.save(player2);
		this.playerRespository.save(player3);
		this.playerRespository.save(player4);
		
		this.diceRollRespository.save(diceRoll1);
		this.diceRollRespository.save(diceRoll2);
		this.diceRollRespository.save(diceRoll3);
		this.diceRollRespository.save(diceRoll4);
		this.diceRollRespository.save(diceRoll5);
		this.diceRollRespository.save(diceRoll6);
		this.diceRollRespository.save(diceRoll7);
	}  
}
