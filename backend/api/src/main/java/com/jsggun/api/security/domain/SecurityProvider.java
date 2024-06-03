package com.jsggun.api.security.domain;


import com.jsggun.api.user.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Component
@Slf4j
public class SecurityProvider implements AuthenticationProvider{

    private final UserDetailsServiceImpl detailsServiceImpl;

    @Value("${security.jwt.token.security-key:secret-key}")
    private String securityKey;

    @Value("${security.jwt.token.expiration-length:3600000}")
    private long validityInMs = 3600000; // 1h

    @PostConstruct  // 인증이 끝나고 실행
    protected  void init(){
        securityKey = Base64.getEncoder().encodeToString(securityKey.getBytes());
        log.info("securityKey : {}",securityKey);
    }

    public String createToken(String username, List<Role> roles){
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(securityKey)))
                .subject(username)
                .issuedAt(new Date(new Date().getTime()))
                .expiration(new Date(new Date().getTime()+validityInMs))
                .compact();
    }


    public Authentication getAuthenticate(String token) throws AuthenticationException {
        UserDetails details = detailsServiceImpl.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(details.getAuthorities(), "", details.getAuthorities());
    }
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(securityKey.getBytes())).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(securityKey.getBytes())).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new Exception();
        }
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        throw new UnsupportedOperationException("Unimplemented method 'supports'");
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }


}
