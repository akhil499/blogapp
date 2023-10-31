package com.blogapp.blogapp.security;

import com.blogapp.blogapp.users.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthentication implements Authentication {
    String jwt;
    UserEntity userEntity;

    public JWTAuthentication(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the credentials of the {@code authentication} request.
     * For eg. the password or the Bearer token, or the cookie.
     *
     * @return
     */
    @Override
    public String getCredentials() {
        return jwt;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * Returns the principal of the {@code Authentication} request.
     * The "principal" is the entity that is being authenticated.
     * In this case it is the user.
     * @return
     */
    @Override
    public UserEntity getPrincipal() {
        return userEntity;
    }

    @Override
    public boolean isAuthenticated() {
        return (userEntity != null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
