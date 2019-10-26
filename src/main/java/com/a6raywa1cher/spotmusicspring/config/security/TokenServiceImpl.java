package com.a6raywa1cher.spotmusicspring.config.security;

import com.a6raywa1cher.spotmusicspring.dao.repositories.UserRepository;
import com.a6raywa1cher.spotmusicspring.models.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Calendar;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	UserRepository userService;

	@Value("${app.jwt}")
	private String secret;


	public String getToken(User user) {

		if (user != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 100);
			JwtBuilder jwtBuilder = Jwts.builder();
			jwtBuilder.setExpiration(calendar.getTime());
			jwtBuilder.setId(user.getId().toString());

			byte[] encoded = Base64.getEncoder().encode(secret.getBytes());
			return jwtBuilder.signWith(SignatureAlgorithm.HS256, encoded).compact();
		} else {
			return null;
		}
	}

	public User checkToken(String token) {
		try {
			byte[] encoded = Base64.getEncoder().encode(secret.getBytes());
			return userService.getById(Long.parseLong(Jwts.parser().setSigningKey(encoded).parseClaimsJws(token).getBody().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
