package RESTApiJWTAuthMongo.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import RESTApiJWTAuthMongo.controllers.PlayerController;
import RESTApiJWTAuthMongo.model.Player;

@Component
public class PlayerModelAssembler implements RepresentationModelAssembler<Player, EntityModel<Player>> {

	@Override
  public EntityModel<Player> toModel(Player player) {

	  return EntityModel.of(player, linkTo(methodOn(PlayerController.class)//
		.onePlayer(player.getPlayerId())).withSelfRel(),linkTo(methodOn(PlayerController.class).all()).withRel("players"));
  }
  
}
