package RESTApiJWTAuthMongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import RESTApiJWTAuthMongo.model.DiceRoll;

//@RepositoryRestResource (collectionResourceRel = "diceRoll", path = "diceRoll")
@Repository
public interface DiceRollRepository extends MongoRepository<DiceRoll, String> {
	
	//List<DiceRoll> findDiceRollsByPlayer (Player player);

}