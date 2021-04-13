package RESTApiJWTAuthMongo.security;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import RESTApiJWTAuthMongo.model.Player;
import RESTApiJWTAuthMongo.repositories.PlayerRepository;
import RESTApiJWTAuthMongo.services.PlayerService;
import RESTApiJWTAuthMongo.utils.JwtUtils;

@RestController
public class AuthController {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private AuthenticationManager authenticatorManager;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping ("/subs")
	private ResponseEntity<?> subscribePlayer (@RequestBody AuthenticationRequest authenticationRequest) {
		
		String playerName = authenticationRequest.gePlayerName();
		String password = authenticationRequest.getPassword();
		Player player = new Player (playerName, password, LocalDateTime.now());
				
		try {
		
			playerRepository.save(player);
		
		} catch (Exception e) {
			
			return ResponseEntity.ok(new AuthenticationResponse("Error during player subcription: " + playerName));
		}
		
		return ResponseEntity.ok(new AuthenticationResponse("Successfull subscription for player: " + playerName));
	}
	
	@PostMapping ("/auth")
	private ResponseEntity<?> authenticatePlayer (@RequestBody AuthenticationRequest authenticationRequest){
		
		String playerName = authenticationRequest.gePlayerName();
		String password = authenticationRequest.getPassword();
		try {
			
			authenticatorManager.authenticate(new UsernamePasswordAuthenticationToken(playerName, password));
		
		} catch (Exception e) {
			
			return ResponseEntity.ok(new AuthenticationResponse("Error during player Authentication: " + playerName));
		}	
		
		UserDetails loadedUser = playerService.loadUserByUsername(playerName);
		
		String generatedToken = jwtUtils.generateToken(loadedUser);
		
		return ResponseEntity.ok(new AuthenticationResponse(generatedToken));
		
	}
}
