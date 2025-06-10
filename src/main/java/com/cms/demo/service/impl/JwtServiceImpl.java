package com.cms.demo.service.impl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cms.demo.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getKey())
                .compact();
    }

//    private Key getKey() {
//    	
//    	try {
//    		KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//    		SecretKey sk = keyGen .generateKey();
//    		Base64.getEncoder().encodeToString(sk.getEncoded());
//    		
//    	}catch (Exception e) {
//			e.printStackTrace();
//		}
//    	  
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
    
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    @Override
    public Claims extractAllClaims(String token) {
        Claims claims= Jwts.parserBuilder()
                .setSigningKey(getKey()) // getKey() decodes and returns the correct Key
                .build()
                .parseClaimsJws(token)   // parses a signed JWT
                .getBody();
        return claims;
    }
    	
	
	@Override
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Object decryptkey(String secretKey2) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
		
	}
	
	@Override
	public String extractUserName(String token) {
	return	extractClaim(token, Claims::getSubject);
		
	}

	@Override
	public Date extractExpairation(String token) {
		return extractClaim(token,Claims::getExpiration);
		//method reference
		
		//token validation
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
	String userName = extractUserName(token);
	Boolean isexpired=  isTokenExpaired(token);
	if(userName.equals(userDetails.getUsername()) && !isexpired) {
		return true;
	}
	return false;	
	}

	private Boolean isTokenExpaired(String token) {
		Date expairationDate = extractExpairation(token);
		return expairationDate.before(new Date());
	}




    

    
    


}
