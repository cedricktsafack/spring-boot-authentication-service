package com.tsafix.securityservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private JwtEncoder  jwtEncoder;
    private UserDetailsService  userDetailsService;

    public AuthenticationServiceImpl(JwtEncoder jwtEncoder, UserDetailsService userDetailsService) {
        this.jwtEncoder = jwtEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Map<String, String> generateToken(String username) {

        Map<String, String> idToken = new HashMap<>();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Instant instant = Instant.now();

        String scope = userDetails
                .getAuthorities().stream().map(auth->auth.getAuthority())
                .collect(Collectors.joining(" ")); //On recupère tous les rôles de l'utilisateur

        //On construit le payload du token
      JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
              .subject(username)
              .issuer("http://licalhost:8888/auth-service") //l'addresse web de l'application
              .expiresAt(instant.plus(5, ChronoUnit.MINUTES)) //le token expire après 5 minutes
              .issuedAt(instant) //la date à laquelle le token a été générer
              .claim("scope",scope) //On met les rôles de l'utilisateur
              .build();

      String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
      idToken.put("access-token", jwtAccessToken);

      return idToken;
    }
}
