package RESTApiJWTAuthMongo.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import RESTApiJWTAuthMongo.controllers.PlayerController;
import RESTApiJWTAuthMongo.model.Ranking;

@Component
public class RankingModelAssembler implements RepresentationModelAssembler<Ranking, EntityModel<Ranking>> {

	@Override
	public EntityModel<Ranking> toModel(Ranking ranking) {

		return EntityModel.of(ranking, linkTo(methodOn(PlayerController.class).averageWinRate()).withSelfRel());
	}
}