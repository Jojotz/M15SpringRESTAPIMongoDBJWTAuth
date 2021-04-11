package RESTApiJWTAuthMongo.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import RESTApiJWTAuthMongo.model.Player;

//@RepositoryRestResource (collectionResourceRel = "player", path = "player")
@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
	
	@Query("{'player_name': 0}")
	Optional<Player> findByPlayerName(String playerName);	

}
