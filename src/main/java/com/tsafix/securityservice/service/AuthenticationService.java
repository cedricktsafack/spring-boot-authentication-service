package com.tsafix.securityservice.service;

import java.util.Map;

public interface AuthenticationService {
    Map<String, String> generateToken(String username);
}
