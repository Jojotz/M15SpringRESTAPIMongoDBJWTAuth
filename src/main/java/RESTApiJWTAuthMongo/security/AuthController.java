package RESTApiJWTAuthMongo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import RESTApiJWTAuthMongo.model.Player;
import RESTApiJWTAuthMongo.repositories.PlayerRepository;

@RestController
public class AuthController {
	
	@Autowired
	private PlayerRepository playerRepository;
	

	@Autowired
	private AuthenticationManager authenticatorManager;
	
	@PostMapping ("/subs")
	private ResponseEntity<?> subscribePlayer (@RequestBody AuthenticationRequest authenticationRequest) {
		
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		Player player = new Player ();
		player.setPlayerName(username);
		player.setPassword(password);
		
		try {
		
			playerRepository.save(player);
		
		} catch (Exception e) {
			
			return ResponseEntity.ok(new AuthenticationResponse("Error during player subcription: " + username));
		}
		
		return ResponseEntity.ok(new AuthenticationResponse("Successfull subscription for player: " + username));
	}
	
	@PostMapping ("/auth")
	private ResponseEntity<?> authenticatePlayer (@RequestBody AuthenticationRequest authenticationRequest){
		
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		try {
			
			authenticatorManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		} catch (Exception e) {
			
			return ResponseEntity.ok(new AuthenticationResponse("Error during player Authentication: " + username));
		}		
		
		return ResponseEntity.ok(new AuthenticationResponse("Successfull Authentication for player: " + username));
		
	}
}
