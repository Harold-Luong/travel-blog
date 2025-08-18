package com.travel.blog.security.jwt;

import com.travel.blog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }
    public String getBio() {
        return user.getBio();
    }

    public Long getId() {
        return user.getId();
    }
    public String getEmail() {
        return user.getEmail();
    }
    public String getAvatar() {
        return user.getAvatarUrl();
    }
    public String getGivenName() {
        return user.getGivenName();
    }
    public String getFamilyName() {
        return user.getFamilyName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // nếu có role, map vào đây
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !user.getIsDeleted();
    }
}
