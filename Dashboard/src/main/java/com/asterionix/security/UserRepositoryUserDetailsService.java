package com.asterionix.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asterionix.dao.UserEntity;
import com.asterionix.dao.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Autowired
	public UserRepositoryUserDetailsService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername
	 * (java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	//	UserEntity user = this.userRepository.findByEmail(username);
		UserEntity user = this.userRepository.findByFirstname(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		return new CustomUserDetails(user);
	}
/*	private final static class CustomUserDetails extends UserEntity implements UserDetails {

	
		private static final long serialVersionUID = 1L;

		private CustomUserDetails(UserEntity user) {
			
			super(user);
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}

		public String getUsername() {
			
			return getEmail();
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
	*/

}
