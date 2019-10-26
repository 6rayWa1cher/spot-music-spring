package com.a6raywa1cher.spotmusicspring.config.security;

import com.a6raywa1cher.spotmusicspring.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {
	private final TokenService jwtTokenUtil;

	public TokenFilter(TokenService tokenService) {
		this.jwtTokenUtil = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		String header = httpServletRequest.getHeader("jwt");

		if (header == null) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);        // If not valid, go to the next filter.
			return;
		}

		User user = jwtTokenUtil.checkToken(header);

		if (user == null) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);        // If not valid, go to the next filter.
			return;
		}

		TokenAuthentication tokenAuthentication = new TokenAuthentication(user);
		tokenAuthentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
