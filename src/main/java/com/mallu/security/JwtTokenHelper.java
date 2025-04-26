package com.mallu.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper {

	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	
	
	private String SECRET_KEY = "AdwDPZmfA5Y1MEYQyGh3VzPHob8ayC6eWmh95tzyCE1SML4dZ2Ev4WTvqAwMeEDnG8Yv6oxb9o2JtPxZm4GqYA==";// JWT token key
	
	// Get the signing key	
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    // Retrieve username from JWT token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	// Retrieve expiration date from JWT token
	public Date getExpirationDateFromToken(String token) {	
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	 // Retrieve a specific claim from token
	public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver ) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	// for retrieving any information from token we will need the secret key
//	private Claims getAllClaimsFromToken(String token) {
//	    return Jwts.parser()
//	    		.verifyWith((SecretKey) getSigningKey())
//	            .build()
//	            .parseSignedClaims(token)
//	            .getPayload();
//	}
	
	private Claims getAllClaimsFromToken(String token) {
	    return Jwts.parser()
	            .verifyWith(getSigningKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	}

	// check if the token has expired or not
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//generate token for user
	
	public String generateToken(UserDetails userDetails) {
	    Map<String, Object> claims = new HashMap<>();
	    return doGenerateToken(claims, userDetails.getUsername());
	}
	
//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		
//		List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        claims.put("roles", roles);
//		
//		return doGenerateToken(claims, userDetails.getUsername());
//	}
	
	// during creating token

	//1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-j
	// compaction of the JWT to a URL-safe string

	private String doGenerateToken (Map<String, Object> claims, String subject) {

	return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject).
			setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
			.signWith(getSigningKey(), SignatureAlgorithm.HS512)
			.compact();
	}
	
	// validate token..
	 public Boolean validateToken(String token) {
	        return !isTokenExpired(token);
	    }
	 
	 
//	 public List<String> getRolesFromToken(String token) {
//	        Claims claims = getAllClaimsFromToken(token);
//	        return claims.get("roles", List.class);
//	    }

	//validate token
//	public Boolean validateToken(String token, UserDetails userDetails) {
//	final String username = getUsernameFromToken(token);
//	return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
	
}
