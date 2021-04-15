package RESTApiJWTAuthMongo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List <SimpleGrantedAuthority> roles = null;
		
		if (username.equals("admin")) {			
			roles = Arrays.asList(new SimpleGrantedAuthority ("ROLE_ADMIN"));
			return new User("admin","admin",roles);
		}
		
		if (username.equals("user")) {			
			roles = Arrays.asList(new SimpleGrantedAuthority ("ROLE_USER"));
			return new User("user","user",roles);
		}
		
		if (username.equals("anonymous")) {			
			roles = Arrays.asList(new SimpleGrantedAuthority ("ROLE_ANONYMOUS"));
			return new User("anonymous","anonymous",roles);
		}
		
		throw new UsernameNotFoundException("User not found with the name "+ username);
					
	}	
	
}
