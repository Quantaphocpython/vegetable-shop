package org.ecommerce.spring.boot.vegetable.project.security;

import lombok.Data;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Data
public class CustomUserDetails implements UserDetails {

    private String userName;
    private String password;
    private Boolean isEnabled;
    private List<GrantedAuthority> authorities;

    CustomUserDetails(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.isEnabled = user.getEnabled();
        this.authorities =
                Arrays.stream(user.getRoles().toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return isEnabled;
    }
}
