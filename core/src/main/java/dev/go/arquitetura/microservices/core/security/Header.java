package dev.go.arquitetura.microservices.core.security;

import lombok.Getter;

@Getter
public class Header {

	private final String name = "Authorization";
	private final String prefix = "Bearer ";
}