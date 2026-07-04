package com.cherrybite.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cherrybite.enums.UserRole;
import com.cherrybite.util.CurrentUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidator extends OncePerRequestFilter {

	private final JwtProperties jwtProperties;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	public JwtValidator(JwtProperties jwtProperties, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
		this.jwtProperties = jwtProperties;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(jwtProperties.getJwtHeader());

		if (jwt != null) {
			jwt = jwt.substring(7);

			try {
				SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getJwtSecret().getBytes());
				Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

				UUID userId = UUID.fromString(claims.get("userId", String.class));
				UserRole role = UserRole.valueOf(claims.get("role", String.class));
				String username = claims.get("username", String.class);
				
				CurrentUser currentUser = new CurrentUser(
				        userId,
				        username,
				        role
				);

				List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role.name());
				Authentication authentication  = new UsernamePasswordAuthenticationToken(currentUser, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				jwtAuthenticationEntryPoint.commence(request, response,
						new BadCredentialsException("Invalid JWT Token"));
				return;
			}
		}

		filterChain.doFilter(request, response);

	}

}
