package com.alex.zanchenko.web.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil { // whenever created club is tied to that specific user
    public static String getSessionUser(){ // we are going to have what's called a Security Context Holder
        // that is going to pull the user information from the cookie
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get the user
        if(!(authentication instanceof UsernamePasswordAuthenticationToken)){ // check to see if the user essentially logged in so if the user is not logged in what is going to happen is that this triger and it will not blow up our software
//        if(authentication!= null){
            String currentUsername = authentication.getName();
            return currentUsername;
        } // and if it does not trigger we will just return null
        return null;
    }
}
