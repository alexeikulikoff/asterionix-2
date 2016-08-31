package com.asterionix.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.asterionix.dao.UserEntity;

public class CustomUserDetails extends UserEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomUserDetails(UserEntity user) {
		
		super(user);
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return AuthorityUtils.createAuthorityList("ROLE_USER");
	}

	public String getUsername() {
		
		return getFirstname();
	}

	public boolean isAccountNonExpired() {
		
		return true;
	}

	public boolean isAccountNonLocked() {
		
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	
}



