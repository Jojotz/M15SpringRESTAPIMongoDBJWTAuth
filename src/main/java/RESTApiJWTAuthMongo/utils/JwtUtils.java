package RESTApiJWTAuthMongo.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mongodb.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtils {
	
	private static final String SECRET_KEY = "secret";

	public String generateToken (UserDetails userDetails) {
		
		Map <String, Object> claims = new HashMap<>();
		
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		
		if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			
			claims.put("isAdmin",true);		
		}
		
		if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			
			claims.put("isUser",true);		
		}
		
		if (roles.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			
			claims.put("isAnonymous",true);		
		}		
		
		return createToken(claims, userDetails.getUsername());
	}
	
	public boolean validateToken (String token) { 
		
		try {			
			
			Jws <Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
		
			return true;
		
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		
		} catch (ExpiredJwtException ex) {
			throw ex;
		}
	}
	
	private String createToken (Map <String, Object> claims, String subject) {
		
		Date now = new Date (System.currentTimeMillis());
		Date until = new Date (System.currentTimeMillis() + 1000 * 60 * 60 * 10); // 10 h
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(until)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
		
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
		
	}
	
	public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
		
		List<SimpleGrantedAuthority> roles = null;
		
		Claims claims = extractAllClaims(token);
		Boolean isAdmin = claims.get("isAdmin", Boolean.class);
		Boolean isUser = claims.get("isUser", Boolean.class);
		Boolean isAnonymous = claims.get("isAnonymous", Boolean.class);
		
		if (isAdmin != null && isAdmin == true) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		if (isUser != null && isUser == true) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}
		
		if (isAnonymous != null && isAnonymous == true) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		}
		
		return roles;
	}
	
	private Claims extractAllClaims (String token) {
		
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public String extractUsername (String token) {
		
		return extractClaim (token, Claims :: getSubject);
	}
	
	public Date extractExpiration(String token) {
		
		return extractClaim (token, Claims :: getExpiration);
	}
	
/*	private boolean isTokenExpired (String token) {
		
		return extractExpiration(token).before(new Date()); 
	} */
	
}
