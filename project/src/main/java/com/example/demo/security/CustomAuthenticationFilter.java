package com.example.demo.security;

import com.example.demo.interfaces.IOperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    IOperatorService operatorService;

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String operatorHeader= request.getHeader("organization");
            if(operatorService.isExist(operatorHeader)){
                PreAuthenticatedAuthenticationToken authentication=
                        new PreAuthenticatedAuthenticationToken(operatorHeader, "ROLE_ADMIN");

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            logger.error("Cannot set user authentication: ", e);
        }
        filterChain.doFilter(request, response);
    }
}
