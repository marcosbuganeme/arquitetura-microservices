package dev.go.arquitetura.microservices.core.security;

public final class Header {

	private String name = "Authorization";
	private String prefix = "Bearer ";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}