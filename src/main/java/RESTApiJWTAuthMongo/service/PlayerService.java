package RESTApiJWTAuthMongo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import RESTApiJWTAuthMongo.repositories.PlayerRepository;

@Service
public class PlayerService {
	  
	private PlayerRepository playerRepository;
	
	 public PlayerService(PlayerRepository playerRepository) {
	        this.playerRepository = playerRepository;
	 }
/*	 public Iterable<Player> list() {
	        return playerRepository.findAll();
	 }

    public Iterable<Player> save(List<Player> players) {
        return playerRepository.save(players);
    } */
}
