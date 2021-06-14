package com.gustavobussolotti.security.jwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavobussolotti.security.jwt.data.UserData;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse response) throws AuthenticationException {
        try {
            UserData creds = new ObjectMapper().readValue(req.getInputStream(), UserData.class);

            System.out.println("Creds Username: "+creds.getUsername());
            System.out.println("Creds pass: "+creds.getPassword());
            return  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );


        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8)));

        resp.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+ token);


    }
}
