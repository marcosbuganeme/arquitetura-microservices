package dev.go.arquitetura.microservices.auth.service;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.go.arquitetura.microservices.core.model.ApplicationUser;

public class CustomUserDetails extends ApplicationUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	public CustomUserDetails(@NotNull ApplicationUser applicationUser) {
		super(applicationUser);
	}

	public @Override Collection<? extends GrantedAuthority> getAuthorities() {
		return commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
	}

	public @Override boolean isAccountNonExpired() {
		return true;
	}

	public @Override boolean isAccountNonLocked() {
		return true;
	}

	public @Override boolean isCredentialsNonExpired() {
		return true;
	}

	public @Override boolean isEnabled() {
		return true;
	}
}