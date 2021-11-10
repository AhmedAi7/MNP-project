package com.example.demo.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

//Customize Authentication Provider
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String customToken = (String) authentication.getPrincipal();
        // Custom logic to validate the token
        return getValidationToken(customToken);
    }

    private Authentication getValidationToken(String customToken) throws AuthenticationException{
        // Check validity of token
        boolean isValid = true;
        if (isValid)
            return new PreAuthenticatedAuthenticationToken(customToken, "ROLE_ADMIN");
        else
            throw new AccessDeniedException("Invalid authetication token");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        // use inbuilt token class for simplicity
        return PreAuthenticatedAuthenticationToken.class.equals(authentication);
    }
}
