package com.major.cookbook.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.major.cookbook.services.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {
	//Validity time in ms (here hh*mm*ss) multiplied by 1000 in generateToken()
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    
    private String secretkey;
    private SecretKey secret;
    
    //Secret key
    public JwtHelper() {
    	try {
		    this.secretkey = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
		    this.secret = Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
    	}catch(Exception e) {
    		throw new RuntimeException("Failed to initialize JwtHelper", e);
    	}
    }
    
    public SecretKey getSecretKey() {
    	return this.secret;
    }
    //retrieve userId from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
    	return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(secret, SignatureAlgorithm.HS512).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    //Generating Token with OTP
    public String generateTokenWithOtp(String username, String otp) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("otp", otp);
        return doGenerateToken(claims, username);
    }
    
    //Generting Token after OTP verification
    public String generateTokenAfterOtp(String username) {
    	Map<String, Object> claims =  new HashMap<>();
    	claims.put("verified", "yes");
    	return doGenerateToken(claims, username);
    }
    
    // Verify OTP from token
    public boolean verifyOtpFromToken(String token, String otp) {
        Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            String tokenOtp = (String) claims.get("otp");
            return tokenOtp.equals(otp); //&& tokenOtp != null
        }
        return false;
    }    
	public String extractOtpFromToken(String token) {
        // Extract the OTP from the token in the Authorization header
	        Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
	        if (claims != null) 
	            return claims.get("otp", String.class);
	        return null;
	}
}
