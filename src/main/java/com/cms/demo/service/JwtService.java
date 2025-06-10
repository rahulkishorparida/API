package com.cms.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public interface JwtService {


	public String generateToken(String username);

	public Claims extractAllClaims(String token);
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolve);

	public String extractUserName(String token);
	
	public Date extractExpairation(String  token);

	public Boolean validateToken(String token, UserDetails userDetails);
		
		
	

}
