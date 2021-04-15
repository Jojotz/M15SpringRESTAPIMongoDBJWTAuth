package RESTApiJWTAuthMongo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import RESTApiJWTAuthMongo.model.Player;
//import RESTApiJWTAuthMongo.repositories.PlayerRepository;
import RESTApiJWTAuthMongo.services.PlayerService;
import RESTApiJWTAuthMongo.utils.JwtUtils;

@RestController
public class AuthController {
	
//	@Autowired
//	private PlayerRepository playerRepository;
	
	@Autowired
	private AuthenticationManager authenticatorManager;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private JwtUtils jwtUtils;
		
	@PostMapping ("/auth")
	public ResponseEntity<?> authenticatePlayer (@RequestBody AuthenticationRequest authenticationRequest){
		
		String userName = authenticationRequest.getUserName();
		String password = authenticationRequest.getPassword();
		
		try {
			
			authenticatorManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		
		} catch (Exception e) {
			
			return ResponseEntity.ok(new AuthenticationResponse("Error during user Authentication: " + userName));
		}	
		
		UserDetails loadedUser = playerService.loadUserByUsername(userName);
		
		String generatedToken = jwtUtils.generateToken(loadedUser);
		
		return ResponseEntity.ok(new AuthenticationResponse(generatedToken));
		
	}
}
