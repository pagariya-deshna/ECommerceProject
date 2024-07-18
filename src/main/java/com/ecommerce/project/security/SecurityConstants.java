package com.ecommerce.project.security;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class SecurityConstants {
	public static final long JWT_EXPIRATION=24*70000;
	public static final String JWT_SECRET="secret";
	
//	  public String extractUsername(String token) {
//	        return extractClaim(token, Claims::getSubject);
//	    }
//	  
//	  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//	        final Claims claims = extractAllClaims(token);
//	        return claimsResolver.apply(claims);
//	    }
//	
//	  private Claims extractAllClaims(String token) {
//	        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
//	    }
//	  
//	   private Boolean isTokenExpired(String token) {
//	        return extractExpiration(token).before(new Date());
//	    }
//	   
//	   public Date extractExpiration(String token) {
//	        return extractClaim(token, Claims::getExpiration);
//	    }
//	  
//	public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

}
