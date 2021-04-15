package RESTApiJWTAuthMongo.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//import RESTApiJWTAuthMongo.services.PlayerService;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtils;
		
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 try {
			 String authorizationHeader = request.getHeader("Authorization");
			 String userName = null;
			 String jwtToken = null;
				
			 if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				
				 jwtToken = authorizationHeader.substring(7);
				 userName = jwtUtils.extractUsername(jwtToken);	
			 }
			
			 if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
								
				 boolean tokenValidated = jwtUtils.validateToken(jwtToken); 
				
				 if (tokenValidated) {
					
					 UserDetails userDetails = new User (jwtUtils.extractUsername(jwtToken), "",
							jwtUtils.getRolesFromToken(jwtToken));
					
					 UsernamePasswordAuthenticationToken	usernamePasswordAuthenticationToken = 
							new UsernamePasswordAuthenticationToken
								(userDetails, null, userDetails.getAuthorities());
					
					 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				 } else {				
					 System.out.println("Cannot set the Security Context");
				 } 
			 } 
			 
		 } catch(ExpiredJwtException ex) {
			 	request.setAttribute("exception", ex);
		
		 } catch(BadCredentialsException ex) {
			 	request.setAttribute("exception", ex);
		 }
			
		 filterChain.doFilter(request, response);
	}
}
