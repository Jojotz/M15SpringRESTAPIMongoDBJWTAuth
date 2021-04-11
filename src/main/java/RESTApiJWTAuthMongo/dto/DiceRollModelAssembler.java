package RESTApiJWTAuthMongo.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import RESTApiJWTAuthMongo.controllers.DiceRollController;
import RESTApiJWTAuthMongo.model.DiceRoll;

@Component
public class DiceRollModelAssembler implements RepresentationModelAssembler<DiceRoll, EntityModel<DiceRoll>> {

	public EntityModel<DiceRoll> toModel(DiceRoll diceroll) {
	
		EntityModel<DiceRoll> diceRollModel = EntityModel.of(diceroll,
			linkTo(methodOn(DiceRollController.class).one(diceroll.getDiceRollId())).withSelfRel());
	        //linkTo(methodOn(PlayerController.class).allThrows(playerId)).withRel("dicerolls"));
	
		return diceRollModel;
	}
}
