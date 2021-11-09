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

//Customize an Authentication Filter to be suitable for the project
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    IOperatorService operatorService;

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class); //initialize a static logger

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            //get the request header named "organization" that will carry the operator name
            String operatorHeader= request.getHeader("organization");
            boolean isValid=operatorService.isExist(operatorHeader); //check if the operator header exists in Operator table (Valid)
            if(isValid){
                //create authenticated token and save it in security holder
                PreAuthenticatedAuthenticationToken authentication=
                        new PreAuthenticatedAuthenticationToken(operatorHeader, "ROLE_ADMIN");

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){ // operator header is not valid
            logger.error("Cannot set user authentication: ", e);
        }
        filterChain.doFilter(request, response);
    }
}
