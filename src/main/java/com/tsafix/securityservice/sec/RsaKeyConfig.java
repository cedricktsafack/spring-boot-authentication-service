package com.tsafix.securityservice.sec;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeyConfig(RSAPublicKey publickey, RSAPrivateKey privatekey) {

}
