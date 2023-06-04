package com.tsafix.securityservice.web;


import com.tsafix.securityservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthRestController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/getmessage")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Map<String, Object>getMessage(){
        return Map.of("message","OKAY");
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/addMessage")
    public Map<String, String> addMessage(String message){

        return Map.of("message",message);

    }

    /**
     * Méthode permettant de generer le token (jwt)
     */

    @PostMapping("/token")
    public Map<String, String> token(Authentication authentication){
        String username = authentication.getName(); //On recupère l'utilisateur

        return authenticationService.generateToken(username);

    }
}
