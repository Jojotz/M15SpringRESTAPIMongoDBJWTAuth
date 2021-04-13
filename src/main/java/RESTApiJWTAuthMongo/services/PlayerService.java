package RESTApiJWTAuthMongo.services;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import RESTApiJWTAuthMongo.model.Player;
import RESTApiJWTAuthMongo.repositories.PlayerRepository;

@Service
public class PlayerService implements UserDetailsService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Player foundedPlayer = playerRepository.findByPlayerName(username).get();
		
		if (foundedPlayer == null) return null;
		
		String playerName = foundedPlayer.getPlayerName();
		String password = foundedPlayer.getPassword();
				
		return new User (playerName, password, emptyList());
	}	
	
}
