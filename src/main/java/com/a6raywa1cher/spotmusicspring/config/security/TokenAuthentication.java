
package com.a6raywa1cher.spotmusicspring.config.security;

import com.a6raywa1cher.spotmusicspring.models.User;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Data
public class TokenAuthentication implements Authentication {
	private boolean isAuthenticated;
	private User user;

	public TokenAuthentication(User user) {
		this.user = user;
		this.isAuthenticated = true;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public void setAuthenticated(boolean b) throws IllegalArgumentException {

	}

	@Override
	public String getName() {
		return null;
	}
}
