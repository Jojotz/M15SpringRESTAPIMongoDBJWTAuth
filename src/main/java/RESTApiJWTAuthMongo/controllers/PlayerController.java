package RESTApiJWTAuthMongo.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import RESTApiJWTAuthMongo.model.DiceRoll;
import RESTApiJWTAuthMongo.model.Player;
import RESTApiJWTAuthMongo.model.Ranking;
import RESTApiJWTAuthMongo.repositories.DiceRollRepository;
import RESTApiJWTAuthMongo.repositories.PlayerRepository;
import RESTApiJWTAuthMongo.exceptions.DiceRollNotFoundException;
import RESTApiJWTAuthMongo.exceptions.PlayerNotFoundException;
import RESTApiJWTAuthMongo.dto.DiceRollModelAssembler;
import RESTApiJWTAuthMongo.dto.PlayerModelAssembler;
import RESTApiJWTAuthMongo.dto.RankingModelAssembler;

@RestController
public class PlayerController {
	
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private PlayerModelAssembler playerAssembler;
	
	@Autowired
	private DiceRollRepository diceRollRepository;
	@Autowired
	private DiceRollModelAssembler diceRollAssembler;
	
	@Autowired
	private RankingModelAssembler rankingAssembler;
		
	//Gets all players
	@GetMapping("/players/")
	public CollectionModel<EntityModel<Player>> all() {

		List<EntityModel<Player>> players = playerRepository.findAll().stream()
				.map(playerAssembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(players, linkTo(methodOn(PlayerController.class).all()).withSelfRel());
	}
	
	//Gets a player by id
	@GetMapping("/players/{playerId}") 
	public EntityModel<Player> onePlayer(@PathVariable String playerId) {

		Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));

		return playerAssembler.toModel(player);
	}
		
	//Gets all dicerolls from a player by id
	@GetMapping(value = "/players/{playerId}/games", produces = { "application/hal+json" })
	public CollectionModel<DiceRoll> getAllDiceThrows(@PathVariable(name = "playerId") String playerId) {
	    		
		List<DiceRoll> dicerolls = playerRepository.findById(playerId).orElseThrow(() 
				-> new PlayerNotFoundException(playerId)).getDiceRolls();
	    Link link = linkTo(methodOn(PlayerController.class).getAllDiceThrows(playerId)).withSelfRel();
	    
	    if (dicerolls.isEmpty()) {
	    	throw new DiceRollNotFoundException(playerId);	    	
	    } else {
	    	
		    CollectionModel<DiceRoll> result = CollectionModel.of(dicerolls, link);
		    
		    return result;
	    }
	}
	
	//Creates a player
	@PostMapping(path="/players", consumes = "application/json")  // consumes=MediaType.APPLICATION_JSON_VALUE
	public ResponseEntity<?> newPlayer(@RequestBody Player newPlayer) {
	
		if (newPlayer.getPlayerName() == null || newPlayer.getPlayerName() == "") {
			newPlayer.setPlayerName("ANÒNIM");

		} else if (playerRepository.findByPlayerName(newPlayer.getPlayerName()).isPresent()) {
			
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Problem.create()
					.withTitle("Please introduce a different name.")
					.withDetail("There is an existing player with that name."));
		}
			
		EntityModel<Player> entityModel = playerAssembler.toModel(playerRepository.insert(newPlayer));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	  
	}
	
	//Creates a diceroll
	@PostMapping(path="/players/{playerId}/games") 
	public ResponseEntity<EntityModel<DiceRoll>> newThrow(@PathVariable(name = "playerId") String playerId) {
				
		Player playerThrowing= playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
		DiceRoll newDiceRoll = new DiceRoll (playerThrowing);
		playerThrowing.getDiceRolls().add(newDiceRoll);
		playerThrowing.setWinRate(playerThrowing.calculateWinRate(playerThrowing));
			
		EntityModel<DiceRoll> entityModel = diceRollAssembler.toModel(diceRollRepository.save(newDiceRoll));
		playerRepository.save(playerThrowing);
		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	} 
	
	//Updates a player
	@PutMapping("/players/{playerId}")
	public ResponseEntity<?> updatePlayer(@RequestBody Player newPlayer, @PathVariable String playerId) {

		Player updatedPlayer = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
		
		if (playerRepository.findByPlayerName(newPlayer.getPlayerName()).isPresent()) {
				
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Problem.create()
		                        .withTitle("Please introduce a different name.")
		                        .withDetail("There is an existing player with that name."));
		} else {
			
			updatedPlayer.setPlayerName(newPlayer.getPlayerName());    
			playerRepository.save(updatedPlayer);
			
			EntityModel<Player> entityModel = playerAssembler.toModel(updatedPlayer);

		    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		}
	}
	
	//Deletes all dicerolls from a player by id
	@DeleteMapping("/players/{playerId}/games")
	public ResponseEntity<?> deleteDiceRolls(@PathVariable String playerId) {
		
		Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
		
		List <DiceRoll> diceRolls = player.getDiceRolls();
		playerRepository.findById(playerId).get().getDiceRolls().removeAll(diceRolls);
		//diceRollRepository.delete((DiceRoll) diceRolls);
		
		player.setWinRate(0.0);
		playerRepository.save(player);
		
		return ResponseEntity.noContent().build();
	}
	
	//Deletes a player by id
	@DeleteMapping("/players/{playerId}")
	public ResponseEntity<?> deletePlayer(@PathVariable String playerId) {

		playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
		playerRepository.deleteById(playerId);

		return ResponseEntity.noContent().build();
	}
	
	//Gets average WinRate for all the players
	@GetMapping("/players/ranking") 
	public ResponseEntity<EntityModel<Ranking>> averageWinRate() {
		
		double diceRolls = (double) playerRepository.findAll().stream().count();
		double winRatessum = playerRepository.findAll().stream()
				.collect(Collectors.summingDouble(Player::getWinRate)); 
		
		Ranking avRanking = new Ranking(winRatessum/diceRolls);
		
		EntityModel<Ranking> entityModel = rankingAssembler.toModel(avRanking);		
		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri()).body(entityModel);			
	}
	
	//Gets the player with the worst WinRate
	@GetMapping("/players/ranking/loser") 
	public EntityModel<Player> loserPlayer() {
		
		Player loserPlayer = playerRepository.findAll().stream()
				.min(Comparator.comparing(Player::getWinRate)).get();	
	
		return playerAssembler.toModel(loserPlayer);	
		
	}
	
	//Gets the player with the best WinRate
	@GetMapping("/players/ranking/winner") 
	public EntityModel<Player> winnerPlayer() {
		
		Player winnerPlayer = playerRepository.findAll().stream()
				.max(Comparator.comparing(Player::getWinRate)).get();	
	
		return playerAssembler.toModel(winnerPlayer);	
		
	}
	
}
