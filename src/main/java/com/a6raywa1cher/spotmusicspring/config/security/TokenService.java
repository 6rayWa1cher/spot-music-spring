package com.a6raywa1cher.spotmusicspring.config.security;


import com.a6raywa1cher.spotmusicspring.models.User;

public interface TokenService {
	String getToken(User user);

	User checkToken(String token);
}
